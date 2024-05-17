<template>
  <div class="background" id="background">
    <img class ="planet" src="/whiteMoon.png">
    </div>
    <div class="course-section">

      <div class="title">
        <h1>Your cart:</h1>
        <p class="description">{{ cartText }}</p>
      </div>

      <ShoppingCart/>
    </div>
</template>

<script setup>

import { ref } from 'vue';
import ShoppingCart from "@/components/ShoppingCart.vue";

const cartText = ref('Your cart is empty');



</script>


<style scoped>

.title {
  border: none;
  border-radius: 15px;
  width: 60%;
  justify-content: center;
  align-items: center;
  text-decoration: none;
  margin: 0 auto;
}

.course-section {
  padding-top: 20px;
  background-color: var(--light-3);
  margin: 0;
}

.description {
  color: #282828;
  font-size: 20px;
  margin: 0 auto;
}

.background {
  top: 0;
  height: min-content;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

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
  }
}

.logo {
  text-align: center;
}


.number {
  font-size: 40px;
}

.buttons {
  font-size: 40px;
  width: 80px;
}

</style>