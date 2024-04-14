<script setup>
import {getAuthenticatedUser} from "../js/authentication";
import {sendApiRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";
import {onMounted} from "vue";
import {doLogout} from "@/js/authentication";

onMounted(loadProfileData);

function loadProfileData() {
  console.log("Loading profile data from API...");
  // TODO: ADD DISABLE BUTTONS AND SHIDD
  const user = getAuthenticatedUser();
  if (user) {
    sendApiRequest("GET", "/api/users/" + user.username, showProfileData);
  } else {
    redirectTo("/no-access");
  }
}

function showProfileData(profileData) {
  if (profileData) {
    document.getElementById("username").innerText = profileData.username ? profileData.username : "";
    document.getElementById("password").innerText = profileData.password ? profileData.password : "";
    document.getElementById("email").innerText = profileData.email ? profileData.email : "";
    document.getElementById("firstName").innerText = profileData.firstName ? profileData.firstName : "";
    document.getElementById("lastName").innerText = profileData.lastName ? profileData.lastName : "";
    document.getElementById("phoneNumber").innerText = profileData.phoneNumber ? profileData.phoneNumber : "";
  }
}
</script>

<template>
  <div id="background" class="background">
    <div class="content-box">
      <div class="course-list">

      </div>
      <div class="profile-information">
        <h1>Profile</h1>
        <p>Username: </p><label for="username"></label>
        <p>Password: </p><label for="password"></label>
        <p>Email: </p><label for="email"></label>
        <p>First name: </p><label for="firstName"></label>
        <p>Last name: </p><label for="lastName"></label>
        <p>Phone number:</p><label for="phoneNumber"></label>
      </div>
      <div class="profile-buttons">
        <button class="standard-button">Edit profile</button>
        <button class="standard-button">Change password</button>
        <button class="log-out" @click="doLogout">Log out</button>
    </div>
  </div>
  </div>
</template>

<style scoped>

</style>