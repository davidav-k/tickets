<template>
  <div>
    <button @click="fetchTestTickets">Получить тикеты</button>
    <pre v-if="tickets">{{ tickets }}</pre>
    <div v-if="error" style="color: red">Ошибка: {{ error }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import keycloak from '../keycloak';

const tickets = ref(null);
const error = ref('');

async function fetchTestTickets() {
  error.value = '';
  tickets.value = null;
  try {
    if (!keycloak.authenticated) {
      await keycloak.login();
      return;
    }
    await keycloak.updateToken(30);
    const token = keycloak.token;
    const response = await axios.get('http://localhost:8088/api/tickets', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    tickets.value = response.data;
  } catch (err: any) {
    error.value = err?.message || 'Ошибка запроса';
  }
}
</script>
