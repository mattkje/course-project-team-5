<script setup>

import {onMounted, ref} from "vue";




</script>

<template>

  <div id="background" class="background">
    <h1>Welcome Back User!</h1>
  </div>

  <div class="content">

  </div>

</template>

<style scoped>

.background {
  top: 0;
  height: min-content;
  background-color: var(--light-3);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

}

.planet1 {
  height: 15vh;
  transform: translateZ(-200px);
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
  transform: scaleX(1.01) translateY(2px) translateZ(-100px);
  margin: 0;
  padding: 0;

}
</style>