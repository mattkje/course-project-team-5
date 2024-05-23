<script setup>
import {getCurrentInstance, ref} from 'vue';
import {showFormError} from "@/js/tools";
import {getAuthenticatedUser, sendAuthenticationRequest} from "@/js/authentication";
import Register from "@/components/Register.vue";
import Alert from "@/components/Alert.vue";

const username = ref('');
const password = ref('');
let compo = ref('login');
const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const showAlert = ref(false);

const login = async () => {
  if(getAuthenticatedUser()) {
    showAlert.value = true;
  }else {
    sendAuthenticationRequest(API_URL, username.value, password.value, onLoginSuccess, showFormError);
  }
};

function onLoginSuccess() {
  console.log("Login successful");
  window.location.href = '/';
}

function changeCompo() {
  if(compo.value === 'login') {
    compo.value = 'register';
  } else {
    compo.value = 'login';
  }
}

function goToMainPage() {
  window.location.href = '/';
}
</script>

<template>
<meta name="login page">
  <Alert v-show="showAlert" title="Already logged in" message="You are already logged in" :buttons="[ 'Go to main page' ]" @buttonClicked="goToMainPage"></Alert>
  <form @submit.prevent="login" id="loginForm" method="post">
    <div class="login-container">
      <div class="login-box" v-show="compo === 'login'">
        <div class="login-field-box">
          <router-link to="/" class="header-button">
            <img class="logo" src="/logo.svg" alt="Connect">
            <img class="logoMobile" src="/logoApp.svg" alt="Connect">
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom">Connect</h4>
          </router-link>

          <div id="error" class="alert-failed" style="display: none;">
            Invalid username or password.
          </div>

          <div id="logout" class="alert alert-success" style="display: none;">
            You have been logged out.
          </div>
          <div class="login-text-field">
            <label for="username">Username:</label>
            <input v-model="username" class="login-prompt" type="text" id="loginUsername" name="username"
                   placeholder="username" required autofocus/>
          </div>
          <div class="login-text-field">
            <label for="password">Password:</label>
            <input v-model="password" class="login-prompt" type="password" id="loginPassword" name="password"
                   placeholder="password" required/>
          </div>
          <p id="result-message" class="hidden"></p>
          <button class="standard-button" type="submit">Login</button>
          <p>No account?</p>
          <button class="standard-button" @click="changeCompo" type="submit">Sign up here</button>
        </div>
      </div>
      <register @change-compo="changeCompo" v-show="compo === 'register'"/>
    </div>


  </form>
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
    height: 100vh;
    margin-bottom: 0;
  }


  .login-box {
    height: min-content;
    background: var(--light-1);
    backdrop-filter: blur(5px);
    border: 2px solid #ffffff;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
    display: flex;
    align-items: center;
    padding: 30px;
    border-radius: 20px;
  }

  .login-box h1 {
    margin-bottom: 20px;
  }

  .login-field-box {
    align-items: center;
    margin-bottom: 10px;
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
    margin: auto;

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