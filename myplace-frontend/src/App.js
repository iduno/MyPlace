import React, { useState, useEffect } from 'react';
import { 
  Container, Box, CssBaseline, Typography, AppBar, Toolbar,
  Tabs, Tab, Paper
} from '@mui/material';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import AcUnitIcon from '@mui/icons-material/AcUnit';
import DashboardIcon from '@mui/icons-material/Dashboard';
import AirconFragment from './components/AirconFragment';
import ZoneFragment from './components/ZoneFragment';
import OfflineDetection from './components/OfflineDetection';
import PWAUpdateNotification from './components/PWAUpdateNotification';
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
  // State for aircon data and loading state
  const [airconData, setAirconData] = useState(null);
  const [loading, setLoading] = useState(true);
  // Tab state
  const [activeTab, setActiveTab] = useState(0);

  // Handle tab change
  const handleTabChange = (event, newValue) => {
    setActiveTab(newValue);
  };

  // Fetch data on component mount
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Replace this with your actual API call
        // Example: const response = await fetch('/api/aircon');
        // const data = await response.json();
        
        // Simulated data
        const data = {
          name: "Living Room",
          power: false,
          temperature: 24,
          fanSpeed: "medium",
          timerEnabled: false,
          timerValue: 0
        };
        
        setAirconData(data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching aircon data:', error);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

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
              value={activeTab} 
              onChange={handleTabChange} 
              variant="fullWidth"
              sx={{
                '& .MuiTabs-indicator': {
                  height: 3,
                },
              }}
            >
              <Tab 
                label="Air Conditioner" 
                icon={<AcUnitIcon />} 
                iconPosition="start"
                {...a11yProps(0)} 
              />
              <Tab 
                label="Zones" 
                icon={<DashboardIcon />} 
                iconPosition="start"
                {...a11yProps(1)} 
              />
            </Tabs>
          </Paper>
          
          {/* Loading State */}
          {loading ? (
            <Box sx={{ p: 4, display: 'flex', justifyContent: 'center' }}>
              <Typography>Loading MyPlace data...</Typography>
            </Box>
          ) : (
            <>
              {/* Tab Panels */}
              <TabPanel value={activeTab} index={0}>
                <AirconFragment />
              </TabPanel>
              
              <TabPanel value={activeTab} index={1}>
                <ZoneFragment />
              </TabPanel>
            </>
          )}
        </Container>
      </Box>
    </ThemeProvider>
  );
}

export default App;
