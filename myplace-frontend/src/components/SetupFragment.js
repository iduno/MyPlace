import React, { useEffect, useState } from 'react';
import { Box, Typography, Paper, Grid, Button, Snackbar, Alert, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Switch, FormControlLabel, Divider, Chip } from '@mui/material';
import SecurityIcon from '@mui/icons-material/Security';
import LockIcon from '@mui/icons-material/Lock';
import DeviceHubIcon from '@mui/icons-material/DeviceHub';
import TimelineIcon from '@mui/icons-material/Timeline';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import WifiIcon from '@mui/icons-material/Wifi';
import SensorsIcon from '@mui/icons-material/Sensors';
import BackupIcon from '@mui/icons-material/Backup';
import FingerprintIcon from '@mui/icons-material/Fingerprint';
import { styled } from '@mui/system';
import ApiService from '../services/ApiService';

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
  // Advanced feature local states (placeholders until backend endpoints wired)
  const [showMeasuredTemps, setShowMeasuredTemps] = useState(false);
  const [quietNightMode, setQuietNightMode] = useState(false);
  const [autoFanSpeed, setAutoFanSpeed] = useState(false);
  const [comfortMode, setComfortMode] = useState('myZone'); // 'myZone' | 'climateControl' | 'myMode'
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

  useEffect(() => {
    ApiService.startAirconPolling();
    const unsub = ApiService.subscribeAircon((data, { error }) => {
      if (error) {
        setSnackbar({ open: true, message: 'Failed to load aircon data', severity: 'error' });
        return;
      }
      if (data) setAircon(data);
      if (data) {
        setQuietNightMode(!!data.energySaving);
        // Simulated access to paired accounts count (placeholder field)
        const ghCount = data._raw?.system?.googleHomePairedAccounts || 0;
        setPairedGoogleAccounts(ghCount);
      }
    });
    return () => unsub();
  }, []);

  const handleAction = (action) => {
    switch (action) {
      case 'renameAircon':
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

  const submitRenameAircon = async () => {
    if (!newAirconName) return setSnackbar({ open: true, message: 'Name cannot be empty', severity: 'warning' });
    setSaving(true);
    try {
      await ApiService.updateAircon({ name: newAirconName });
      setSnackbar({ open: true, message: 'Aircon renamed', severity: 'success' });
      setRenameAirconOpen(false);
      // give ApiService a moment to refresh; local cache will update subscribers
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

  return (
    <StyledPaper elevation={1}>
      <Box display="flex" flexDirection="column">
        <Typography variant="h6" sx={{ mb: 2 }}>{aircon?.zoneName || 'Advanced Setup'}</Typography>
        <Box mb={1}>
          <Chip label={aircon?.systemStatus === 'active' ? 'System Active' : 'Standby'} color={aircon?.systemStatus === 'active' ? 'success' : 'default'} size="small" />
        </Box>


        <Divider sx={{ my: 3 }} />

        {/* Toggles Section */}
        <Typography variant="subtitle1" gutterBottom>System Options</Typography>
        <Grid container spacing={2}>
          <Grid item xs={12} md={4}>
            <FormControlLabel control={<Switch checked={showMeasuredTemps} onChange={(e) => { setShowMeasuredTemps(e.target.checked); setSnackbar({ open: true, message: 'Measured temps toggle (placeholder)', severity: 'info' }); }} />} label="Show Measured Temps" />
          </Grid>
          <Grid item xs={12} md={4}>
            <FormControlLabel control={<Switch checked={quietNightMode} onChange={(e) => { setQuietNightMode(e.target.checked); setSnackbar({ open: true, message: 'Quiet Night Mode updated (placeholder)', severity: 'info' }); }} />} label="Quiet Night Mode" />
          </Grid>
          <Grid item xs={12} md={4}>
            <FormControlLabel control={<Switch checked={autoFanSpeed} onChange={(e) => { setAutoFanSpeed(e.target.checked); setSnackbar({ open: true, message: 'Auto Fan Speed updated (placeholder)', severity: 'info' }); }} />} label="Auto Fan Speed" />
          </Grid>
        </Grid>

        <Box mt={2}>
          <Typography variant="subtitle2">Comfort Mode</Typography>
          <Grid container spacing={1}>
            <Grid item>
              <Button size="small" variant={comfortMode === 'myZone' ? 'contained' : 'outlined'} onClick={() => { setComfortMode('myZone'); setSnackbar({ open: true, message: 'Comfort: My Zone (placeholder)', severity: 'info' }); }}>My Zone</Button>
            </Grid>
            <Grid item>
              <Button size="small" variant={comfortMode === 'climateControl' ? 'contained' : 'outlined'} onClick={() => { setComfortMode('climateControl'); setSnackbar({ open: true, message: 'Comfort: Climate Control (placeholder)', severity: 'info' }); }}>Climate Control</Button>
            </Grid>
            <Grid item>
              <Button size="small" variant={comfortMode === 'myMode' ? 'contained' : 'outlined'} onClick={() => { setComfortMode('myMode'); setSnackbar({ open: true, message: 'Comfort: My Mode (placeholder)', severity: 'info' }); }}>My Mode</Button>
            </Grid>
          </Grid>
        </Box>
        <Box mt={2}>
            <Grid container spacing={2}>
        <Grid item xs={12} md={12}>
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Box display="flex" alignItems="center" gap={1}>
                <SecurityIcon fontSize="small" />
                <Typography variant="caption" color="text.secondary">AirCon Name</Typography>
              </Box>
              <Typography variant="body2">Name: {aircon?.name}</Typography>
              <Button size="small" sx={{ mt: 1 }} onClick={() => handleAction('renameAircon')}>Edit</Button>
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
              <Typography variant="caption" color="text.secondary">Postcode</Typography>
              <Typography variant="body2">{aircon?._raw?.system?.postCode || 'Not set'}</Typography>
              <Button size="small" sx={{ mt: 1 }} onClick={() => setSnackbar({ open: true, message: 'Set postcode (placeholder)', severity: 'info' })}>Edit</Button>
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
          <Typography variant="caption" color="text.secondary">Placeholder only – backend activation not yet implemented.</Typography>
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
          <Typography variant="caption" color="text.secondary">Placeholder only – no backend call yet.</Typography>
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
