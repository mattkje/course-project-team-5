<script setup>

</script>

<template>
  <div class="center">
    <p>There was an error fetching your user data. Try logging in</p>
    <router-link to="/login" class="standard-button">Log in</router-link>
  </div>
</template>

<style scoped>
.center {
  background-color: var(--light-1);
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50vh;
  flex-direction: column;
}

p, router-link{
  color: #151515;
}
</style>