<template>

  <!--- Show/hide hero section depending on user authenticated--->
  <HeroSection v-if="getAuthenticatedUser() === null"/>
  <WelcomeBack v-else />

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
        <div  class="three-body">
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
        </div>
      </div>
      <button id="scrollRightButton">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>

    <div class="greeting"></div>
  </div>

  <!--- Hide pro ad if user is already of type pro--->
  <ProBox v-if="!isOfRoleUser('ROLE_PRO')"/>

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
        <div  class="three-body">
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
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
        <div  class="three-body">
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
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
    <button class="fancy-button">Learn More</button>
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
        <div  class="three-body">
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
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
        <div  class="three-body">
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
          <div class="three-body__dot"></div>
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
import ProBox from "@/components/ProBox.vue";
import HeroSection from "@/components/HeroSection.vue";
const loading = ref(true);
import "@/courseBox.css"
import {getCurrentInstance, onMounted, ref} from 'vue';
import {getAuthenticatedUser, isOfRoleUser} from "@/js/authentication";
import {currency, setDefaultCurrency} from "@/js/currency";
import WelcomeBack from "@/components/WelcomeBack.vue";


const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;


onMounted(() => {
  populateCourses('.featured', () => true);
  populateCourses('.learniverse-pro', checkIfProCourse);
  populateCourses('.digital-marketing', checkIfDmCourse);
  populateCourses('.data-Science', checkIfDsCourse);
  populateCourses('.information-technologies', checkIfItCourse);
  currency(API_URL);
  loadButtons();
});

function populateCourses(selector, filterFn) {
  document.querySelector(selector).innerHTML = '';
  const defaultCurrency = setDefaultCurrency() || 'USD';
  fetch(API_URL + '/courses')
      .then(response => response.json())
      .then(data => {
        fetch(API_URL + '/currency')
            .then(response => response.json())
            .then(currencies => {
              data.forEach(courseProvider => {

                if (filterFn(courseProvider)) {

                  const contentBox = document.createElement('a');
                  contentBox.href = `/courses?id=${courseProvider.course.courseId}`;
                  contentBox.className = 'content-box';

                  const image = document.createElement('img');
                  image.src = courseProvider.course.image || '/noImage.svg';
                  image.alt = 'Course image';
                  image.className = 'content-box-image';
                  contentBox.appendChild(image);



                  const descriptionBox = document.createElement('div');
                  descriptionBox.className = 'content-box-description';
                  contentBox.appendChild(descriptionBox);

                  const title = document.createElement('h2');
                  title.className = 'content-box-title';
                  title.textContent = courseProvider.course.title;
                  descriptionBox.appendChild(title);

                  const hr = document.createElement('hr');
                  descriptionBox.appendChild(hr);

                  const attributes = document.createElement('div');
                  attributes.className = 'content-box-attributes';
                  descriptionBox.appendChild(attributes);

                  // Create and append the category attribute
                  const categoryAttribute = document.createElement('div');
                  categoryAttribute.className = 'content-box-attribute';
                  attributes.appendChild(categoryAttribute);

                  const categoryIcon = document.createElement('img');
                  categoryIcon.className = 'content-box-icon';
                  categoryIcon.src = '/category.svg';
                  categoryAttribute.appendChild(categoryIcon);

                  const category = document.createElement('p');
                  category.className = 'content-box-text';
                  category.textContent = courseProvider.course.category;
                  categoryAttribute.appendChild(category);

                  // Create and append the providers attribute
                  const providersAttribute = document.createElement('div');
                  providersAttribute.className = 'content-box-attribute';
                  attributes.appendChild(providersAttribute);

                  const providersIcon = document.createElement('img');
                  providersIcon.className = 'content-box-icon';
                  providersIcon.src = '/providers.svg';
                  providersAttribute.appendChild(providersIcon);

                  const providersElement = document.createElement('p');
                  providersElement.className = 'content-box-text';


                  const courseProviders = courseProvider.providers.filter(provider => provider.courseId === courseProvider.course.courseId);

                  if (Array.isArray(courseProviders) && courseProviders.length) {
                    providersElement.innerHTML = `${courseProviders.length}&nbsp;Providers`;
                  } else {
                    providersElement.innerHTML = 'No&nbsp;Providers';
                  }

                  providersAttribute.appendChild(providersElement);



                  const price = document.createElement('p');
                  price.className = 'content-button';


                  const lowestPriceProvider = courseProviders.reduce((prev, curr) => {
                    return (prev.price < curr.price) ? prev : curr;
                  });

                  const currency = lowestPriceProvider.currency;
                  const priceInCurrency = lowestPriceProvider.price;


                  let symbol = '';
                  let rate = 1;

                  for (let i = 0; i < currencies.length; i++) {
                    if (currencies[i].code === currency) {
                      rate = currencies[i].rate;
                      break;
                    }
                  }

                  const priceInDefaultCurrency = priceInCurrency / rate;

                  for (let i = 0; i < currencies.length; i++) {
                    if (currencies[i].code === defaultCurrency) {
                      symbol = currencies[i].symbol;
                      rate = currencies[i].rate;
                      break;
                    }
                  }

                  const finalPrice = priceInDefaultCurrency * rate;

                  const priceBox = document.createElement('div');
                  priceBox.className = 'price-box';


                  if (currency === 'SUB') {
                    const image2 = document.createElement('img');
                    image2.className = 'content-box-image-banner';
                    image2.src = '/proBanner.svg';
                    priceBox.appendChild(image2);
                    price.textContent = symbol + finalPrice.toFixed(2) + "/month";
                  } else {
                    const startsAt = document.createElement('p');
                    startsAt.className = 'content-box-text';
                    startsAt.textContent = 'Starts at';
                    priceBox.appendChild(startsAt);
                    price.textContent =symbol + finalPrice.toFixed(2);
                  }


                  priceBox.appendChild(price);

                  const hr2 = document.createElement('hr');
                  descriptionBox.appendChild(hr2);

                  descriptionBox.appendChild(priceBox);

                  document.querySelector(selector).appendChild(contentBox.cloneNode(true));
                }
              });
              loading.value = false;
            })
            .catch(error => console.error('Error:', error));
      })
      .catch(error => console.error('Error:', error));
}


