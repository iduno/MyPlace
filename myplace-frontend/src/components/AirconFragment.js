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
  Card
} from '@mui/material';
import ApiService from '../services/ApiService';
import { styled } from '@mui/system';
import DeleteIcon from '@mui/icons-material/Delete';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import AcUnitIcon from '@mui/icons-material/AcUnit';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import NightsStayIcon from '@mui/icons-material/NightsStay';
import WaterDropIcon from '@mui/icons-material/WaterDrop';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';

// Styled components to match the Android layout
const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(2),
  backgroundColor: '#f5f5f5', // Light background similar to Android
  height: '100%',
  borderRadius: 8,
}));

const RoundButton = styled(Button)(({ theme }) => ({
  borderRadius: 20,
  textTransform: 'none',
  padding: theme.spacing(1, 2),
  backgroundColor: '#ffffff',
  color: '#424242', // Dark grey text
  '&:hover': {
    backgroundColor: '#e0e0e0',
  },
}));

const LeftButton = styled(Button)(({ theme }) => ({
  borderRadius: '20px 0 0 20px',
  textTransform: 'none',
  padding: theme.spacing(1, 2),
  backgroundColor: '#ffffff',
  color: '#424242',
  '&:hover': {
    backgroundColor: '#e0e0e0',
  },
}));

const RightButton = styled(Button)(({ theme }) => ({
  borderRadius: '0 20px 20px 0',
  textTransform: 'none',
  padding: theme.spacing(1, 2),
  backgroundColor: '#ffffff',
  color: '#424242',
  '&:hover': {
    backgroundColor: '#e0e0e0',
  },
}));

const PowerToggle = styled(Switch)(({ theme }) => ({
  '& .MuiSwitch-switchBase.Mui-checked': {
    color: theme.palette.primary.main,
    '&:hover': {
      backgroundColor: 'rgba(25, 118, 210, 0.08)',
    },
  },
  '& .MuiSwitch-switchBase.Mui-checked + .MuiSwitch-track': {
    backgroundColor: theme.palette.primary.main,
  },
  '& .MuiSwitch-track': {
    borderRadius: 22 / 2,
    width: '100% !important', // Make track take full width
  },
  '& .MuiSwitch-thumb': {
    boxShadow: '0 2px 4px 0 rgba(0, 35, 11, 0.2)',
    width: 28,
    height: 28,
  },
  width: 70, // Increased from 60 to 100
  height: 36,
  padding: 8,
  '& .MuiSwitch-switchBase': {
    margin: 4,
    padding: 0,
    transform: 'translateX(4px)',
    '&.Mui-checked': {
      transform: 'translateX(30px)', // Adjusted to move further right on the wider switch
    }
  },
}));

const FanSpeedButton = styled(Button)(({ theme, selected }) => ({
  margin: theme.spacing(0.5),
  padding: theme.spacing(1),
  backgroundColor: selected ? theme.palette.primary.main : '#ffffff',
  color: selected ? '#ffffff' : '#424242',
  '&:hover': {
    backgroundColor: selected ? theme.palette.primary.dark : '#e0e0e0',
  },
}));

const TemperatureDisplay = styled(Box)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
  padding: theme.spacing(2),
  backgroundColor: '#ffffff',
  borderRadius: 8,
  boxShadow: '0px 2px 4px rgba(0,0,0,0.1)',
}));

const ModeButton = styled(ToggleButton)(({ theme }) => ({
  margin: theme.spacing(0.5),
  borderRadius: 8,
  padding: theme.spacing(1),
  '&.Mui-selected': {
    backgroundColor: theme.palette.primary.main,
    color: '#ffffff',
    '&:hover': {
      backgroundColor: theme.palette.primary.dark,
    }
  }
}));

const ModesContainer = styled(Box)(({ theme }) => ({
  padding: theme.spacing(2),
  backgroundColor: '#ffffff',
  borderRadius: 8,
  boxShadow: '0px 2px 4px rgba(0,0,0,0.1)',
  marginTop: theme.spacing(2),
}));

