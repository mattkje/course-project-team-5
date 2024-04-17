<template>
  <div class="course-page-background">
    <div class="providerList">
      <div class="course-image-box">
        <img id="courseImage" class="course-image" alt="Course Image">
        <div class="course-image-blur"></div>
      </div>
      <div class="courseLinkElement">
        <a href="/community">Community</a>
        <p>&nbsp;->&nbsp;</p>
        <p id="courseCategoryLink"></p>
        <p>&nbsp;->&nbsp;</p>
        <p id="courseTitleLink"></p>
      </div>
      <div class="course">
        <div class="course-description-box">
          <h2 id="courseTitle"></h2>
          <div class="author-and-time">
            <p id="postAuthor"></p>
            <p id="postDate"></p>
          </div>

          <hr>
          <h3>Topic</h3>
          <p id="courseDescription"></p>
          <hr>
          <h3>Post</h3>
          <p id="postContent"></p>
        </div>
        <div class="course-description-box"/>
      </div>
    </div>

    <div class="greeting">
      <h2 id="pbsu"/>
    </div>

    <div class="featured">
      <div class="course-block" id="courseBlock" ref="courseBlock">
      </div>
    </div>
    <div class="greeting">
    </div>
  </div>
</template>

<script setup>
import {getCurrentInstance, onMounted} from 'vue';
import "@/assets/coursePage.css"
import {isOfRoleUser} from "@/js/authentication";

onMounted(() => {
  populateCoursePage();
  currency();
});

const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;


// Function to populate the course page with the course data
function populateCoursePage() {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');

  fetch(API_URL + `/community/courses/${id}`)
      //Checking if course exists
      .then(response => {
        if (!response.ok) {
          if (response.status === 404) {
            //Use custom course not found page?
            window.location.href = '/404';
          } else {
            throw new Error('Network response was not ok');
          }
        }
        return response.json();
      })
      //Continuing if course exists
      .then(data => {

        //Populating the similar courses box
        populatePosts('.featured');

        const dateText = formatDate(data.postDate)
        //Populating the course page
        document.getElementById('courseCategoryLink').innerText = data.category;
        document.getElementById('courseTitleLink').innerText = data.title;
        document.getElementById('postAuthor').innerText = data.author;
        document.getElementById('postDate').innerText = 'Posted: '+ dateText;
        document.getElementById('courseDescription').innerText = data.description;
        document.getElementById('postContent').innerText = data.content;
        document.getElementById('courseImage').src = data.image || '/noImageCom.svg';
        document.getElementById('courseTitle').innerText = data.title;


      })
      .catch(error => console.error('Error:', error));

}


//Should add "st", "nd", "rd" or "th" to the day number.
function getOrdinalSuffix(day) {
  if (day % 10 === 1 && day !== 11) {
    return day + "st";
  } else if (day % 10 === 2 && day !== 12) {
    return day + "nd";
  } else if (day % 10 === 3 && day !== 13) {
    return day + "rd";
  } else {
    return day + "th";
  }
}

// Output: 2024-02-01
function formatDate(dateString) {
  const originalDate = new Date(dateString);

  const year = originalDate.getFullYear();

  const month = originalDate.getMonth() + 1;
  const day = originalDate.getDate();

  const formattedDate = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;

  return formattedDate;
}

function populatePosts() {
  fetch(API_URL + '/community/courses')
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(courses => {
        const courseBlock = document.getElementById('courseBlock');


        courseBlock.innerHTML = '';

        courses.forEach(course => {

          if (course.author === document.getElementById('postAuthor').innerText && course.title !== document.getElementById('courseTitle').innerText) {
            document.getElementById('pbsu').innerText = 'Posts by the same user';
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
                </div>`;

            // Append the course card to the course block
            courseBlock.appendChild(courseCard);
          }

        });
        if (courseBlock.innerHTML === '') {
          courses.forEach(course => {
            if (course.title !== document.getElementById('courseTitle').innerText) {
              document.getElementById('pbsu').innerText = 'Other posts';
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
                </div>`;
              // Append the course card to the course block
              courseBlock.appendChild(courseCard);
            }
          });
        }
      })
      .catch(error => {
        console.log('There was a problem with the fetch operation: ' + error.message);
      });
}



