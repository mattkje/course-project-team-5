<script setup>

import PasswordChange from "@/components/PasswordChange.vue";
import {onMounted, ref} from "vue";
import {getAuthenticatedUser, hasRole} from "@/js/authentication";
import AdminUserManagement from "@/components/AdminUserManagement.vue";
import AdminCourseManagement from "@/components/AdminCourseManagement.vue";
import {redirectTo} from "@/js/navigation";


onMounted(loadProfileData);
const loading = ref(true);
const user = getAuthenticatedUser();
let navigate = ref("userManage");

async function loadProfileData() {
  if(!hasRole('ROLE_ADMIN')) {
    redirectTo("/no-access");
  }
  navigate.value = "userManage";
  document.querySelector('.nav-button').style.background = 'var(--light-1)';
}


function siteManage() {
  const buttons = document.querySelectorAll('.nav-button');
  buttons[0].style.background = 'none';
  buttons[1].style.background = 'none';
  buttons[2].style.background = 'var(--light-1)';
  navigate.value = "siteManage";
}

function userManage() {
  const buttons = document.querySelectorAll('.nav-button');
  buttons[0].style.background = 'var(--light-1)';
  buttons[1].style.background = 'none';
  buttons[2].style.background = 'none';
  navigate.value = "userManage";
}

function courseManage() {
  const buttons = document.querySelectorAll('.nav-button');
  buttons[0].style.background = 'none';
  buttons[1].style.background = 'var(--light-1)';
  buttons[2].style.background = 'none';
  navigate.value = "courseManage";
}

</script>

<template>
  <div id="background" class="background">
    <div class="hero-container">
      <div class="hero-box">
        <img id="planet" class="planet1" src="/settingsPlanet.svg" alt="Logo">

      </div>
    </div>
    <img class="bend" src="/bend.svg">
  </div>
  <div class="profile-background">
    <div class="profile-bar">

    </div>

    <div class="profile-display">
      <div class="navigation-bar">
        <p>Admin tools</p>
        <button class="nav-button" @click="userManage()"><img class="nav-icon" src="/settingsaccount.svg">User Management</button>
        <button class="nav-button" @click="courseManage()"><img class="nav-icon" src="/settingscourses.svg">Course Management</button>
        <button class="nav-button" @click="siteManage()"><img class="nav-icon"src="/settingsPlanet.svg">Site Traffic</button>
      </div>
      <AdminUserManagement v-show="navigate === 'userManage' && !loading"/>
      <AdminCourseManagement v-show="navigate === 'courseManage' && !loading"/>
    </div>
  </div>
</template>

<style scoped>
.background {
  top: 0;
  height: min-content;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

}

.profile-background {
  background-color: var(--light-3);
  margin: 0;
  padding-top: 50px;
}

.profile-box {
  background-color: var(--light-1);
  padding: 30px;
  border-radius: 30px;
  margin: 10px;
  display: flex;
  flex-direction: column;
  justify-content: left;
  align-content: center;
  width: 60%;
  overflow: hidden;
  box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
}

#currentPassword {
  margin-bottom: 30px;
}

.courses tr:nth-child(odd) {
  background-color: #fff;
}

.courses tr:nth-child(even) {
  background-color: lightgrey;
}

.course-information {
  background-color: var(--light-1);
  border-radius: 20px;
  padding: 20px;
  display: grid;
  width: 100%;
  margin: 50px 0;
  line-height: 50px;
  grid-template-rows: auto;
  justify-content: space-between;
  overflow: auto;
  max-height: 400px;
  min-height: 100px;
}

.hero-container {
  display: flex;
  justify-content: center;
}

.hero-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.bend {
  width: 100%;
  transform: scaleX(1.01) translateY(2px);
  margin: 0;
  padding: 0;
}

.planet1 {
  height: 150px;
  animation: ease-out slideIn 1s forwards, ease-out pulse 5s infinite 1s;
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

.title p {
  color: var(--dark-3);
  font-size: 1.3em;
  margin: 0;
  padding: 0;
}

.profile-display {
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 0 0 50px 0;
}

.navigation-bar {
  display: flex;
  flex-direction: column;
}

.navigation-bar p {
  font-weight: bold;
  color: var(--light-2);
  font-size: 17px;
  margin-left: 70px;
}

.nav-button {
  color: var(--dark-3);
  background: none;
  font-family: 'Inter', sans-serif;
  font-weight: 900;
  font-size: 23px;
  padding: 10px 20px;
  border-radius: 10px;
  text-decoration: none;
  align-items: center;
  display: flex;
  border: none;
  margin-bottom: 5px;
  margin-left: 50px;
  width: 450px;
  max-height: 50px;
  min-height: 50px;
  transition: all 0.15s ease-in-out;
}

.nav-icon {
  width: 23px;
  height: 23px;
  margin-right: 13px;
}

.nav-button:hover {
  background: var(--light-1);
  cursor: pointer;
}

.nav-button:focus {
  background: var(--light-1);
  outline: none;
}

.nav-button:active {
  background: var(--light-1);
}

.profile-item label {
  padding: 5px 20px;
  display: flex;
  border-radius: 5px;
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

.course-table {
  margin: 20px 0;
}
</style>