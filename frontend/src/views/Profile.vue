<template>
  <Alert v-show="showAlert1" title="Error" message="Passwords don't match" :buttons="[ 'OK' ]" @buttonClicked="handleButtonClick"></Alert>
  <Alert v-show="showAlert2" title="Error" message="The password must be longer than 8 characters" :buttons="[ 'OK' ]" @buttonClicked="handleButtonClick"></Alert>
  <div id="background" class="background">
    <img alt="planet" class="planet" src="/settingsPlanet.svg">
  </div>
  <div class="profile-background">
    <div class="profile-bar">

    </div>

    <div class="profile-display">
      <div class="navigation-bar">
        <p>General</p>
        <button class="nav-button" @click="accountDetails()"><img alt="Account Details" class="nav-icon" src="/settingsaccount.svg">Account Details</button>
        <button class="nav-button" @click="myCourses()"><img alt="My Courses" class="nav-icon" src="/settingscourses.svg">My Courses</button>
        <button class="nav-button" @click="subscription()"><img alt="Subscription" class="nav-icon"src="/settingswallet.svg">Subscription</button>
      </div>

      <div v-show="loading" class="loader">
        <div class="wrapper">
          <div class="circle"></div>
          <div class="line-1"></div>
          <div class="line-2"></div>
          <div class="line-3"></div>
          <div class="line-4"></div>
          <div class="line-5"></div>
        </div>
      </div>

      <AccountDetails v-show="navigate === 'accountDetails' && !loading && !changePassword" @newPassword="newPassword()"
                      @doLogoutToHome="doLogoutToHome()" @changeProfilePicture="changeProfilePicture()"/>
      <div class="profile-box" id="profileInformation" v-show="navigate === 'myCourses' && !loading">
        <div class="title" v-show="!loading">
          <h1>My Courses</h1>
          <p>Manage your courses</p>
        </div>
        <table class="course-table">
          <th>
            <p>You have no courses</p>
          </th>
        </table>
      </div>
      <div class ="profile-box" id="profileInformation" v-show="navigate === 'subscription' && !loading">
        <div v-show="loading" class="three-body">
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
        </div>
        <div class="title" v-show="!loading">
          <h1>Subscription</h1>
          <p>Manage your subscription</p>
          <div class="subscription-box" v-show="!loading && !hasRole('ROLE_PRO')">
            <p>You do not have a Learniverse Pro subscription.</p>
            <Subscription/>
          </div>
          <div class="subscription-box-pro" v-show="!loading && hasRole('ROLE_PRO')">
            <p>You have a Learniverse Pro subscription<br>Your subscription ends <span></span></p>
            <button class="fancy-button" @click="window.location.href = ('/pro')">Learniverse Pro</button>
            <button class="fancy-button" @click="endSubscription">End your subscription</button>
          </div>
        </div>
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
import {getCurrentInstance, onMounted, ref} from 'vue';
import {doLogout, getAuthenticatedUser, hasRole, removeRole} from "@/js/authentication";
import {sendApiRequest, sendTokenRefreshRequest} from "@/js/requests";
import {getCookie, isTokenAboutToExpire} from "@/js/tools";
import PasswordChange from "@/components/PasswordChange.vue";
import AccountDetails from "@/components/AccountDetails.vue";
import Subscription from "@/components/Subscription.vue";
import Alert from "@/components/Alert.vue";

onMounted(loadProfileData);
const loading = ref(true);
const user = getAuthenticatedUser();
const changePassword = ref(false);
let navigate = ref("accountDetails");
const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const showAlert1 = ref(false);
const showAlert2 = ref(false);

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
      await sendApiRequest(API_URL,"GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
    }
  } else {
    window.location.href = ("/login");
  }
}

