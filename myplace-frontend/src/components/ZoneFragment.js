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
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import TextField from '@mui/material/TextField';

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
  const [editingZoneId, setEditingZoneId] = useState(null);
  const [editingZoneName, setEditingZoneName] = useState('');
  
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
                      {zone.isMaster && <StarIcon color="secondary" fontSize="small" sx={{ mr: 0.5 }} />}
                      {zone.isConstant && <LockIcon color="warning" fontSize="small" sx={{ mr: 0.5 }} />}
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
                        {editingZoneId === zone.id ? (
                          <Box display="flex" alignItems="center" sx={{ width: '100%' }}>
                            <TextField
                              size="small"
                              value={editingZoneName}
                              onChange={(e) => setEditingZoneName(e.target.value)}
                              onKeyDown={(e) => {
                                if (e.key === 'Enter') {
                                  // submit
                                  updateZoneData(zone.id, { name: editingZoneName });
                                  setEditingZoneId(null);
                                } else if (e.key === 'Escape') {
                                  setEditingZoneId(null);
                                }
                              }}
                              sx={{ mr: 1, flex: 1 }}
                            />
                            <IconButton size="small" onClick={() => { updateZoneData(zone.id, { name: editingZoneName }); setEditingZoneId(null); }}>
                              <CheckIcon fontSize="small" />
                            </IconButton>
                            <IconButton size="small" onClick={() => { setEditingZoneId(null); }}>
                              <CloseIcon fontSize="small" />
                            </IconButton>
                          </Box>
                        ) : (
                          <Box display="flex" alignItems="center">
                            <span style={{ display: 'inline-block', maxWidth: 220, textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>{zone.name}</span>
                            {!zone.isMaster && (
                              <IconButton size="small" onClick={(e) => { e.stopPropagation(); setEditingZoneId(zone.id); setEditingZoneName(zone.name); }} sx={{ ml: 1 }}>
                                <EditIcon fontSize="small" />
                              </IconButton>
                            )}
                          </Box>
                        )}
                      </Typography>
                    </Box>
                    <Switch
                      size="small"
                      checked={zone.isOpen}
                      onChange={() => handleZoneToggle(zone.id)}
                      color="primary"
                    />
                  </Box>
                  <Box display="flex" justifyContent="space-between" alignItems="center" mb={0.5}>
                    <Typography variant="caption" color="text.secondary">Cur: {zone.measuredTemp ? `${zone.measuredTemp}°` : 'N/A'}</Typography>
                    <Typography variant="caption" color={zone.isOpen ? 'primary.main' : 'text.disabled'}>Set: {zone.temperature}°</Typography>
                  </Box>
                  <Box display="flex" alignItems="center" justifyContent="space-between" mb={0.5}>
                    <IconButton size="small" onClick={() => handleTemperatureChange(zone.id, 'down')} disabled={zone.temperature <= 16}>
                      <ArrowDownwardIcon fontSize="inherit" />
                    </IconButton>
                    <Typography variant="h6" sx={{ mx: 0.5, fontWeight: 'bold', color: 'primary.main' }}>{zone.temperature}°</Typography>
                    <IconButton size="small" onClick={() => handleTemperatureChange(zone.id, 'up')} disabled={zone.temperature >= 32}>
                      <ArrowUpwardIcon fontSize="inherit" />
                    </IconButton>
                  </Box>
                  <Slider
                    size="medium"
                    value={zone.damperValue}
                    onChange={(e,val) => handleDamperValueChange(zone.id, val)}
                    disabled={zone.isConstant} // allow when closed now
                    min={zone.minDamper || 0}
                    max={zone.maxDamper || 100}
                    step={5}
                    valueLabelDisplay="auto"
                    valueLabelFormat={(v) => `${v}%`}
                    sx={{
                      mt: 0.5,
                      width: { xs: '15%', sm: '15%', md: '15%' },
                      alignSelf: 'center',
                      '& .MuiSlider-track': { height: 4 },
                      '& .MuiSlider-rail': { height: 4 },
                      '& .MuiSlider-thumb': { width: 14, height: 14 },
                      '& .MuiSlider-valueLabel': { fontSize: '0.65rem', top: -6 }
                    }}
                  />
                  <Typography variant="caption" color="text.secondary" sx={{ display:'block', mt: 0.5, textAlign:'right', ml:'auto' }}>
                    {zone.isMaster ? 'Master zone' : zone.isConstant ? 'Constant zone' : zone.isOpen ? 'Open' : 'Closed'}
                  </Typography>
                </ZoneCard>
              </Grid>
            ))}
          </Grid>
        </Box>

  {/* Zone Summary and Actions */}
  <Box mt={2} p={2} bgcolor="#ffffff" borderRadius={2} boxShadow="0px 2px 4px rgba(0,0,0,0.05)">
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
                variant="outlined"
                onClick={fetchZoneStatus}
                disabled={updating}
                startIcon={updating ? <CircularProgress size={18} /> : <RefreshIcon />}
                size="small"
              >
                {updating ? 'Refreshing...' : 'Refresh'}
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