function checkIfProCourse(courseProvider) {
  return courseProvider.providers.some(provider => provider.name === "Learniverse");
}

function checkIfDmCourse(courseProvider) {
  return courseProvider.course.category === "Digital Marketing";
}

function checkIfDsCourse(courseProvider) {
  return courseProvider.course.category === "Data Science and Analytics";
}

function checkIfItCourse(courseProvider) {
  return courseProvider.course.category === "Information Technology";
}


function loadButtons() {
  const scrollAmount = 332; // Amount of pixels to scroll
  const scrollDuration = 500; //Time to scroll

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
    if(usernameLabel) {
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
@media (max-width: 600px) {

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

  #featured {
    display: flow;
    justify-content: center;
    align-content: center;
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
  width: 100%;
  transform: scaleX(1.01) translateY(2px);
  margin: 0;
  padding: 0;

}

.bend-bottom {
  rotate: 180deg;
  width: 100%;
  transform: scaleX(1.01) translateY(2px);
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
  background-color: #f1f0fd;
  transform: translateZ(-50px);
  margin: 0;
}

.featured,
.data-Science,
.digital-marketing,
.information-technologies,
.learniverse-pro {
  justify-content: space-between;
  background: #f8f8ff;
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 40px;
  width: 100%;
  min-height: 550px;
  padding: 50px 30px;
  margin: 30px 0;
  display: flex;
  overflow: auto;


  scrollbar-width: none;
  -ms-overflow-style: none;

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
  font-smoothing: antialiased;
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
  border: 0.5px solid #252525;

}

.fancy-button:hover {
  background-color: #262626;
  box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
}


* {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
}


.three-body {
  --uib-size: 35px;
  --uib-speed: 0.8s;
  --uib-color: #5D3FD3;
  position: relative;
  display: inline-block;
  height: var(--uib-size);
  width: var(--uib-size);
  animation: spin78236 calc(var(--uib-speed) * 2.5) infinite linear;
}

.three-body__dot {
  position: absolute;
  height: 100%;
  width: 30%;
}

.three-body__dot:after {
  content: '';
  position: absolute;
  height: 0%;
  width: 100%;
  padding-bottom: 100%;
  background-color: var(--uib-color);
  border-radius: 50%;
}

.three-body__dot:nth-child(1) {
  bottom: 5%;
  left: 0;
  transform: rotate(60deg);
  transform-origin: 50% 85%;
}

.three-body__dot:nth-child(1)::after {
  bottom: 0;
  left: 0;
  animation: wobble1 var(--uib-speed) infinite ease-in-out;
  animation-delay: calc(var(--uib-speed) * -0.3);
}

.three-body__dot:nth-child(2) {
  bottom: 5%;
  right: 0;
  transform: rotate(-60deg);
  transform-origin: 50% 85%;
}

.three-body__dot:nth-child(2)::after {
  bottom: 0;
  left: 0;
  animation: wobble1 var(--uib-speed) infinite
  calc(var(--uib-speed) * -0.15) ease-in-out;
}

.three-body__dot:nth-child(3) {
  bottom: -5%;
  left: 0;
  transform: translateX(116.666%);
}

.three-body__dot:nth-child(3)::after {
  top: 0;
  left: 0;
  animation: wobble2 var(--uib-speed) infinite ease-in-out;
}

@keyframes spin78236 {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

@keyframes wobble1 {
  0%,
  100% {
    transform: translateY(0%) scale(1);
    opacity: 1;
  }

  50% {
    transform: translateY(-66%) scale(0.65);
    opacity: 0.8;
  }
}

@keyframes wobble2 {
  0%,
  100% {
    transform: translateY(0%) scale(1);
    opacity: 1;
  }

  50% {
    transform: translateY(66%) scale(0.65);
    opacity: 0.8;
  }
}

</style>
