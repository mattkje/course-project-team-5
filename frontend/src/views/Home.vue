<template>

  <HeroSection msg="HeroSection"/>

  <div class="course-section">

    <div class="greeting">
      <h2>Our Top Picks</h2>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div class="featured">
        <!--- Featured courses will be appended here --->
      </div>
      <button id="scrollRightButton">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>

    <div class="greeting"></div>
  </div>

  <ProBox/>

  <div class="course-section">

    <div class="greeting">
      <h2>Explore Our Data Science Courses</h2>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-ds">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div class="data-Science">
        <!--- Data Science courses will be appended here --->
      </div>
      <button id="scrollRightButton-ds">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>


    <div class="greeting">
      <h2>Dive into the World of Digital Marketing</h2>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-dm">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div class="digital-marketing">
        <!--- DM courses will be appended here --->
      </div>
      <button id="scrollRightButton-dm">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>


    <div class="greeting"></div>
  </div>
  <div class="pro-box">
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
      <h2>Uncover the World of Information Technologies</h2>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-it">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div class="information-technologies">
        <!--- IT courses will be appended here --->
      </div>
      <button id="scrollRightButton-it">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>

    <div class="greeting">
      <h2>Access exclusive courses with Learniverse Pro</h2>
    </div>

    <div class="content-container">
      <button id="scrollLeftButton-lp">
        <img class="leftArrow" src="/arrow.svg" alt="Connect">
      </button>
      <div class="learniverse-pro">
        <!--- LP courses will be appended here --->
      </div>
      <button id="scrollRightButton-lp">
        <img class="rightArrow" src="/arrow.svg" alt="Connect">
      </button>
    </div>

    <div class="greeting">
    </div>
  </div>

  <img class="bend-bottom" src="/bend.svg" alt="Connect">

</template>

<script setup>
import ProBox from "@/components/ProBox.vue";
import HeroSection from "@/components/HeroSection.vue";

import "@/courseBox.css"
import { onMounted } from 'vue';

const API_URL = 'http://localhost:8080/api';

window.onload = function () {
  populateCourses('.featured', () => true);
  populateCourses('.learniverse-pro', checkIfProCourse);
  populateCourses('.digital-marketing', checkIfDmCourse);
  populateCourses('.data-Science', checkIfDsCourse);
  populateCourses('.information-technologies', checkIfItCourse);
  currency();
  loadButtons();
};

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
                  image.src = courseProvider.course.image || 'media/noImage.svg';
                  image.alt = 'Course image';
                  image.className = 'content-box-image';
                  contentBox.appendChild(image);

                  const image2 = document.createElement('img');
                  image2.className = 'content-box-image-banner';
                  contentBox.appendChild(image2);

                  const descriptionBox = document.createElement('div');
                  descriptionBox.className = 'content-box-description';
                  contentBox.appendChild(descriptionBox);

                  const title = document.createElement('h2');
                  title.className = 'content-box-title';
                  title.textContent = courseProvider.course.title;
                  descriptionBox.appendChild(title);

                  const hr = document.createElement('hr');
                  descriptionBox.appendChild(hr);

                  const contentDescription = document.createElement('div');
                  contentDescription.className = 'content-description';
                  descriptionBox.appendChild(contentDescription);

                  const attributes = document.createElement('div');
                  attributes.className = 'content-box-attributes';
                  contentDescription.appendChild(attributes);

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

                  if (currency === 'SUB') {
                    image2.src = '/proBanner.svg';
                    price.textContent = symbol + finalPrice.toFixed(2) + "/month";
                  } else {
                    price.textContent = symbol + finalPrice.toFixed(2);
                  }

                  const priceBox = document.createElement('div');
                  priceBox.className = 'price-box';
                  priceBox.appendChild(price);

                  contentDescription.appendChild(priceBox);

                  document.querySelector(selector).appendChild(contentBox.cloneNode(true));
                }
              });
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


function currency(){
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
        document.getElementById('currencySelect').addEventListener('change', function() {
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

function loadButtons() {
  document.getElementById('showPro').addEventListener('click', function() {
    var proInfoText = document.getElementById('proInfoText');
    if (proInfoText.style.height === '0px') {
      proInfoText.style.height = '300px';
      proInfoText.style.opacity = '1';
    } else {
      proInfoText.style.height = '0px';
      proInfoText.style.opacity = '0';
    }
  });

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
  margin: 0;
  padding: 0;

}

.bend-bottom {
  rotate: 180deg;
  width: 100%;
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
  background-color: #EAEAEA;
  margin: 0;
}

.featured,
.data-Science,
.digital-marketing,
.information-technologies,
.learniverse-pro {
  justify-content: space-between;
  background: white;
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 40px;
  width: 100%;
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
  width: 70%;
  justify-content: center;
  align-content: center;
  text-decoration: none;
  display: flex;
  margin: 0 auto;
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

</style>
