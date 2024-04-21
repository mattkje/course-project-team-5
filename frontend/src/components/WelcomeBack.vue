<script setup>

import {onMounted, ref} from "vue";




</script>

<template>

  <div id="background" class="background">
    <div class="hero-container">
      <div class="hero-box">
        <img id="planet" class="planet1" src="/logo.svg" alt="Logo">
      </div>
    </div>
    <img class="bend" src="/bend.svg">
  </div>

  <div class="content">

  </div>

</template>

<style scoped>

.background {
  top: 0;
  height: 400px;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

}

.planet1 {
  height: 150px;
  animation: ease-out slideIn 1s forwards, ease-out pulse 5s infinite 1s;
}

.hero-container {
  display: flex;
  justify-content: center;
}

.hero-box {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.hero-text {
  font-family: 'Inter', sans-serif;
  font-size: 50px;
  text-transform: uppercase;
  text-align: center;
  font-style: normal;
  font-weight: 700;
  background: linear-gradient(180deg, #FFFFFF -148.55%, #9087EF 236.16%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  color: transparent;
  animation: ease-in-out fadeIn 1s;
}

@keyframes slideIn {
  0% {
    transform: translatex(-1000px) translateY(-100px) scale(1.2);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes pulse {
  0% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
  50% {
    transform: translatex(0) translateY(120px) scale(1.7);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }}

.bend {
  width: 100%;
  margin: 0;
  padding: 0;

}
</style>