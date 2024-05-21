<template>
  <Alert v-show="showAlert === true" title="Important Message" message="This website is a result of a university group project, performed in the course IDATA2301 Web
  technologies, at NTNU. All the information provided here is a result of imagination. Any resemblance with real companies or products is a coincidence.
  All the provided material is free to use for non-commercial purposes." :buttons="['OK']" @buttonClicked="handleButtonClick"></Alert>
  <!--- Show/hide hero section depending on user authenticated--->
  <HeroSection v-if="getAuthenticatedUser() === null"/>
  <WelcomeBack v-else/>

  <div class="course-section">

    <div class="greeting">
      <h1>Our Top Picks</h1>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div v-show="!loading" id="featured" class="featured">
        <!--- Featured courses will be appended here --->
      </div>

      <div v-show="loading" class="featured">
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
      </div>
      <button id="scrollRightButton">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>

    <div class="greeting"></div>
  </div>

  <!--- Hide pro ad if user is already of type pro--->
  <div class="pro-simple-container">
    <ProBoxSimple v-if="!hasRole('ROLE_PRO')"/>
  </div>
  <div class="pro-container">
    <ProBox v-if="!hasRole('ROLE_PRO')"/>
  </div>



  <div class="course-section">

    <div class="greeting">
      <h1>Explore Our Data Science Courses</h1>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-ds">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div v-show="!loading" class="data-Science">

      </div>
      <div v-show="loading" class="data-Science">
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
      </div>
      <button id="scrollRightButton-ds">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>


    <div class="greeting">
      <h1>Dive into the World of Digital Marketing</h1>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-dm">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div v-show="!loading" class="digital-marketing">

      </div>
      <div v-show="loading" class="digital-marketing">
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
      </div>
      <button id="scrollRightButton-dm">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>


    <div class="greeting"></div>
  </div>
  <div class="pro-box" v-if="getAuthenticatedUser() === null">
    <img class="bend-bottom" src="/bend.svg" alt="Connect">
    <h2>Featured courses from</h2>
    <div class="greeting">
      <img class="featured-providers"
           src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Microsoft_logo_%282012%29.svg/1024px-Microsoft_logo_%282012%29.svg.png"
           alt="Provider"/>
      <img class="featured-providers" src="https://1000logos.net/wp-content/uploads/2016/10/Apple-Logo.png"
           alt="Provider"/>
      <img class="featured-providers"
           src="https://upload.wikimedia.org/wikipedia/en/thumb/3/35/Pearson_logo.svg/2560px-Pearson_logo.svg.png"
           alt="Provider"/>
      <img class="featured-providers"
           src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/2560px-Amazon_logo.svg.png"
           alt="Provider"/>
    </div>
    <p class="pro-text">Learniverse offers courses from top providers like Microsoft, Apple, Pearson, and Amazon.</p>
    <img class="bend" src="/bend.svg" alt="Connect">
  </div>
  <div class="course-section">
    <div class="greeting">
      <h1>Uncover the World of Information Technologies</h1>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-it">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div v-show="!loading" class="information-technologies">

      </div>
      <div v-show="loading" class="information-technologies">
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
      </div>
      <button id="scrollRightButton-it">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>

    <div class="greeting">
      <h1>Access exclusive courses with Learniverse Pro</h1>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-lp">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div v-show="!loading" class="learniverse-pro">

      </div>
      <div v-show="loading" class="learniverse-pro">
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
        <div class="loader">
          <div class="wrapper">
            <div class="circle"></div>
            <div class="line-1"></div>
            <div class="line-2"></div>
            <div class="line-3"></div>
            <div class="line-4"></div>
            <div class="line-5"></div>
          </div>
        </div>
      </div>
      <button id="scrollRightButton-lp">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>

    <div class="greeting">
    </div>
  </div>

</template>

<script setup>
import ProBoxSimple from "@/components/ProBoxSimple.vue";
import ProBox from "@/components/ProBox.vue";
import HeroSection from "@/components/HeroSection.vue";