const StatusIndicator = styled('div')(({ status, theme }) => ({
  width: 12,
  height: 12,
  borderRadius: '50%',
  backgroundColor: 
    status === 'active' ? theme.palette.success.main :
    status === 'standby' ? theme.palette.warning.main :
    theme.palette.error.main,
  marginRight: theme.spacing(1),
}));

const AirconFragment = () => {
  const theme = useTheme();
  const [power, setPower] = useState(false);
  const [temperature, setTemperature] = useState(24);
  const [fanSpeed, setFanSpeed] = useState(1); // 1: Low, 2: Medium, 3: High
  const [timerEnabled, setTimerEnabled] = useState(false);
  const [timerValue, setTimerValue] = useState(0.5); // Hours (starting with 30min)
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');
  
  // New state variables
  const [mode, setMode] = useState('cool'); // cool, heat, fan, auto, dry
  const [systemStatus, setSystemStatus] = useState('standby'); // active, standby, error
  const [airconName, setAirconName] = useState('Aircon'); // Zone/room name
  const [updating, setUpdating] = useState(false); // For update operations in progress
  const [myAutoEnabled, setMyAutoEnabled] = useState(false);
  const [freshAirStatus, setFreshAirStatus] = useState('none'); // none, on, off

  // Subscribe to ApiService polling cache
  useEffect(() => {
    setLoading(true);
    // Start polling (idempotent) & subscribe
    ApiService.startAirconPolling();
    const unsubscribe = ApiService.subscribeAircon((data, { error }) => {
      if (error) {
        // Only show error if we have never loaded data
        if (!ApiService.getCachedAircon()) {
          setError('Failed to fetch aircon data');
          setLoading(false);
        }
        return; // keep previous state on errors
      }
      if (!data) return;
      setPower(data.power || false);
      setTemperature(data.temperature || 24);
      setFanSpeed(mapFanSpeedFromString(data.fanSpeed) || 1);
      setTimerEnabled(data.timerEnabled || false);
      setTimerValue((data.timerValue || 0) / 60.0);
      setMode(data.mode || 'cool');
      setSystemStatus(data.systemStatus || 'standby');
      setAirconName(data.airconName || 'Room');
      // derive myAutoModeEnabled and freshAirStatus from raw system info if present
      try {
        const raw = data._raw;
        const airconId = raw && raw.aircons ? Object.keys(raw.aircons || {})[0] : null;
        const info = airconId ? (raw.aircons?.[airconId]?.info || {}) : {};
        setMyAutoEnabled(!!info.myAutoModeEnabled);
        setFreshAirStatus((info.freshAirStatus || 'none').toLowerCase());
      } catch (err) {
        // ignore
      }
      setLoading(false);
      setError(null);
    });
    return () => unsubscribe();
  }, []);

  // Map fan speed from string to number
  const mapFanSpeedFromString = (speedString) => {
    switch (speedString?.toLowerCase()) {
      case 'low': return 1;
      case 'medium': return 2;
      case 'high': return 3;
      case 'auto': return 4;
      default: return 1;
    }
  };

  // Map fan speed from number to string
  const mapFanSpeedToString = (speedNumber) => {
    switch (speedNumber) {
      case 1: return 'low';
      case 2: return 'medium';
      case 3: return 'high';
      case 4: return 'auto'
      default: return 'low';
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

  // Update aircon data on server
  const updateAirconData = async (overrideData = {}) => {
    try {
      setUpdating(true);
      
      // Create data object with current state values
      // but allow specific fields to be overridden for immediate updates
      const airconData = {
        state: power ? 'on' : 'off', // Convert boolean to string as per DataAirconInfo
        setTemp: temperature,
        fan: mapFanSpeedToString(fanSpeed),
        mode: mode,
        // myAutoModeEnabled: quietNightModeEnabled,
        countDownToOff: (power && timerEnabled) ? Math.round(timerValue * 60) : 0, // Convert hours to minutes
        countDownToOn: (!power && timerEnabled) ? Math.round(timerValue * 60) : 0, // Convert hours to minutes
        ...overrideData // Override with any specific values passed
      };

      console.log('Updating aircon data:', airconData);
      
  const response = await ApiService.updateAircon(airconData); // This triggers immediate refresh
      
      if (response.ack) {
        showSnackbar('Aircon settings updated successfully');
        // Update system status based on power state
        setSystemStatus(power ? 'active' : 'standby');
      } else {
        showSnackbar('Failed to update aircon settings', 'error');
      }
    } catch (err) {
      console.error('Failed to update aircon data:', err);
      showSnackbar('Failed to update aircon settings', 'error');
    } finally {
      setUpdating(false);
    }
  };

  const handlePowerToggle = () => {
    const newPowerState = !power;
    
    console.log('Power state changed to:', newPowerState);
    

    // Update server immediately with the new power state
    updateAirconData({
      state: newPowerState ? 'on' : 'off',
      countDownToOff: 0,
      countDownToOn: 0,
    });
    
    // Update local state after API call is initiated
    setPower(newPowerState);
    if (timerEnabled) {
      setTimerEnabled(false);
      setTimerValue(0);
    }
  };

  const handleTemperatureChange = (direction) => {
    let newTemp = temperature;
    
    if (direction === 'up' && temperature < 32) {
      newTemp = temperature + 1;
    } else if (direction === 'down' && temperature > 16) {
      newTemp = temperature - 1;
    } else {
      return; // No change needed
    }
    
    // Update server with new temperature immediately
    updateAirconData({ setTemp: newTemp });
    
    // Update local state
    setTemperature(newTemp);
  };

  const handleFanSpeedChange = (speed) => {
    // Update server with new fan speed immediately
    updateAirconData({ fan: mapFanSpeedToString(speed) });
    
    // Update local state
    setFanSpeed(speed);
  };

  // Get the formatted timer display value
  const getFormattedTimerValue = (value) => {
    // For values less than 1 hour (30 min), show as minutes
    if (value < 1) {
      return `${Math.round(value * 60)}min`;
    }
    // For whole hour values
    if (value % 1 === 0) {
      return `${value}h`;
    }
    // For hour and half values (like 1.5h)
    return `${Math.floor(value)}h ${Math.round((value % 1) * 60)}min`;
  };
  
  // Handle incrementing the timer value
  const handleTimerIncrement = () => {
    let newValue = timerValue;
    let newTimerEnabled = timerEnabled;
    
    // If timer is not enabled, enable it and start with 30min
    if (!timerEnabled) {
      newTimerEnabled = true;
      newValue = 0.5;
    } 
    // If timer is already enabled, increment according to pattern
    else {
      // For values under 3 hours, increment by 30min (0.5h)
      if (timerValue < 3) {
        newValue = timerValue + 0.5;
      } 
      // For values 3h and above, increment by 1h
      else if (timerValue < 12) {
        newValue = timerValue + 1;
      } 
      // Cycle back to 30min after reaching 12h
      else {
        newValue = 0.5;
      }
    }
    
    // Update server with new timer value immediately
    updateAirconData({ 
      countDownToOff: (power && newTimerEnabled) ? Math.round(newValue * 60) : 0, // Convert to minutes
      countDownToOn: (!power && newTimerEnabled) ? Math.round(newValue * 60) : 0  // Convert to minutes
    });
    
    // Update local state
    setTimerValue(newValue);
    setTimerEnabled(newTimerEnabled);
  };
  
  const handleModeChange = (event, newMode) => {
    if (newMode !== null) {
      // Update server with new mode immediately
      updateAirconData({ mode: newMode });
      
      // Update local state
      setMode(newMode);
    }
  };

  const handleFreshAirToggle = () => {
    const newFreshAirStatus = freshAirStatus === 'on' ? 'off' : 'on';
    
    // Update server with new fresh air status immediately
    updateAirconData({ freshAirStatus: newFreshAirStatus });
    
    // Update local state
    setFreshAirStatus(newFreshAirStatus);
  };

  return (
    <StyledPaper elevation={1}>
      {loading ? (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
          <Typography variant="h6">Loading aircon data...</Typography>
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
          {airconName}
        </Typography>
        
        <Box display="flex" alignItems="center">
          <StatusIndicator status={systemStatus} />
          <Typography variant="body2" color="text.secondary">
            {systemStatus === 'active' ? 'Running' : 
             systemStatus === 'standby' ? 'Standby' : 'Error'}
          </Typography>
        </Box>
      </Box>
      
      <Grid container spacing={2}>
        {/* Left Side - Power, Timer, Fan Speed */}
        <Grid item xs={12} md={6} lg={5.5}>
          <Box 
            display="flex" 
            flexDirection="column" 
            alignItems="center"
            sx={{ height: '100%' }}
          >
            {/* Power Toggle */}
            <Box mb={3} display="flex" flexDirection="column" alignItems="center">
              <Typography variant="body2" color="textSecondary" gutterBottom>POWER</Typography>
              <Box 
                display="flex" 
                justifyContent="center" 
                alignItems="center" 
                width="100%"
                sx={{
                  marginTop: 1,
                  padding: '0 10px' // Add some horizontal padding
                }}
              >
                <PowerToggle 
                  checked={power} 
                  onChange={handlePowerToggle} 
                />
              </Box>
            </Box>

            {/* Timer Controls */}
            <Box mb={2} width="100%">
              <Grid container spacing={1}>
                <Grid item xs={8}>
                  <LeftButton 
                    fullWidth
                    onClick={handleTimerIncrement}
                  >
                    {power 
                      ? (timerEnabled ? `Timer: ${getFormattedTimerValue(timerValue)}` : 'Timer Off') 
                      : (timerEnabled ? `Timer On: ${getFormattedTimerValue(timerValue)}` : 'Timer On')}
                  </LeftButton>
                </Grid>
                <Grid item xs={4}>
                  <RightButton 
                    fullWidth
                    disabled={!timerEnabled}
                    onClick={() => {
                      setTimerEnabled(false);
                      setTimeout(() => updateAirconData({
                        countDownToOff: 0,
                        countDownToOn: 0
                      }), 300);
                    }}
                  >
                    <DeleteIcon fontSize="small" />
                  </RightButton>
                </Grid>
              </Grid>
            </Box>

            {/* Fan Speed */}
            <Box width="100%">
              <Divider sx={{ my: 2 }} />
              <Typography variant="body2" align="center" color="textSecondary" gutterBottom>
                FAN SPEED
              </Typography>
              <Box display="flex" justifyContent="space-between" mt={1}>
                <FanSpeedButton 
                  variant={fanSpeed === 1 ? "contained" : "outlined"}
                  selected={fanSpeed === 1}
                  onClick={() => handleFanSpeedChange(1)}
                >
                  Low
                </FanSpeedButton>
                <FanSpeedButton 
                  variant={fanSpeed === 2 ? "contained" : "outlined"}
                  selected={fanSpeed === 2}
                  onClick={() => handleFanSpeedChange(2)}
                >
                  Med
                </FanSpeedButton>
                <FanSpeedButton 
                  variant={fanSpeed === 3 ? "contained" : "outlined"}
                  selected={fanSpeed === 3}
                  onClick={() => handleFanSpeedChange(3)}
                >
                  High
                </FanSpeedButton>
                <FanSpeedButton 
                  variant={fanSpeed === 4 ? "contained" : "outlined"}
                  selected={fanSpeed === 4}
                  onClick={() => handleFanSpeedChange(4)}
                >
                  Auto
                </FanSpeedButton>
              </Box>
            </Box>

            {/* Fresh Air Toggle */}
            {freshAirStatus && freshAirStatus !== 'none' && (
              <Box width="100%">
                <Divider sx={{ my: 2 }} />
                <Typography variant="body2" align="center" color="textSecondary" gutterBottom>
                  FRESH AIR
                </Typography>
                <Box 
                  display="flex" 
                  justifyContent="center" 
                  alignItems="center" 
                  width="100%"
                  sx={{
                    marginTop: 1,
                    padding: '0 10px'
                  }}
                >
                  <PowerToggle 
                    checked={freshAirStatus === 'on'} 
                    onChange={handleFreshAirToggle} 
                  />
                </Box>
              </Box>
            )}
            
          </Box>
        </Grid>

        {/* Divider */}
        <Grid item xs={12} md={0.5}>
          <Divider orientation="vertical" sx={{ 
            height: '100%', 
            mx: 'auto',
            display: { xs: 'none', md: 'block' }
          }} />
          <Divider sx={{ 
            my: 2,
            display: { xs: 'block', md: 'none' }
          }} />
        </Grid>

        {/* Right Side - Temperature Controls and Modes */}
        <Grid item xs={12} md={5}>
          {/* Operation Mode Selection */}
          <ModesContainer>
            <Typography variant="body2" align="center" color="textSecondary" gutterBottom>
              MODE
            </Typography>
            <ToggleButtonGroup
              value={mode}
              exclusive
              onChange={handleModeChange}
              aria-label="operation mode"
              fullWidth
            >
              <ModeButton value="cool" aria-label="cooling mode">
                <AcUnitIcon sx={{ mr: 1 }} />
                Cool
              </ModeButton>
              <ModeButton value="heat" aria-label="heating mode">
                <WbSunnyIcon sx={{ mr: 1 }} />
                Heat
              </ModeButton>
            </ToggleButtonGroup>
            
            <Box mt={1}>
              <ToggleButtonGroup
                value={mode}
                exclusive
                onChange={handleModeChange}
                aria-label="additional modes"
                fullWidth
              >
                <ModeButton value="vent" aria-label="fan only mode">
                  Fan
                </ModeButton>
                <ModeButton value="dry" aria-label="dry mode">
                  <WaterDropIcon sx={{ mr: 1 }} />
                  Dry
                </ModeButton>
                {myAutoEnabled && (
                  <ModeButton value="myauto" aria-label="auto mode">
                    <AutoAwesomeIcon sx={{ mr: 1 }} />
                    Auto
                  </ModeButton>
                )}
              </ToggleButtonGroup>
            </Box>
          </ModesContainer>
          
          <TemperatureDisplay sx={{ mt: 2 }}>
            <Typography variant="body2" color="textSecondary" gutterBottom>
              TEMPERATURE
            </Typography>
            
            <Box display="flex" alignItems="center" justifyContent="center">
              <IconButton 
                onClick={() => handleTemperatureChange('down')}
                disabled={temperature <= 16}
                size="large"
              >
                <ArrowDownwardIcon />
              </IconButton>
              
              <Typography 
                variant="h2" 
                sx={{ 
                  fontWeight: 'bold',
                  mx: 3,
                  color: 'primary.main'
                }}
              >
                {temperature}°
              </Typography>
              
              <IconButton 
                onClick={() => handleTemperatureChange('up')}
                disabled={temperature >= 32}
                size="large"
              >
                <ArrowUpwardIcon />
              </IconButton>
            </Box>
            
            <Typography variant="body2" color="textSecondary" sx={{ mt: 2 }}>
              {power ? 'SET TEMPERATURE' : 'OFF'}
            </Typography>
          </TemperatureDisplay>
        </Grid>
      </Grid>
      
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

export default AirconFragment;
