import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import tsconfigPaths from "vite-tsconfig-paths";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), tsconfigPaths()],
  server: {
    host: "0.0.0.0",
    port: 3000,
    proxy: {
      "/api": {
        target: "https://rest.cmhvscode.dev",
        changeOrigin: true,
        secure: false,
      },
    },
  },
  preview: {
    port: 3000,
  },
  build: {
    target: "esnext",
  },
});
