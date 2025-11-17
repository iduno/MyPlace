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

    // Derived caches
    this._airconCache = null;
    this._zoneCache = null;

    // Listener sets (public subscriptions remain stable)
    this._airconListeners = new Set();
    this._zoneListeners = new Set();
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
        zoneName: aircon.name || 'AC',
        energySaving: aircon.quietNightModeEnabled || false,
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
  getCachedAircon() {
    return this._airconCache;
  }

  /**
   * Subscribe to aircon cache updates. Listener receives (data, {error, fromCache})
   * Returns an unsubscribe function.
   */
  subscribeAircon(listener) {
    this._airconListeners.add(listener);
    // Immediately emit current cache if available
    if (this._airconCache) {
      try { listener(this._airconCache, { error: null, fromCache: true }); } catch (_) { /* ignore */ }
    }
    return () => {
      this._airconListeners.delete(listener);
      // If no listeners remain, stop polling to save resources
      if (this._airconListeners.size === 0) {
        this.stopAirconPolling();
      }
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
      // Derive specialized views
      this._airconCache = this._extractAircon(raw);
      this._zoneCache = this._extractZones(raw);
      this._notifyAirconListeners(this._airconCache, null);
      this._notifyZoneListeners(this._zoneCache, null);
    } catch (err) {
      this._systemLastError = err;
      if (!this._systemCache) { // Only push an error event if nothing loaded yet
        this._notifyAirconListeners(null, err);
        this._notifyZoneListeners(null, err);
      }
    } finally {
      this._systemPollingActive = false;
    }
  }
  _pollSystemImmediate() { this._pollSystem(); }

  _notifyAirconListeners(data, error) { this._airconListeners.forEach(l => { try { l(data, { error, fromCache: !error }); } catch(_){} }); }
  _notifyZoneListeners(data, error) { this._zoneListeners.forEach(l => { try { l(data, { error, fromCache: !error }); } catch(_){} }); }

  async _fetchSystemRaw() {
    const response = await fetch(`${this.baseUrl}/getSystemData`);
    if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
    const data = await response.json();
    data._fetchedAt = Date.now();
    return data;
  }

  _extractAircon(systemData) {
    if (!systemData || !systemData.aircons) return null;
    const airconId = Object.keys(systemData.aircons)[0] || 'ac1';
    const aircon = systemData.aircons[airconId]?.info || {};
    return {
      power: aircon.state === 'on',
      temperature: aircon.setTemp || 24,
      fanSpeed: aircon.fan || 'low',
      timerEnabled: (aircon.countDownToOff > 0 || aircon.countDownToOn > 0),
      timerValue: Math.max(aircon.countDownToOff || 0, aircon.countDownToOn || 0),
      mode: aircon.mode || 'cool',
      systemStatus: aircon.state === 'on' ? 'active' : 'standby',
      zoneName: aircon.name || 'AC',
      energySaving: aircon.quietNightModeEnabled || false,
      zones: systemData.aircons[airconId]?.zones || {},
      _raw: systemData,
      _fetchedAt: systemData._fetchedAt
    };
  }
  _extractZones(systemData) {
    if (!systemData || !systemData.aircons) return null;
    const airconId = Object.keys(systemData.aircons)[0] || 'ac1';
    const aircon = systemData.aircons[airconId];
    if (!aircon || !aircon.zones) {
      return { zones: [], systemId: systemData.systemInfo?.id || 'System 1', _fetchedAt: systemData._fetchedAt };
    }
    const masterZoneNumber = aircon.info?.myZone || 1;
    const noOfConstants = aircon.info?.noOfConstants || 0;
    const zonesArray = Object.entries(aircon.zones).map(([id, zoneData]) => {
      const zoneNumber = zoneData.number || parseInt(id.replace('z',''));
      const isMaster = zoneNumber === masterZoneNumber;
      const isConstant = zoneData.type === 0;
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
   * @returns {Promise<Object>} The response from the server
   */
  async updateAircon(airconData) {
    try {
      // Adapt to your backend API structure
      const systemData = await this.getSystem();
      const airconId = Object.keys(systemData.aircons)[0] || 'ac1';

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
            ...(airconData.myAutoModeEnabled !== undefined && { myAutoModeEnabled: airconData.myAutoModeEnabled }),
            ...(airconData.aaAutoFanModeEnabled !== undefined && { aaAutoFanModeEnabled: airconData.aaAutoFanModeEnabled }),
            ...(airconData.climateControlModeEnabled !== undefined && { climateControlModeEnabled: airconData.climateControlModeEnabled }),
            ...(airconData.freshAirStatus && { freshAirStatus: airconData.freshAirStatus }),
            ...(airconData.countDownToOff !== undefined && { countDownToOff: airconData.countDownToOff }),
            ...(airconData.countDownToOn !== undefined && { countDownToOn: airconData.countDownToOn })
            // Allow setting the display name for the aircon and device
            , ...(airconData.name && { name: airconData.name })
            , ...(airconData.deviceName && { deviceName: airconData.deviceName })
          },
          timestamp: new Date().toISOString()
        }
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
      const response = await fetch(`${this.baseUrl}/system`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(systemData),
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      
      return await response.json();
    } catch (error) {
      console.error('Error updating system data:', error);
      throw error;
    }
  }

  /**
   * Get zones information
   * @returns {Promise<Object>} The zones data
   */
  async getZones() {
    try {
      const response = await fetch(`${this.baseUrl}/getSystemData`);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      
      // Extract zones data from the system response
      // Assuming the first aircon in the list is the one we want
      const airconId = Object.keys(data.aircons)[0] || 'ac1';
      const aircon = data.aircons[airconId];
      
      if (!aircon || !aircon.zones) {
        return { zones: [], systemId: data.systemInfo?.id || 'System 1' };
      }
      
      // Get the master zone from aircon info
      const masterZoneNumber = aircon.info?.myZone || 1;
      // Get the number of constant zones
      const noOfConstants = aircon.info?.noOfConstants || 0;
      
      // Convert zones object to array with proper structure
      const zonesArray = Object.entries(aircon.zones).map(([id, zoneData]) => {
        // Check if this is a master zone
        const zoneNumber = zoneData.number || parseInt(id.replace('z', ''));
        const isMaster = zoneNumber === masterZoneNumber;
        
        // Check if this is a constant zone (type will be different)
        const isConstant = zoneData.type === 0; // Assuming type 0 means constant zone
        
        return {
          id,
          name: zoneData.name || `Zone ${id}`,
          temperature: zoneData.setTemp || 24,
          measuredTemp: zoneData.measuredTemp, // Current actual temperature
          damperValue: zoneData.value || 0, // Assuming value is the damper percentage (0-100)
          isOpen: zoneData.state === 'open',
          isMaster: isMaster,
          isConstant: isConstant,
          type: zoneData.type,
          minDamper: zoneData.minDamper,
          maxDamper: zoneData.maxDamper,
          following: zoneData.following || 0,
          followers: zoneData.followers || [],
          zoneNumber: zoneNumber
        };
      });
      
      return {
        zones: zonesArray,
        systemId: data.system?.name || 'System 1',
        masterZoneNumber: masterZoneNumber,
        noOfConstants: noOfConstants,
        _raw: data,
        _fetchedAt: Date.now()
      };
    } catch (error) {
      console.error('Error fetching zones data:', error);
      throw error;
    }
  }
  
  // Zones cache helpers
  getCachedZones() { return this._zoneCache; }

  subscribeZones(listener) {
    this._zoneListeners.add(listener);
    if (this._zoneCache) {
      try { listener(this._zoneCache, { error: null, fromCache: true }); } catch (_) {}
    }
    return () => {
      this._zoneListeners.delete(listener);
      if (this._zoneListeners.size === 0) {
        this.stopZonePolling();
      }
    };
  }

  // (Removed duplicate zone polling – handled by unified system polling)
  
  /**
   * Update zone settings
   * @param {Object} zoneData - The zone data to update
   * @returns {Promise<Object>} The response from the server
   */
  async updateZone(zoneData) {
    try {
      // First get the current system data to identify the aircon ID
      const systemData = await this.getSystem();
      
      // Get the first aircon ID or use default
      const airconId = Object.keys(systemData.aircons)[0] || 'ac1';
      
      // Create zone update structure matching the DataZone Java class
      const zoneUpdate = {
        state: zoneData.isOpen ? 'open' : 'close',
        setTemp: zoneData.temperature,
        value: zoneData.damperValue, // This is the damper percentage
        ...(zoneData.name && { name: zoneData.name }),
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
          },
          timestamp: new Date().toISOString()
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
