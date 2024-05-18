<script setup>


import { ref } from 'vue';
import { sendApiRequest } from '../js/requests.js';
import Alert from "@/components/Alert.vue";


const username = ref('');
const password = ref('');
const firstName = ref('');
const lastName = ref('');
const email = ref('');
const phoneNumber = ref('');
const repeatPassword = ref('');
const isUserCreated = ref(false);
const errorMessage = ref('');
const showAlert = ref(false);

const register = async () => {
  if(repeatPassword.value === password.value) {
    const signupData = {
      "username": username.value,
      "password": password.value,
      "email": email.value,
      "firstName": firstName.value,
      "lastName": lastName.value,
      "phoneNumber": phoneNumber.value
    };
    await sendApiRequest('POST', '/users/register', onSignupSuccess, signupData, onSignUpError);
  } else {
    alert("The password must match!")
  }
};

function onSignupSuccess() {
  isUserCreated.value = true;
}

function onSignUpError(error) {
  errorMessage.value = error;
  showAlert.value = true;
}

function handleButtonClick() {
  showAlert.value = false;
}

</script>

<template>
  <form v-if="!isUserCreated" @submit.prevent="register" id="registrationForm" method="post">
    <Alert v-if="showAlert" title="An error has occurred" :message="errorMessage" :buttons="[ 'OK' ]" @buttonClicked="handleButtonClick"></Alert>
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
            <input v-model="username" class="login-prompt" type="text" id="username" name="username" placeholder="Enter a username" maxlength="16" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="password">Password*:</label>
            <input v-model="password" class="login-prompt" type="password" id="password" name="password" placeholder="Enter a password" maxlength="64" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="repeatPassword">Repeat password*:</label>
            <input v-model="repeatPassword" class="login-prompt" type="password" id="repeatPassword" name="repeatPassword" placeholder="Repeat the password" maxlength="64" required autofocus>
          </div>
          <div class="login-text-field">
            <label for="firstName">First Name*:</label>
            <input v-model="firstName" class="login-prompt" type="text" id="firstName" name="firstName" placeholder="Enter your first name" maxlength="20" x required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="lastName">Last Name*:</label>
            <input v-model="lastName" class="login-prompt" type="text" id="lastName" name="lastName" placeholder="Enter your last name" maxlength="20" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="email">Email*:</label>
            <input v-model="email" class="login-prompt" type="text" id="email" name="email" placeholder="Enter your email" maxlength="45" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="phoneNumber">Phone Number:</label>
            <input v-model="phoneNumber" class="login-prompt" type="text" id="phoneNumber" name="phoneNumber" placeholder="Enter your phone number" maxlength="20" required autofocus/>
          </div>

          <button class="standard-button" type="submit" id="signup-button">Create user</button>

          <p>Already have an account?</p>
          <a class="standard-button" @click="$emit('change-compo')" type="submit">Log in here</a>
        </div>
      </div>
    </div>
  </form>
  <div v-else class="sign-up-confirmation"><p>User created successfully!</p><a @click="$emit('change-compo')" class="standard-button">Log in</a></div>
</template>

<style scoped>
body {
  font-family: Inter, sans-serif;
  background-color: var(--dark-1);
  background-image: url(https://uploads-ssl.webflow.com/6408bea3de5aef58b7e197d4/6438c3059d443cd7a78762eb_stars-bg.png);
  background-repeat: no-repeat;
  background-position: top;
  background-size: cover;
  margin: 0;
  padding: 0;
}

h1 {
  font-size: 50px;
  font-weight: 900;
}

h2 {
  font-size: 40px;
}

h3 {
  font-size: 30px;
  font-weight: 900;
  text-align: center;
}

h4 {
  font-family: Bebas Neue, sans-serif;
  font-size: 30px;
  color: whitesmoke;
}

h5 {
  font-size: 20px;
}

img {
  margin: auto;
  padding: 0;
}

button {
  background-color: transparent;
  border-color: transparent;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.logo-top {
  font-weight: 900;
  font-size: 37px;
  padding-left: 5px;
  color: var(--dark-1);
}

.logo-bottom {
  font-size: 37px;
  color: var(--base-1);
}


.header-button {
  color: var(--dark-1);
  font-size: 20px;
  padding: 0;
  text-decoration: none;
  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    opacity: 60%;
  }
}

input:focus {
  outline: none;
}

.logo {
  width: 60px;
  height: 60px;
  margin: auto;
}

.standard-button {
  width: 100%;
  height: 40px;
  background: var(--base-1);
  color: var(--light-1);
  font-weight: bold;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  font-size: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-decoration: none;

  &:hover {
    background-color: var(--base-2);
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }
}


/* LOGIN PAGE */



.login-container {
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  height: 800px;
  margin-bottom: 0;
}


.login-box {
  height: min-content;
  background-color: var(--light-1);
  display: flex;
  align-items: center;
  padding: 30px;
  border-radius: 20px;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
}

.login-box h1 {
  margin-bottom: 20px;
}

.login-field-box {
  align-items: center;
  margin-bottom: 10px;
}

.login-prompt {
  color: var(--dark-3);
  width: 100%;
  height: 40px;
  background: none;
  padding: 20px;
  border: 2px solid var(--dark-3);
  border-radius: 10px;
  font-size: 12px;
  margin: 10px 0;
}

.alert-failed {
  color: red;
  font-size: 14px;
  margin: 0;
}

label {
  color: var(--dark-3);
}


.login-text-field {
  padding: 0;
  margin: 0;
  justify-content: center;
  align-items: flex-start;
  display: flex;
  flex-direction: column;
}

.login-text-field label {
  font-size: 0.7vw;
}

.login-image {
  width: 50%;
  height: auto;
  margin: auto;
  margin-bottom: 12vh;
}

.sign-up-confirmation {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 50vh;
  font-size: 25px;
}

.sign-up-confirmation p {
  margin: 10px;
  color: var(--vt-c-white);
}

.sign-up-confirmation .standard-button {
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 2vh;
  font-size: 15px;
}

p{
  margin: 10px;
  color: var(--dark-1);
}


</style>
