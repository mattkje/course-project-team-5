<script setup>

import {onMounted, ref} from "vue";
import {getAuthenticatedUser} from "@/js/authentication";
import {sendApiRequest} from "@/js/requests";
import CourseSlide from "@/components/CourseSlide.vue";

onMounted(putUsername)

function putUsername() {
  sendApiRequest('GET', '/users/' + getAuthenticatedUser().username, success, error)
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
    margin: 20px 0;
  }
}

@media (min-width: 769px) {

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

</style>