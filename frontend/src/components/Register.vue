<script setup>
import {getCurrentInstance, ref} from 'vue';

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

const username = ref('');
const password = ref('');
const firstName = ref('');
const lastName = ref('');
const email = ref('');
const phoneNumber = ref('');

const register = async () => {
  const formData = {
    username: username.value,
    password: password.value,
    firstName: firstName.value,
    lastName: lastName.value,
    email: email.value,
    phoneNumber: phoneNumber.value,
  };

  try {
    const response = await fetch(API_URL + '/users/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    });

    if (!response.ok) {
      throw new Error('Registration failed');
    }

    alert('Registration successful');
  } catch (error) {
    alert(error.message);
  }
};
</script>

<template>
  <form @submit.prevent="register" id="registrationForm">
    <div class="login-container">
      <div class="login-box">
        <div class="login-field-box">
          <router-link to="/" class="header-button">
            <img class="logo" src="/logo.svg" alt="Connect">
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom">Connect</h4>
          </router-link>
          <div class="login-text-field">
            <label for="username">Username*:</label>
            <input v-model="username" class="login-prompt" type="text" id="username" name="username" placeholder="Enter a username" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="password">Password*:</label>
            <input v-model="password" class="login-prompt" type="text" id="password" name="password" placeholder="Enter a password" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="firstName">First Name*:</label>
            <input v-model="firstName" class="login-prompt" type="text" id="firstName" name="firstName" placeholder="Enter your first name" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="lastName">Last Name*:</label>
            <input v-model="lastName" class="login-prompt" type="text" id="lastName" name="lastName" placeholder="Enter your last name" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="email">Email*:</label>
            <input v-model="email" class="login-prompt" type="text" id="email" name="email" placeholder="Enter your email" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="phoneNumber">Phone Number:</label>
            <input v-model="phoneNumber" class="login-prompt" type="text" id="phoneNumber" name="phoneNumber" placeholder="Enter your phone number" required autofocus/>
          </div>

          <button class="standard-button" type="submit">Create user</button>

          <p>Already have an account?</p>
          <a class="standard-button" href="/login" type="submit">Log in here</a>
        </div>
        <img class="login-image" src="/signup.svg" alt="Connect">
      </div>
    </div>
  </form>
</template>

<style scoped>

</style>
