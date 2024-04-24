<script setup>
import { ref } from 'vue';
import {getAuthenticatedUser} from "@/js/authentication";
import {sendApiRequest, sendTokenRefreshRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";
import {onMounted} from "vue";
import {doLogout} from "@/js/authentication";
import {getCookie, isTokenAboutToExpire} from "@/js/tools";

onMounted(loadProfileData);
const loading = ref(true);
const user = getAuthenticatedUser();
const changePassword = ref(false);


async function loadProfileData() {
  console.log("Loading profile data from API...");
  console.log("User: ", user);
  console.log(user.password);
  if (user) {
    const jwt = getCookie("jwt");
    if (jwt && isTokenAboutToExpire(jwt)) {
      sendTokenRefreshRequest(onTokenRefreshSuccess, onTokenRefreshError);
    } else {
      await sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
    }
  } else {
    //redirectTo("/no-access");
  }
}

function onTokenRefreshSuccess() {
  console.log("Token has been refreshed.");
  sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
}

function onTokenRefreshError(error) {
  console.error("Error refreshing token: ", error);
  //redirectTo("/no-access");
}

function onProfileDataSuccess(data) {
  console.log("Profile data loaded: ", data);
  document.getElementById("username").innerText = data.user.username;
  document.getElementById("firstName").innerText = data.user.firstName;
  document.getElementById("lastName").innerText = data.user.lastName;
  document.getElementById("email").innerText = data.user.email;
  document.getElementById("phoneNumber").innerText = data.user.phoneNumber;
  if(data.courses.length > 0) {
    addCourses(data);
  }
  loading.value = false;
}

function onProfileDataError(error) {
  console.error("Error loading profile data: ", error);
  redirectTo("/no-access");
}

function newPassword() {
  changePassword.value = true;
  window.scroll(0,0);
}

function changePasswordRequest() {
  const oldPassword = document.getElementById("currentPassword").value;
  const newPassword = document.getElementById("newPassword").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  if (newPassword !== confirmPassword) {
    alert("New password and confirm password do not match.");
    return;
  } else if (newPassword.length < 8) {
    alert("Password must be at least 8 characters long.");
    return;
  }

  const data = {
    oldPassword: oldPassword,
    newPassword: newPassword
  };

  sendApiRequest("PUT", "/users/" + user.username + "/change-password", onChangePasswordSuccess, data, onChangePasswordError);
}

function onChangePasswordSuccess(data) {
  console.log("Password changed: ", data);
  alert("Password changed successfully.");
  changePassword.value = false;
}

function onChangePasswordError(error) {
  console.error("Error changing password: ", error);
  alert("Error changing password. Please try again.");
}

function addCourses(data) {
  document.getElementById("no-course").remove();
  const courseList = document.createElement("table");
  courseList.classList.add("course-list");
  const courseBody = document.createElement("tbody");
  courseList.appendChild(courseBody);
  for (let i = 0; i < data.courses.length; i++) {
    const course = data.courses[i];
    const row = document.createElement("tr");
    const courseName = document.createElement("td");
    courseName.innerText = course.course.title;
    row.appendChild(courseName);
    courseBody.appendChild(row);
    const line = document.createElement("hr");
    courseBody.appendChild(line);
  }
  document.getElementById("courses").appendChild(courseList);
}

function doLogoutToHome() {
  doLogout();
  redirectTo("/");
}

function cancelChangePassword() {
  changePassword.value = false;
  window.scroll(0,0);
}
</script>

<template>
  <div class="background">
    <div class="profile-box" id="profileInformation" v-show="!changePassword">
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
        <div class="course-information" id="courses">
          <p id="no-course">You do not have any courses yet.</p>
        </div>
      </div>
      <div v-show="!loading" class="profile-buttons">
        <button class="standard-button" @click="newPassword">Change password</button>
        <button class="log-out" @click="doLogoutToHome">Log out</button>
    </div>
  </div>
    <div class="profile-box" id="changePassword" v-show="changePassword">
      <h1>Change password</h1>
      <div class="profile-information">
        <label for="currentPassword">Current password:</label>
        <input class="passwordField" type="password" id="currentPassword" maxlength="64">
        <label for="newPassword">New password:</label>
        <input class="passwordField" type="password" id="newPassword" maxlength="64">
        <label for="confirmPassword">Confirm new password:</label>
        <input class="passwordField" type="password" id="confirmPassword" maxlength="64">
      </div>
      <div class="profile-buttons">
        <button class="standard-button" @click="changePasswordRequest">Save</button>
        <button class="standard-button" @click="cancelChangePassword">Cancel</button>
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
  height: 850px;
  background-color: white;
  border-radius: 20px;
  overflow: hidden;
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

.passwordField {
  width: 100%;
  height: 40px;
  border-radius: 10px;
  border: 1px solid var(--base-1);
  padding: 10px;
  margin: 10px 0;
}

#currentPassword {
  margin-bottom: 30px;
}

.courses tr:nth-child(odd){
  background-color: #fff;
}

.courses tr:nth-child(even){
  background-color: lightgrey;
}

.course-information {
  background-color: #f1f1f1;
  border-radius: 20px;
  padding: 20px;
  display: grid;
  width: 100%;
  margin: 50px 0;
  line-height: 50px;
  grid-template-rows: auto;
  justify-content: space-between;
  overflow: auto;
  max-height: 200px;
}
</style>