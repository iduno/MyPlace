import React from 'react';
import { Box, Paper, Tabs, Tab } from '@mui/material';

// Scrollable unit strip: swipeable on phones, fits several units on tablets.
const AirconUnitSelector = ({ aircons, selectedId, onSelect }) => (
  <Paper sx={{ mt: 1, borderRadius: 2 }}>
    <Tabs
      value={selectedId}
      onChange={(event, value) => onSelect(value)}
      variant="scrollable"
      scrollButtons="auto"
      allowScrollButtonsMobile
      aria-label="aircon units"
    >
      {aircons.map(ac => (
        <Tab
          key={ac.id}
          value={ac.id}
          sx={{ textTransform: 'none', minHeight: 48 }}
          label={
            <Box display="flex" alignItems="center" gap={1}>
              <Box
                sx={{
                  width: 10,
                  height: 10,
                  borderRadius: '50%',
                  flexShrink: 0,
                  backgroundColor: ac.power ? 'success.main' : 'grey.400',
                }}
              />
              <span>{ac.name}</span>
            </Box>
          }
        />
      ))}
    </Tabs>
  </Paper>
);

export default AirconUnitSelector;
