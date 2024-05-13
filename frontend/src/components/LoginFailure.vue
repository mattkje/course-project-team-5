<script setup>

</script>

<template>
  <div class="center">
    <img class="center-illustration" src="/fetchingerror.svg"/>
    <h1>There was an error fetching your user data. Try logging in</h1>
    <router-link to="/login" class="fancy-button">Log in</router-link>
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