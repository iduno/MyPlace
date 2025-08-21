import React, { useState, useEffect } from 'react';
import { 
  Box, 
  Typography, 
  Grid, 
  Paper, 
  Switch, 
  Button, 
  Slider,
  IconButton,
  Divider,
  useTheme,
  Snackbar,
  Alert,
  ToggleButtonGroup,
  ToggleButton,
  CircularProgress,
  Card,
  ButtonGroup
} from '@mui/material';
import ApiService from '../services/ApiService';
import { styled } from '@mui/system';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import ThermostatIcon from '@mui/icons-material/Thermostat';
import AirIcon from '@mui/icons-material/Air';
import SettingsIcon from '@mui/icons-material/Settings';
import StarIcon from '@mui/icons-material/Star';
import LockIcon from '@mui/icons-material/Lock';
import RefreshIcon from '@mui/icons-material/Refresh';
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined';

// Styled components
const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(2),
  backgroundColor: '#f5f5f5',
  height: '100%',
  borderRadius: 8,
}));

const ZoneButton = styled(Button)(({ theme, selected }) => ({
  margin: theme.spacing(0.5),
  padding: theme.spacing(1.5),
  backgroundColor: selected ? theme.palette.primary.main : '#ffffff',
  color: selected ? '#ffffff' : '#424242',
  fontWeight: 'bold',
  minWidth: '60px',
  '&:hover': {
    backgroundColor: selected ? theme.palette.primary.dark : '#e0e0e0',
  },
  '&.Mui-disabled': {
    backgroundColor: '#f5f5f5',
    color: '#9e9e9e',
  }
}));

const ZoneCard = styled(Card)(({ theme, active }) => ({
  padding: theme.spacing(2),
  backgroundColor: active ? '#ffffff' : '#f5f5f5',
  marginBottom: theme.spacing(2),
  borderLeft: active ? `4px solid ${theme.palette.primary.main}` : '4px solid #e0e0e0',
}));

const ControlButton = styled(Button)(({ theme }) => ({
  margin: theme.spacing(0.5),
  padding: theme.spacing(1),
  backgroundColor: '#ffffff',
  color: '#424242',
  '&:hover': {
    backgroundColor: '#e0e0e0',
  },
}));

