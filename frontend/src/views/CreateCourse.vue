<script setup>
  import { ref, computed, onMounted } from 'vue';
  import Guidelines from "@/components/Guidelines.vue";
  import {redirectTo} from "@/js/navigation";
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

  const remainingCharacters = computed(() => 6000 - course.value.description.length);

  function toggleGuidelinesModal() {
  showGuidelinesModal.value = !showGuidelinesModal.value;
}

  function createCourse() {
    if(course.value.closestCourseSession) {
      const dates = course.value.closestCourseSession.split(' to ');
      course.value.startDate = dates[0];
      course.value.endDate = dates[1];
      console.log(course.value.startDate + course.value.endDate);
      console.log(course.value);
    }
  sendApiRequest('POST', '/courses', onSuccess, course.value, error);
}

  onMounted(() => {
  initSite();
});

  function initSite() {
  const currentUser = getAuthenticatedUser();
  if (!currentUser || !hasRole('ROLE_ADMIN')) {
    redirectTo('/no-access');
  }
}

  function onSuccess() {
  alert('Course created successfully!');
  resetForm();
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
</script>

<template>
  <div class="course-section">
    <div class="post-form">
      <p>Please ensure that your course adheres to our guidelines.</p>
      <button class="guideline-button" @click="toggleGuidelinesModal">Guidelines</button>
      <p>If you're unsure about any aspect of our guidelines, please take a moment to review them. We appreciate your cooperation in maintaining a positive and constructive community.</p>
    </div>

    <guidelines class="post-form" v-if="showGuidelinesModal" @close="showGuidelinesModal = false" />

    <form @submit.prevent="createCourse" class="post-form">
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
        <input type="text" id="closest-size" v-model="course.courseSize" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="hours-per-week">Hours per week:</label>
        <input type="text" id="hours-per-week" v-model="course.hoursPerWeek" required maxlength="100">
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
      <button type="submit">Create Post</button>
    </form>
  </div>
</template>

<style scoped>
.course-section {
  background-color: var(--light-3);
  padding: 50px 0;
}

.post-form {
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