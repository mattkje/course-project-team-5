<script setup>
import { ref } from 'vue';
import {getAuthenticatedUser} from "../js/authentication";
import {sendApiRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";
import {onMounted} from "vue";
import {doLogout} from "@/js/authentication";

onMounted(loadProfileData);
const user = getAuthenticatedUser();
const username = ref('');
const password = ref('');
const firstName = ref('');
const lastName = ref('');
const email = ref('');
const phoneNumber = ref('');
let editIsPressed = ref(false);

async function loadProfileData() {
  console.log("Loading profile data from API...");
  // TODO: ADD DISABLE BUTTONS AND SHIDD
  console.log("User: ", user);
  if (user) {
    await sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
    document.getElementById("username").innerText = username.value;
    document.getElementById("firstName").innerText = firstName.value;
    document.getElementById("lastName").innerText = lastName.value;
    document.getElementById("email").innerText = email.value;
    document.getElementById("phoneNumber").innerText = phoneNumber.value;
  } else {
    redirectTo("/no-access");
  }
}

function onProfileDataSuccess(data) {
  console.log("Profile data loaded: ", data);
  username.value = data.username;
  firstName.value = data.firstName;
  lastName.value = data.lastName;
  email.value = data.email;
  phoneNumber.value = data.phoneNumber;
}

function onProfileDataError(error) {
  console.error("Error loading profile data: ", error);
  redirectTo("/no-access");
}

function editProfile() {
  console.log("Editing profile...");
  editIsPressed.value = true;
}
</script>

<template>
  <div id="background" class="background">
    <div class="content-box" id="profileInformation">
      <div class="course-list">

      </div>
      <div class="profile-information">
        <h1>Profile</h1>
        <p>Username: <label for="username" id="username"></label></p>
        <p>Email: <label for="email" id="email"></label></p>
        <p>First name: <label for="firstName" id="firstName"></label></p>
        <p>Last name: <label for="lastName" id="lastName"></label></p>
        <p>Phone number: <label for="phoneNumber" id="phoneNumber"></label></p>
      </div>
      <div class="profile-buttons">
        <button class="standard-button">Change password</button>
        <button class="log-out" @click="doLogout">Log out</button>
    </div>
  </div>
    </div>
</template>

<style scoped>
.background{
  display: flex;
  justify-content: center;
}
</style>