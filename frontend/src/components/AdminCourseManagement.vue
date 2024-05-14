<script setup>
import {ref, onMounted, getCurrentInstance} from 'vue';
import {getCookie} from "@/js/tools";

const courses = ref([]);
const loading = ref(true);
const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

onMounted(async () => {
  const response = await fetch(API_URL + '/courses');
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  courses.value = await response.json();
  loading.value = false;
});

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
        <button class="fancy-button" @click="deleteCourse(courseProvider)">Delete</button>
      </div>
    </div>

  </div>
</template>

<style scoped>
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
</style>