function onTokenRefreshSuccess() {
  console.log("Token has been refreshed.");
  sendApiRequest(API_URL,"GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
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
  window.location.href = ("/no-access");
}

function onProfileDataSuccess(data) {
  console.log("Profile data loaded: ", data);
  document.getElementById("username").innerText = data.user.username;
  document.getElementById("firstName").innerText = data.user.firstName;
  document.getElementById("lastName").innerText = data.user.lastName;
  document.getElementById("email").innerText = data.user.email;
  document.getElementById("phoneNumber").innerText = data.user.phoneNumber;
  document.querySelector('.subscription-box-pro p span').innerText = data.user.subscriptionExpire;
  console.log("test")
  if (data.courses.length > 0) {
    addCourses(data);
  } else {
    const profileInfo = document.getElementById("profileInformation");
    profileInfo.style.maxHeight = "900px";
  }
  loading.value = false;
}

function onProfileDataError(error) {
  console.error("Error loading profile data: ", error);
  window.location.href = ("/no-access");
}

function newPassword() {
  changePassword.value = true;
}

function changePasswordRequest() {
  const oldPassword = document.getElementById("currentPassword").value;
  const newPassword = document.getElementById("newPassword").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  if (newPassword !== confirmPassword) {
    showAlert1.value = true;
  } else if (newPassword.length < 8) {
    showAlert2.value = true;
  } else {
    const data = {
      oldPassword: oldPassword,
      newPassword: newPassword
    };
    sendApiRequest(API_URL,"PUT", "/users/" + user.username + "/change-password", onChangePasswordSuccess, data, onChangePasswordError);
  }
}

function onChangePasswordSuccess(data) {
  console.log("Password changed: ", data);
  alert("Password changed successfully.");
  changePassword.value = false;
}

function onChangePasswordError(error) {
  console.error("Error changing password: ", error);
}

function endSubscription() {
  sendApiRequest(API_URL,"PUT", "/users/unsubscribe", onEndSubscriptionSuccess, onEndSubscriptionError);
  removeRole('ROLE_PRO');
  alert('You have successfully ended you subscription');
  window.location.href = ('/profile');
}

function onEndSubscriptionSuccess(data) {
  console.log("Subscription ended: ", data);
}

function onEndSubscriptionError(error) {
  console.error("Error ending subscription: ", error);
  alert("Error ending subscription. Please try again.");
}

function addCourses(data) {
  const courseList = document.getElementsByClassName("course-table")[0];
  courseList.children[0].remove();
  const courseBody = document.createElement("tbody");
  courseBody.classList.add("course-block");
  courseBody.style.width= "80%";
  courseBody.style.margin = "auto";
  courseBody.style.maxWidth = "800px";
  courseList.appendChild(courseBody);
  for (const element of data.courses) {
    const course = element;
    const row = document.createElement("tr");
    const courseName = document.createElement("p");
    const courseImg = document.createElement("img");
    const courseDiv = document.createElement("div");
    courseDiv.append(courseImg, courseName);
    courseDiv.style.display = "flex";
    courseDiv.style.flexDirection = "flex-start"
    courseImg.classList.add("course-image");
    row.classList.add("course-card");
    const button = document.createElement("button");
    button.classList.add("fancy-button");
    button.style.backgroundColor = "orangered";
    button.style.color = "white";
    button.innerText = "Delete";
    button.onclick = function() {
      sendApiRequest(API_URL, 'DELETE', '/users/delete-course/' + course.course.id, onCourseDeleteSuccess, null, onCourseDeleteError);
    };
    button.style.cursor = "pointer";
    row.style.justifyContent = "space-between";
    row.style.backgroundColor = "var(--light-3)";
    row.style.padding = "10px"
    courseName.innerText = course.course.title;
    courseName.style.paddingLeft = "20px";
    courseImg.src = course.course.image || '/noImageCom.svg';
    courseImg.style.borderRadius = "0";
    courseImg.style.margin = "0 10px 0 10px";
    row.append(courseDiv);
    row.append(button);
    editCourseCard(row);
    courseBody.appendChild(row);
  }
}

function onCourseDeleteSuccess(data) {
  console.log("Course deleted: ", data);
  alert("Course deleted successfully.");
  window.location.href = ("/profile");
}

function onCourseDeleteError(error) {
  console.error("Error deleting course: ", error);
  alert("Error deleting course. Please try again.");
}

function editCourseCard(object) {
  object.style.Width = "100%"
  object.style.minHeight = "50px";
}

function doLogoutToHome() {
  doLogout();
  window.location.href = ("/");
}

function cancelChangePassword() {
  changePassword.value = false;
}

function handleButtonClick() {
  showAlert1.value = false;
  showAlert2.value = false;
}

// Code made by external sources
function changeProfilePicture() {
  const input = document.getElementById('imageInput');
  if (input.files && input.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      const base64String = e.target.result.split(',')[1];
      console.log(base64String);
      sendApiRequest(API_URL, 'PUT', '/users/' + user.username + '/change-image', onProfilePictureSuccess, base64String, onProfilePictureError);
    };
    reader.readAsDataURL(input.files[0]);
  }
}

function onProfilePictureSuccess(data) {
  console.log("Profile picture changed: ", data);
  alert("Profile picture changed successfully.");
  location.reload();
}

function onProfilePictureError(error) {
  console.error("Error changing profile picture: ", error);
}
</script>

