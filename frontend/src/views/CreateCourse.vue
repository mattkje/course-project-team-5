<script setup>
import {ref, computed, onMounted, getCurrentInstance} from 'vue';
  import Guidelines from "@/components/Guidelines.vue";
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

  const showGuidelinesModal = ref(false);
  const keywords = ref([]);
  const activatedKeywords = ref([]);
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

  function toggleGuidelinesModal() {
  showGuidelinesModal.value = !showGuidelinesModal.value;
}

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

async  function onSuccess() {
    await sendApiRequest(API_URL, 'GET', '/courses/newest' , newestSuccess, newestError);
  alert('Course created successfully!');
  resetForm();
}

function newestError() {
    alert('There was an error retrieving the newest course. Please try again.');
}

async function newestSuccess(data) {
    for (let keyword of activatedKeywords.value) {
      console.log(keyword);
      await sendApiRequest(API_URL, 'POST', '/courses/keyword/' + data.id + '/' + keyword.id, keywordSuccess, keywordError);
    }
}

function keywordSuccess() {
  console.log('Keyword added successfully');
}

function keywordError() {
  alert('There was an error adding the keyword. Please try again.');
}

  function error() {
  alert('There was an error creating the course. Please try again.');
}

  function resetForm() {
  course.value = {
    title: '',
    category: '',
    level: '',
    closestCourseSession: '',
    courseSize: '',
    hoursPerWeek: '',
    relatedCertifications: '',
    description: '',
    image: ''
  }
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
</script>

<template>
  <div class="course-section">

    <guidelines class="course-form" v-if="showGuidelinesModal" @close="showGuidelinesModal = false" />

    <form @submit.prevent="createCourse" class="course-form">
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
          <button type="button" class="fancy-button" @click="addKeyword">Add keyword</button>
        </div>
        <div>
          <p>Activated keywords:</p>
          <ul>
            <li v-for="keyword in activatedKeywords">{{keyword.keyword}}
              <button type="button" @click="removeKeyword(keyword)">x</button>
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
  background-color: #f9f9f9;
  border-radius: 5px;
}

.form-group {
  margin-bottom: 20px;
}

.big-input {
  height: 600px;
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

button[type="submit"] {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: var(--base-1);
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

button[type="submit"]:hover {
  background-color: var(--base-2);
}

.formatting-tools {
  margin-top: 10px;
}

.formatting-tools button {
  margin-right: 5px;
  padding: 5px 10px;
  background-color: #ffffff;
  box-shadow: none;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
}

.formatting-tools button:hover {
  background-color: #e0e0e0;
}

.guideline-button{
  padding: 0;
  color: var(--base-1);
  background: none;
  border: none;

  &:hover {
    color: var(--base-2);
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
</style>