<script setup>

import PasswordChange from "@/components/PasswordChange.vue";
import {onMounted, ref} from "vue";
import {getAuthenticatedUser, hasRole} from "@/js/authentication";
import AdminUserManagement from "@/components/AdminUserManagement.vue";
import AdminCourseManagement from "@/components/AdminCourseManagement.vue";
import AdminPostManagement from "@/components/AdminPostManagement.vue";


onMounted(loadProfileData);
const loading = ref(false);
const user = getAuthenticatedUser();
let navigate = ref("userManage");

async function loadProfileData() {
  if(!hasRole('ROLE_ADMIN')) {
    window.location.href = ('/no-access');
  }

  navigate.value = "userManage";
  document.querySelector('.nav-button').style.background = 'var(--light-1)';
}


function postManage() {
  const buttons = document.querySelectorAll('.nav-button');
  buttons[0].style.background = 'none';
  buttons[1].style.background = 'none';
  buttons[2].style.background = 'var(--light-1)';
  navigate.value = "postManage";
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

function createCourse() {
  window.location.href = ("/create-course");
}

function createProvider() {
  window.location.href = ("/create-provider");
}

</script>

<template>
  <div id="background" class="background">
    <img class="planet" src="/whiteMoon.png">
  </div>
  <div class="profile-background">
    <div class="profile-bar">

    </div>

    <div class="profile-display">
      <div class="navigation-bar">
        <p>Admin tools</p>
        <button class="nav-button" @click="userManage()">
          <img class="nav-icon" src="/settingsaccount.svg">
          <p>User Management</p>
        </button>
        <button class="nav-button" @click="courseManage()">
          <img class="nav-icon" src="/settingscourses.svg">
          <p>Course Management</p>
        </button>
        <button class="nav-button" @click="postManage()">
          <img class="nav-icon"src="/settingsPost.svg">
          <p>Post Management</p>
        </button>
        <button class="nav-button" @click="createCourse()">
          <img class="nav-icon" src="/settingscoursesplus.svg">
          <p>Create Course</p>
        </button>
        <button class="nav-button" @click="createProvider()">
          <img class="nav-icon" src="/settingsprovider.svg">
          <p>Create Provider</p>
        </button>
      </div>
      <AdminUserManagement v-show="navigate === 'userManage' && !loading"/>
      <AdminCourseManagement v-show="navigate === 'courseManage' && !loading"/>
      <AdminPostManagement v-show="navigate === 'postManage' && !loading"/>
    </div>
  </div>
</template>

<style scoped>

@media (max-width: 769px) {
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
    width: 400px;
    margin: 10px auto;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .navigation-bar p {
    display: none;
  }

  .nav-button {
    color: var(--dark-3);
    background: none;
    text-decoration: none;
    border-radius: 20px;
    align-items: center;
    justify-content: center;
    display: flex;
    border: none;
    width: 33%;
    height: 70px;
    transition: all 0.15s ease-in-out;
  }
  .nav-button p {
    display: none;
  }

  .nav-button img {
    width: 30px;
    height: 30px;
    margin: auto;
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
    margin-right: 13px;
  }

  .profile-background {
    background-color: var(--light-3);
    margin: 0;
  }
}

@media (min-width: 769px) {
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

  .nav-button p {
    color: var(--dark-3);
    font-family: 'Inter', sans-serif;
    font-weight: 900;
    font-size: 23px;
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