<script setup>
import { ref } from 'vue';
import {getAuthenticatedUser} from "../js/authentication";
import {sendApiRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";
import {onMounted} from "vue";
import {doLogout} from "@/js/authentication";

onMounted(loadProfileData);
const loading = ref(true);
const user = getAuthenticatedUser();

async function loadProfileData() {
  console.log("Loading profile data from API...");
  console.log("User: ", user);
  if (user) {
    await sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
  } else {
    redirectTo("/no-access");
  }
}

function onProfileDataSuccess(data) {
  console.log("Profile data loaded: ", data);
  document.getElementById("username").innerText = data.username;
  document.getElementById("firstName").innerText = data.firstName;
  document.getElementById("lastName").innerText = data.lastName;
  document.getElementById("email").innerText = data.email;
  document.getElementById("phoneNumber").innerText = data.phoneNumber;
  loading.value = false;
}

function onProfileDataError(error) {
  console.error("Error loading profile data: ", error);
  redirectTo("/no-access");
}
</script>

<template>
  <div class="background">
    <div class="profile-box" id="profileInformation">
      <div v-show="loading" class="three-body">
        <div class="three-body__dot"></div>
        <div class="three-body__dot"></div>
        <div class="three-body__dot"></div>
      </div>
      <div v-show="!loading">
        <h1>Account</h1>
        <div class="profile-information">
          <p>Username: <label for="username" id="username"></label></p>
          <p>Email: <label for="email" id="email"></label></p>
          <p>First name: <label for="firstName" id="firstName"></label></p>
          <p>Last name: <label for="lastName" id="lastName"></label></p>
          <p>Phone number: <label for="phoneNumber" id="phoneNumber"></label></p>
        </div>
      </div>
      <div v-show="!loading">
        <h1>Your courses</h1>
        <div class="profile-information">
          <table class="course-list">
            <tbody>
              <p>You do not have any courses yet.</p>
            </tbody>
          </table>
        </div>
      </div>
      <div v-show="!loading" class="profile-buttons">
        <button class="standard-button">Change password</button>
        <button class="log-out" @click="doLogout">Log out</button>
    </div>
  </div>
    </div>
</template>

<style scoped>
.background{
  display: flex;
  background-color: var(--light-1);
  justify-content: center;
}

.profile-box {
  margin: 60px auto;
  display: flex;
  flex-direction: column;
  justify-content: left;
  align-content: center;
  padding: 100px;
  width: 60%;
  height: 700px;
  background-color: white;
  border-radius: 20px;
}

.profile-information {
  background-color: #f1f1f1;
  border-radius: 20px;
  padding: 20px;
  display: grid;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);
  margin: 50px 0;
  line-height: 50px;
  grid-template-rows: auto;
  justify-content: space-between;
}

.three-body {
  --uib-size: 35px;
  --uib-speed: 0.8s;
  --uib-color: #5D3FD3;
  display: inline-block;
  margin: auto;
  height: var(--uib-size);
  width: var(--uib-size);
  animation: spin78236 calc(var(--uib-speed) * 2.5) infinite linear;
}

.three-body__dot {
  position: absolute;
  height: 100%;
  width: 30%;
}

.three-body__dot:after {
  content: '';
  position: absolute;
  height: 0%;
  width: 100%;
  padding-bottom: 100%;
  background-color: var(--uib-color);
  border-radius: 50%;
}

.three-body__dot:nth-child(1) {
  bottom: 5%;
  left: 0;
  transform: rotate(60deg);
  transform-origin: 50% 85%;
}

.three-body__dot:nth-child(1)::after {
  bottom: 0;
  left: 0;
  animation: wobble1 var(--uib-speed) infinite ease-in-out;
  animation-delay: calc(var(--uib-speed) * -0.3);
}

.three-body__dot:nth-child(2) {
  bottom: 5%;
  right: 0;
  transform: rotate(-60deg);
  transform-origin: 50% 85%;
}

.three-body__dot:nth-child(2)::after {
  bottom: 0;
  left: 0;
  animation: wobble1 var(--uib-speed) infinite
  calc(var(--uib-speed) * -0.15) ease-in-out;
}

.three-body__dot:nth-child(3) {
  bottom: -5%;
  left: 0;
  transform: translateX(116.666%);
}

.three-body__dot:nth-child(3)::after {
  top: 0;
  left: 0;
  animation: wobble2 var(--uib-speed) infinite ease-in-out;
}

@keyframes spin78236 {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

@keyframes wobble1 {
  0%,
  100% {
    transform: translateY(0%) scale(1);
    opacity: 1;
  }

  50% {
    transform: translateY(-66%) scale(0.65);
    opacity: 0.8;
  }
}

@keyframes wobble2 {
  0%,
  100% {
    transform: translateY(0%) scale(1);
    opacity: 1;
  }

  50% {
    transform: translateY(66%) scale(0.65);
    opacity: 0.8;
  }
}

.course-list{

}
</style>