import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { VitePWA } from 'vite-plugin-pwa'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      registerType: 'autoUpdate',
      workbox: {
        globPatterns: ['**/*.{js,css,html,ico,png,svg}']
      },
      manifest: {
        name: 'MyPlace HVAC Control System',
        short_name: 'MyPlace',
        description: 'Monitor and control your home environment',
        theme_color: '#1976d2',
        icons: [
          {
            src: 'splash/ios/192.png',
            sizes: '192x192',
            type: 'image/png'
          },
          {
            src: 'splash/ios/512.png',
            sizes: '512x512',
            type: 'image/png'
          }
        ]
      }
    })
  ],
  esbuild: {
    loader: 'jsx',
    include: /src\/.*\.[jt]sx?$/,
    exclude: []
  },
  optimizeDeps: {
    esbuildOptions: {
      loader: {
        '.js': 'jsx'
      }
    }
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:2025',
        changeOrigin: true,
        secure: false,
        // Strip the /api prefix so frontend calls to /api/foo -> backend /foo
        rewrite: (path) => path.replace(/^\/api/, ''),
      }
    }
  },
  build: {
    outDir: 'build',
    assetsDir: 'static'
  }
})
