<script setup>

import {getCurrentInstance, onMounted, ref} from "vue";
import {getAuthenticatedUser} from "@/js/authentication";
import {sendApiRequest} from "@/js/requests";
import CourseSlide from "@/components/CourseSlide.vue";

onMounted(putUsername)

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

function putUsername() {
  sendApiRequest(API_URL,'GET', '/users/' + getAuthenticatedUser().username, success, error)
}

function success(data) {
  document.querySelector('span').innerText = data.user.firstName
}

function error() {
  console.log('error')
}
</script>

<template>
  <div class="show-slide">
    <CourseSlide/>
  </div>
  <div id="background" class="background">
    <h1>Welcome Back <span></span></h1>
  </div>
</template>

<style scoped>

@media (max-width: 769px) {
  .show-slide {
    display: none;
  }

  .background h1 {
    font-size: 30px;
    margin: 20px 0 ;
  }
}

span {
  font-weight: 900;
}

.background {
  background-color: var(--light-3);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  margin: 0;
}

.background h1 {
  background-color: var(--light-1);
  padding: 20px;
  width: 70%;
  text-align: center;
  border-radius: 20px;
  margin: 20px 0 60px 0;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
}

</style>