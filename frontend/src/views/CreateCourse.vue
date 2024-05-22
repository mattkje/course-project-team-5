<script setup>
import {ref, computed, onMounted, getCurrentInstance, defineEmits} from 'vue';
  import {getAuthenticatedUser, hasRole} from "@/js/authentication";
  import {sendApiRequest} from "@/js/requests";
  import flatPickr from 'vue-flatpickr-component';
  import 'flatpickr/dist/flatpickr.css';

  const config = ref({
    mode: 'range',
    dateFormat: 'Y-m-d',
    altFormat: 'Y-m-d',
    minDate: 'today',
    conjunction: ' to ',
  });

  const keywords = ref([]);
  const activatedKeywords = ref([]);
  const emit = defineEmits(['navigate']);
  const id = ref(0);
  const course = ref({
  title: '',
  category: '',
  level: '',
  startDate: '',
  endDate: '',
  hoursPerWeek: '',
  relatedCertifications: '',
  description: '',
  image: ''
});

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const remainingCharacters = computed(() => 6000 - course.value.description.length);

async function createCourse() {
    if(course.value.closestCourseSession) {
      const dates = course.value.closestCourseSession.split(' to ');
      course.value.startDate = dates[0];
      course.value.endDate = dates[1];
      console.log(course.value.startDate + course.value.endDate);
      console.log(course.value);
    }
  await sendApiRequest(API_URL,'POST', '/courses', onSuccess, course.value, error);
}

  onMounted(() => {
  initSite();
});

  function initSite() {
  const currentUser = getAuthenticatedUser();
  if (!currentUser || !hasRole('ROLE_ADMIN')) {
    window.location.href = ('/no-access');
  }
  sendApiRequest(API_URL, 'GET', '/keywords', getCoursesSuccess, getCoursesError);
}

function getCoursesSuccess(data) {
  keywords.value = data;
}

function getCoursesError() {
  alert('There was an error retrieving the courses. Please try again.');
}

function onSuccess() {
    sendApiRequest(API_URL, 'GET', '/courses/newest' , newestSuccess, newestError);
}

function newestError() {
    alert('There was an error retrieving the newest course. Please try again.');
}

async function newestSuccess(data) {
  id.value = data.id;
  const keywordIds = activatedKeywords.value.map(keyword => keyword.id);
  console.log(keywordIds);
  await sendApiRequest(API_URL, 'POST', '/courses/keywords/' + data.id, keywordSuccess, keywordIds, keywordError);
}

async function keywordSuccess() {
  console.log('Course created successfully!');
  window.location.href = ('/courses?id=' + id.value);
}

function keywordError() {
  alert('There was an error adding the keyword. Please try again.');
}

  function error() {
  alert('There was an error creating the course. Please try again.');
}

function addKeyword() {
  const keywordName = document.getElementById("keywords").value;
  if (keywordName) {
    // Find the keyword object from the keywords array
    const keyword = keywords.value.find(k => k.keyword === keywordName);
    if (keyword) {
      // Push the keyword object to the activatedKeywords array
      activatedKeywords.value.push(keyword);
    }
    // Clear the input field
    document.getElementById("keywords").value = '';
  }
}

function removeKeyword(keyword) {
  activatedKeywords.value = activatedKeywords.value.filter(item => item !== keyword);
}

function removeCourse() {
  emit('navigateBack', 'courseManage');
}

function createKeyword() {
    sendApiRequest(API_URL, 'POST', '/courses/keyword', onPostKeywordSuccess, {keyword: document.getElementById("keywords").value}, error);
}

function onPostKeywordSuccess() {
    sendApiRequest(API_URL, 'GET', '/keywords', getCoursesSuccess, error);
}
</script>