const loading = ref(true);
import "@/courseBox.css"
import {getCurrentInstance, onMounted, ref} from 'vue';
import {getAuthenticatedUser, hasRole} from "@/js/authentication";
import {currency, setDefaultCurrency} from "@/js/currency";
import {
  createContentBox,
  checkIfCourseInCategory,
  checkIfProCourse,
  fetchCourses,
  fetchCurrencies
} from "@/js/populationTools";
import WelcomeBack from "@/components/WelcomeBack.vue";
import Alert from "@/components/Alert.vue";
import {getCookie, setCookie} from "@/js/tools";


const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const showAlert = ref(true);


const showAlertCookie = getCookie('showAlert');
if (showAlertCookie === 'false') {
  showAlert.value = false;
}



// Initialize the page
onMounted(() => {
  populateCourses('.featured', () => true);
  populateCourses('.learniverse-pro', checkIfProCourse);
  populateCourses('.digital-marketing', courseProvider => checkIfCourseInCategory(courseProvider, "Digital Marketing"));
  populateCourses('.data-Science', courseProvider => checkIfCourseInCategory(courseProvider, "Data Science and Analytics"));
  populateCourses('.information-technologies', courseProvider => checkIfCourseInCategory(courseProvider, "Information Technology"));
  currency(API_URL);
  loadButtons();
});

const handleButtonClick = (button) => {
  if (button === 'OK') {
    showAlert.value = false;
    setCookie('showAlert', 'false', 168);
  }
};

// Populate the courses in the given selector
async function populateCourses(selector, filterFn) {
  document.querySelector(selector).innerHTML = '';
  const defaultCurrency = setDefaultCurrency() || 'USD';
  try {
    const [data, currencies] = await Promise.all([fetchCourses(API_URL), fetchCurrencies(API_URL)]);

    data.forEach(courseProvider => {

      if (filterFn(courseProvider)) {
        const contentBox = createContentBox(courseProvider, currencies, defaultCurrency);
        document.querySelector(selector).appendChild(contentBox.cloneNode(true));
      }
    });
    loading.value = false;
  } catch (error) {
    console.error('Error:', error);
  }
}

// Responsible for the scrolling buttons. Written with assistance from AI
function loadButtons() {
  const scrollAmount = 332;
  const scrollDuration = 500;

  function smoothScroll(target, direction) {
    const start = target.scrollLeft;
    const increment = direction === 'right' ? scrollAmount : -scrollAmount;
    let startTime = null;

    function animateScroll(currentTime) {
      if (!startTime) {
        startTime = currentTime;
      }

      const elapsed = currentTime - startTime;
      const progress = Math.min(elapsed / scrollDuration, 1);

      // Used copilot to generate the easing function
      const easeInOutCubic = t => t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
      const easedProgress = easeInOutCubic(progress);

      target.scrollLeft = start + increment * easedProgress;

      if (progress < 1) {
        requestAnimationFrame(animateScroll);
      }
    }

    requestAnimationFrame(animateScroll);
    const usernameLabel = document.getElementById('usernameLabel');
    if (usernameLabel) {
      usernameLabel.textContent = 'Welcome, ' + localStorage.getItem('username');
    }
  }

  function addScrollEvent(containerId, leftButtonId, rightButtonId) {
    const container = document.querySelector(containerId);
    const scrollLeftButton = document.getElementById(leftButtonId);
    const scrollRightButton = document.getElementById(rightButtonId);

    scrollLeftButton.addEventListener('click', () => {
      smoothScroll(container, 'left');
    });

    scrollRightButton.addEventListener('click', () => {
      smoothScroll(container, 'right');
    });
  }

  addScrollEvent('.featured', 'scrollLeftButton', 'scrollRightButton');
  addScrollEvent('.data-Science', 'scrollLeftButton-ds', 'scrollRightButton-ds');
  addScrollEvent('.digital-marketing', 'scrollLeftButton-dm', 'scrollRightButton-dm');
  addScrollEvent('.information-technologies', 'scrollLeftButton-it', 'scrollRightButton-it');
  addScrollEvent('.learniverse-pro', 'scrollLeftButton-lp', 'scrollRightButton-lp');

}

