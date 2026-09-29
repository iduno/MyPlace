import React, { useState, useEffect } from 'react';
import { 
  Container, Box, CssBaseline, Typography, AppBar, Toolbar,
  Tabs, Tab, Paper, useMediaQuery
} from '@mui/material';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import AcUnitIcon from '@mui/icons-material/AcUnit';
import DashboardIcon from '@mui/icons-material/Dashboard';
import LightbulbIcon from '@mui/icons-material/Lightbulb';
import SettingsIcon from '@mui/icons-material/Settings';
import AirconFragment from './components/AirconFragment';
import ZoneFragment from './components/ZoneFragment';
import LightsFragment from './components/LightsFragment';
import AirconUnitSelector from './components/AirconUnitSelector';
import SetupFragment from './components/SetupFragment';
import OfflineDetection from './components/OfflineDetection';
import PWAUpdateNotification from './components/PWAUpdateNotification';
import ApiService from './services/ApiService';
import './App.css';

// Create a custom theme to match the Android app
const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    background: {
      default: '#f5f5f5',
    },
    text: {
      primary: '#212121',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
  },
});

// Tab panel component to wrap each fragment
function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`myplace-tabpanel-${index}`}
      aria-labelledby={`myplace-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ pt: 3 }}>
          {children}
        </Box>
      )}
    </div>
  );
}

function a11yProps(index) {
  return {
    id: `myplace-tab-${index}`,
    'aria-controls': `myplace-tabpanel-${index}`,
  };
}

function App() {
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('aircon');
  const [aircons, setAircons] = useState([]);
  const [selectedAirconId, setSelectedAirconId] = useState(null);
  const [hasLights, setHasLights] = useState(false);
  const isPhone = useMediaQuery(theme.breakpoints.down('sm'));

  const handleTabChange = (event, newValue) => {
    setActiveTab(newValue);
  };

  const handleNavigate = (page) => {
    // Basic navigation mapping from SetupFragment
    switch (page) {
      case 'ZoneSetup':
        setActiveTab('zones');
        break;
      case 'AdvancedInfo':
        setActiveTab('aircon');
        break;
      case 'CloseApp':
        // no-op in web, could show a message
        console.log('CloseApp requested');
        break;
      default:
        // fallback: log
        console.log('Navigate to', page);
    }
  };

  // Track the aircon units and whether lights exist from the shared system poll
  useEffect(() => {
    ApiService.startSystemPolling();
    return ApiService.subscribeSystem((raw, { error }) => {
      if (error || !raw) {
        setLoading(false);
        return;
      }
      setAircons(ApiService.getAirconList(raw));
      setHasLights(ApiService.getLightGroups(raw).length > 0);
      setLoading(false);
    });
  }, []);

  const activeAirconId = aircons.find(a => a.id === selectedAirconId)?.id ?? aircons[0]?.id ?? null;
  const currentTab = activeTab === 'lights' && !hasLights ? 'aircon' : activeTab;
  const showUnitSelector = aircons.length > 1 && ['aircon', 'zones', 'setup'].includes(currentTab);
  const iconPosition = isPhone ? 'top' : 'start';
  const tabSx = isPhone ? { minHeight: 64, minWidth: 0, px: 0.5, fontSize: '0.75rem' } : undefined;

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <OfflineDetection />
      <PWAUpdateNotification />
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              MyPlace
            </Typography>
          </Toolbar>
        </AppBar>

        <Container maxWidth="lg" sx={{ mt: 2, mb: 4 }}>
          {/* Navigation Tabs */}
          <Paper sx={{ borderRadius: '8px 8px 0 0' }}>
            <Tabs 
              value={currentTab} 
              onChange={handleTabChange} 
              variant="fullWidth"
              sx={{
                '& .MuiTabs-indicator': {
                  height: 3,
                },
              }}
            >
              <Tab 
                value="aircon"
                label={isPhone ? 'Aircon' : 'Air Conditioner'} 
                icon={<AcUnitIcon />} 
                iconPosition={iconPosition}
                sx={tabSx}
                {...a11yProps('aircon')} 
              />
              <Tab 
                value="zones"
                label="Zones" 
                icon={<DashboardIcon />} 
                iconPosition={iconPosition}
                sx={tabSx}
                {...a11yProps('zones')} 
              />
              {hasLights && (
                <Tab
                  value="lights"
                  label="Lights"
                  icon={<LightbulbIcon />}
                  iconPosition={iconPosition}
                  sx={tabSx}
                  {...a11yProps('lights')}
                />
              )}
              <Tab
                value="setup"
                label="Setup"
                icon={<SettingsIcon />}
                iconPosition={iconPosition}
                sx={tabSx}
                {...a11yProps('setup')}
              />
            </Tabs>
          </Paper>

          {showUnitSelector && (
            <AirconUnitSelector
              aircons={aircons}
              selectedId={activeAirconId}
              onSelect={setSelectedAirconId}
            />
          )}
          
          {/* Loading State */}
          {loading ? (
            <Box sx={{ p: 4, display: 'flex', justifyContent: 'center' }}>
              <Typography>Loading MyPlace data...</Typography>
            </Box>
          ) : (
            <>
              {/* Tab Panels (keyed by aircon so state resets when switching units) */}
              <TabPanel value={currentTab} index="aircon">
                <AirconFragment key={activeAirconId} airconId={activeAirconId} />
              </TabPanel>
              
              <TabPanel value={currentTab} index="zones">
                <ZoneFragment key={activeAirconId} airconId={activeAirconId} />
              </TabPanel>

              {hasLights && (
                <TabPanel value={currentTab} index="lights">
                  <LightsFragment />
                </TabPanel>
              )}

              <TabPanel value={currentTab} index="setup">
                <SetupFragment key={activeAirconId} airconId={activeAirconId} onNavigate={handleNavigate} />
              </TabPanel>
            </>
          )}
        </Container>
      </Box>
    </ThemeProvider>
  );
}

export default App;
