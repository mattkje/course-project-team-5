<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";
import {getAuthenticatedUser} from "@/js/authentication";
import router from "@/router";
import Alert from "@/components/Alert.vue";
import {sendApiRequest, sendTokenRefreshRequest} from "@/js/requests";
import {getCookie, isTokenAboutToExpire} from "@/js/tools";

const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const searchQuery = ref('');
const showAlert = ref(false);

onMounted(() => {
  populateCourses()
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
        courses.forEach(async course => {
          if (course.title.toLowerCase().includes(searchQuery.value.toLowerCase())) {


            const courseCard = document.createElement('a');
            courseCard.className = 'course-card';
            courseCard.href = `/community/post?id=${course.courseId}`;
            let imageData = '/nopfp.svg';
            const authorExistsResponse = await fetch(API_URL + '/users/' + course.author);
            if (authorExistsResponse.ok) {
              const response = await fetch(API_URL + '/users/' + course.author + '/image');
              const imageString = await response.text();
              if (isValidBase64(imageString)) {
                imageData = 'data:image/jpeg;base64,' + imageString;
                console.log(imageData);
              }
            } else {
              console.log('User does not exist');
            }

            const formattedDate = formatDate(course.postDate)

            courseCard.innerHTML = `
                    <div class="post-parent">
                        <img src="${imageData}" alt="Course ${course.courseId}">
                        <div class="course-card-description">
                            <h3>${course.title}</h3>
                            <div class="author-and-date">
                                <p class="author-text">${course.author}</p>
                                <p>Posted ${formattedDate}</p>
                            </div>
                        </div>
                    </div>
                    <p class="post-category">${course.category}</p>

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

function isValidBase64(base64String) {
  const base64Regex = /^[A-Za-z0-9+/=]+$/;
  return base64Regex.test(base64String);
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

function authenticatePost() {
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

function onTokenRefreshSuccess() {
  console.log("Token has been refreshed.");
  sendApiRequest(API_URL, "GET", "/users/" + user.username, onProfileDataSuccess);
}

function onTokenRefreshError(error) {
  console.error("Error refreshing token: ", error);
  window.location.href = ("/no-access");
}

async function loadProfileData(course, courseblock) {
  console.log("Loading profile data from API...");
  const jwt = getCookie("jwt");
  if (jwt && isTokenAboutToExpire(jwt)) {
    sendTokenRefreshRequest(onTokenRefreshSuccess, onTokenRefreshError);
  } else {
    await sendApiRequest(API_URL, "GET", "/users/" + course.author, onSuccess);
  }
}

function formatDate(dateString) {
  const date = new Date(dateString);

  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return date.toLocaleDateString(undefined, options);
}

function onSuccess(data) {
  createPosts(data.course, data, courseblock)
}

</script>

<template>
  <Alert v-show="showAlert === true" title="Missing user" message="You must create a user to access this functionality"
         :buttons="['OK', 'Cancel']" @buttonClicked="handleButtonClick"></Alert>
  <div id="background" class="background">
    <img class="planet" src="/greenPlanet.svg">
    <div class="logo-button">
      <h4 class="logo-top">Learniverse&nbsp;</h4>
      <h4 class="logo-bottom">Community</h4>
    </div>
  </div>
  <div class="course-section">
    <div class="community-top-container">
      <div class="search-container">

        <div class="search-bar">
          <label for="myTextBox"></label>
          <input class="search-prompt" type="text" id="myTextBox" name="myTextBox"
                 placeholder="Search for community posts" v-model="searchQuery" @input="searchPosts">
          <img class="search-icon" src="/search.png" alt="Connect">
        </div>
        <button class="post-button" @click="authenticatePost">
          <p>Create Post</p>
        </button>
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
    flex-direction: column;
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

  .post-button {
    margin-top: 10px;
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

  .post-button p {
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
    justify-content: flex-start;
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
    height: 60px;
    margin-right: 20px;
    position: relative;
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
  }

  .post-button {
    color: #EAEAEA;
    font-family: 'Inter', sans-serif;
    font-weight: bold;
    font-size: 14px;
    padding: 10px;
    border-radius: 20px;
    text-decoration: none;
    display: flex;
    min-width: 70px;
    height: 60px;
    background: #0C0C0C;
    border: 0.5px solid #252525;
    transition: all 0.3s ease-in-out;
    white-space: nowrap;

    &:hover {
      background-color: #262626;
      box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
    }

    p {
      color: #EAEAEA !important;
      font-family: 'Inter', sans-serif;
      font-weight: bold;
      font-size: 16px;
      margin: auto;
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

.logo-button {
  position: absolute;
  top: 310px;
  margin-left: auto;
  margin-right: auto;
  height: 70px;
  border: none;
  text-decoration: none;
  display: flex;
  align-items: center;
}

.logo-top {
  font-size: 50px;
  font-weight: bold;
  padding-left: 5px;
  color: #ececf4;
}

.logo-bottom {
  font-size: 50px;
  font-weight: bold;
  color: #4eeb91;
}

.author-and-date {
  display: flex;
  flex-direction: row;
}

</style>