</script>

<style scoped>
@media (max-width: 769px) {

  #scrollLeftButton,
  #scrollRightButton,
  #scrollLeftButton-ds,
  #scrollRightButton-ds,
  #scrollLeftButton-dm,
  #scrollRightButton-dm,
  #scrollLeftButton-it,
  #scrollRightButton-it,
  #scrollLeftButton-lp,
  #scrollRightButton-lp {
    display: none;
  }

  .featured,
  .data-Science,
  .digital-marketing,
  .information-technologies,
  .learniverse-pro {

    border-radius: 20px;
    justify-content: flex-start;
    align-content: center;
    width: 100%;
    height: min-content;
    margin: 0;
    display: flex;
    flex-direction: row;
    overflow: auto;

    scrollbar-width: none;
    -ms-overflow-style: none;
  }
  .greeting {
    border: none;
    width: 100%;
    justify-content: center;
    align-content: center;
    text-decoration: none;
    display: flex;
    margin: 0 auto;
  }
  .greeting h1 {
    text-align: center;
    font-size: 30px;
  }

  .pro-container {
    display: none;
  }

  .loader {
    position: relative;
    min-width: 300px;
    height: 340px;
    margin: 30px;
    border: 1px solid #ffffff;
    border-radius: 20px;
    padding: 20px 20px 40px 20px;
    background-color: #ffffff;
    overflow: hidden;
  }

  .loader:after {
    content: "";
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background: linear-gradient(110deg, rgba(227, 227, 227, 0) 0%, rgba(227, 227, 227, 0) 40%, rgba(255, 255, 255, 0.5) 50%, rgba(227, 227, 227, 0) 60%, rgba(227, 227, 227, 0) 100%);
    animation: gradient-animation_2 1.2s linear infinite;
  }

  .loader .wrapper {
    width: 100%;
    height: 100%;
    position: relative;
  }

  .loader .wrapper > div {
    background-color: #ececec;
  }

  .loader .circle {
    border-radius: 10px;
    height: 150px;
    width: 100%;
  }

  .loader .button {
    display: inline-block;
    height: 32px;
    width: 75px;
  }

  .loader .line-1 {
    position: absolute;
    top: 190px;
    left: 0;
    height: 10px;
    width: 100%;
  }

  .loader .line-2 {
    position: absolute;
    top: 210px;
    left: 0;
    height: 10px;
    width: 64%;
  }

  .loader .line-3 {
    position: absolute;
    top: 230px;
    left: 0px;
    height: 10px;
    width: 60%;
  }

  .loader .line-4 {
    position: absolute;
    top: 280px;
    left: 0px;
    height: 10px;
    width: 92%;
  }

  .loader .line-5 {
    position: absolute;
    top: 340px;
    left: 70px;
    height: 10px;
    width: 90px;
  }
}

