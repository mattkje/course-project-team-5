<script setup>
import { getCurrentInstance, onMounted, ref } from "vue";
import {getAuthenticatedUser} from "@/js/authentication";
import createPost from "@/views/CreatePost.vue";
import router from "@/router";
import Alert from "@/components/Alert.vue";

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const searchQuery = ref('');
const showAlert = ref(false);

onMounted(() => {
  populateCourses();
});

function populateCourses() {
  fetch(API_URL + '/community/courses')
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(courses => {
        const courseBlock = document.querySelector('.course-block');
        // Clear the course block
        courseBlock.innerHTML = '';
        courses.forEach(course => {
          if (course.title.toLowerCase().includes(searchQuery.value.toLowerCase())) {
            // Create a new course card element
            const courseCard = document.createElement('a');
            courseCard.className = 'course-card';
            courseCard.href = `/community/post?id=${course.courseId}`;
            const imageUrl = course.image ? course.image : '/noImageCom.svg';
            courseCard.innerHTML = `
                        <img src="${imageUrl}" alt="Course ${course.courseId}">
                        <div class="course-card-description">
                            <h3>${course.title}</h3>
                            <p>Posted By: ${course.author}</p>
                            <p>${course.description}</p>
                        </div>
                    `;
            // Append the course card to the course block
            courseBlock.appendChild(courseCard);
          }
        });
      })
      .catch(error => {
        console.log('There was a problem with the fetch operation: ' + error.message);
      });
}

//For searching through posts
function searchPosts() {
  populateCourses();

  const divElement = document.querySelector('.course-block');

  if (!divElement.innerHTML.trim()) {
    if (!document.querySelector('.no-courses-message')) {
      const noCourses = document.createElement('h2');
      noCourses.innerText = 'No courses matching prompt';
      noCourses.classList.add('no-courses-message');
      const courseSection = document.querySelector('.course-section');
      courseSection.append(noCourses);
    }
  } else {
    const existingMessage = document.querySelector('.no-courses-message');
    if (existingMessage) {
      existingMessage.remove();
    }
  }
}

function authenticatePost(){
  const currentUser = getAuthenticatedUser();
  if (!currentUser) {
    showAlert.value = true;
    return;
  }
  router.push('/community/create');
}

const handleButtonClick = (button) => {
  if (button === 'OK') {
    showAlert.value = false;
    window.location.href = ('/login');
  }
  if (button === 'Cancel') {
    showAlert.value = false;
  }
};

</script>

<template>
  <Alert v-show="showAlert === true" title="Missing user" message="You must create a user to access this functionality" :buttons="['OK', 'Cancel']" @buttonClicked="handleButtonClick"></Alert>
  <div id="background" class="background">
    <img class="planet" src="/greenPlanet.svg">
  </div>
  <div class="course-section">
    <div class="community-top-container">
    <div class="community-title-container">
      <div class="title">
        <h1>Community</h1>
        <p class="description">Connect with other learners, share your knowledge, and grow together.</p>
      </div>
      <button class="fancy-button" @click="authenticatePost">
        <p>Create Post</p>
      </button>
    </div>
    <div class="search-container">

      <div class="search-bar">
        <label for="myTextBox"></label>
        <input class="search-prompt" type="text" id="myTextBox" name="myTextBox" placeholder="Search for community posts" v-model="searchQuery" @input="searchPosts">
        <img class="search-icon" src="/search.png" alt="Connect">
      </div>
    </div>
    </div>
    <div class="course-block" ref="courseBlock">
    </div>
    <div class="greeting"></div>
  </div>
</template>


<style scoped>
@media (max-width: 769px) {
  .background {
    display: none;

  }

  .search-container {
    position: relative;
    width: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    padding-top: 20px;
  }

  .search-bar {
    box-sizing: border-box;
    width: 100%;
    background-color: var(--light-1);
    border-radius: 20px;
    position: relative;
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
  }

  .community-title-container {
    flex-direction: column;
    align-items: center;
  }

  .course-block p:nth-child(2) {
    display: none;
  }

  .fancy-button {

    padding: 10px;
    border-radius: 20px;
    text-decoration: none;
    width: 400px;
    text-align: center;
    align-content: center;
    min-height: 60px;
    background: #0C0C0C;
    transition: all 0.3s ease-in-out;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);

    &:active {
      background-color: #262626;
    }
  }
  .fancy-button p {
    color: #EAEAEA;
    font-family: 'Inter', sans-serif;
    font-weight: bold;
    font-size: 20px;
    margin: auto;
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

  .search-container {
    position: relative;
    width: 80%;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    padding-top: 20px;
  }

  .search-bar {
    box-sizing: border-box;
    width: 60%;
    background-color: var(--light-1);
    border-radius: 20px;
    position: relative;
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
  }

  .fancy-button {
    color: #EAEAEA;
    font-family: 'Inter', sans-serif;
    font-weight: bold;
    font-size: 14px;
    padding: 10px;
    border-radius: 10px;
    text-decoration: none;
    display: flex;
    min-width: 100px;
    background: #0C0C0C;
    border: 0.5px solid #252525;
    transition: all 0.3s ease-in-out;
    white-space: nowrap;
    &:hover {
      background-color: #262626;
      box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
    }
  }
}





.hero-container {
  display: flex;
  justify-content: center;
}

.hero-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.title {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  width: 100%;
  border: none;
  text-decoration: none;
  padding-bottom: 5%;
}

.description {
  max-width: 400px;
  color: #282828;
  font-size: 20px;
  margin: auto;
}

.course-section {
  padding-top: 20px;
  background-color: var(--light-3);
  margin: 0;
}



.search-icon {
  position: absolute;
  top: 50%;
  left: 30px;
  transform: translateY(-50%);
  width: 30px;
  height: 30px;
  margin: auto;
  filter: invert(50%);
}

.search-prompt {
  color: var(--dark-1);
  background: none;
  width: 100%;
  padding: 20px 20px 20px 100px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
  text-overflow: ellipsis;
}


.course-block {
  display: grid;
  grid-column-gap: 30px;
  padding-top: 20px;
  justify-content: center;
  align-items: start;
  margin: 0 auto;
  width: 70%;
  justify-items: center;
}

@media (max-width: 1320px) {
  .course-block {
    grid-template-columns: repeat(1, 1fr);
  }
}

@media (max-width: 450px) {
  .course-block .course-card.course-card img {
    width: 100px;
    height: 50%;
  }

  .course-block .course-description-box p:nth-child(2) {
    display: none;
  }

  .course-block p:nth-child(1) {
    display: block;
  }

  .course-block .course-card.course-card h3 {
    font-size: 16px;
  }
}

.community-top-container {
  display: flex;
  flex-direction: column;
  align-items: center;
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

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}




.community-title-container {
  display: flex;
  justify-content: space-between;
  margin: auto;
  width: 60%;
}

</style>
