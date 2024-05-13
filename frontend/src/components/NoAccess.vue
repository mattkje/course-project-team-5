<script setup>

</script>

<template>
  <div class="center">
    <img class="center-illustration" src="/unauthorized.svg"/>
    <h1>You don't have access to this page!</h1>
    <router-link to="/" class="fancy-button">Back to main page</router-link>
  </div>
</template>

<style scoped>
.center {
  background-color: var(--light-3);
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  flex-direction: column;
}

.center-illustration {
  width: 30%;
}
</style>