@media (min-width: 769px) {
  .featured,
  .data-Science,
  .digital-marketing,
  .information-technologies,
  .learniverse-pro {
    justify-content: space-between;
    background: #f8f8ff;
    border: 1px solid rgba(0, 0, 0, 0.08);
    border-radius: 20px;
    width: 100%;
    min-height: 550px;
    padding: 50px 30px;
    margin: 30px 0;
    display: flex;
    overflow: auto;

    scrollbar-width: none;
    -ms-overflow-style: none;
  }
  .greeting {
    border: none;
    border-radius: 15px;
    width: 60%;
    justify-content: flex-start;
    align-content: center;
    text-decoration: none;
    display: flex;
    margin: 0 auto;
  }

  .pro-simple-container {
    display: none;
  }

  .loader {
    position: relative;
    height: 390px;
    width: 270px;
    margin: 30px;
    border: 1px solid #ffffff;
    padding: 20px 20px 40px 20px;
    background-color: #ffffff;
    overflow: hidden;
  }

  .loader:after {
    content: "";
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background: linear-gradient(110deg, rgba(227, 227, 227, 0) 0%, rgba(227, 227, 227, 0) 40%, rgba(255, 255, 255, 0.5) 50%, rgba(227, 227, 227, 0) 60%, rgba(227, 227, 227, 0) 100%);
    animation: gradient-animation_2 1.2s linear infinite;
  }

  .loader .wrapper {
    width: 100%;
    height: 100%;
    position: relative;
  }

  .loader .wrapper > div {
    background-color: #ececec;
  }

  .loader .circle {
    height: 170px;
    width: 230px
  }

  .loader .button {
    display: inline-block;
    height: 32px;
    width: 75px;
  }

  .loader .line-1 {
    position: absolute;
    top: 211px;
    left: 0;
    height: 10px;
    width: 100%;
  }

  .loader .line-2 {
    position: absolute;
    top: 234px;
    left: 0;
    height: 10px;
    width: 64%;
  }

  .loader .line-3 {
    position: absolute;
    top: 257px;
    left: 0px;
    height: 10px;
    width: 60%;
  }

  .loader .line-4 {
    position: absolute;
    top: 280px;
    left: 0px;
    height: 10px;
    width: 92%;
  }

  .loader .line-5 {
    position: absolute;
    top: 340px;
    left: 70px;
    height: 10px;
    width: 90px;
  }

}


hr {
  background: #ADADAD;
  height: 0.15vw;
  border: none;
}

img {
  margin: auto;
  padding: 0;
}


ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}


@keyframes slide {
  0% {
    background-position: 0 0;
  }
  100% {
    background-position: -100px 0;
  }
}


.bend {
  max-width: 100%;
  transform: translateY(2px);
  margin: 0;
  padding: 0;
}

.bend-bottom {
  rotate: 180deg;
  max-width: 100%;
  transform: translateY(2px);
  margin: 0;
  padding: 0;
}


@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.pro-box {
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  color: white;
  font-weight: bold;
  font-size: 20px;
  margin: 0;
}

.pro-box h2 {
  color: white;
}

.course-section {
  background-color: var(--light-3);
  transform: translateZ(-50px);
  margin: 0;
}



.featured::-webkit-scrollbar,
.data-Science::-webkit-scrollbar,
.digital-marketing::-webkit-scrollbar,
.information-technologies::-webkit-scrollbar,
.learniverse-pro::-webkit-scrollbar {
  display: none;
}

.rightArrow {
  transform: scaleX(-1);
  transition: filter 0.5s ease;

  &:hover {
    filter: brightness(0.8);
  }

  &:active {
    filter: brightness(0.9);
  }
}

.leftArrow {
  transition: filter 0.5s ease;

  &:hover {
    filter: brightness(0.8);
  }

  &:active {
    filter: brightness(0.9);
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

@keyframes courseHover {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(1.1);
  }
}


@keyframes orbit {
  0% {
    transform: translateY(70px) scale(1.2);
  }
  50% {
    transform: translateY(70px) scale(1.4);
  }
  100% {
    transform: translateY(70px) scale(1.2);
  }
}

input:focus {
  outline: none;
}




.pro-text {
  color: white;
}

.content-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  padding: 0;
}

* {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-rendering: optimizeLegibility;
}

.featured-providers {
  height: 3vw;
  filter: brightness(0) invert(1);
}

button {
  background-color: transparent;
  border-color: transparent;
}


.fancy-button {
  color: var(--light-1);
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  margin: 0 10px;
  max-height: 50px;
  background: #0C0C0C;
  border: 1px solid #252525;

}

.fancy-button:hover {
  background-color: #262626;
  box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
}


* {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-rendering: optimizeLegibility;
}



@keyframes gradient-animation_2 {
  0% {
    transform: translateX(-100%);
  }

  100% {
    transform: translateX(100%);
  }
}

</style>
