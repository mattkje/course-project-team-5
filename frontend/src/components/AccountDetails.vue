<template>
  <div class="profile-box" id="profileInformation">
    <div v-show="loading" class="three-body">
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
    </div>
    <div class="title" v-show="!changePassword">
      <h1>Account Details</h1>
      <p>Manage your profile</p>
    </div>
    <hr>
    <div class="profile-information" v-show="!loading">
      <div class="profile-item">
        <p>Username</p>
        <label for="username" id="username"></label>
      </div>
      <div class="profile-item">
        <p>Email</p>
        <label for="email" id="email"></label>
      </div>
      <div class="profile-item">
        <p>First name</p>
        <label for="firstName" id="firstName"></label>
      </div>
      <div class="profile-item"><p>Last name</p>
        <label for="lastName" id="lastName"></label></div>
      <div class="profile-item">
        <p>Phone number</p>
        <label for="phoneNumber" id="phoneNumber"></label>
      </div>
    </div>
    <hr>
    <div class="course-options">
      <button class="fancy-button" @click="newPassword">Change password</button>
      <button class="fancy-button" @click="doLogoutToHome">Log out</button>
    </div>
  </div>
</template>
<script>
import {defineComponent} from "vue";

export default defineComponent({
  name: 'AccoundDetails',
  setup(_, context) {
    return {
      newPassword: () => context.emit("newPassword"),
      doLogoutToHome: () => context.emit("doLogoutToHome"),
    };
  },
});
</script>
<style scoped>

.profile-information {
  background-color: var(--light-1);
  border-radius: 20px;
  padding: 0 0 0 20px;
  display: grid;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);
  margin-bottom: 20px;
  line-height: 50px;
  grid-template-rows: auto;
  grid-column-gap: 10px;
  justify-content: space-between;
}

.courses tr:nth-child(odd) {
  background-color: #fff;
}

.courses tr:nth-child(even) {
  background-color: lightgrey;
}

.course-options {
  background-color: var(--light-1);
  border-radius: 20px;
  display: grid;
  flex-direction: column;
  width: 100%;
  line-height: 50px;
  margin: 30px 0;
  justify-content: left;
  overflow: auto;
  max-height: 400px;
  min-height: 100px;
}

@keyframes slideIn {
  0% {
    transform: translatex(-1000px) translateY(-100px) scale(1.2);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes pulse {
  0% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
  50% {
    transform: translatex(0) translateY(120px) scale(1.7);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

.title {
  border: none;
  border-radius: 15px;
  width: 100%;
  text-align: left;
  justify-content: left;
  text-decoration: none;
  margin: 0 auto;
}

.title p {
  color: var(--dark-3);
  font-size: 1.3em;
  margin: 0;
  padding: 0;
}

.navigation-bar p {
  font-weight: bold;
  color: var(--light-2);
  font-size: 17px;
  margin-left: 70px;
}

.profile-item {
  width: 100%;
}

.profile-item label {
  padding: 5px 20px;
  display: inline-block;
  border-radius: 10px;
  color: var(--dark-3);
  border: 2px solid var(--light-2);
  width: 100%;
}

.fancy-button {
  background-color: var(--light-3);
}

.fancy-button:hover {
  cursor: pointer;
}

hr {
  max-width: 100%;
  margin: 20px;
}

.title{
  margin-bottom: 20px;
}
</style>