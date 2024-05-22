<script setup>
import {ref, onMounted, getCurrentInstance, defineProps, defineEmits} from 'vue';
import {getCookie} from "@/js/tools";

const courses = ref([]);
const loading = ref(true);
const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const emit = defineEmits(['navigate']);
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

function createCourse() {
  emit('navigate', 'createCourse');
}

function createProvider() {
  emit('navigate', 'createProvider');
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
    <div class="button-bar">
      <button class="option-button" @click="createCourse">
        <img class="nav-icon" src="/settingscoursesplus.svg">
        <p>Create Course</p>
      </button>
      <button class="option-button" @click="createProvider">
        <img class="nav-icon" src="/settingsprovider.svg">
        <p>Create Provider</p>
      </button>
      <button class="option-button" @click="createKeyword">
        <img class="nav-icon" src="/settingskeywords.svg">
        <p>Create Keyword</p>
      </button>
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


.button-bar {
  width: 98%;
  display: flex;
  flex-direction: row;
  margin: auto;
}



.right-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.option-button {
  background: #8fec8f;
  border: none;
  display: flex;
  text-decoration: none;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 70px;
  border-radius: 15px;
  cursor: pointer;
  margin: 10px;
  transition: all .5s;

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}

.option-button img{
  width: 40px;
  height: 40px;
  filter: brightness(0) invert(1);
}

.option-button p {
  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: #ffffff;
  font-size: 20px;
}

</style>