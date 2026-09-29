/**
 * API service for communicating with the MyPlace backend
 */
class ApiService {
  constructor() {
    // Base URL of your Quarkus backend
    // Prefer Vite environment variable (define in .env files as VITE_API_BASE_URL)
    let envBase = '';
    try {
      if (typeof import.meta !== 'undefined' && import.meta.env && import.meta.env.VITE_API_BASE_URL) {
        envBase = import.meta.env.VITE_API_BASE_URL;
      }
    } catch (_) { /* ignore if not available */ }

    // Fallback logic: if not set, use '/api' during dev (so it hits Vite proxy), '' otherwise
    if (!envBase) {
      const isDev = typeof window !== 'undefined' && window.location && window.location.port === '3000';
      envBase = isDev ? '/api' : '';
    }

    // Normalize (remove trailing slash except root '/')
    if (envBase.length > 1 && envBase.endsWith('/')) {
      envBase = envBase.slice(0, -1);
    }
    this.baseUrl = envBase;

    // Unified system polling internals (single endpoint powers both aircon + zones)
    this._systemCache = null;              // Last raw system data
    this._systemLastError = null;          // Last error
    this._systemPollingHandle = null;      // Interval handle
    this._systemPollingIntervalMs = 2000;  // 2s
    this._systemPollingActive = false;     // Concurrency guard

    // Listener -> airconId (null means first aircon)
    this._airconListeners = new Map();
    this._zoneListeners = new Map();
    this._systemListeners = new Set();
  }

  /**
   * Resolve the aircon id to use; falls back to the first aircon.
   */
  _resolveAirconId(systemData, airconId = null) {
    const ids = this._sortedAirconIds(systemData);
    if (airconId && ids.includes(airconId)) return airconId;
    return ids[0] || 'ac1';
  }

  _sortedAirconIds(systemData) {
    return Object.keys(systemData?.aircons || {})
      .sort((a, b) => a.localeCompare(b, undefined, { numeric: true }));
  }

  /**
   * Summary list of aircon units: [{ id, name, power, mode }]
   */
  getAirconList(systemData = this._systemCache) {
    return this._sortedAirconIds(systemData).map(id => {
      const info = systemData.aircons[id]?.info || {};
      return { id, name: info.name || id, power: info.state === 'on', mode: info.mode };
    });
  }

  /**
   * Lights grouped by myLights.groups (in groupsOrder); ungrouped lights are appended.
   * Returns [] when there are no lights.
   */
  getLightGroups(systemData = this._systemCache) {
    const myLights = systemData?.myLights;
    const lights = myLights?.lights || {};
    const lightIds = Object.keys(lights);
    if (!lightIds.length) return [];

    const used = new Set();
    const groupIds = myLights.groupsOrder?.length ? myLights.groupsOrder : Object.keys(myLights.groups || {});
    const groups = groupIds
      .map(gid => {
        const group = myLights.groups?.[gid];
        if (!group) return null;
        const groupLights = (group.lightsOrder || [])
          .filter(id => lights[id] && !used.has(id))
          .map(id => { used.add(id); return lights[id]; });
        return { id: gid, name: group.name || gid, lights: groupLights };
      })
      .filter(g => g && g.lights.length);

    const ungrouped = lightIds.filter(id => !used.has(id)).map(id => lights[id]);
    if (ungrouped.length) {
      groups.push({ id: '_ungrouped', name: groups.length ? 'Other' : 'Lights', lights: ungrouped });
    }
    return groups;
  }

  getCachedSystem() {
    return this._systemCache;
  }

  /**
   * Subscribe to raw system data updates. Listener receives (rawData, {error, fromCache}).
   */
  subscribeSystem(listener) {
    this._systemListeners.add(listener);
    if (this._systemCache) {
      try { listener(this._systemCache, { error: null, fromCache: true }); } catch (_) { /* ignore */ }
    }
    return () => {
      this._systemListeners.delete(listener);
      this._stopPollingIfIdle();
    };
  }

  _stopPollingIfIdle() {
    if (this._airconListeners.size === 0 && this._zoneListeners.size === 0 && this._systemListeners.size === 0) {
      this.stopSystemPolling();
    }
  }

