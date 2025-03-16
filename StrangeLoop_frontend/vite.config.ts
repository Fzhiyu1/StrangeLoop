import  { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import {VitePWA} from "vite-plugin-pwa";


// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(),
    VitePWA({
      registerType: 'autoUpdate', // 自动更新 Service Worker
      includeAssets: ['favicon.ico', 'apple-touch-icon.png', 'mask-icon.svg'], // 需要缓存的静态资源
      manifest: {
        name: '怪圈', // 应用名称
        short_name: '怪圈', // 短名称
        description: '怪圈', // 描述
        theme_color: '#ffffff', // 主题颜色
        icons: [
          {
            src: 'img.png', // 图标路径
            sizes: '192x192', // 图标尺寸
            type: 'image/png', // 图标类型
          }
        ],
      },
      workbox: {
        globPatterns: ['**/*.{tsx,jsx,vue,d.ts,ts,js,css,html,ico,png,svg}'], // 需要缓存的资源类型
        runtimeCaching: [
          {
            // https://api.bianxie.ai/v1/chat/completions
            urlPattern: /^https:\/\/api\.bianxie\/.*/i, // 缓存 API 请求
            handler: 'NetworkFirst', // 优先从网络获取
            options: {
              cacheName: 'api-cache',
              expiration: {
                maxEntries: 10,
                maxAgeSeconds: 60 * 60 * 24, // 缓存 1 天
              },
            },
          },
        ],
      },
    })],
  resolve:{
    alias:{
      '@': path.resolve(__dirname, 'src'),
    }
  },
  server: {
    port: 80,
    cors: true, // 允许所有跨域请求
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:9090',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }



})
