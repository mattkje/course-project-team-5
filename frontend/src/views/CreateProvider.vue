<script setup>
import {ref, onMounted, getCurrentInstance, defineEmits} from 'vue';
import Guidelines from "@/components/Guidelines.vue";
import {getAuthenticatedUser, hasRole} from "@/js/authentication";
import {sendApiRequest} from "@/js/requests";

const showGuidelinesModal = ref(false);
const provider = ref({
  name: ''
});
const emit = defineEmits(['navigate']);

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

function createProvider() {
  console.log(provider.value)
  sendApiRequest(API_URL,'POST', '/providers', onSuccess, provider.value, error);
}

onMounted(() => {
  initSite();
});

function initSite() {
  const currentUser = getAuthenticatedUser();
  if (!currentUser || !hasRole('ROLE_ADMIN')) {
    window.location.href = ('/no-access');
  }
}

function onSuccess() {
  alert('Provider created successfully!');
  resetForm();
}

function error() {
  alert('There was an error creating the provider. Please try again.');
}

function resetForm() {
  provider.value = {
    name: ''
  };
}

function removeCourse() {
  emit('navigateBack', 'courseManage');
}
</script>

<template>
  <div class="provider-section">

    <guidelines class="provider-form" v-if="showGuidelinesModal" @close="showGuidelinesModal = false" />

    <form @submit.prevent="createProvider" class="provider-form">
      <div class="form-group">
        <button type="button" class="back" @click="removeCourse">
          <img class="nav-icon" src="/angle-small-left.svg">
          <p>Back</p>
        </button>
        <label for="name">Name:</label>
        <input type="text" id="name" v-model="provider.name" required maxlength="255">
      </div>
      <button type="submit">Create Provider</button>
    </form>
  </div>
</template>

<style scoped>
.provider-section {
  background-color: var(--light-3);
  padding: 50px 0;
}

.provider-form {
  max-width: 900px;
  margin: auto auto 50px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 5px;
}

.form-group {
  margin-bottom: 20px;
}

.big-input {
  height: 600px;
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

button[type="submit"] {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: var(--base-1);
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

button[type="submit"]:hover {
  background-color: var(--base-2);
}

.formatting-tools {
  margin-top: 10px;
}

.formatting-tools button {
  margin-right: 5px;
  padding: 5px 10px;
  background-color: #ffffff;
  box-shadow: none;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
}

.formatting-tools button:hover {
  background-color: #e0e0e0;
}

.guideline-button{
  padding: 0;
  color: var(--base-1);
  background: none;
  border: none;

  &:hover {
    color: var(--base-2);
  }
}

#max-characters {
  font-size: 14px;
  color: var(--dark-1);
}

.content-div {
  display: flex;
  flex-flow: row;
  align-items: flex-end;
}

#max-characters {
  font-size: 10px;
  margin-bottom: 5px;
  padding-left: 5px;
}

.char-counter {
  display: flex;
  font-size: 14px;
  color: var(--dark-1);
}

.nav-icon {
  width: 23px;
  height: 23px;
}

.back {
  display: flex;
  background: none;
  flex-direction: row;
  justify-content: start;
  width: 10%;
  color: black;
}

button {

  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: #ffffff;
  font-size: 20px;
  background: #000000;
  border: none;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 70px;
  border-radius: 15px;
  cursor: pointer;
  margin-top: 10px;
  transition: all .5s;

  &:hover {
    transform: scale(1.01);
    transition-duration: .5s;
  }

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}
</style>