<template>
  <div class="course-section">
    <form @submit.prevent="createCourse" class="course-form">
      <button type="button" class="back" @click="removeCourse">
        <img class="nav-icon" src="/angle-small-left.svg">
        <p>Back</p>
      </button>
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="course.title" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="category">Category:</label>
        <input type="text" id="category" v-model="course.category" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="level">Level:</label>
        <input type="text" id="level" v-model="course.level" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="closest-course-session">Closest course session:</label>
        <flat-pickr id="closest-course-session" v-model="course.closestCourseSession" :config="config" class="form-control" required ></flat-pickr>
      </div>
      <div class="form-group">
        <label for="closest-size">Course size:</label>
        <input type="number" id="closest-size" v-model="course.courseSize" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="hours-per-week">Hours per week:</label>
        <input type="number" id="hours-per-week" v-model="course.hoursPerWeek" required maxlength="100" >
      </div>
      <div>
        <div>
          <label for="keywords">Keywords:</label>
            <input type="text" id="keywords" list="keywordList">
            <datalist id="keywordList">
              <option v-for="keyword in keywords">{{keyword.keyword}}</option>
            </datalist>
          <div class="keyword-buttons">
            <button type="button" style="height: 40px" @click="addKeyword">Add keyword</button>
            <button type="button" style="height: 40px" @click="createKeyword">Create keyword</button>
          </div>
        </div>
        <div>
          <p>Activated keywords:</p>
          <ul>
            <li v-for="keyword in activatedKeywords">{{keyword.keyword}}
              <button type="button" style="height: 30px; width: 30px" @click="removeKeyword(keyword)">x</button>
            </li>
          </ul>
        </div>
      </div>
      <div class="form-group">
        <label for="related-certifications">Related certifications:</label>
        <input type="text" id="related-certifications" v-model="course.relatedCertifications" required maxlength="100">
      </div>
      <div class="form-group">
        <div class="content-div">
          <label for="description">Description:</label>
          <p id="max-characters">(max characters: 6000)</p>
        </div>
        <textarea class="big-input" id="description" v-model="course.description" required maxlength="6000"></textarea>
        <p class="char-counter">{{ remainingCharacters }}</p>
      </div>
      <div class="form-group">
        <label for="image">Image URL:</label>
        <input type="text" id="image" v-model="course.image" maxlength="255">
      </div>
      <button type="submit">Create Course</button>
    </form>
  </div>
</template>

<style scoped>
.course-section {
  background-color: var(--light-3);
  padding: 50px 0;
}

.course-form {
  max-width: 900px;
  margin: auto auto 50px auto;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.big-input {
  height: 600px;
  margin-bottom: 20px;
}

label {
  font-family: Inter, sans-serif;
  font-weight: bold;
  font-size: 16px;
  display: block;
  margin-bottom: 5px;
}

input[type="text"],
textarea {
  font-family: Inter, sans-serif;
  width: 100%;
  height: 60px;
  padding: 10px;
  border: 2px solid var(--light-2);
  border-radius: 15px;
  font-size: 16px;
}

button {

  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: #ffffff;
  font-size: 20px;
  background: #000000;
  border: none;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 70px;
  border-radius: 15px;
  cursor: pointer;
  margin-top: 10px;
  transition: all .5s;

  &:hover {
    transform: scale(1.01);
    transition-duration: .5s;
  }

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}


#max-characters {
  font-size: 14px;
  color: var(--dark-1);
}

.content-div {
  display: flex;
  flex-flow: row;
  align-items: flex-end;
}

#max-characters {
  font-size: 10px;
  margin-bottom: 5px;
  padding-left: 5px;
}

.char-counter {
  display: flex;
  font-size: 14px;
  color: var(--dark-1);
}

.nav-icon {
  width: 23px;
  height: 23px;
}

.back {
  display: flex;
  background: none;
  flex-direction: row;
  justify-content: start;
  width: 10%;
  color: black;
}

.keyword-buttons {
  display: flex;
  flex-direction: row;
}

.keyword-buttons button {
  margin-left: 10px;
}
</style>