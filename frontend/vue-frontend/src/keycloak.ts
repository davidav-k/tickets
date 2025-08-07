import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:9090',
  realm: 'tickets',
  clientId: 'vue-frontend',
});

export default keycloak;
