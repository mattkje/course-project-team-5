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
  <CourseSlide/>
  <div id="background" class="background">
    <h1>Welcome Back <span></span></h1>
  </div>
</template>

<style scoped>

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