import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

// This address should be the address of the backend server.
// Content will not load on external machines if this address is localhost.
// Also check the address in the backend server before starting.
//app.config.globalProperties.$apiAddress = 'https://connect.learniverse.store:8443/api';
app.config.globalProperties.$apiAddress = 'https://localhost:8443/api';
app.use(router).mount('#app')