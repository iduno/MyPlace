/* eslint-disable no-restricted-globals */

// This service worker can be customized!
// See https://developers.google.com/web/tools/workbox/modules
// for the list of available Workbox modules, or add any other
// code you'd like.
// You can also remove this file if you'd prefer not to use a
// service worker, and the Workbox build step will be skipped.

const CACHE_NAME = 'myplace-cache-v1';
const urlsToCache = [
  '/',
  '/index.html',
  '/static/js/main.chunk.js',
  '/static/js/0.chunk.js',
  '/static/js/bundle.js',
  '/manifest.json',
  // Local Material Icons assets
  '/fonts/material-icons.css',
  '/fonts/material-icons.woff2',
  // iOS icons
  '/splash/ios/16.png',
  '/splash/ios/32.png',
  '/splash/ios/64.png',
  '/splash/ios/128.png',
  '/splash/ios/152.png',
  '/splash/ios/167.png',
  '/splash/ios/180.png',
  '/splash/ios/192.png',
  '/splash/ios/512.png',
  // Android icons
  '/splash/android/android-launchericon-48-48.png',
  '/splash/android/android-launchericon-72-72.png',
  '/splash/android/android-launchericon-96-96.png',
  '/splash/android/android-launchericon-144-144.png',
  '/splash/android/android-launchericon-192-192.png',
  '/splash/android/android-launchericon-512-512.png'
];

// Install a service worker
self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then(cache => {
        console.log('Opened cache');
        return cache.addAll(urlsToCache);
      })
  );
  // Force the waiting service worker to become the active service worker
  self.skipWaiting();
});

// Cache and return requests
self.addEventListener('fetch', event => {
  // Skip cross-origin requests, like those for Google Analytics
  if (event.request.url.startsWith(self.location.origin)) {
    event.respondWith(
      caches.match(event.request)
        .then(cachedResponse => {
          if (cachedResponse) {
            return cachedResponse;
          }
          
          return fetch(event.request).then(
            response => {
              // Return the response as-is for non-GET requests or for API calls
              if (
                !response || 
                response.status !== 200 || 
                response.type !== 'basic' ||
                event.request.url.includes('/getSystemData') ||
                event.request.url.includes('/setAircon')
              ) {
                return response;
              }

              // Clone the response since it can only be consumed once
              const responseToCache = response.clone();

              caches.open(CACHE_NAME)
                .then(cache => {
                  cache.put(event.request, responseToCache);
                });

              return response;
            }
          );
        })
    );
  }
});

// Update a service worker
self.addEventListener('activate', event => {
  const cacheWhitelist = [CACHE_NAME];
  event.waitUntil(
    caches.keys().then(cacheNames => {
      return Promise.all(
        cacheNames.map(cacheName => {
          if (cacheWhitelist.indexOf(cacheName) === -1) {
            return caches.delete(cacheName);
          }
          return null;
        })
      );
    })
  );
  // Allows a service worker to set itself as the controller for all clients within scope
  self.clients.claim();
});
