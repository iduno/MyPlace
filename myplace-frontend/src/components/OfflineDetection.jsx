import React, { useState, useEffect } from 'react';
import { Snackbar, Alert, Button } from '@mui/material';
import WifiOffIcon from '@mui/icons-material/WifiOff';
import SignalWifiStatusbarConnectedNoInternet4Icon from '@mui/icons-material/SignalWifiStatusbarConnectedNoInternet4';

const OfflineDetection = () => {
  const [isOnline, setIsOnline] = useState(navigator.onLine);
  const [showOfflineMessage, setShowOfflineMessage] = useState(!navigator.onLine);

  useEffect(() => {
    // Function to update online status
    const handleStatusChange = () => {
      const condition = navigator.onLine;
      setIsOnline(condition);
      if (!condition) {
        setShowOfflineMessage(true);
      }
    };

    // Listen for online/offline events
    window.addEventListener('online', handleStatusChange);
    window.addEventListener('offline', handleStatusChange);

    return () => {
      window.removeEventListener('online', handleStatusChange);
      window.removeEventListener('offline', handleStatusChange);
    };
  }, []);

  return (
    <>
      {!isOnline && (
        <div style={{
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          background: '#f44336',
          color: 'white',
          padding: '8px 16px',
          textAlign: 'center',
          zIndex: 1100,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center'
        }}>
          <WifiOffIcon style={{ marginRight: '8px' }} />
          You are offline. Some features may be limited.
        </div>
      )}
      
      <Snackbar
        open={showOfflineMessage && !isOnline}
        autoHideDuration={6000}
        onClose={() => setShowOfflineMessage(false)}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert 
          severity="warning" 
          variant="filled"
          icon={<SignalWifiStatusbarConnectedNoInternet4Icon />}
          action={
            <Button color="inherit" size="small" onClick={() => setShowOfflineMessage(false)}>
              OK
            </Button>
          }
        >
          You are currently offline. The app will continue to work with limited functionality.
        </Alert>
      </Snackbar>
    </>
  );
};

export default OfflineDetection;
