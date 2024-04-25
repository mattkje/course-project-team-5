<template>
  <div class="pro-box">
    <img class="bend-bottom" src="/bend.svg" alt="Connect">
    <img id="pro-icon" class="pro-icon" src="/pro.svg" alt="Connect">
    <h2>Unlock Premium Features with Learniverse Pro</h2>
    <p>Get access to exclusive courses, personalized learning paths, and more with Learniverse Pro.</p>
    <button class="fancy-button" @click="toggleProInfo">Learn More</button>
    <div class="pro-features" id="proInfoText" :style="{ height: isProInfoVisible ? '300px' : '0px', opacity: isProInfoVisible ? '1' : '0' }">
      <h2>Perks</h2>
      <ul>
        <li>Access to exclusive courses</li>
        <li>Personalized learning paths</li>
        <li>Priority support</li>
      </ul>
      <div class="pro-pricing">
        <h2>Starting at $9.99/month</h2>
        <router-link to="/pro" v-if="isOfRoleUser('ROLE_USER') || isOfRoleUser('ROLE_PRO') || isOfRoleUser('ROLE_ADMIN')" class="standard-button">Sign up to Get Started</router-link>
        <router-link to="/register" v-else class="standard-button">Sign up to Get Started</router-link>
      </div>
    </div>
    <img class="bend" src="/bend.svg" alt="Connect">
  </div>
</template>
<script>
import {isOfRoleUser} from "@/js/authentication";

export default {
  data() {
    return {
      isProInfoVisible: false,
    };
  },
  methods: {
    isOfRoleUser,
    toggleProInfo() {
      this.isProInfoVisible = !this.isProInfoVisible;
    },
  },
};
</script>

<style scoped>
@media (max-width: 600px) {
  #pro-icon {
    height: 100px;
    width: 100px;
    animation: orbit 5s infinite;
  }
}

:root {
  font-family: 'Inter', sans-serif;
}


hr {
  background: #ADADAD;
  height: 0.15vw;
  border: none;
}

img {
  margin: auto;
  padding: 0;
}


ul {
  margin: 0;
  padding: 0;
}


@keyframes slide {
  0% {
    background-position: 0 0;
  }
  100% {
    background-position: -100px 0;
  }
}


.bend {
  width: 100%;
  margin: 0;
  padding: 0;

}

.bend-bottom {
  rotate: 180deg;
  width: 100%;
  margin: 0;
  padding: 0;
}


@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.pro-box {
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  color: white;
  font-weight: bold;
  font-size: 20px;
  margin: 0;
}

.pro-box h2 {
  font-weight: bold;
  color: white;
}

.pro-box p {
  color: white;
  font-size: 15px;
  margin: 0;
  padding: 0;
}

.pro-pricing {
  min-height: 100px;
  margin: 20px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

@keyframes courseHover {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(1.1);
  }
}


@keyframes orbit {
  0% {
    transform: translateY(70px) scale(1.2);
  }
  50% {
    transform: translateY(70px) scale(1.4);
  }
  100% {
    transform: translateY(70px) scale(1.2);
  }
}

input:focus {
  outline: none;
}


* {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
}

#proInfoText {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 0;
  opacity: 0;
  overflow: hidden;
  transition: height 0.5s ease-out, opacity 0.5s ease-out;
}

button {
  background-color: transparent;
  border-color: transparent;
}


.fancy-button {
  color: #EAEAEA;
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  margin: 0 10px;
  max-height: 50px;
  background: #0C0C0C;
  border: 0.5px solid #252525;

}

.fancy-button:hover {
  background-color: #262626;
  box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
}

.standard-button {
  background-color: #584BEB;
  color: #EAEAEAFF;
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  width: max-content;

  &:hover {
    background-color: #6E67FCFF;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }
}

* {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
}

</style>