function onlineCourse() {
  document.getElementById('map-container').style.display = "none";
}

function currency() {
  fetch(API_URL + '/currency')
      .then(response => response.json())
      .then(currencies => {
        const select = document.getElementById('currencySelect');
        currencies.forEach(currency => {
          const option = document.createElement('option');
          option.value = currency.code;
          option.text = currency.code + ' - ' + currency.symbol;
          select.appendChild(option);
        });

        const defaultCurrency = setDefaultCurrency();
        if (defaultCurrency) {
          select.value = defaultCurrency;
        }
        document.getElementById('currencySelect').addEventListener('change', function () {
          document.cookie = `defaultCurrency=${this.value}; path=/; max-age=31536000`;
          location.reload();
        });
      })
      .catch(error => console.error('Error:', error));

}

function setDefaultCurrency() {
  const cookies = document.cookie.split('; ');
  const defaultCurrencyCookie = cookies.find(row => row.startsWith('defaultCurrency='));
  if (defaultCurrencyCookie) {
    return defaultCurrencyCookie.split('=')[1];
  }
}


</script>

<style scoped>

.course-page-background {
  font-family: Inter, sans-serif;
  background-color: #eaeaea;
  color: #090909;
  margin: 0;
  padding: 0;
}


img {
  margin: auto;
  padding: 0;
}

button {
  background-color: transparent;
  border-color: transparent;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}


/* Header  */

#menubar {
  background-color: rgba(6, 6, 16, 0.9);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  position: sticky;
  top: 0;
  width: 100%;
  z-index: 100;
}

.left-content {
  max-height: 70px;
  padding: 0 2vw;
  display: flex;
  align-items: center;
}

.right-content {
  max-height: 70px;
  margin: 3vw;
  display: flex;
  max-width: min-content;
  align-items: center;
}

.left-content-general {
  display: flex;
  align-items: center;
  margin-left: 50px;
  flex: 1; /* Allow the right content to take up remaining space */
  justify-content: flex-end; /* Align content to the right */
}

.right-content-general {
  display: flex;
  align-items: center;
  margin-right: 50px;
  flex: 1; /* Allow the right content to take up remaining space */
  justify-content: flex-end; /* Align content to the right */
}

.content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
}

.menu-links {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 50px;
}

.logo-text-container {
  margin: 0;
  padding: 0;
}

.logo-top {
  font-weight: 900;
  padding-left: 5px;
}

.logo-bottom {
  color: var(--base-1);
}


.background-overlay {
  background: linear-gradient(rgba(0, 0, 33, 0.83), rgba(0, 0, 33, 0.74));
}

/* Hero  */

.hero-hbox {
  display: flex;
  justify-content: left;
  align-items: center;
  padding-left: 10vw;
}

.hero-container {
  display: flex;
}

.hero-box {
  flex: 1;
  max-width: 600px;
}

.mail-box {
  display: flex;
}

.mail-prompt {
  width: 100%;
  padding: 20px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 22px;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
}

.hbox {
  display: flex;
}

.stack-pane {
  position: relative;
}

.hero-image {
  max-width: 400px;
  height: auto;
  position: absolute;
  top: 150px;
  left: 0;
}

.hero-graphic {
  max-width: 700px;
  width: 50%;
  top: 0;
  left: 0;
  opacity: 0;
}

input:focus {
  outline: none;
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
  border: 3px solid #b2aece;
  border-radius: 20px;
  position: relative; /* Make it a positioning context for the absolute position of the button */
}


.search-prompt {
  background: none;
  width: 60%;
  padding: 20px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
}


.greeting {
  border: none;
  padding: 10px 20px;
  border-radius: 15px;
  width: 70%;
  align-content: center;
  text-decoration: none;
  display: flex;
  margin: auto;
}

.course-image {
  background-color: #3c3c3c;
  position: absolute;
  height: 400px;
  width: 100%;
  object-fit: cover;
  margin: 0;
}

