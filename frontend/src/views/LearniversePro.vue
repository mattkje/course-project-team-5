<script setup>

import Subscription from "@/components/Subscription.vue";
import Alert from "@/components/Alert.vue";
import {ref} from "vue";

const showAlert = ref(false);

function handleButtonClick(button) {
  if (button === 'OK') {
    window.location.href = ('/login');
  }
  if (button === 'Cancel') {
    showAlert.value = false;
  }
}

function handleAlert() {
  showAlert.value = true;
}
</script>

<template>
  <meta name="description" content="Showcases all possible subscription that Learniverse provides">
  <div id="background" class="background">
    <img alt="planet" class="planet" src="/pro.svg">
    <h2>Learniverse Pro</h2>
    <Alert v-show="showAlert" title="Missing user" message="You must create a user to access this functionality" :buttons="['OK', 'Cancel']" @buttonClicked="handleButtonClick"></Alert>
    <Subscription  @show-alert="handleAlert"/>
    <div name="filler" style="height: 500px"></div>
  </div>

</template>

<style scoped>

.background {
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(133, 132, 145, 0.14) 100%);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;
}

.background h2 {
  font-family: "Bebas Neue", sans-serif;
  color: whitesmoke;
  font-size: 50px;
  margin: 0;
  animation: ease-in-out fadeIn 1.5s;
}

@media (max-width: 769px) {
  .background {
    background: #05060d;
  }
}

.planet {
  margin: 80px auto 10px auto;
  width: 100%;
  max-height: 200px;
  height: 100%;
  animation: ease-out pulse 5s infinite;
}


@keyframes pulse {
  0% {
    transform: scale(100%);
  }
  50% {
    transform: scale(110%);
  }
  100% {
    transform: scale(100%);
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
