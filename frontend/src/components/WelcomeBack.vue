<script setup>

import {onMounted, ref} from "vue";


onMounted(() => {
  showGreeting()
});


function showGreeting() {
  document.getElementById("part1").innerText = "Welcome Back!";
}


</script>

<template>

  <div id="background" class="background">
    <div class="hero-container">
      <div class="hero-box">
        <h1 id="part1" class="hero-text">.</h1>
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
  height: 450px;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

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
  margin-top: 200px;
  animation: ease-in-out fadeIn 1s;
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