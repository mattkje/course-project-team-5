<script setup>
import {getCurrentInstance, onMounted} from "vue";
const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
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

          // Create a new course card element
          const courseCard = document.createElement('a');
          courseCard.className = 'course-card';
          courseCard.href = `/community/course/${course.courseId}`;

          const imageUrl = course.image ? course.image : '/noImageCom.svg';
          courseCard.innerHTML = `
        <img src="${imageUrl}" alt="Course ${course.courseId}">
        <div class="course-card-description">
            <h3>${course.title}</h3>
            <p>${course.description}</p>
        </div>
      `;

          // Append the course card to the course block
          courseBlock.appendChild(courseCard);
        });
      })
      .catch(error => {
        console.log('There was a problem with the fetch operation: ' + error.message);
      });
}

</script>

<template>
  <div id="background" class="background" >
    <div class="hero-container">
      <div class="hero-box">
        <img id="planet" class="planet1" src="/greenPlanet.svg" alt="Logo">

      </div>
    </div>
    <img class="bend" src="/bend.svg">
  </div>
  <div class="course-section">

    <div class="title">
      <h2>Community</h2>

    </div>
    <p class="description" >Connect with other learners, share your knowledge, and grow together.</p>


    <div class="search-container">

      <div class="search-bar">
        <label for="myTextBox"></label><input class="search-prompt" type="text" id="myTextBox" name="myTextBox"
                                              placeholder="Search for courses to boost your skills">
        <img class="search-icon" src="/search.png" alt="Connect">
      </div>
    </div>

    <div class="course-block" ref="courseBlock">
    </div>
    <div class="greeting"></div>
  </div>
</template>

<style scoped>
@media (max-width: 600px) {

  #planet {
    height: 120px;
    animation: ease-in-out pulse 5s infinite;
  }
}

.shrink-enter-active, .shrink-leave-active {
  transition: height 0.5s;
}
.shrink-enter, .shrink-leave-to /* .shrink-leave-active in <2.1.8 */ {
  height: 840px;
}

.background {
  top: 0;
  height: 500px;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

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



.bend {
  width: 100%;
  margin: 0;
  padding: 0;

}

.title {
  border: none;
  border-radius: 15px;
  width: 70%;
  justify-content: center;
  align-content: center;
  text-decoration: none;
  display: flex;
  margin: 0 auto;
}

.description {
  color: #282828;
  font-size: 20px;
  text-align: center;
  margin: 0 auto;
  width: 70%;
}

.course-section {
  background-color: #EAEAEA;
  margin: 0;
}

.search-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 10vh; /* Adjust as needed */
}

.search-bar {
  box-sizing: border-box;
  width: 60%;
  border: 3px solid #606060;
  border-radius: 20px;
  position: relative; /* Make it a positioning context for the absolute position of the button */
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
  color: var(--light-1);
  background: none;
  width: 60%;
  padding: 20px 20px 20px 100px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
}

.course-block {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  margin: 0 auto;
  width: 70%;
}


.planet1 {
  height: 150px;
  animation: ease-out slideIn 1s forwards, ease-out pulse 5s infinite 1s;
}

@keyframes slideIn {
  0% {
    transform: translatex(-1000px) translateY(-100px) scale(1.2);
  }
  100% {
    transform:  translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes pulse {
  0% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
  50% {
    transform: translatex(0) translateY(120px) scale(1.7);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }}

</style>
