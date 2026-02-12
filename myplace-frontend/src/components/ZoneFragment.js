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
// Removed SettingsIcon (config removed per new requirements)
import StarIcon from '@mui/icons-material/Star';
import LockIcon from '@mui/icons-material/Lock';
import RefreshIcon from '@mui/icons-material/Refresh';

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
  padding: theme.spacing(1),
  backgroundColor: active ? '#ffffff' : '#fafafa',
  marginBottom: theme.spacing(1),
  borderLeft: active ? `3px solid ${theme.palette.primary.main}` : '3px solid #e0e0e0',
  display: 'flex',
  alignItems: 'stretch'
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
  
  
  // Subscribe to zone polling cache
  useEffect(() => {
    setLoading(true);
    ApiService.startZonePolling();
    const unsubscribe = ApiService.subscribeZones((data, { error }) => {
      if (error) {
        if (!ApiService.getCachedZones()) {
          // Only show error initially if nothing loaded yet
            setError('Failed to fetch zones data');
            setLoading(false);
        }
        return; // keep old data on errors
      }
      if (!data) return;
      const sorted = [...data.zones].sort((a,b) => (a.zoneNumber||0) - (b.zoneNumber||0));
      setZones(sorted);
      setSystemId(data.systemId || 'System 1');
      if (!selectedZoneId && sorted.length) {
        setSelectedZoneId(sorted[0].id);
      } else if (selectedZoneId && !sorted.find(z => z.id === selectedZoneId) && sorted.length) {
        setSelectedZoneId(sorted[0].id);
      }
      setLoading(false);
      setError(null);
    });
    return () => unsubscribe();
  }, [selectedZoneId]);
  
  // Fetch just the zone status information without clearing existing data
  const fetchZoneStatus = async () => {
    // Manual refresh button now just forces immediate poll
    setUpdating(true);
    ApiService.refreshZones();
    // We rely on subscription callback to update UI; remove spinner after short delay
    setTimeout(() => setUpdating(false), 600);
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
        // Optimistic UI update
        const zonesWithUpdatedMaster = zones.map(zone => ({
          ...zone,
            isMaster: zone.id === zoneId
        }));
        setZones(zonesWithUpdatedMaster);

        try {
          // Send master zone update via setAircon (myZone = zoneNumber)
          await ApiService.updateAircon({ myZone: zoneToUpdate.zoneNumber });
          showSnackbar(`${zoneToUpdate.name} set as master zone`);
        } catch (err) {
          console.error('Failed to set master zone:', err);
          showSnackbar('Failed to set master zone', 'error');
          // Re-sync from server
          await fetchZoneStatus();
        } finally {
          setUpdating(false);
        }
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
  const handleDamperValueChange = (zoneId, direction) => {
    const zone = zones.find(z => z.id === zoneId);
    if (!zone) return;
    
    const minDamper = zone.minDamper || 0;
    const maxDamper = zone.maxDamper || 100;
    const step = 5;
    
    let newValue = zone.damperValue;
    
    if (direction === 'up' && zone.damperValue < maxDamper) {
      newValue = Math.min(zone.damperValue + step, maxDamper);
    } else if (direction === 'down' && zone.damperValue > minDamper) {
      newValue = Math.max(zone.damperValue - step, minDamper);
    } else {
      return; // No change needed
    }
    
    // Update server with new damper value
    updateZoneData(zoneId, { damperValue: newValue });
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
          
        {/* All Zones List */}
        <Box mb={2}>
          <Typography variant="subtitle2" gutterBottom sx={{ fontWeight: 'bold', letterSpacing: '.5px' }}>
            ZONES
          </Typography>
          
          <Grid container spacing={0.5} direction="column" >
            {zones.map(zone => (
              <Grid item xs={12} key={zone.id}>
                <ZoneCard
                  active={zone.isOpen}
                  sx={{
                    borderLeft: zone.isMaster
                      ? `3px solid ${theme.palette.secondary.main}`
                      : zone.isConstant
                        ? `3px solid ${theme.palette.warning.main}`
                        : zone.isOpen
                          ? `3px solid ${theme.palette.primary.main}`
                          : '3px solid #e0e0e0'
                  }}
                >
                  <Box display="flex" alignItems="center" justifyContent="space-between" mb={0.5}>
                    <Box display="flex" alignItems="center" sx={{ minWidth: 0 }}>
                      <Typography
                        variant="subtitle2"
                        sx={{
                          fontWeight: zone.isMaster ? 'bold' : 500,
                          cursor: 'pointer',
                          textOverflow: 'ellipsis',
                          overflow: 'hidden',
                          whiteSpace: 'nowrap',
                          '&:hover': { textDecoration: !zone.isMaster ? 'underline' : 'none' }
                        }}
                        title={zone.name + (zone.isMaster ? ' (Master)' : '')}
                        onClick={() => {
                          if (!zone.isMaster) updateZoneData(zone.id, { isMaster: true });
                        }}
                      >
                          <Box
                            display="flex"
                            alignItems="center"
                            sx={{
                              flexBasis: { xs: '30%', sm: '25%', md: '20%', lg: '15%' },
                              minWidth: { xs: '90px', sm: '90px' },
                            }}
                          >
                            {zone.isMaster && <StarIcon color="secondary" fontSize="small" sx={{ mr: 0.5 }} />}
                            {zone.isConstant && <LockIcon color="warning" fontSize="small" sx={{ mr: 0.5 }} />}
                            <span style={{ display: 'inline-block', flex: 1, textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>{zone.name}</span>
                          </Box>
                      </Typography>
                    </Box>
                    <Switch
                      size="small"
                      checked={zone.isOpen}
                      onChange={() => handleZoneToggle(zone.id)}
                      color="primary"
                    />
                  </Box>
                  <Box mb={0.5}>
                    <Typography variant="caption" color="text.secondary">{zone.measuredTemp ? `${zone.measuredTemp}°` : 'N/A'}</Typography>
                    <Typography variant="caption" color="text.secondary" display="block">{zone.damperValue}%</Typography>
                  </Box>
                  {zone.type === 0 ? (
                    <Box display="flex" alignItems="center" justifyContent="space-between" mb={0.5}>
                      <IconButton size="small" onClick={() => handleDamperValueChange(zone.id, 'down')} disabled={zone.damperValue <= (zone.minDamper || 0)}>
                        <ArrowDownwardIcon fontSize="inherit" />
                      </IconButton>
                      <Typography variant="body1" sx={{ mx: 0.5, fontWeight: 'bold' }}>{zone.damperValue}%</Typography>
                      <IconButton size="small" onClick={() => handleDamperValueChange(zone.id, 'up')} disabled={zone.damperValue >= (zone.maxDamper || 100)}>
                        <ArrowUpwardIcon fontSize="inherit" />
                      </IconButton>
                    </Box>
                  ) : (
                    <Box display="flex" alignItems="center" justifyContent="space-between" mb={0.5}>
                      <IconButton size="small" onClick={() => handleTemperatureChange(zone.id, 'down')} disabled={zone.temperature <= 16}>
                        <ArrowDownwardIcon fontSize="inherit" />
                      </IconButton>
                      <Typography variant="h6" sx={{ mx: 0.5, fontWeight: 'bold', color: 'primary.main' }}>{zone.temperature}°</Typography>
                      <IconButton size="small" onClick={() => handleTemperatureChange(zone.id, 'up')} disabled={zone.temperature >= 32}>
                        <ArrowUpwardIcon fontSize="inherit" />
                      </IconButton>
                    </Box>
                  )}
                  <Typography variant="caption" color="text.secondary" sx={{ display:'block', mt: 0.5, textAlign:'right', ml:'auto' }}>
                    {zone.isMaster ? 'Master zone' : zone.isConstant ? 'Constant zone' : zone.isOpen ? 'Open' : 'Closed'}
                  </Typography>
                </ZoneCard>
              </Grid>
            ))}
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
