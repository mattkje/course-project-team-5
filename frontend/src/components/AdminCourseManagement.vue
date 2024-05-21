<script setup>
import {ref, onMounted, getCurrentInstance} from 'vue';
import {getCookie} from "@/js/tools";

const courses = ref([]);
const loading = ref(true);
const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

onMounted(async () => {
  const response = await fetch(API_URL + '/courses', {
    headers: {
      'Authorization': 'Bearer ' + getCookie('jwt'),
    }
  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  courses.value = await response.json();
  loading.value = false;
});

function visitPage(courseProvider) {
  location.href = '/courses?id=' + courseProvider.course.courseId;
}

async function deleteCourse(courseProvider) {
  const token = getCookie('jwt');
  const response = await fetch(API_URL + '/courses/' + courseProvider.course.courseId, {

    method: 'DELETE',
    headers: {
      'Authorization': 'Bearer ' + token,
    },
  });

  console.log(response);
  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(`HTTP error! status: ${response.status}, message: ${errorData}`);
  }

  location.reload();
}

async function disableCourse(courseProvider) {
  const token = getCookie('jwt');
  const response = await fetch(API_URL + '/courses/active/' + courseProvider.course.courseId, {
    method: 'PUT',
    headers: {
      'Authorization': 'Bearer ' + token,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ enabled: false })
  });

  console.log(response);
  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(`HTTP error! status: ${response.status}, message: ${errorData.message}`);
  }

  location.reload();
}

</script>

<template>
  <div class="page-background" id="profileInformation">
    <div v-show="loading" class="three-body">
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
    </div>
    <div class="title">
      <h1>Course Management</h1>
      <p>Review active courses</p>
    </div>
    <div class="page-highlight" v-show="!loading">
      <div v-for="courseProvider in courses" :key="courseProvider.course.id" class="user-block">
        <p>{{ courseProvider.course.title }}</p>
        <div class="right-content">
          <button class="fancy-button" @click="visitPage(courseProvider)">Review</button>
          <button class="fancy-button" style="background-color: lightgreen;" @click="disableCourse(courseProvider)" v-if="courseProvider.course.active">Enabled</button>
          <button class="fancy-button" style="background-color: brown; color: white" @click="disableCourse(courseProvider)" v-if="!courseProvider.course.active">Disabled</button>
          <button class="fancy-button" style="background-color: orangered; color: white" @click="deleteCourse(courseProvider)">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

@media (max-width: 769px) {
  .page-background {
    background-color: var(--light-1);
    padding: 30px 20px;
    margin: 10px auto;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-content: center;
    width: 400px;
    overflow: hidden;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .page-highlight {
    display: flex;
    flex-direction: column;
    width: 100%;
    margin: 20px 0;
    grid-template-rows: auto;
    justify-content: space-between;
  }
  .user-block {
    width: 370px;
    display: flex;
    align-items: center;
    height: 60px;
    justify-content: space-between;
    padding: 10px;
    border: 1px solid var(--light-1);
    border-radius: 10px;
    margin-bottom: 5px;
    background-color: var(--light-3);
  }
}
@media (min-width: 769px) {
  .page-background {
    background-color: var(--light-1);
    padding: 30px;
    border-radius: 30px;
    margin: auto;
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-content: center;
    width: 60%;
    overflow: hidden;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  }

  .page-highlight {
    background-color: var(--light-1);
    border-radius: 20px;
    padding: 20px;
    display: grid;
    width: 100%;
    min-height: 50vh;
    grid-template-columns: repeat(2, 1fr);
    margin: 20px 0;
    line-height: 50px;
    grid-template-rows: auto;
    justify-content: space-between;
  }

  .user-block {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px;
    border: 1px solid var(--light-1);
    border-radius: 5px;
    margin-bottom: 10px;
    background-color: var(--light-3);
  }
}






.right-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>