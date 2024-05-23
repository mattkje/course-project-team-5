<script setup>


import {getCurrentInstance, ref} from 'vue';
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
const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

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
    await sendApiRequest(API_URL,'POST', '/users/register', onSignupSuccess, signupData, onSignUpError);
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

function goToLogin() {
  window.location.href = '/login';
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
            <img class="logoMobile" src="/logoApp.svg" alt="Connect">
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom">Connect</h4>
          </router-link>
          <div class="field-flow">
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
              <input v-model="email" class="login-prompt" type="text" id="email" pattern=".+@.+" name="email" placeholder="Enter your email" maxlength="45" required autofocus/>
            </div>
            <div class="login-text-field">
              <label for="phoneNumber">Phone Number:</label>
              <input v-model="phoneNumber" class="login-prompt" type="text" id="phoneNumber" name="phoneNumber" placeholder="Enter your phone number" maxlength="20" required autofocus/>
            </div>
          </div>


          <button class="standard-button" type="submit" id="signup-button">Create user</button>

          <p>Already have an account?</p>
          <button class="standard-button" @click="$emit('change-compo')" type="submit">Log in here</button>
        </div>
      </div>
    </div>
  </form>
  <Alert v-else title="User created!" message="Try logging in" :buttons="[ 'Login' ]" @buttonClicked="goToLogin"></Alert>
</template>

<style scoped>

@media (max-width: 769px) {
  .login-container {
    background: white;
    display: flex;
    justify-content: center;
    align-items: center;
  }


  .login-box {
    height: min-content;
    background: none;
    border: 2px solid #ffffff;
    display: flex;
    align-items: center;
    margin: 0;
    border-radius: 20px;
  }

  .login-box h1 {
    margin-bottom: 20px;
  }

  .login-field-box {
    align-items: center;
    margin-bottom: 10px;
    width: 400px;
  }

  .login-prompt {
    color: var(--dark-1);
    font-family: Inter, sans-serif;
    width: 100%;
    height: 70px;
    border-radius: 15px;
    padding: 20px;
    border: none;
    font-size: 17px;
    background-color: var(--light-1);
    margin: 10px 0;
  }

  .header-button{
    display: flex;
    justify-content: center;
    align-content: center;
  }

  .header-button h4{
    display: none;
  }

  .logo {
    display: none;
  }

  .logoMobile {
    width: 200px;
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

    &:active {
      transform: scale(0.95);
      transition-duration: .5s;
    }
  }

  .login-text-field label {
    margin-left: 20px;
    color: var(--dark-1);
    font-family: 'Inter', sans-serif;
    font-size: 17px;
    font-weight: bold;
  }
}

@media (min-width: 769px) {
  .login-container {
    background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    margin-bottom: 0;
  }


  .login-box {
    height: min-content;
    background: var(--light-1);
    backdrop-filter: blur(5px);
    border: 2px solid #ffffff;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
    display: flex;
    width: 1000px;
    justify-content: center;
    align-items: center;
    padding: 30px;
    border-radius: 20px;
  }

  .login-box h1 {
    margin-bottom: 20px;
  }

  .field-flow {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-auto-rows: auto;
    gap: 10px;
    overflow-y: auto;
  }


  .login-field-box {

    align-items: center;
    margin-bottom: 10px;
  }

  .login-prompt {
    color: var(--dark-1);
    font-family: Inter, sans-serif;
    width: 300px;
    height: 50px;
    border-radius: 15px;
    padding: 20px;
    border: none;
    font-size: 17px;
    background-color: var(--light-3);
    margin: 10px 0;
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

  .logoMobile {
    display: none;
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

    &:active {
      transform: scale(0.95);
      transition-duration: .5s;
    }
  }

  .login-text-field label {
    margin-left: 20px;
    color: var(--dark-1);
    font-family: 'Inter', sans-serif;
    font-size: 17px;
    font-weight: bold;
  }

  .logo {
    width: 60px;
    height: 60px;
    margin: 0;
  }

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






input:focus {
  outline: none;
}







/* LOGIN PAGE */






.alert-failed {
  color: red;
  font-size: 14px;
  margin: 0;
}


.login-text-field {
  padding: 0;
  margin: 0;
  justify-content: center;
  align-items: flex-start;
  display: flex;
  flex-direction: column;

}



p {
  margin: 10px;
  color: var(--dark-1);
}

</style>
