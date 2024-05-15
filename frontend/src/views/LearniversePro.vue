<script setup>

import Subscription from "@/components/Subscription.vue";
</script>

<template>
  <div id="background" class="background">
    <img class="planet" src="/pro.svg">
    <h2>Learniverse Pro</h2>
  </div>
  <Subscription style="padding: 100px 0 200px 0"/>
</template>

<style scoped>
@media (max-width: 600px) {

  #planet {
    height: 120px;
    animation: ease-in-out pulse 5s infinite;
  }
}

.background {
  top: 0;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(133, 132, 145, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;
}

.background h2 {
  color: whitesmoke;
  font-size: 40px;
  margin: 0;
  padding-bottom: 30px;
  animation: ease-in-out fadeIn 1.5s;
}

.planet {
  height: 300px;
  animation: ease-out pulse 5s infinite;
}


@keyframes pulse {
  0% {
    transform: scale(0.5);
  }
  50% {
    transform: scale(0.6);
  }
  100% {
    transform: scale(0.5);
  }
}


@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }}

.purchase-button span {
  font-weight: 700;
}

</style>
