<script setup>
import { ref } from 'vue';
import {redirectTo} from "@/js/navigation";
import {showFormError} from "@/js/tools";
import {sendAuthenticationRequest} from "@/js/authentication";

const username = ref('');
const password = ref('');

const login = async () => {
  sendAuthenticationRequest(username.value, password.value, onLoginSuccess, showFormError);
};

function onLoginSuccess() {
  console.log("Login successful");
  redirectTo("/");
}
</script>

<template>

  <form @submit.prevent="login" id="loginForm" method="post">
    <div class="login-container">
      <div class="login-box">
        <div class="login-field-box">
          <a href="/" class="header-button">
            <img class="logo" src="/logo.svg" alt="Connect">
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom">Connect</h4>
          </a>

          <div id="error" class="alert-failed" style="display: none;">
            Invalid username or password.
          </div>

          <div id="logout" class="alert alert-success" style="display: none;">
            You have been logged out.
          </div>
          <div class="login-text-field">
            <label for="username">Username:</label>
            <input v-model="username" class="login-prompt" type="text" id="username" name="username"
                   placeholder="username" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="password">Password:</label>
            <input v-model="password" class="login-prompt" type="password" id="password" name="password"
                   placeholder="password" required/>
          </div>
          <p id="result-message" class="hidden"></p>
          <button class="standard-button" type="submit">Login</button>
          <p>No account?</p>
          <a class="standard-button" href="/register" type="submit">Sign up here</a>
        </div>
      </div>
    </div>


  </form>
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

p {
  margin: 10px;
  color: var(--dark-1);
}

</style>