<style scoped>
@media (min-width: 599px){
  .nav-button {
    gap: 4%;
    justify-content: space-evenly;
  }
}
@media (max-width: 1201px) {
  .background {
    display: none;
  }

  .profile-display {
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding: 0 0 50px 0;
  }

  .navigation-bar {
    background-color: #d3d3d3;
    display: flex;
    border-radius: 20px;
    flex-direction: row;
    justify-content: center;
    align-content: center;
    height: 70px;
    width: 93%;
    margin: 10px auto;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .navigation-bar p {
    display: none;
  }

  .nav-button {
    color: var(--dark-3);
    background: none;
    font-family: 'Inter', sans-serif;
    font-weight: 900;
    font-size: 12px;
    padding: 5px;
    text-decoration: none;
    border-radius: 20px;
    align-items: center;
    justify-content: center;
    display: flex;
    border: none;
    width: 33%;
    height: 70px;
    transition: all 0.15s ease-in-out;
    word-break: break-word;
  }
  .nav-button:hover {
    background: var(--light-3);
    cursor: pointer;
  }

  .nav-button:focus {
    background: var(--light-3);
    outline: none;
  }

  .nav-button:active {
    background: var(--light-3);
  }

  .nav-icon {
    width: 23px;
    height: 23px;
  }

  .profile-background {
    background-color: var(--light-3);
    margin: 0;
  }

  .profile-box {
    background-color: var(--light-1);
    padding: 30px 20px;
    margin: 10px auto;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-content: center;
    width: 93%;
    overflow: hidden;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .loader {
    position: relative;
    width: 93%;
    height: 740px;
    margin: 10px auto;
    border: 1px solid #ffffff;
    border-radius: 30px;
    padding: 20px 20px 40px 20px;
    background-color: #ffffff;
    overflow: hidden;
  }

}

@media (min-width: 1201px) {
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

  .profile-display {
    display: flex;
    flex-direction: row;
    justify-content: center;
    padding: 0 0 50px 0;
    gap: 1.5%;
  }

  .navigation-bar {
    display: flex;
    flex-direction: column;
    width: 20%;
    max-width: 350px;
  }

  .navigation-bar p {
    font-weight: bold;
    color: var(--light-2);
    font-size: 17px;
    padding-left: 50px;
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
    padding-left: 50px;
    justify-content: flex-start ;
    width: 100%;
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

  .profile-background {
    background-color: var(--light-3);
    margin: 0;
    padding-top: 50px;
  }
  .loader {
    position: relative;
    max-width: 1100px;
    width: 55%;
    height: 740px;
    margin: 10px auto;
    border: 1px solid #ffffff;
    border-radius: 30px;
    padding: 20px 20px 40px 20px;
    background-color: #ffffff;
    overflow: hidden;
  }
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

.subscription-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0;
  padding-top: 40px;
}

.subscription-box-pro {
  display: flex;
  flex-direction: column;
  padding: 20px 0 0 20px;
  align-items: start;
  gap: 10px;
}

.subscription-box-pro p {
  font-size: 16px;
  color: var(--dark-1);
}

hr {
  max-width: 100%;
  margin: 20px;
}

.profile-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
}



.loader:after {
  content: "";
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: linear-gradient(110deg, rgba(227, 227, 227, 0) 0%, rgba(227, 227, 227, 0) 40%, rgba(255, 255, 255, 0.5) 50%, rgba(227, 227, 227, 0) 60%, rgba(227, 227, 227, 0) 100%);
  animation: gradient-animation_2 1.2s linear infinite;
}

.loader .wrapper {
  width: 100%;
  height: 100%;
  position: relative;
}

.loader .wrapper > div {
  background-color: #ececec;
}

.loader .circle {
  position: absolute;
  top: 40px;
  left: 0;
  height: 20px;
  width: 30%;
}

.loader .circle {
  position: absolute;
  top: 40px;
  left: 0;
  height: 20px;
  width: 30%;
}

.loader .line-1 {
  position: absolute;
  top: 70px;
  left: 0;
  height: 10px;
  width: 20%;
}

.loader .line-2 {
  position: absolute;
  top: 210px;
  left: 0;
  height: 10px;
  width: 64%;
}

.loader .line-3 {
  position: absolute;
  top: 300px;
  left: 0px;
  height: 50px;
  width: 100%;
}

.loader .line-4 {
  position: absolute;
  top: 400px;
  left: 0px;
  height: 50px;
  width: 92%;
}

.loader .line-5 {
  position: absolute;
  top: 500px;
  left: 0;
  height: 50px;
  width: 100%;
}

@keyframes gradient-animation_2 {
  0% {
    transform: translateX(-100%);
  }

  100% {
    transform: translateX(100%);
  }
}
</style>