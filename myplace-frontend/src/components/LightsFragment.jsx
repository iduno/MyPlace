import React, { useState, useEffect } from 'react';
import {
  Box,
  Typography,
  Grid,
  Paper,
  Card,
  Switch,
  Slider,
  Snackbar,
  Alert
} from '@mui/material';
import { styled } from '@mui/system';
import LightbulbIcon from '@mui/icons-material/Lightbulb';
import LightbulbOutlinedIcon from '@mui/icons-material/LightbulbOutlined';
import ApiService from '../services/ApiService';

const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(2),
  backgroundColor: '#f5f5f5',
  height: '100%',
  borderRadius: 8,
}));

const LightsFragment = () => {
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  // Slider values while the user is dragging, so polling doesn't fight the drag
  const [dragValues, setDragValues] = useState({});
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  useEffect(() => {
    ApiService.startSystemPolling();
    return ApiService.subscribeSystem((raw, { error }) => {
      if (error) {
        if (!ApiService.getCachedSystem()) {
          setError('Failed to fetch lights data');
          setLoading(false);
        }
        return;
      }
      if (!raw) return;
      setGroups(ApiService.getLightGroups(raw));
      setLoading(false);
      setError(null);
    });
  }, []);

  const updateLight = async (lightId, changes) => {
    setGroups(prev => prev.map(g => ({
      ...g,
      lights: g.lights.map(l => (l.id === lightId ? { ...l, ...changes } : l)),
    })));
    try {
      await ApiService.setLight({ id: lightId, ...changes });
    } catch (err) {
      setSnackbar({ open: true, message: ApiService.getErrorMessage(err), severity: 'error' });
      ApiService.refreshSystem();
    }
  };

  const handleSliderCommit = (lightId, value) => {
    setDragValues(prev => {
      const next = { ...prev };
      delete next[lightId];
      return next;
    });
    updateLight(lightId, { value });
  };

  return (
    <StyledPaper elevation={1}>
      {loading ? (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
          <Typography variant="h6">Loading lights...</Typography>
        </Box>
      ) : error ? (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="300px">
          <Typography variant="h6" color="error">{error}</Typography>
        </Box>
      ) : (
        <>
          <Typography variant="h5" sx={{ fontWeight: 'medium', color: '#424242', mb: 2 }}>
            Lights
          </Typography>

          {groups.map(group => {
            const onCount = group.lights.filter(l => l.state === 'on').length;
            return (
              <Box key={group.id} mb={3}>
                <Box display="flex" justifyContent="space-between" alignItems="baseline" mb={1}>
                  <Typography variant="subtitle2" sx={{ fontWeight: 'bold', letterSpacing: '.5px', textTransform: 'uppercase' }}>
                    {group.name}
                  </Typography>
                  <Typography variant="caption" color="text.secondary">
                    {onCount} of {group.lights.length} on
                  </Typography>
                </Box>

                <Grid container spacing={1}>
                  {group.lights.map(light => {
                    const isOn = light.state === 'on';
                    const isDimmer = light.deviceType === 'dimmer';
                    const value = dragValues[light.id] ?? light.value ?? 100;
                    return (
                      <Grid key={light.id} size={{ xs: 12, sm: 6, md: 4 }}>
                        <Card
                          sx={{
                            p: 1.5,
                            height: '100%',
                            backgroundColor: isOn ? '#ffffff' : '#fafafa',
                            borderLeft: theme => `3px solid ${isOn ? theme.palette.primary.main : '#e0e0e0'}`,
                          }}
                        >
                          <Box display="flex" alignItems="center" justifyContent="space-between">
                            <Box display="flex" alignItems="center" sx={{ minWidth: 0 }}>
                              {isOn
                                ? <LightbulbIcon sx={{ color: 'warning.main', mr: 1 }} />
                                : <LightbulbOutlinedIcon color="disabled" sx={{ mr: 1 }} />}
                              <Typography
                                variant="subtitle2"
                                title={light.name}
                                sx={{ fontWeight: 500, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}
                              >
                                {light.name}
                              </Typography>
                            </Box>
                            <Switch
                              checked={isOn}
                              onChange={() => updateLight(light.id, { state: isOn ? 'off' : 'on' })}
                              color="primary"
                            />
                          </Box>
                          {isDimmer && (
                            <Box display="flex" alignItems="center" gap={2} px={1}>
                              <Slider
                                size="small"
                                min={5}
                                max={100}
                                step={5}
                                value={value}
                                onChange={(e, v) => setDragValues(prev => ({ ...prev, [light.id]: v }))}
                                onChangeCommitted={(e, v) => handleSliderCommit(light.id, v)}
                                aria-label={`${light.name} brightness`}
                              />
                              <Typography variant="caption" color="text.secondary" sx={{ minWidth: 36, textAlign: 'right' }}>
                                {value}%
                              </Typography>
                            </Box>
                          )}
                        </Card>
                      </Grid>
                    );
                  })}
                </Grid>
              </Box>
            );
          })}
        </>
      )}

      <Snackbar
        open={snackbar.open}
        autoHideDuration={6000}
        onClose={() => setSnackbar(s => ({ ...s, open: false }))}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={() => setSnackbar(s => ({ ...s, open: false }))} severity={snackbar.severity} sx={{ width: '100%' }}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </StyledPaper>
  );
};

export default LightsFragment;
