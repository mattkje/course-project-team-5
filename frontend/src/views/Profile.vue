<script setup>
import { ref } from 'vue';
import {getAuthenticatedUser} from "@/js/authentication";
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
  document.getElementById("username").innerText = data.user.username;
  document.getElementById("firstName").innerText = data.user.firstName;
  document.getElementById("lastName").innerText = data.user.lastName;
  document.getElementById("email").innerText = data.user.email;
  document.getElementById("phoneNumber").innerText = data.user.phoneNumber;
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

.course-list{

}
</style>