import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server:{
    proxy: {
      '/api': {  // 代理路径前缀（根据业务命名）
        target: 'http://localhost:9899',  // 目标服务器地址
        changeOrigin: true,  // 修改请求头中的 Origin 为目标域名
        rewrite: (path) => path.replace(/^\/api/, '') // 路径重写（可选）
      }
    }
  }
})
