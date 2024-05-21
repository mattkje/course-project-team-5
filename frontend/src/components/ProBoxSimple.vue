<script>
import {hasRole} from "@/js/authentication";

export default {
  data() {
    return {
      isProInfoVisible: false,
    };
  },
  methods: {
    isOfRoleUser: hasRole,
    toggleProInfo() {
      this.isProInfoVisible = !this.isProInfoVisible;
    },
  },
};
</script>

<template>
  <div class="background">
    <div class="parent-container">
      <img id="pro-icon" class="pro-icon" src="/pro.svg" alt="Connect">
      <h2>Unlock Premium Features with Learniverse Pro</h2>
      <p>Get access to exclusive courses, personalized learning paths, and more with Learniverse Pro.</p>

      <h3>Starting at $9.99/month</h3>
      <router-link to="/pro" v-if="isOfRoleUser('ROLE_USER') || isOfRoleUser('ROLE_PRO') || isOfRoleUser('ROLE_ADMIN')" class="register-button">Sign up to Get Started</router-link>
      <router-link to="/register" v-else class="register-button">Sign up to Get Started</router-link>
    </div>
  </div>
</template>

<style scoped>
.background {
  display: flex;
  background-color: var(--light-3);
  width: 100%;
}

.parent-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-content: center;
  background-color: white;
  border-radius: 20px;
  width: 400px;
  padding: 20px;
  margin: 30px auto;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
}

.parent-container h2{
  text-align: center;
}

.parent-container h3{
  font-weight: 900;
  text-align: center;
}

.parent-container p{
  color: var(--dark-3);
  text-align: center;
}

.pro-icon {
  width: 200px;
  margin: auto;
}

.register-button {

  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: #ffffff;
  font-size: 20px;
  background: #000000;
  border: none;
  text-align: center;
  text-decoration: none;
  align-content: center;
  justify-content: center;
  width: 100%;
  padding: 20px;
  height: 70px;
  border-radius: 15px;
  cursor: pointer;
  margin: auto;

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}

</style>