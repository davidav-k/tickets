import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import keycloak from '../keycloak';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
      meta: { requiresAuth: true },
    },
  ],
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !keycloak.authenticated) {
    keycloak.login();
  } else {
    next();
  }
});

export default router;
