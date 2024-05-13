<template>
  <div id="background" class="background">
    <img class="planet" src="/settingsPlanet.svg">
  </div>
  <div class="profile-background">
    <div class="profile-bar">

    </div>

    <div class="profile-display">
      <div class="navigation-bar">
        <p>General</p>
        <button class="nav-button" @click="accountDetails()"><img class="nav-icon" src="/settingsaccount.svg">Account Details</button>
        <button class="nav-button" @click="myCourses()"><img class="nav-icon" src="/settingscourses.svg">My Courses</button>
        <button class="nav-button" @click="subscription()"><img class="nav-icon"src="/settingswallet.svg">Subscription</button>
      </div>
      <AccoundDetails v-show="navigate === 'accountDetails' && !loading && !changePassword" @newPassword="newPassword()"
                      @doLogoutToHome="doLogoutToHome()"/>
      <div class="profile-box" id="profileInformation" v-show="navigate === 'myCourses' && !loading">
        <div v-show="loading" class="three-body">
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
        </div>
        <div class="title">
          <h1>My Courses</h1>
          <p>Manage your courses</p>
        </div>
        <table class="course-table">
          <p>You have no courses</p>
        </table>
      </div>
      <div class="profile-box" id="changePassword" v-show="changePassword">
        <PasswordChange/>
        <div class="profile-buttons">
          <button class="fancy-button" @click="changePasswordRequest">Save</button>
          <button class="fancy-button" @click="cancelChangePassword">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {doLogout, getAuthenticatedUser} from "@/js/authentication";
import {sendApiRequest, sendTokenRefreshRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";
import {getCookie, isTokenAboutToExpire} from "@/js/tools";
import PasswordChange from "@/components/PasswordChange.vue";
import AccoundDetails from "@/components/AccoundDetails.vue";

onMounted(loadProfileData);
const loading = ref(true);
const user = getAuthenticatedUser();
const changePassword = ref(false);
let navigate = ref("accountDetails");

async function loadProfileData() {
  navigate.value = "accountDetails";
  document.querySelector('.nav-button').style.background = 'var(--light-1)';
  console.log("Loading profile data from API...");
  console.log("User: ", user);
  if (user) {
    const jwt = getCookie("jwt");
    if (jwt && isTokenAboutToExpire(jwt)) {
      sendTokenRefreshRequest(onTokenRefreshSuccess, onTokenRefreshError);
    } else {
      await sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
    }
  } else {
    redirectTo("/no-access");
  }
}

function onTokenRefreshSuccess() {
  console.log("Token has been refreshed.");
  sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
}

function subscription() {
  const buttons = document.querySelectorAll('.nav-button');
  buttons[0].style.background = 'none';
  buttons[1].style.background = 'none';
  buttons[2].style.background = 'var(--light-1)';
  navigate.value = "subscription";
}

function accountDetails() {
  const buttons = document.querySelectorAll('.nav-button');
  buttons[0].style.background = 'var(--light-1)';
  buttons[1].style.background = 'none';
  buttons[2].style.background = 'none';
  navigate.value = "accountDetails";
}

function myCourses() {
  const buttons = document.querySelectorAll('.nav-button');
  buttons[0].style.background = 'none';
  buttons[1].style.background = 'var(--light-1)';
  buttons[2].style.background = 'none';
  navigate.value = "myCourses";
}

function onTokenRefreshError(error) {
  console.error("Error refreshing token: ", error);
  redirectTo("/no-access");
}

function onProfileDataSuccess(data) {
  console.log("Profile data loaded: ", data);
  document.getElementById("username").innerText = data.user.username;
  document.getElementById("firstName").innerText = data.user.firstName;
  document.getElementById("lastName").innerText = data.user.lastName;
  document.getElementById("email").innerText = data.user.email;
  document.getElementById("phoneNumber").innerText = data.user.phoneNumber;
  console.log("test")
  if (data.courses.length > 0) {
    addCourses(data);
  } else {
    const profileInfo = document.getElementById("profileInformation");
    profileInfo.style.maxHeight = "800px";
  }
  loading.value = false;
}

function onProfileDataError(error) {
  console.error("Error loading profile data: ", error);
  redirectTo("/no-access");
}

function newPassword() {
  changePassword.value = true;
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
  const courseList = document.getElementsByClassName("course-table")[0];
  courseList.children[0].remove();
  const courseBody = document.createElement("tbody");
  courseBody.classList.add("course-block");
  courseList.appendChild(courseBody);
  for (const element of data.courses) {
    const course = element;
    const row = document.createElement("tr");
    const courseName = document.createElement("p");
    const courseImg = document.createElement("img");
    courseImg.classList.add("course-image");
    row.classList.add("course-card");
    row.style.cursor = "pointer";
    courseName.innerText = course.course.title;
    courseName.style.paddingLeft = "20px";
    courseImg.src = course.course.image || '/noImageCom.svg';
    row.appendChild(courseImg);
    row.appendChild(courseName);
    editCourseCard(row, course);
    courseBody.appendChild(row);
    const line = document.createElement("hr");
    line.style.maxWidth = "600px";
    line.style.margin = "20px";
    courseBody.appendChild(line);
  }
}

function editCourseCard(object, course) {
  object.style.minWidth = "500px";
  object.style.minHeight = "50px";
  object.onclick = function () {
    redirectTo("/courses/?id=" + course.course.courseId);
  };
}

function doLogoutToHome() {
  doLogout();
  redirectTo("/");
}

function cancelChangePassword() {
  changePassword.value = false;
  window.scroll(0, 0);
}
</script>

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

.planet {
  height: 300px;
  animation: ease-out pulse 5s infinite;
}


@keyframes pulse {
  0% {
    transform: scale(0.5);
  }
  50% {
    transform: scale(0.6);
  }
  100% {
    transform: scale(0.5);
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