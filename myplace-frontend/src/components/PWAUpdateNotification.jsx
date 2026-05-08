import React, { useState, useEffect } from 'react';
import { Box, Button, Snackbar, Alert } from '@mui/material';
import SystemUpdateIcon from '@mui/icons-material/SystemUpdate';

function PWAUpdateNotification() {
  const [newVersionAvailable, setNewVersionAvailable] = useState(false);
  const [registration, setRegistration] = useState(null);

  useEffect(() => {
    // Check if service workers are supported
    if ('serviceWorker' in navigator) {
      // Listen for new service worker updates
      const listenForWaitingServiceWorker = (reg) => {
        setRegistration(reg);
        
        // If there's already a waiting service worker
        if (reg.waiting) {
          setNewVersionAvailable(true);
        }

        // Listen for state changes to detect when a new version is waiting
        const listenForStateChange = (reg) => {
          if (reg.installing) {
            reg.installing.addEventListener('statechange', function() {
              if (this.state === 'installed' && navigator.serviceWorker.controller) {
                setNewVersionAvailable(true);
              }
            });
          }
        };

        // Listen for updates
        reg.addEventListener('updatefound', () => listenForStateChange(reg));
      };

      // Get the service worker registration
      navigator.serviceWorker.getRegistration().then((reg) => {
        if (reg) {
          listenForWaitingServiceWorker(reg);
        }
      });
    }
  }, []);

  // Function to update the service worker and refresh the page
  const updateServiceWorker = () => {
    if (registration && registration.waiting) {
      // Send message to the waiting service worker
      registration.waiting.postMessage({ type: 'SKIP_WAITING' });
      
      // Add listener to reload the page when the new service worker activates
      navigator.serviceWorker.addEventListener('controllerchange', () => {
        window.location.reload();
      });
    }
  };

  return (
    <Snackbar
      open={newVersionAvailable}
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
    >
      <Alert 
        severity="info"
        icon={<SystemUpdateIcon />}
        action={
          <Button 
            color="inherit" 
            size="small" 
            onClick={updateServiceWorker}
            variant="outlined"
            sx={{ fontWeight: 'bold' }}
          >
            Update Now
          </Button>
        }
      >
        A new version of MyPlace is available!
      </Alert>
    </Snackbar>
  );
}

export default PWAUpdateNotification;
