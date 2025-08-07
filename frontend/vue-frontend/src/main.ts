import keycloak from './keycloak';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';

keycloak.init({ onLoad: 'login-required' }).then((authenticated) => {
  if (authenticated) {
    const app = createApp(App);
    app.use(createPinia());
    app.use(router);
    app.provide('keycloak', keycloak);
    app.mount('#app');
  } else {
    keycloak.login();
  }
}).catch((err) => {
  console.error('Keycloak init failed', err);
});

