/**
 * API service for communicating with the MyPlace backend
 */
class ApiService {
  constructor() {
    // Base URL of your Quarkus backend
    this.baseUrl = process.env.REACT_APP_API_BASE_URL || '';
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
        zones: data.aircons[airconId]?.zones || {}
      };
    } catch (error) {
      console.error('Error fetching aircon data:', error);
      throw error;
    }
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
      
      return await response.json();
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
        noOfConstants: noOfConstants
      };
    } catch (error) {
      console.error('Error fetching zones data:', error);
      throw error;
    }
  }
  
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
      
      return await response.json();
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
