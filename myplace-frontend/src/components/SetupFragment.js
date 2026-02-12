import React, { useEffect, useState, useRef } from 'react';
import { Box, Typography, Paper, Grid, Button, Snackbar, Alert, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Switch, FormControlLabel, Divider, Chip } from '@mui/material';
import SecurityIcon from '@mui/icons-material/Security';
import LockIcon from '@mui/icons-material/Lock';
import DeviceHubIcon from '@mui/icons-material/DeviceHub';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import TimelineIcon from '@mui/icons-material/Timeline';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import RefreshIcon from '@mui/icons-material/Refresh';
import WifiIcon from '@mui/icons-material/Wifi';
import SensorsIcon from '@mui/icons-material/Sensors';
import BackupIcon from '@mui/icons-material/Backup';
import FingerprintIcon from '@mui/icons-material/Fingerprint';
import { styled } from '@mui/system';
import IconButton from '@mui/material/IconButton';
import ApiService from '../services/ApiService';
import CircularProgress from '@mui/material/CircularProgress';

const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(2),
  backgroundColor: '#fafafa',
  borderRadius: 8,
}));

const ActionButton = styled(Button)(() => ({
  textTransform: 'none',
  borderRadius: 8,
  marginBottom: 8,
}));

const SetupFragment = ({ onBack = () => {}, onNavigate = () => {} }) => {
  const [aircon, setAircon] = useState(null);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'info' });
  const [renameAirconOpen, setRenameAirconOpen] = useState(false);
  const [renameDeviceOpen, setRenameDeviceOpen] = useState(false);
  const [newAirconName, setNewAirconName] = useState('');
  const [newDeviceName, setNewDeviceName] = useState('');
  const [saving, setSaving] = useState(false);
  const [editingAircon, setEditingAircon] = useState(false);
  const [editingPostcode, setEditingPostcode] = useState(false);
  const [newPostcode, setNewPostcode] = useState('');
  // Advanced feature local states (placeholders until backend endpoints wired)
  const [showMeasuredTemps, setShowMeasuredTemps] = useState(false);
  const [quietNightMode, setQuietNightMode] = useState(false);
  const [autoFanSpeed, setAutoFanSpeed] = useState(false);
  const [freshAirEnabled, setFreshAirEnabled] = useState(false);
  const [zonesCount, setZonesCount] = useState(null);
  const [editingZones, setEditingZones] = useState(false);
  const [editingZonesCount, setEditingZonesCount] = useState(null);
  const editingZonesRef = useRef(false);
  const [zoneSaving, setZoneSaving] = useState(false);
  const [comfortMode, setComfortMode] = useState('myZone'); // 'myZone' | 'myTemp' | 'myAuto'
  // Newly added state placeholders mirroring native advanced setup features
  const [activationDialogOpen, setActivationDialogOpen] = useState(false);
  const [activationCode, setActivationCode] = useState('');
  const [garageDoorPinEnabled, setGarageDoorPinEnabled] = useState(false);
  const [lockReminderEnabled, setLockReminderEnabled] = useState(false);
  const [deviceLockEnabled, setDeviceLockEnabled] = useState(false);
  const [rfSetupDone, setRfSetupDone] = useState(false);
  const [backupMode, setBackupMode] = useState('none'); // none | manage | restore
  const [timeDateDialogOpen, setTimeDateDialogOpen] = useState(false);
  const [timeDateValue, setTimeDateValue] = useState('');
  const [zonesList, setZonesList] = useState([]);
  const [editingZoneId, setEditingZoneId] = useState(null);
  const [editingZoneName, setEditingZoneName] = useState('');
  const [editingDamperId, setEditingDamperId] = useState(null);
  const [editingMinDamper, setEditingMinDamper] = useState(0);
  const [editingMaxDamper, setEditingMaxDamper] = useState(100);
  const [updatingZones, setUpdatingZones] = useState(false);
  const [systemName, setSystemName] = useState('');
  const [editingSystem, setEditingSystem] = useState(false);
  const [newSystemName, setNewSystemName] = useState('');
  const editingSystemRef = useRef(false);
  const [systemSaving, setSystemSaving] = useState(false);

  useEffect(() => {
    ApiService.startAirconPolling();
    const unsub = ApiService.subscribeAircon((data, { error }) => {
      if (error) {
        setSnackbar({ open: true, message: 'Failed to load aircon data', severity: 'error' });
        return;
      }
      if (data) setAircon(data);
      if (data) {
        setQuietNightMode(!!data.quietNightModeEnabled);
        // Simulated access to paired accounts count (placeholder field)
        const ghCount = data._raw?.system?.googleHomePairedAccounts || 0;
        try {
          setPairedGoogleAccounts(ghCount);
        } catch (_) {
          // ignore if that state isn't present in this build
        }

        // Derive comfort mode and fresh air status from backend info flags when available
        try {
          const raw = data._raw;
          const airconId = raw && raw.aircons ? Object.keys(raw.aircons || {})[0] : null;
          const info = airconId ? (raw.aircons?.[airconId]?.info || {}) : {};
          if (info.myAutoModeEnabled) {
            setComfortMode('myAuto');
          } else if (info.climateControlModeEnabled) {
            setComfortMode('myTemp');
          } else {
            setComfortMode('myZone');
          }
          // Fresh air is enabled if status is 'on' or 'off' (not 'none')
          const freshAirStatus = (info.freshAirStatus || 'none').toLowerCase();
          setFreshAirEnabled(freshAirStatus !== 'none');
        } catch (err) {
          // ignore and leave existing comfortMode
        }
        // Initialize showMeasuredTemps from system-level flag if present
        try {
          const sys = data._raw && data._raw.system ? data._raw.system : null;
          if (sys && typeof sys.showMeasuredTemp !== 'undefined') {
            setShowMeasuredTemps(!!sys.showMeasuredTemp);
          }
          // Initialize system name from system object when available
          try {
            const sysName = data._raw?.system?.name || data._raw?.systemInfo?.id || data._raw?.system?.id || '';
            if (sysName && !editingSystemRef.current) {
              setSystemName(sysName);
            }
          } catch (err) {
            // ignore
          }
          // Initialize zonesCount from aircon info.noOfZones if present
          try {
            const airconId = data._raw && data._raw.aircons ? Object.keys(data._raw.aircons || {})[0] : null;
            const info = airconId ? (data._raw.aircons?.[airconId]?.info || {}) : {};
            const infoCount = info?.noOfZones;
            if (typeof infoCount !== 'undefined' && infoCount !== null) {
              // Only update zonesCount from backend when the user is not actively editing it
              if (!editingZonesRef.current) {
                setZonesCount(Number(infoCount));
              }
            }
          } catch (err) {
            // ignore
          }
        } catch (err) {
          // ignore
        }
      }
    });
    const unsubZones = ApiService.subscribeZones(async (data, { error }) => {
      if (error) return;
      if (data && data.zones) {
        // Try to read constants from cached aircon info first
        let constants = [];
        try {
          const cachedAir = ApiService.getCachedAircon ? ApiService.getCachedAircon() : null;
          if (cachedAir && cachedAir._raw) {
            const system = cachedAir._raw;
            const airconId = Object.keys(system.aircons || {})[0];
            const info = system.aircons?.[airconId]?.info || {};
            constants = [info.constant1, info.constant2, info.constant3].filter(v => v !== undefined && v !== null);
          } else {
            const system = await ApiService.getSystem();
            const airconId = Object.keys(system.aircons || {})[0];
            const info = system.aircons?.[airconId]?.info || {};
            constants = [info.constant1, info.constant2, info.constant3].filter(v => v !== undefined && v !== null);
          }
        } catch (err) {
          // ignore and fall back to zone.type mapping
          constants = [];
        }

        const newZones = data.zones.map(z => ({ ...z, isConstant: constants.includes(z.zoneNumber) }));
        setZonesList(newZones);
      }
    });

    return () => {
      try { unsub(); } catch(_) {}
      try { unsubZones(); } catch(_) {}
    };
  }, []);

  // keep editing ref updated when editingZones changes
  useEffect(() => {
    editingZonesRef.current = editingZones;
  }, [editingZones]);

  // keep editing ref updated when editingSystem changes
  useEffect(() => {
    editingSystemRef.current = editingSystem;
  }, [editingSystem]);

  const handleAction = (action) => {
    switch (action) {
      case 'renameAircon':
        setNewAirconName(aircon?.name || '');
        setRenameAirconOpen(true);
        break;
      case 'renameDevice':
        // Attempt to pick device name from known raw payload (if available)
        setNewDeviceName(aircon?._raw?.systemInfo?.deviceName || 'Device');
        setRenameDeviceOpen(true);
        break;
      case 'techSetup':
        setSnackbar({ open: true, message: 'Open Tech Setup (not implemented)', severity: 'info' });
        break;
      case 'manageBackup':
        setSnackbar({ open: true, message: 'Manage/Restore Backup (placeholder)', severity: 'info' });
        break;
      case 'analyticsLog':
        setSnackbar({ open: true, message: 'Analytics Log (placeholder)', severity: 'info' });
        break;
      case 'zoneLog':
        setSnackbar({ open: true, message: 'Zone Log (placeholder)', severity: 'info' });
        break;
      case 'rfSetup':
        setRfSetupDone(true);
        setSnackbar({ open: true, message: 'RF Setup (placeholder)', severity: 'info' });
        break;
      case 'garageDoorPin':
        setGarageDoorPinEnabled(!garageDoorPinEnabled);
        setSnackbar({ open: true, message: `Garage Door PIN ${garageDoorPinEnabled ? 'disabled' : 'enabled'} (placeholder)`, severity: 'info' });
        break;
      case 'lockReminder':
        setLockReminderEnabled(!lockReminderEnabled);
        setSnackbar({ open: true, message: `Lock Reminder ${lockReminderEnabled ? 'disabled' : 'enabled'} (placeholder)`, severity: 'info' });
        break;
      case 'deviceLock':
        setDeviceLockEnabled(!deviceLockEnabled);
        setSnackbar({ open: true, message: `Device Lock ${deviceLockEnabled ? 'disabled' : 'enabled'} (placeholder)`, severity: 'info' });
        break;
      case 'activationCode':
        setActivationDialogOpen(true);
        break;
      case 'timeDate':
        setTimeDateDialogOpen(true);
        break;
      default:
        setSnackbar({ open: true, message: `Action: ${action}`, severity: 'info' });
    }
  };

  const handleSetComfortMode = async (mode) => {
    // Determine payload based on requested mode
    let payload = {};
    if (mode === 'myZone') {
      payload = { myAutoModeEnabled: false, climateControlModeEnabled: false };
    } else if (mode === 'myTemp' || mode === 'mytemp') {
      payload = { myAutoModeEnabled: false, climateControlModeEnabled: true };
    } else if (mode === 'myAuto' || mode === 'myauto') {
      payload = { myAutoModeEnabled: true, climateControlModeEnabled: false };
    } else {
      // default fallback
      payload = { myAutoModeEnabled: false, climateControlModeEnabled: false };
    }

    // Optimistically set local state then call API
    setComfortMode(mode);
    try {
      await ApiService.updateAircon(payload);
      setSnackbar({ open: true, message: `Comfort mode set: ${mode}`, severity: 'success' });
    } catch (err) {
      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
    }
  };

  const submitRenameAircon = async () => {
    if (!newAirconName) return setSnackbar({ open: true, message: 'Name cannot be empty', severity: 'warning' });
    setSaving(true);
    try {
      await ApiService.updateAircon({ name: newAirconName });
      setSnackbar({ open: true, message: 'Aircon renamed', severity: 'success' });
      setRenameAirconOpen(false);
      setEditingAircon(false);
      // Trigger refresh to update display
      ApiService.refreshAircon();
    } catch (err) {
      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
    } finally {
      setSaving(false);
    }
  };

  const submitRenameDevice = async () => {
    if (!newDeviceName) return setSnackbar({ open: true, message: 'Device name cannot be empty', severity: 'warning' });
    setSaving(true);
    try {
      await ApiService.updateAircon({ deviceName: newDeviceName });
      setSnackbar({ open: true, message: 'Device renamed', severity: 'success' });
      setRenameDeviceOpen(false);
    } catch (err) {
      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
    } finally {
      setSaving(false);
    }
  };

  const submitPostcode = async () => {
    if (!newPostcode) return setSnackbar({ open: true, message: 'Postcode cannot be empty', severity: 'warning' });
    setSaving(true);
    try {
      // Update system-level postcode
      await ApiService.updateSystem({ postCode: newPostcode });
      setSnackbar({ open: true, message: 'Postcode updated', severity: 'success' });
      setEditingPostcode(false);
      // Trigger refresh to update display
      ApiService.refreshAircon();
    } catch (err) {
      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
    } finally {
      setSaving(false);
    }
  };

  const submitActivationCode = () => {
    if (activationCode.length !== 4) {
      setSnackbar({ open: true, message: 'Activation code must be 4 digits (placeholder)', severity: 'warning' });
      return;
    }
    setActivationDialogOpen(false);
    setSnackbar({ open: true, message: 'Activation code submitted (placeholder)', severity: 'success' });
  };

  const submitTimeDate = () => {
    if (!timeDateValue) {
      setSnackbar({ open: true, message: 'Enter time/date value (placeholder)', severity: 'warning' });
      return;
    }
    setTimeDateDialogOpen(false);
    setSnackbar({ open: true, message: 'Time & Date set (placeholder)', severity: 'success' });
  };

  const submitSystemName = async () => {
    const nameToSave = newSystemName?.trim();
    if (!nameToSave) return setSnackbar({ open: true, message: 'System name cannot be empty', severity: 'warning' });
    setSystemSaving(true);
    try {
      await ApiService.updateSystem({ name: nameToSave });
      setSnackbar({ open: true, message: 'System name updated', severity: 'success' });
      setEditingSystem(false);
      setNewSystemName('');
      ApiService.refreshSystem();
    } catch (err) {
      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
    } finally {
      setSystemSaving(false);
    }
  };

  const submitZones = async () => {
    const valueToSave = editingZonesCount;
    if (valueToSave === null || isNaN(valueToSave)) { setSnackbar({ open: true, message: 'Enter a valid number of zones', severity: 'warning' }); return; }
    if (valueToSave < 1 || valueToSave > 10) { setSnackbar({ open: true, message: 'Zones must be between 1 and 10', severity: 'warning' }); return; }
    setZoneSaving(true);
    try {
      await ApiService.updateAircon({ noOfZones: valueToSave });
      setSnackbar({ open: true, message: `Number of zones set to ${valueToSave}`, severity: 'success' });
      setEditingZones(false);
      setEditingZonesCount(null);
      ApiService.refreshAircon();
    } catch (err) {
      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
    } finally {
      setZoneSaving(false);
    }
  };

  return (
    <StyledPaper elevation={1}>
      <Box display="flex" flexDirection="column">
        <Grid item xs={12} md={12}>
          <Paper variant="outlined" sx={{ p: 2, mb: 1 }}>
            <Box display="flex" alignItems="center" gap={1}>
              <DeviceHubIcon fontSize="small" />
              <Typography variant="caption" color="text.secondary">System Name</Typography>
            </Box>
            <Box display="flex" alignItems="center" gap={1} sx={{ mt: 1 }}>
              {editingSystem ? (
                <TextField
                  size="small"
                  value={newSystemName}
                  onChange={e => setNewSystemName(e.target.value)}
                  onKeyDown={e => {
                    if (e.key === 'Enter') {
                      submitSystemName();
                    } else if (e.key === 'Escape') {
                      setEditingSystem(false);
                      setNewSystemName(systemName || aircon?._raw?.system?.name || '');
                    }
                  }}
                  sx={{ flex: 1 }}
                  autoFocus
                />
              ) : (
                <Typography variant="body2" sx={{ flex: 1, overflow: 'hidden', textOverflow: 'ellipsis' }}>{systemName || aircon?._raw?.system?.name || aircon?._raw?.systemInfo?.id || 'System'}</Typography>
              )}

              {editingSystem ? (
                <>
                  <Button size="small" onClick={submitSystemName} variant="contained" disabled={systemSaving}>OK</Button>
                  <Button size="small" onClick={() => { setEditingSystem(false); setNewSystemName(systemName || aircon?._raw?.system?.name || ''); }} disabled={systemSaving}>Cancel</Button>
                </>
              ) : (
                <IconButton size="small" onClick={() => { setNewSystemName(systemName || aircon?._raw?.system?.name || ''); setEditingSystem(true); }}>
                  <EditIcon fontSize="small" />
                </IconButton>
              )}
            </Box>
          </Paper>
        </Grid>
        <Divider sx={{ my: 3 }} />

        {/* Toggles Section */}
        <Typography variant="subtitle1" gutterBottom>System Options</Typography>
        <Grid container spacing={2}>
          <Grid item xs={12} md={4}>
            <FormControlLabel control={<Switch checked={showMeasuredTemps} onChange={async (e) => { 
              const enabled = e.target.checked;
              // Optimistic update
              setShowMeasuredTemps(enabled);
              try {
                await ApiService.updateSystem({ showMeasuredTemp: enabled });
                setSnackbar({ open: true, message: `Show measured temps ${enabled ? 'enabled' : 'disabled'}`, severity: 'success' });
                // Refresh cached aircon to pick up change
                ApiService.refreshAircon();
              } catch (err) {
                // Revert optimistic change
                setShowMeasuredTemps(!enabled);
                setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
              }
            }} />} label="Show Measured Temps" />
          </Grid>
          <Grid item xs={12} md={4}>
            <FormControlLabel control={<Switch checked={quietNightMode} onChange={async (e) => { 
              const enabled = e.target.checked;
              // Optimistic update
              setQuietNightMode(enabled);
              try {
                await ApiService.updateAircon({ quietNightModeEnabled: enabled });
                setSnackbar({ open: true, message: `Quiet Night Mode ${enabled ? 'enabled' : 'disabled'}`, severity: 'success' });
                ApiService.refreshAircon();
              } catch (err) {
                // revert
                setQuietNightMode(!enabled);
                setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
              }
            }} />} label="Quiet Night Mode" />
          </Grid>
          <Grid item xs={12} md={4}>
            <FormControlLabel control={<Switch checked={autoFanSpeed} onChange={(e) => { setAutoFanSpeed(e.target.checked); setSnackbar({ open: true, message: 'Auto Fan Speed updated (placeholder)', severity: 'info' }); }} />} label="Auto Fan Speed" />
          </Grid>
          <Grid item xs={12} md={4}>
            <FormControlLabel control={<Switch checked={freshAirEnabled} onChange={async (e) => { 
              const enabled = e.target.checked;
              setFreshAirEnabled(enabled);
              try {
                await ApiService.updateAircon({ freshAirStatus: enabled ? 'off' : 'none' });
                setSnackbar({ open: true, message: `Fresh Air system ${enabled ? 'enabled' : 'disabled'}`, severity: 'success' });
              } catch (err) {
                setFreshAirEnabled(!enabled);
                setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
              }
            }} />} label="Enable Fresh Air" />
          </Grid>
        </Grid>

        <Box mt={2}>
          <Typography variant="subtitle2">Comfort Mode</Typography>
          <Grid container spacing={1}>
            <Grid item>
              <Button size="small" variant={comfortMode === 'myZone' ? 'contained' : 'outlined'} onClick={() => handleSetComfortMode('myZone')}>My Zone</Button>
            </Grid>
            <Grid item>
              <Button size="small" variant={comfortMode === 'myTemp' ? 'contained' : 'outlined'} onClick={() => handleSetComfortMode('myTemp')}>My Temp</Button>
            </Grid>
            <Grid item>
              <Button size="small" variant={comfortMode === 'myAuto' ? 'contained' : 'outlined'} onClick={() => handleSetComfortMode('myAuto')}>My Auto</Button>
            </Grid>
          </Grid>
        </Box>
        <Box mt={2}>
          <Grid container spacing={2} alignItems="flex-start">
          <Grid item xs={12} md={12}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" gap={1}>
                <SecurityIcon fontSize="small" />
                <Typography variant="caption" color="text.secondary">AirCon Name</Typography>
              </Box>
              <Box display="flex" alignItems="center" gap={1} sx={{ mt: 1 }}>
                {editingAircon ? (
                  <TextField
                    size="small"
                    value={newAirconName}
                    onChange={e => setNewAirconName(e.target.value)}
                    onKeyDown={e => {
                      if (e.key === 'Enter') {
                        submitRenameAircon();
                      } else if (e.key === 'Escape') {
                        setEditingAircon(false);
                        setNewAirconName(aircon?.airconName || '');
                      }
                    }}
                    sx={{ flex: 1 }}
                    autoFocus
                  />
                ) : (
                  <Typography variant="body2" sx={{ flex: 1, overflow: 'hidden', textOverflow: 'ellipsis' }}>{aircon?.airconName}</Typography>
                )}

                {editingAircon ? (
                  <>
                    <Button size="small" onClick={submitRenameAircon} variant="contained" disabled={saving}>OK</Button>
                    <Button size="small" onClick={() => { setEditingAircon(false); setNewAirconName(aircon?.airconName || ''); }} disabled={saving}>Cancel</Button>
                  </>
                ) : (
                  <IconButton size="small" onClick={() => { setNewAirconName(aircon?.airconName || ''); setEditingAircon(true); }}>
                    <EditIcon fontSize="small" />
                  </IconButton>
                )}
              </Box>
            </Paper>
          </Grid>
            
          <Grid item xs={12} md={12}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" gap={1}>
                <SensorsIcon fontSize="small" />
                <Typography variant="caption" color="text.secondary">Number of Zones</Typography>
              </Box>
              <Box display="flex" alignItems="center" gap={1} sx={{ mt: 1 }}>
                {editingZones ? (
                  <>
                    <TextField
                      size="small"
                      type="number"
                      inputProps={{ min: 1, max: 10 }}
                      value={editingZonesCount === null ? '' : editingZonesCount}
                      onChange={(e) => setEditingZonesCount(e.target.value === '' ? null : Number(e.target.value))}
                      onKeyDown={(e) => {
                        if (e.key === 'Escape') {
                          setEditingZones(false);
                          setEditingZonesCount(null);
                        } else if (e.key === 'Enter') {
                          submitZones();
                        }
                      }}
                      sx={{ flex: 1 }}
                      autoFocus
                    />
                    <Button size="small" onClick={submitZones} variant="contained" disabled={zoneSaving}>OK</Button>
                    <Button size="small" onClick={() => { setEditingZones(false); setEditingZonesCount(null); }} sx={{ ml: 1 }}>Cancel</Button>
                  </>
                ) : (
                  <>
                    <Typography variant="body2" sx={{ flex: 1, overflow: 'hidden', textOverflow: 'ellipsis' }}>{zonesCount === null ? 'Not set' : zonesCount}</Typography>
                    <IconButton size="small" onClick={() => { setEditingZones(true); setEditingZonesCount(zonesCount); }}>
                      <EditIcon fontSize="small" />
                    </IconButton>
                  </>
                )}
              </Box>
            </Paper>
          </Grid>
          {/* Zones configuration section */}
          <Grid item xs={12} md={12}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" justifyContent="space-between">
                <Box display="flex" alignItems="center" gap={1}>
                  <DeviceHubIcon fontSize="small" />
                  <Typography variant="subtitle2">Zones Configuration</Typography>
                </Box>
                <Box display="flex" alignItems="center" gap={1}>
                  <Typography variant="caption" color="text.secondary">Max 3 constant zones</Typography>
                  <IconButton size="small" onClick={async () => {
                    setUpdatingZones(true);
                    try {
                      // Trigger immediate refresh
                      ApiService.refreshAircon();
                      ApiService.refreshZones();
                      // Wait briefly for cache to update
                      await new Promise(r => setTimeout(r, 500));
                      const system = await ApiService.getSystem();
                      const airconId = Object.keys(system.aircons || {})[0];
                      const info = system.aircons?.[airconId]?.info || {};
                      const constants = [info.constant1, info.constant2, info.constant3].filter(v => v !== undefined && v !== null);
                      const zonesData = await ApiService.getZones();
                      const newZones = (zonesData.zones || []).map(z => ({ ...z, isConstant: constants.includes(z.zoneNumber) }));
                      setZonesList(newZones);
                      setSnackbar({ open: true, message: 'Zones refreshed', severity: 'success' });
                    } catch (err) {
                      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
                    } finally {
                      setUpdatingZones(false);
                    }
                  }}>
                    {updatingZones ? <CircularProgress size={18} /> : <RefreshIcon fontSize="small" />}
                  </IconButton>
                </Box>
              </Box>

              <Box mt={2}>
                {zonesList && zonesList.length ? (
                  zonesList.map(zone => (
                    <Box key={zone.id} display="flex" alignItems="center" gap={1} sx={{ mb: 1 }}>
                      <Typography variant="body2" sx={{ width: 48 }}>{zone.zoneNumber}</Typography>
                      {editingZoneId === zone.id ? (
                        <Box display="flex" alignItems="center" sx={{ flex: 1 }}>
                          <TextField
                            size="small"
                            value={editingZoneName}
                            onChange={(e) => setEditingZoneName(e.target.value)}
                            onKeyDown={(e) => {
                              if (e.key === 'Enter') {
                                // save
                                const newName = editingZoneName?.trim();
                                if (!newName) { setSnackbar({ open: true, message: 'Zone name cannot be empty', severity: 'warning' }); return; }
                                (async () => {
                                  try {
                                    await ApiService.updateZone({ id: zone.id, name: newName });
                                    setZonesList(prev => prev.map(z => z.id === zone.id ? { ...z, name: newName } : z));
                                    setSnackbar({ open: true, message: 'Zone name updated', severity: 'success' });
                                    setEditingZoneId(null);
                                  } catch (err) {
                                    setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
                                  }
                                })();
                              } else if (e.key === 'Escape') {
                                // cancel
                                setEditingZoneId(null);
                                setEditingZoneName('');
                              }
                            }}
                            sx={{ flex: 1 }}
                            autoFocus
                          />
                          <IconButton size="small" onClick={async () => {
                            const newName = editingZoneName?.trim();
                            if (!newName) { setSnackbar({ open: true, message: 'Zone name cannot be empty', severity: 'warning' }); return; }
                            try {
                              await ApiService.updateZone({ id: zone.id, name: newName });
                              setZonesList(prev => prev.map(z => z.id === zone.id ? { ...z, name: newName } : z));
                              setSnackbar({ open: true, message: 'Zone name updated', severity: 'success' });
                              setEditingZoneId(null);
                              setEditingZoneName('');
                            } catch (err) {
                              setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
                            }
                          }}>
                            <CheckIcon fontSize="small" />
                          </IconButton>
                          <IconButton size="small" onClick={() => { setEditingZoneId(null); setEditingZoneName(''); }}>
                            <CloseIcon fontSize="small" />
                          </IconButton>
                        </Box>
                      ) : (
                        <Box display="flex" alignItems="center" sx={{ flex: 1 }}>
                          <Typography variant="body2" sx={{ overflow: 'hidden', textOverflow: 'ellipsis' }}>{zone.name}</Typography>
                          <IconButton size="small" onClick={(e) => { e.stopPropagation(); setEditingZoneId(zone.id); setEditingZoneName(zone.name); }}>
                            <EditIcon fontSize="small" />
                          </IconButton>
                        </Box>
                      )}
                      {editingDamperId === zone.id ? (
                        <Box display="flex" alignItems="center" gap={1} sx={{ mr: 2 }}>
                          <TextField
                            size="small"
                            label="Min"
                            type="number"
                            value={editingMinDamper}
                            onChange={(e) => {
                              const value = parseInt(e.target.value);
                              if (!isNaN(value) && value >= 0 && value <= 100) {
                                setEditingMinDamper(value);
                              }
                            }}
                            inputProps={{ min: 0, max: 100, style: { width: '50px' } }}
                            sx={{ width: '80px' }}
                          />
                          <TextField
                            size="small"
                            label="Max"
                            type="number"
                            value={editingMaxDamper}
                            onChange={(e) => {
                              const value = parseInt(e.target.value);
                              if (!isNaN(value) && value >= 0 && value <= 100) {
                                setEditingMaxDamper(value);
                              }
                            }}
                            inputProps={{ min: 0, max: 100, style: { width: '50px' } }}
                            sx={{ width: '80px' }}
                          />
                          <IconButton size="small" onClick={async () => {
                            if (editingMinDamper > editingMaxDamper) {
                              setSnackbar({ open: true, message: 'Min cannot be greater than max', severity: 'warning' });
                              return;
                            }
                            try {
                              await ApiService.updateZone({ id: zone.id, minDamper: editingMinDamper, maxDamper: editingMaxDamper });
                              setZonesList(prev => prev.map(z => z.id === zone.id ? { ...z, minDamper: editingMinDamper, maxDamper: editingMaxDamper } : z));
                              setSnackbar({ open: true, message: 'Damper values updated', severity: 'success' });
                              setEditingDamperId(null);
                            } catch (err) {
                              setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
                            }
                          }}>
                            <CheckIcon fontSize="small" />
                          </IconButton>
                          <IconButton size="small" onClick={() => { setEditingDamperId(null); }}>
                            <CloseIcon fontSize="small" />
                          </IconButton>
                        </Box>
                      ) : (
                        <Box display="flex" alignItems="center" gap={1} sx={{ mr: 2 }}>
                          <Typography variant="body2" sx={{ width: '80px', textAlign: 'center' }}>Min: {zone.minDamper ?? 0}</Typography>
                          <Typography variant="body2" sx={{ width: '80px', textAlign: 'center' }}>Max: {zone.maxDamper ?? 100}</Typography>
                          <IconButton size="small" onClick={(e) => { 
                            e.stopPropagation(); 
                            setEditingDamperId(zone.id); 
                            setEditingMinDamper(zone.minDamper ?? 0); 
                            setEditingMaxDamper(zone.maxDamper ?? 100); 
                          }}>
                            <EditIcon fontSize="small" />
                          </IconButton>
                        </Box>
                      )}
                      <FormControlLabel
                        control={
                          <Switch
                            size="small"
                            checked={!!zone.isConstant}
                            onChange={async () => {
                              const currentlyConstants = zonesList.filter(z => z.isConstant).length;
                              const turningOn = !zone.isConstant;
                              if (turningOn && currentlyConstants >= 3) {
                                setSnackbar({ open: true, message: 'Maximum of 3 constant zones allowed', severity: 'warning' });
                                return;
                              }
                              // Optimistic update
                              setZonesList(prev => prev.map(z => z.id === zone.id ? { ...z, isConstant: !z.isConstant } : z));
                              try {

                                // Compute updated constant zone numbers (unique), assigning this zone to first available slot when turning on
                                const currentConstants = zonesList.filter(z => z.isConstant).map(z => z.zoneNumber);
                                let updatedConstants;
                                if (turningOn) {
                                  // add if not present
                                  updatedConstants = Array.from(new Set([...currentConstants, zone.zoneNumber]));
                                } else {
                                  // remove
                                  updatedConstants = currentConstants.filter(n => n !== zone.zoneNumber);
                                }
                                // limit to 3
                                updatedConstants = updatedConstants.slice(0, 3);

                                // Build explicit slots with 0 for unused
                                const airconPayload = {
                                  constant1: updatedConstants[0] || 0,
                                  constant2: updatedConstants[1] || 0,
                                  constant3: updatedConstants[2] || 0,
                                  noOfConstants: updatedConstants.length
                                };

                                // Send to ApiService.updateAircon so the info object contains constants (slots 1..3)
                                await ApiService.updateAircon(airconPayload);

                                setSnackbar({ open: true, message: 'Zone constant flag updated', severity: 'success' });
                              } catch (err) {
                                // revert optimistic update
                                setZonesList(prev => prev.map(z => z.id === zone.id ? { ...z, isConstant: zone.isConstant } : z));
                                setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
                              }
                            }}
                          />
                        }
                        label="Constant"
                      />
                    </Box>
                  ))
                ) : (
                  <Typography variant="body2" color="text.secondary">No zones available</Typography>
                )}
              </Box>
            </Paper>
          </Grid>
          </Grid>
          </Box>

        <Divider sx={{ my: 3 }} />

        {/* Postcode & Backup placeholders */}
        <Typography variant="subtitle1" gutterBottom>Location & Backup</Typography>
        <Grid container spacing={2}>
          <Grid item xs={12} md={6}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" gap={1}>
                <Typography variant="caption" color="text.secondary">Postcode</Typography>
              </Box>
              <Box display="flex" alignItems="center" gap={1} sx={{ mt: 1 }}>
                {editingPostcode ? (
                  <TextField
                    size="small"
                    value={newPostcode}
                    onChange={e => setNewPostcode(e.target.value)}
                    onKeyDown={e => {
                      if (e.key === 'Enter') {
                        submitPostcode();
                      } else if (e.key === 'Escape') {
                        setEditingPostcode(false);
                        setNewPostcode(aircon?._raw?.system?.postCode || '');
                      }
                    }}
                    sx={{ flex: 1 }}
                    autoFocus
                  />
                ) : (
                  <Typography variant="body2" sx={{ flex: 1, overflow: 'hidden', textOverflow: 'ellipsis' }}>{aircon?._raw?.system?.postCode || 'Not set'}</Typography>
                )}

                {editingPostcode ? (
                  <>
                    <Button size="small" onClick={submitPostcode} variant="contained" disabled={saving}>OK</Button>
                    <Button size="small" onClick={() => { setEditingPostcode(false); setNewPostcode(aircon?._raw?.system?.postCode || ''); }} disabled={saving}>Cancel</Button>
                  </>
                ) : (
                  <IconButton size="small" onClick={() => { setNewPostcode(aircon?._raw?.system?.postCode || ''); setEditingPostcode(true); }}>
                    <EditIcon fontSize="small" />
                  </IconButton>
                )}
              </Box>
            </Paper>
          </Grid>
          <Grid item xs={12} md={6}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Typography variant="caption" color="text.secondary">Backup / Restore</Typography>
              <Typography variant="body2">Mode: {backupMode}</Typography>
              <Button size="small" sx={{ mt: 1, mr: 1 }} onClick={() => { setBackupMode('manage'); handleAction('manageBackup'); }}>Manage</Button>
              <Button size="small" sx={{ mt: 1 }} onClick={() => { setBackupMode('restore'); handleAction('manageBackup'); }}>Restore</Button>
            </Paper>
          </Grid>
        </Grid>

        <Divider sx={{ my: 3 }} />
        {/* Security & Automation */}
        <Typography variant="subtitle1" gutterBottom>Security & Automation</Typography>
        <Grid container spacing={2}>
          <Grid item xs={12} md={4}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" gap={1}>
                <SecurityIcon fontSize="small" />
                <Typography variant="caption" color="text.secondary">Activation</Typography>
              </Box>
              <Typography variant="body2">Status: {aircon?.systemStatus === 'active' ? 'Active' : 'Standby'}</Typography>
              <Button size="small" sx={{ mt: 1 }} onClick={() => handleAction('activationCode')}>Enter Code</Button>
            </Paper>
          </Grid>
          <Grid item xs={12} md={4}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" gap={1}>
                <FingerprintIcon fontSize="small" />
                <Typography variant="caption" color="text.secondary">Garage Door PIN</Typography>
              </Box>
              <FormControlLabel control={<Switch checked={garageDoorPinEnabled} onChange={() => handleAction('garageDoorPin')} />} label={garageDoorPinEnabled ? 'Enabled' : 'Disabled'} />
              <Button size="small" onClick={() => setSnackbar({ open: true, message: 'Setup Garage Door PIN (placeholder)', severity: 'info' })}>Setup</Button>
            </Paper>
          </Grid>
          <Grid item xs={12} md={4}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" gap={1}>
                <LockIcon fontSize="small" />
                <Typography variant="caption" color="text.secondary">Lock Reminder</Typography>
              </Box>
              <FormControlLabel control={<Switch checked={lockReminderEnabled} onChange={() => handleAction('lockReminder')} />} label={lockReminderEnabled ? 'On' : 'Off'} />
              <Button size="small" onClick={() => handleAction('deviceLock')}>{deviceLockEnabled ? 'Disable Device Lock' : 'Enable Device Lock'}</Button>
            </Paper>
          </Grid>
        </Grid>

        <Divider sx={{ my: 3 }} />
        {/* Additional Modules */}
        <Typography variant="subtitle1" gutterBottom>Additional Modules</Typography>
        <Grid container spacing={2}>
          <Grid item xs={12} md={3}>
            <Button fullWidth variant="outlined" startIcon={<WifiIcon />} onClick={() => handleAction('rfSetup')}>{rfSetupDone ? 'RF Setup (Done)' : 'RF Setup'}</Button>
          </Grid>
          <Grid item xs={12} md={3}>
            <Button fullWidth variant="outlined" startIcon={<TimelineIcon />} onClick={() => handleAction('analyticsLog')}>Analytics Log</Button>
          </Grid>
          <Grid item xs={12} md={3}>
            <Button fullWidth variant="outlined" startIcon={<TimelineIcon />} onClick={() => handleAction('zoneLog')}>Zone Log</Button>
          </Grid>
        </Grid>
      </Box>

      <Snackbar open={snackbar.open} autoHideDuration={3500} onClose={() => setSnackbar(s => ({ ...s, open: false }))}>
        <Alert severity={snackbar.severity} onClose={() => setSnackbar(s => ({ ...s, open: false }))}>{snackbar.message}</Alert>
      </Snackbar>

      {/* Rename Aircon Dialog */}
      <Dialog open={renameAirconOpen} onClose={() => setRenameAirconOpen(false)} fullWidth>
        <DialogTitle>Rename Aircon</DialogTitle>
        <DialogContent>
          <TextField
            label="Aircon name"
            value={newAirconName}
            onChange={e => setNewAirconName(e.target.value)}
            fullWidth
            autoFocus
            margin="dense"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setRenameAirconOpen(false)} disabled={saving}>Cancel</Button>
          <Button onClick={submitRenameAircon} disabled={saving} variant="contained">Save</Button>
        </DialogActions>
      </Dialog>

      {/* Rename Device Dialog */}
      <Dialog open={renameDeviceOpen} onClose={() => setRenameDeviceOpen(false)} fullWidth>
        <DialogTitle>Rename Device</DialogTitle>
        <DialogContent>
          <TextField
            label="Device name"
            value={newDeviceName}
            onChange={e => setNewDeviceName(e.target.value)}
            fullWidth
            autoFocus
            margin="dense"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setRenameDeviceOpen(false)} disabled={saving}>Cancel</Button>
          <Button onClick={submitRenameDevice} disabled={saving} variant="contained">Save</Button>
        </DialogActions>
      </Dialog>

      {/* Activation Code Dialog */}
      <Dialog open={activationDialogOpen} onClose={() => setActivationDialogOpen(false)} fullWidth>
        <DialogTitle>Enter Activation Code</DialogTitle>
        <DialogContent>
          <TextField
            label="4-digit code"
            value={activationCode}
            onChange={e => setActivationCode(e.target.value.replace(/[^0-9]/g,'').slice(0,4))}
            fullWidth
            autoFocus
            margin="dense"
          />
          <Typography variant="caption" color="text.secondary">Placeholder only - backend activation not yet implemented.</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setActivationDialogOpen(false)}>Cancel</Button>
          <Button onClick={submitActivationCode} variant="contained">Submit</Button>
        </DialogActions>
      </Dialog>
      {/* Time & Date Dialog */}
      <Dialog open={timeDateDialogOpen} onClose={() => setTimeDateDialogOpen(false)} fullWidth>
        <DialogTitle>Set Time & Date</DialogTitle>
        <DialogContent>
          <TextField
            label="Time & Date Value"
            placeholder="YYYY-MM-DD HH:mm"
            value={timeDateValue}
            onChange={e => setTimeDateValue(e.target.value)}
            fullWidth
            autoFocus
            margin="dense"
          />
          <Typography variant="caption" color="text.secondary">Placeholder only - no backend call yet.</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setTimeDateDialogOpen(false)}>Cancel</Button>
          <Button onClick={submitTimeDate} variant="contained">Set</Button>
        </DialogActions>
      </Dialog>
    </StyledPaper>
  );
};

export default SetupFragment;
