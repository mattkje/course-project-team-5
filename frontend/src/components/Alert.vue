<script setup>
import { ref } from 'vue'

const props = defineProps({
  title: String,
  message: String,
  buttons: Array
});

const emit = defineEmits(['buttonClicked']);

const buttonClicked = (button) => {
  emit('buttonClicked', button);
};
</script>

<template>
  <div id="overlay">
    <div id="alertBox">
      <h2>{{ title }}</h2>
      <p>{{ message }}</p>
      <div id="buttons">
        <button class="fancy-button" v-for="(button, index) in buttons" :key="index" @click="buttonClicked(button)"> {{ button }}</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
#overlay {
  position: fixed;
  display: block;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  z-index: 2;
}

#alertBox {
  position: fixed;
  display: block;
  width: 500px;
  height: max-content;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  background: linear-gradient(#f3f3ff -0%, #ede9ff 100%);
  z-index: 3;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0,0,0,0.5);
  padding: 20px;
}

.fancy-button{
  display: inline-block;
  width: 100px;
  height: 40px;
}

h2 {
  text-align: center;
}

p {
  padding: 10px 0 0 0;
  height: 90px;
  text-align: center;
}

#buttons {
  margin-top: 60px;
  display: flex;
  justify-content: space-around;
}
</style>