const ZoneFragment = () => {
  const theme = useTheme();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');
  const [updating, setUpdating] = useState(false);
  
  // Zone data
  const [zones, setZones] = useState([]);
  const [selectedZoneId, setSelectedZoneId] = useState(null);
  const [systemId, setSystemId] = useState('');
  
  // Fetch zones data on component mount
  useEffect(() => {
    const fetchZonesData = async () => {
      try {
        setLoading(true);
        const data = await ApiService.getZones();
        
        // Validate the response data
        if (data && data.zones && data.zones.length > 0) {
          // Sort zones by zone number for consistent display
          const sortedZones = [...data.zones].sort((a, b) => (a.zoneNumber || 0) - (b.zoneNumber || 0));
          
          // Update state with fetched data
          setZones(sortedZones);
          setSystemId(data.systemId || 'System 1');
          
          // Select first zone by default
          setSelectedZoneId(sortedZones[0].id);
        } else {
          throw new Error('No zones data available');
        }
        
        setLoading(false);
      } catch (err) {
        console.error('Failed to fetch zones data:', err);
        setError('Failed to fetch zones data');
        setLoading(false);
        
        // Use mock data in case of error
        const mockZones = [
          { 
            id: '1', 
            name: 'Living Room', 
            temperature: 24, 
            damperValue: 80, 
            isOpen: true, 
            measuredTemp: 22.5,
            isMaster: true,
            isConstant: false,
            type: 1,
            minDamper: 0,
            maxDamper: 100,
            zoneNumber: 1
          },
          { 
            id: '2', 
            name: 'Bedroom', 
            temperature: 22, 
            damperValue: 60, 
            isOpen: true,
            measuredTemp: 21.3,
            isMaster: false,
            isConstant: false,
            type: 1,
            minDamper: 0,
            maxDamper: 100,
            zoneNumber: 2
          },
          { 
            id: '3', 
            name: 'Kitchen', 
            temperature: 23, 
            damperValue: 70, 
            isOpen: false,
            measuredTemp: 24.1,
            isMaster: false,
            isConstant: true,
            type: 0,
            minDamper: 0,
            maxDamper: 100,
            zoneNumber: 3
          },
          { 
            id: '4', 
            name: 'Bathroom', 
            temperature: 25, 
            damperValue: 50, 
            isOpen: true,
            measuredTemp: 23.8,
            isMaster: false,
            isConstant: false,
            type: 1,
            minDamper: 0,
            maxDamper: 80,
            zoneNumber: 4
          }
        ];
        setZones(mockZones);
        setSystemId('System 1 (Mock)');
        setSelectedZoneId('1');
        
        showSnackbar('Could not connect to server. Using mock data.', 'warning');
      }
    };

    fetchZonesData();
    
    // We've removed the polling as requested
  }, []);
  
  // Fetch just the zone status information without clearing existing data
  const fetchZoneStatus = async () => {
    try {
      setUpdating(true);
      
      // Store a copy of the current zones data in case we need to restore it
      const originalZones = [...zones];
      const originalSelectedId = selectedZoneId;
      
      const data = await ApiService.getZones();
      
      // If we didn't get valid data, abort and keep current data
      if (!data || !data.zones || data.zones.length === 0) {
        showSnackbar('No zone data received from server', 'warning');
        setUpdating(false);
        return;
      }
      
      // Get list of IDs from both current and new data
      const existingIds = zones.map(zone => zone.id);
      const newIds = data.zones.map(zone => zone.id);
      
      // Check if we have completely different zones (possible API inconsistency)
      const hasCommonZones = existingIds.some(id => newIds.includes(id));
      if (zones.length > 0 && !hasCommonZones) {
        console.warn('Received completely different zone IDs from API - may indicate inconsistent data');
      }
      
      // Carefully merge the data - prioritize keeping existing zone structure
      const updatedZones = zones.map(existingZone => {
        // Try to find matching zone in new data
        const newZone = data.zones.find(z => z.id === existingZone.id);
        if (newZone) {
          // Update only specific properties, keeping all other state intact
          return {
            ...existingZone,  // Keep ALL existing properties
            name: newZone.name || existingZone.name,
            isOpen: newZone.isOpen !== undefined ? newZone.isOpen : existingZone.isOpen,
            temperature: newZone.temperature || existingZone.temperature,
            damperValue: newZone.damperValue !== undefined ? newZone.damperValue : existingZone.damperValue,
            measuredTemp: newZone.measuredTemp || existingZone.measuredTemp,
            isMaster: newZone.isMaster !== undefined ? newZone.isMaster : existingZone.isMaster,
            isConstant: newZone.isConstant !== undefined ? newZone.isConstant : existingZone.isConstant,
            type: newZone.type || existingZone.type,
            minDamper: newZone.minDamper || existingZone.minDamper,
            maxDamper: newZone.maxDamper || existingZone.maxDamper,
            zoneNumber: newZone.zoneNumber || existingZone.zoneNumber,
            following: newZone.following || existingZone.following,
            followers: newZone.followers || existingZone.followers
          };
        }
        // If zone doesn't exist in new data, keep the existing one
        return existingZone;
      });
      
      // Add any new zones that didn't exist before
      data.zones.forEach(newZone => {
        if (!existingIds.includes(newZone.id)) {
          updatedZones.push(newZone);
        }
      });
      
      // Only update if we have valid data
      if (updatedZones.length > 0) {
        // Keep the currently selected zone if possible
        if (selectedZoneId) {
          const selectedZoneStillExists = updatedZones.some(z => z.id === selectedZoneId);
          if (!selectedZoneStillExists && updatedZones.length > 0) {
            setSelectedZoneId(updatedZones[0].id);
          }
        } else if (updatedZones.length > 0) {
          setSelectedZoneId(updatedZones[0].id);
        }
        
        // Update system ID if provided
        if (data.systemId) {
          setSystemId(data.systemId);
        }
        
        // Sort zones by number for consistent display
        updatedZones.sort((a, b) => (a.zoneNumber || 0) - (b.zoneNumber || 0));
        
        setZones(updatedZones);
        showSnackbar('Zones refreshed successfully', 'success');
      } else {
        // If somehow we ended up with no zones, restore original data
        setZones(originalZones);
        setSelectedZoneId(originalSelectedId);
        showSnackbar('No zone data available - keeping current view', 'warning');
      }
    } catch (err) {
      console.error('Failed to fetch zone status:', err);
      showSnackbar('Failed to refresh zones', 'error');
    } finally {
      setUpdating(false);
    }
  };

  // Show snackbar message
  const showSnackbar = (message, severity = 'success') => {
    setSnackbarMessage(message);
    setSnackbarSeverity(severity);
    setSnackbarOpen(true);
  };

  // Handle snackbar close
  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  // Update zone data on server
  const updateZoneData = async (zoneId, updates) => {
    try {
      setUpdating(true);
      
      // Find the zone to update
      const zoneToUpdate = zones.find(zone => zone.id === zoneId);
      if (!zoneToUpdate) {
        throw new Error(`Zone with ID ${zoneId} not found`);
      }
      
      // If this update is to set a zone as master, handle that specially
      if (updates.isMaster) {
        // Update all zones to not be master
        const zonesWithUpdatedMaster = zones.map(zone => ({
          ...zone,
          isMaster: zone.id === zoneId // Only the selected zone should be master
        }));
        setZones(zonesWithUpdatedMaster);
        
        // TODO: Add API call to set master zone
        showSnackbar(`${zoneToUpdate.name} set as master zone`);
        setUpdating(false);
        return;
      }
      
      // Create updated zone object
      const updatedZone = {
        ...zoneToUpdate,
        ...updates
      };
      
      const response = await ApiService.updateZone(updatedZone);
      
      // Update local state regardless of server response to provide immediate feedback
      const updatedZones = zones.map(zone => 
        zone.id === zoneId ? { ...zone, ...updates } : zone
      );
      setZones(updatedZones);
      
      if (response && response.success !== false) {
        showSnackbar('Zone settings updated successfully');
      } else {
        showSnackbar('Zone update sent', 'info');
      }
    } catch (err) {
      console.error('Failed to update zone data:', err);
      showSnackbar('Failed to update zone settings', 'error');
      
      // Revert changes in UI if the API call fails
      await fetchZoneStatus();
    } finally {
      setUpdating(false);
    }
  };

  // Handle zone selection
  const handleZoneSelect = (zoneId) => {
    setSelectedZoneId(zoneId);
  };
  
  // Calculate summary data
  const openZones = zones.filter(zone => zone.isOpen).length;
  const totalZones = zones.length;

  // Handle temperature change
  const handleTemperatureChange = (zoneId, direction) => {
    const zone = zones.find(z => z.id === zoneId);
    if (!zone) return;
    
    let newTemp = zone.temperature;
    
    if (direction === 'up' && zone.temperature < 32) {
      newTemp = zone.temperature + 1;
    } else if (direction === 'down' && zone.temperature > 16) {
      newTemp = zone.temperature - 1;
    } else {
      return; // No change needed
    }
    
    // Update server with new temperature
    updateZoneData(zoneId, { temperature: newTemp });
  };

  // Handle damper value change
  const handleDamperValueChange = (zoneId, value) => {
    // Update server with new damper value
    updateZoneData(zoneId, { damperValue: value });
  };

  // Handle zone open/close toggle
  const handleZoneToggle = (zoneId) => {
    const zone = zones.find(z => z.id === zoneId);
    if (!zone) return;
    
    // Update server with new zone state
    updateZoneData(zoneId, { isOpen: !zone.isOpen });
  };

  // Get the selected zone
  const selectedZone = selectedZoneId ? zones.find(zone => zone.id === selectedZoneId) : null;

  return (
    <StyledPaper elevation={1}>
      {loading ? (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
          <Typography variant="h6">Loading zones data...</Typography>
        </Box>
      ) : error ? (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
          <Typography variant="h6" color="error">{error}</Typography>
        </Box>
      ) : (
      <>
        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
          <Typography 
            variant="h5" 
            sx={{ 
              fontWeight: 'medium', 
              color: '#424242',
            }}
          >
            Zones Control
          </Typography>
          
          <Box display="flex" alignItems="center">
            <Typography variant="body2" color="text.secondary">
              {systemId}
            </Typography>
          </Box>
        </Box>
        
        {/* Zone Summary */}
        <Box mb={3} p={2} bgcolor="#f0f7ff" borderRadius={2}>
          <Grid container spacing={2}>
            <Grid item xs={12} md={4}>
              <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                ZONES SUMMARY
              </Typography>
              <Typography variant="h6">
                {zones.filter(z => z.isOpen).length} of {zones.length} Zones Open
              </Typography>
              {zones.find(z => z.isMaster) && (
                <Typography variant="body2" color="text.secondary" mt={1}>
                  Master Zone: {zones.find(z => z.isMaster)?.name || 'None'}
                </Typography>
              )}
              {zones.filter(z => z.isConstant).length > 0 && (
                <Typography variant="body2" color="text.secondary">
                  Constant Zones: {zones.filter(z => z.isConstant).length}
                </Typography>
              )}
            </Grid>
            
            <Grid item xs={12} md={8}>
              <Box 
                sx={{ 
                  display: 'flex', 
                  flexWrap: 'wrap',
                  justifyContent: 'flex-end',
                  gap: 1
                }}
              >
                {zones.map(zone => (
                  <Box 
                    key={zone.id}
                    sx={{
                      width: '40px',
                      height: '40px',
                      borderRadius: '50%',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      fontWeight: 'bold',
                      bgcolor: zone.isOpen ? 'primary.main' : '#e0e0e0',
                      color: zone.isOpen ? 'white' : 'text.secondary',
                      border: zone.isMaster ? '2px solid' : 'none',
                      borderColor: 'secondary.main'
                    }}
                  >
                    {zone.zoneNumber}
                  </Box>
                ))}
              </Box>
            </Grid>
          </Grid>
        </Box>
                
        {/* All Zones List */}
        <Box mb={2}>
          <Typography variant="subtitle1" gutterBottom>
            All Zones
          </Typography>
          
          {zones.map((zone) => (
            <ZoneCard 
              key={zone.id} 
              active={zone.isOpen}
              sx={{ 
                cursor: 'pointer',
                borderLeft: zone.isMaster 
                  ? `4px solid ${theme.palette.secondary.main}`
                  : zone.isConstant
                    ? `4px solid ${theme.palette.warning.main}`
                    : zone.isOpen 
                      ? `4px solid ${theme.palette.primary.main}` 
                      : '4px solid #e0e0e0',
                mb: 2,
              }}
              onClick={() => handleZoneSelect(zone.id)}
            >
              <Grid container spacing={2}>
                {/* Zone Name and Status with Badges */}
                <Grid item xs={12}>
                  <Box display="flex" justifyContent="space-between" alignItems="center">
                    <Box display="flex" alignItems="center">
                      <Box display="flex" alignItems="center">
                        {zone.isMaster && (
                          <StarIcon 
                            color="secondary" 
                            fontSize="small" 
                            sx={{ mr: 1 }}
                          />
                        )}
                        {zone.isConstant && (
                          <LockIcon 
                            color="warning" 
                            fontSize="small" 
                            sx={{ mr: 1 }}
                          />
                        )}
                        <Typography variant="h6">
                          {zone.name}
                        </Typography>
                      </Box>
                      {zone.isMaster && (
                        <Box 
                          component="span" 
                          sx={{ 
                            ml: 1,
                            px: 1,
                            py: 0.5,
                            borderRadius: 1,
                            fontSize: '0.65rem',
                            bgcolor: theme.palette.secondary.main,
                            color: 'white',
                            fontWeight: 'bold',
                            display: 'flex',
                            alignItems: 'center'
                          }}
                        >
                          <StarIcon fontSize="inherit" sx={{ mr: 0.5 }} />
                          MASTER
                        </Box>
                      )}
                      {zone.isConstant && (
                        <Box 
                          component="span" 
                          sx={{ 
                            ml: 1,
                            px: 1,
                            py: 0.5,
                            borderRadius: 1,
                            fontSize: '0.65rem',
                            bgcolor: theme.palette.warning.main,
                            color: 'white',
                            fontWeight: 'bold',
                            display: 'flex',
                            alignItems: 'center'
                          }}
                        >
                          <LockIcon fontSize="inherit" sx={{ mr: 0.5 }} />
                          CONSTANT
                        </Box>
                      )}
                    </Box>
                    <Box display="flex" alignItems="center">
                      <Typography variant="body2" mr={1}>
                        {zone.isOpen ? 'Open' : 'Closed'}
                      </Typography>
                      <Switch
                        checked={zone.isOpen}
                        onChange={() => handleZoneToggle(zone.id)}
                        color="primary"
                      />
                    </Box>
                  </Box>
                </Grid>
                
                {/* Current Temperature Row - Always shown */}
                <Grid item xs={12}>
                  <Box 
                    display="flex" 
                    justifyContent="space-between" 
                    alignItems="center"
                    p={1}
                    bgcolor="#f9f9f9"
                    borderRadius={1}
                  >
                    <Box display="flex" alignItems="center">
                      <ThermostatIcon 
                        color={zone.isOpen ? "primary" : "disabled"} 
                        fontSize="small"
                        sx={{ mr: 1 }}
                      />
                      <Typography variant="body2" color="text.secondary">
                        Current:
                      </Typography>
                      <Typography 
                        variant="body1" 
                        fontWeight="medium"
                        ml={1}
                      >
                        {zone.measuredTemp ? `${zone.measuredTemp}°C` : 'N/A'}
                      </Typography>
                    </Box>
                    
                    <Box display="flex" alignItems="center">
                      <Typography variant="body2" color="text.secondary" mr={1}>
                        Set:
                      </Typography>
                      <Typography 
                        variant="body1" 
                        fontWeight="medium"
                        color={zone.isOpen ? 'primary.main' : 'text.disabled'}
                      >
                        {zone.temperature}°C
                      </Typography>
                    </Box>
                  </Box>
                </Grid>
                
                {/* Temperature Control - Always shown */}
                <Grid item xs={12} md={6}>
                  <Box 
                    p={2} 
                    bgcolor="#ffffff" 
                    borderRadius={2} 
                    boxShadow="0px 2px 4px rgba(0,0,0,0.1)"
                  >
                    <Typography variant="body2" color="textSecondary" align="center" gutterBottom>
                      TEMPERATURE
                    </Typography>
                    <Box display="flex" alignItems="center" justifyContent="center">
                      <IconButton 
                        onClick={() => handleTemperatureChange(zone.id, 'down')}
                        disabled={!zone.isOpen || zone.temperature <= 16}
                        size="large"
                      >
                        <ArrowDownwardIcon />
                      </IconButton>
                      
                      <Typography 
                        variant="h3" 
                        sx={{ 
                          fontWeight: 'bold',
                          mx: 3,
                          color: zone.isOpen ? 'primary.main' : 'text.disabled'
                        }}
                      >
                        {zone.temperature}°
                      </Typography>
                      
                      <IconButton 
                        onClick={() => handleTemperatureChange(zone.id, 'up')}
                        disabled={!zone.isOpen || zone.temperature >= 32}
                        size="large"
                      >
                        <ArrowUpwardIcon />
                      </IconButton>
                    </Box>
                  </Box>
                </Grid>
                
                {/* Damper Control - Always shown */}
                <Grid item xs={12} md={6}>
                  <Box 
                    p={2} 
                    bgcolor="#ffffff" 
                    borderRadius={2} 
                    boxShadow="0px 2px 4px rgba(0,0,0,0.1)"
                  >
                    <Typography variant="body2" color="textSecondary" align="center" gutterBottom>
                      DAMPER SETTING
                    </Typography>
                    <Box px={2}>
                      <Slider
                        value={zone.damperValue}
                        onChange={(e, value) => handleDamperValueChange(zone.id, value)}
                        disabled={!zone.isOpen || zone.isConstant}
                        min={zone.minDamper || 0}
                        max={zone.maxDamper || 100}
                        step={10}
                        marks={[
                          { value: zone.minDamper || 0, label: `${zone.minDamper || 0}%` },
                          { value: zone.maxDamper || 100, label: `${zone.maxDamper || 100}%` }
                        ]}
                        valueLabelDisplay="on"
                        valueLabelFormat={(value) => `${value}%`}
                      />
                    </Box>
                  </Box>
                </Grid>
                
                {/* Zone Actions - Always shown */}
                <Grid item xs={12}>
                  <Box display="flex" justifyContent="space-between" mt={2}>
                    <Box>
                      {zone.isMaster ? (
                        <Typography variant="body2" color="secondary" fontWeight="medium">
                          Master Zone (controls system temperature)
                        </Typography>
                      ) : zone.isConstant ? (
                        <Typography variant="body2" color="warning.dark" fontWeight="medium">
                          Constant Zone (always receives air)
                        </Typography>
                      ) : (
                        <Typography variant="body2" color="textSecondary">
                          {updating ? 'Updating...' : 'Standard zone'}
                        </Typography>
                      )}
                    </Box>
                    
                    <ButtonGroup variant="outlined" size="small">
                      {zone.isMaster ? (
                        <Button 
                          variant="contained"
                          color="secondary"
                          startIcon={<ThermostatIcon />}
                        >
                          Master
                        </Button>
                      ) : (
                        <Button 
                          startIcon={<ThermostatIcon />}
                          disabled={!zone.isOpen || zone.isConstant}
                          onClick={() => updateZoneData(zone.id, { isMaster: true })}
                        >
                          Set as Master
                        </Button>
                      )}
                      <Button 
                        startIcon={<SettingsIcon />}
                        disabled={!zone.isOpen}
                      >
                        Config
                      </Button>
                    </ButtonGroup>
                  </Box>
                </Grid>
              </Grid>
            </ZoneCard>
          ))}
        </Box>

        {/* Zone Summary and Actions */}
        <Box mt={3} p={2} bgcolor="#ffffff" borderRadius={2} boxShadow="0px 2px 4px rgba(0,0,0,0.05)">
          <Grid container spacing={2} alignItems="center">
            <Grid item xs={12} md={8}>
              <Box>
                <Typography variant="body1" gutterBottom>
                  {openZones} of {totalZones} zones open
                </Typography>
                <Typography variant="body2" color="textSecondary" fontSize="0.75rem">
                  Last updated: {new Date().toLocaleTimeString()}
                </Typography>
                {zones.find(z => z.isMaster) && (
                  <Box 
                    mt={1} 
                    p={1} 
                    bgcolor="#f5f5f5" 
                    borderRadius={1} 
                    display="inline-flex" 
                    alignItems="center"
                  >
                    <ThermostatIcon color="secondary" fontSize="small" sx={{ mr: 0.5 }} />
                    <Typography variant="body2" color="secondary.dark">
                      System controlled by {zones.find(z => z.isMaster)?.name || 'Master Zone'}
                    </Typography>
                  </Box>
                )}
              </Box>
            </Grid>
            
            <Grid item xs={12} md={4} sx={{ display: 'flex', justifyContent: 'flex-end' }}>
              <Button 
                variant="contained" 
                onClick={fetchZoneStatus} 
                disabled={updating}
                startIcon={updating ? <CircularProgress size={20} /> : <RefreshIcon />}
                size="large"
              >
                {updating ? 'Updating...' : 'Refresh All Zones'}
              </Button>
            </Grid>
          </Grid>
        </Box>
      </>
      )}
      
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleSnackbarClose}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert 
          onClose={handleSnackbarClose} 
          severity={snackbarSeverity} 
          sx={{ width: '100%' }}
        >
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </StyledPaper>
  );
};

export default ZoneFragment;