  /**
   * Update a light (id required; state 'on'/'off' and/or value 0-100)
   */
  async setLight(lightData) {
    try {
      const payload = {
        id: lightData.id,
        ...(lightData.state && { state: lightData.state }),
        ...(lightData.value !== undefined && { value: lightData.value }),
      };
      const response = await fetch(`${this.baseUrl}/setLight`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const result = await response.json();
      this.refreshSystem();
      return result;
    } catch (error) {
      console.error('Error updating light:', error);
      throw error;
    }
  }

  /**
   * Get the current aircon status
   * @returns {Promise<Object>} The aircon data
   */
  async getAircon() {
    try {
      const response = await fetch(`${this.baseUrl}/getSystemData`);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      
      // Extract aircon specific data based on the provided config.json structure
      // Assuming the first aircon in the list is the one we want
      const airconId = Object.keys(data.aircons)[0] || 'ac1';
      const aircon = data.aircons[airconId]?.info || {};
      
      return {
        power: aircon.state === 'on',
        temperature: aircon.setTemp || 24,
        fanSpeed: aircon.fan || 'low',
        timerEnabled: (aircon.countDownToOff > 0 || aircon.countDownToOn > 0),
        timerValue: Math.max(aircon.countDownToOff || 0, aircon.countDownToOn || 0),
        mode: aircon.mode || 'cool',
        systemStatus: aircon.state === 'on' ? 'active' : 'standby',
        airconName: aircon.name || 'AC',
        quietNightModeEnabled: aircon.quietNightModeEnabled || false,
        zones: data.aircons[airconId]?.zones || {},
        _raw: data,
        _fetchedAt: Date.now()
      };
    } catch (error) {
      console.error('Error fetching aircon data:', error);
      throw error;
    }
  }

  /**
   * Return the last cached aircon data (may be null if not yet fetched)
   */
  getCachedAircon(airconId = null) {
    return this._extractAircon(this._systemCache, airconId);
  }

  /**
   * Subscribe to aircon cache updates for one aircon (default: first).
   * Listener receives (data, {error, fromCache}). Returns an unsubscribe function.
   */
  subscribeAircon(listener, airconId = null) {
    this._airconListeners.set(listener, airconId);
    const cached = this.getCachedAircon(airconId);
    if (cached) {
      try { listener(cached, { error: null, fromCache: true }); } catch (_) { /* ignore */ }
    }
    return () => {
      this._airconListeners.delete(listener);
      this._stopPollingIfIdle();
    };
  }

  // --- Unified system polling (backwards-compatible wrappers below) ---
  startSystemPolling() {
    if (this._systemPollingHandle) return;
    this._pollSystemImmediate();
    this._systemPollingHandle = setInterval(() => this._pollSystem(), this._systemPollingIntervalMs);
  }
  refreshSystem() { this._pollSystemImmediate(); }
  stopSystemPolling() {
    if (this._systemPollingHandle) {
      clearInterval(this._systemPollingHandle);
      this._systemPollingHandle = null;
    }
  }
  // Backwards compatible aliases
  startAirconPolling() { this.startSystemPolling(); }
  startZonePolling() { this.startSystemPolling(); }
  refreshAircon() { this.refreshSystem(); }
  refreshZones() { this.refreshSystem(); }
  stopAirconPolling() { this.stopSystemPolling(); }
  stopZonePolling() { this.stopSystemPolling(); }

  async _pollSystem() {
    if (this._systemPollingActive) return;
    this._systemPollingActive = true;
    try {
      const raw = await this._fetchSystemRaw();
      this._systemCache = raw;
      this._systemLastError = null;
      this._notifyListeners(raw, null);
    } catch (err) {
      this._systemLastError = err;
      if (!this._systemCache) { // Only push an error event if nothing loaded yet
        this._notifyListeners(null, err);
      }
    } finally {
      this._systemPollingActive = false;
    }
  }
  _pollSystemImmediate() { this._pollSystem(); }

  _notifyListeners(raw, error) {
    const meta = { error, fromCache: !error };
    this._airconListeners.forEach((airconId, l) => { try { l(error ? null : this._extractAircon(raw, airconId), meta); } catch(_){} });
    this._zoneListeners.forEach((airconId, l) => { try { l(error ? null : this._extractZones(raw, airconId), meta); } catch(_){} });
    this._systemListeners.forEach(l => { try { l(error ? null : raw, meta); } catch(_){} });
  }

  async _fetchSystemRaw() {
    const response = await fetch(`${this.baseUrl}/getSystemData`);
    if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
    const data = await response.json();
    data._fetchedAt = Date.now();
    return data;
  }

  _extractAircon(systemData, requestedId = null) {
    if (!systemData || !systemData.aircons) return null;
    const airconId = this._resolveAirconId(systemData, requestedId);
    const aircon = systemData.aircons[airconId]?.info || {};
    return {
      airconId,
      power: aircon.state === 'on',
      temperature: aircon.setTemp || 24,
      fanSpeed: aircon.fan || 'low',
      timerEnabled: (aircon.countDownToOff > 0 || aircon.countDownToOn > 0),
      timerValue: Math.max(aircon.countDownToOff || 0, aircon.countDownToOn || 0),
      mode: aircon.mode || 'cool',
      systemStatus: aircon.state === 'on' ? 'active' : 'standby',
      airconName: aircon.name || 'AC',
      quietNightModeEnabled: aircon.quietNightModeEnabled || false,
      zones: systemData.aircons[airconId]?.zones || {},
      _raw: systemData,
      _fetchedAt: systemData._fetchedAt
    };
  }
  _extractZones(systemData, requestedId = null) {
    if (!systemData || !systemData.aircons) return null;
    const airconId = this._resolveAirconId(systemData, requestedId);
    const aircon = systemData.aircons[airconId];
    if (!aircon || !aircon.zones) {
      return { airconId, zones: [], systemId: systemData.systemInfo?.id || 'System 1', _fetchedAt: systemData._fetchedAt };
    }
    const masterZoneNumber = aircon.info?.myZone || 1;
    const noOfConstants = aircon.info?.noOfConstants || 0;
    const zonesArray = Object.entries(aircon.zones).map(([id, zoneData]) => {
      const zoneNumber = zoneData.number || parseInt(id.replace('z',''));
      const isMaster = zoneNumber === masterZoneNumber;
      const isConstant = aircon.info?.constant1 == zoneNumber || aircon.info?.constant2 == zoneNumber || aircon.info?.constant3 == zoneNumber;
      return {
        id,
        name: zoneData.name || `Zone ${id}`,
        temperature: zoneData.setTemp || 24,
        measuredTemp: zoneData.measuredTemp,
        damperValue: zoneData.value || 0,
        isOpen: zoneData.state === 'open',
        isMaster,
        isConstant,
        type: zoneData.type,
        minDamper: zoneData.minDamper,
        maxDamper: zoneData.maxDamper,
        following: zoneData.following || 0,
        followers: zoneData.followers || [],
        zoneNumber
      };
    });
    return {
      airconId,
      zones: zonesArray,
      systemId: systemData.system?.name || 'System 1',
      masterZoneNumber,
      noOfConstants,
      _raw: systemData,
      _fetchedAt: systemData._fetchedAt
    };
  }

  /**
   * Update aircon settings
   * @param {Object} airconData - The aircon data to update
   * @param {String} [targetAirconId] - Aircon id (e.g. 'ac2'); defaults to the first aircon
   * @returns {Promise<Object>} The response from the server
   */
  async updateAircon(airconData, targetAirconId = null) {
    try {
      const systemData = this._systemCache || await this.getSystem();
      const airconId = this._resolveAirconId(systemData, targetAirconId);

      // Based on the DataAircon Java model, we need to structure the data correctly
      // with info and zones properties
      const payload = {
        [airconId]: {
          info: {
            // Only include properties that should be updated
            // These match the DataAirconInfo structure
            ...(airconData.mode && { mode: airconData.mode }),
            ...(airconData.state && { state: airconData.state }),
            ...(airconData.fan && { fan: airconData.fan }),
            ...(airconData.setTemp && { setTemp: airconData.setTemp }),
            ...(airconData.myZone && { myZone: airconData.myZone }),
            ...(airconData.noOfZones && { noOfZones: airconData.noOfZones }),
            ...(airconData.myAutoModeEnabled !== undefined && { myAutoModeEnabled: airconData.myAutoModeEnabled }),
            ...(airconData.aaAutoFanModeEnabled !== undefined && { aaAutoFanModeEnabled: airconData.aaAutoFanModeEnabled }),
            ...(airconData.climateControlModeEnabled !== undefined && { climateControlModeEnabled: airconData.climateControlModeEnabled }),
            ...(airconData.quietNightModeEnabled !== undefined && { quietNightModeEnabled: airconData.quietNightModeEnabled }),
            ...(airconData.freshAirStatus && { freshAirStatus: airconData.freshAirStatus }),
            ...(airconData.countDownToOff !== undefined && { countDownToOff: airconData.countDownToOff }),
            ...(airconData.countDownToOn !== undefined && { countDownToOn: airconData.countDownToOn })
            // Allow setting the display name for the aircon and device
            , ...(airconData.name && { name: airconData.name })
            , ...(airconData.deviceName && { deviceName: airconData.deviceName })
            // Constant zones (up to three)
            , ...(airconData.constant1 !== undefined && { constant1: airconData.constant1 })
            , ...(airconData.constant2 !== undefined && { constant2: airconData.constant2 })
            , ...(airconData.constant3 !== undefined && { constant3: airconData.constant3 })
          }
        },
      };
      
      // Log what we're sending for debugging
      console.log('Sending aircon update:', JSON.stringify(payload, null, 2));
      
      const response = await fetch(`${this.baseUrl}/setAircon`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const result = await response.json();
      // Trigger an immediate refresh so cache is up-to-date after mutation
      this.refreshAircon();
      return result;
    } catch (error) {
      console.error('Error updating aircon data:', error);
      throw error;
    }
  }

  /**
   * Get system status
   * @returns {Promise<Object>} The system data
   */
  async getSystem() {
    try {
      const response = await fetch(`${this.baseUrl}/getSystemData`);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return await response.json();
    } catch (error) {
      console.error('Error fetching system data:', error);
      throw error;
    }
  }

  /**
   * Update system settings
   * @param {Object} systemData - The system data to update
   * @returns {Promise<Object>} The response from the server
   */
  async updateSystem(systemData) {
    try {
      const response = await fetch(`${this.baseUrl}/setMySystem`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(systemData),
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      
      const result = await response.json();
      // Trigger an immediate refresh so cache is up-to-date after mutation
      this.refreshSystem();
      return result;
    } catch (error) {
      console.error('Error updating system data:', error);
      throw error;
    }
  }

  /**
   * Get zones information
   * @param {String} [airconId] - Aircon id (e.g. 'ac2'); defaults to the first aircon
   * @returns {Promise<Object>} The zones data
   */
  async getZones(airconId = null) {
    try {
      const data = await this._fetchSystemRaw();
      return this._extractZones(data, airconId) || { zones: [], systemId: 'System 1' };
    } catch (error) {
      console.error('Error fetching zones data:', error);
      throw error;
    }
  }
  
  // Zones cache helpers
  getCachedZones(airconId = null) { return this._extractZones(this._systemCache, airconId); }

  subscribeZones(listener, airconId = null) {
    this._zoneListeners.set(listener, airconId);
    const cached = this.getCachedZones(airconId);
    if (cached) {
      try { listener(cached, { error: null, fromCache: true }); } catch (_) {}
    }
    return () => {
      this._zoneListeners.delete(listener);
      this._stopPollingIfIdle();
    };
  }

  // (Removed duplicate zone polling - handled by unified system polling)
  
  /**
   * Update zone settings
   * @param {Object} zoneData - The zone data to update
   * @param {String} [targetAirconId] - Aircon id (e.g. 'ac2'); defaults to the first aircon
   * @returns {Promise<Object>} The response from the server
   */
  async updateZone(zoneData, targetAirconId = null) {
    try {
      const systemData = this._systemCache || await this.getSystem();
      const airconId = this._resolveAirconId(systemData, targetAirconId);
      
      // Create zone update structure matching the DataZone Java class
      const zoneUpdate = {
        ...(zoneData.isOpen !== undefined && {state: zoneData.isOpen ? 'open' : 'close'}),
        ...(zoneData.temperature !== undefined && {setTemp: zoneData.temperature}),
        ...(zoneData.damperValue !== undefined && {value: zoneData.damperValue}), // This is the damper percentage
        ...(zoneData.name !== undefined && { name: zoneData.name }),
        ...(zoneData.minDamper !== undefined && { minDamper: zoneData.minDamper }),
        ...(zoneData.maxDamper !== undefined && { maxDamper: zoneData.maxDamper }),
        ...(zoneData.following !== undefined && { following: zoneData.following }),
        ...(zoneData.followers && { followers: zoneData.followers }),
        ...(zoneData.motionConfig !== undefined && { motionConfig: zoneData.motionConfig }),
      };
      
      // Create a payload that matches the setAircon structure
      // We're updating a specific zone within the aircon
      const payload = {
        [airconId]: {
          zones: {
            [zoneData.id]: zoneUpdate
          }
        }
      };
      
      // Log what we're sending for debugging
      console.log('Sending zone update:', JSON.stringify(payload, null, 2));
      
      const response = await fetch(`${this.baseUrl}/setAircon`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const result = await response.json();
      // Immediate refresh after mutation
      this.refreshZones();
      return result;
    } catch (error) {
      console.error('Error updating zone data:', error);
      throw error;
    }
  }


  /**
   * Handle common API errors and provide meaningful messages
   * @param {Error} error - The error that occurred
   * @returns {String} A user-friendly error message
   */
  getErrorMessage(error) {
    if (error.message.includes('Failed to fetch') || error.message.includes('NetworkError')) {
      return 'Unable to connect to the server. Please check your network connection.';
    }
    
    if (error.message.includes('404')) {
      return 'The requested resource was not found. Please check the API endpoint.';
    }
    
    if (error.message.includes('401')) {
      return 'Unauthorized access. Please log in again.';
    }
    
    if (error.message.includes('403')) {
      return 'You do not have permission to access this resource.';
    }
    
    if (error.message.includes('500')) {
      return 'Server error. Please try again later or contact support.';
    }
    
    return error.message || 'An unknown error occurred.';
  }
}

export default new ApiService();
