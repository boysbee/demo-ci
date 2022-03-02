// playwright.config.ts
import { PlaywrightTestConfig } from '@playwright/test';

const config: PlaywrightTestConfig = {
  use: {
    baseURL: 'http://localhost:3001/api',
  },
  reporter: [ ['html', { outputFolder: 'reports' }] ],
};
export default config;