.course-image-blur {
  position: relative;
  height: 400px;
  padding: 0;
  width: 100%;
  margin: 0;
  background: linear-gradient(to top, rgba(15, 16, 19, 0.68) 0%, transparent 20%);
}

.course-image-box {
  position: absolute;
  height: 400px;
  width: 100%;
  margin: 0;
}

.course {
  padding: 10px 20px;
  width: 1400px;
  align-content: center;
  justify-content: center;
  text-decoration: none;
  display: flex;
  margin: 0 auto;
  height: min-content;
}

.content-box {
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  justify-content: flex-start;
}

.course-description-box {
  border: none;
  padding: 10px 20px;
  margin: 400px auto 0 auto;
  width: 100%;
}

.course-description-box h3 {
  font-size: 20px;
  font-weight: bold;
}

.course-description-box hr {
  width: 1400px;

}

#courseDescription,
#postAuthor,
#postContent,
#postDate{
  color: #282828;
}


.hbox {
  display: flex;
}

.product-slide {
  display: flex;
  margin: 30px auto auto;
}

.hgrow {
  margin: auto;
}

.logo {
  width: 40px;
  height: 40px;
  padding-left: 20px;
  transform: translateY(-3px);
}

.cart {
  width: 35px;
  height: 35px;
}

.menu-icons {
  width: 30px;
  height: 30px;
  padding: 0;
}


.additional-info {
  display: none;
}


.content-hbox {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: auto;
  justify-content: space-between;
}

hr {
  border: 0;
  height: 3px;
  background: #282828;
  margin: 20px 0;
}

.courseInfoElement {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  width: max-content;
}

.courseInfoElement p {
  font-weight: bold;
  color: #282828;
}

.courseLinkElement {
  background-color: white;
  padding: 9px 7px;
  border-radius: 0 0 10px 0;
  z-index: 50;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  margin-left: 100px;
  position: relative;
  right: 100px;
  width: min-content;
  white-space: nowrap;
}

.courseLinkElement p {
  font-weight: bold;
  color: #5d5d5d;
}

.courseLinkElement a {
  font-weight: bold;
  color: #5d5d5d;
  text-decoration: none;
}

.course-action-box {
  display: flex;
  justify-content: left;
  align-items: center;
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
  border: 2px solid var(--light-2);
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
  opacity: 50%;
}


.search-prompt {
  background: none;
  width: 60%;
  padding: 20px 20px 20px 100px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
}


.clear {
  clear: both;
}

.checkBox {
  display: block;
  cursor: pointer;
  width: 30px;
  height: 30px;
  border: 3px solid rgba(255, 255, 255, 0);
  border-radius: 10px;
  position: relative;
  overflow: hidden;
  box-shadow: 0px 0px 0px 2px #fff;
}

.checkBox div {
  width: 60px;
  height: 60px;
  background-color: #fff;
  top: -52px;
  left: -52px;
  position: absolute;
  transform: rotateZ(45deg);
  z-index: 100;
}

.checkBox input[type=checkbox]:checked + div {
  left: -10px;
  top: -10px;
}

.checkBox input[type=checkbox] {
  position: absolute;
  left: 50px;
  visibility: hidden;
}

.transition {
  transition: 300ms ease;
}

.filter-box {
  background-color: rgba(29, 28, 32, 0.7);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, .08);
  margin: 3vw;
  padding: 2vw;
  border-radius: 17px;
}

.featured {
  background-color: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 17px;
  width: 90%;
  margin: auto;
}


.map-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 1400px;
  margin: auto;
  border-radius: 30px;
}

#map {
  height: 400px;
  width: 1400px;
  object-fit: cover;
  border-radius: 15px;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  transition: transform 0.5s ease;
}

button {
  background-color: transparent;
  border-color: transparent;
}

.header-button .text {
  font-size: 0;
  width: 0;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.header-button:hover .text {
  font-size: medium;
  opacity: 1;
}

.course-block {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  margin: 0 auto;
  width: 90%;
}

.author-and-time{
  display: flex;
  justify-content: space-between;
}
</style>
