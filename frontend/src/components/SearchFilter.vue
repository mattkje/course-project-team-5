<template>
  <div class="search-container">

    <div class="search-bar">
      <label for="myTextBox"></label>
      <input class="search-prompt" type="text" id="myTextBox" name="myTextBox"
             placeholder="Search for courses to boost your skills" v-model="searchQuery" @input="searchCourses">
      <img class="search-icon" src="/search.png" alt="Connect">
    </div>
  </div>

  <div class="active-filter-container">

    </div>
  <div class="filter-container">
    <div class="range-container">

      <button class="price-ranger" @click="toggleShowCategory" :style="{
          'border-radius': isCategoryVisible ? '10px 10px 0 0' : '10px'
        }">Category
      </button>
      <div class="wrapper" id="categoryContainer" :style="{
          height: isCategoryVisible ? '200px' : '0px',
          opacity: isCategoryVisible ? '1' : '0',
          zIndex: isCategoryVisible ? '0' : -100,
          'border-radius': isCategoryVisible ? '0 0 10px 10px' : '10px'
        }">
        <div class="category-list">
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Information Technologies</label>
            <input class="inp-cbx" id="morning" type="checkbox">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Digital Marketing</label>
            <input class="inp-cbx" id="morning" type="checkbox" v-model="isCheckboxChecked" @change="onCheckboxChange">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Business and Entrepreneurship</label>
            <input class="inp-cbx" id="morning" type="checkbox">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Data Science and Analytics</label>
            <input class="inp-cbx" id="morning" type="checkbox">
          </div>
        </div>
      </div>


      <button class="price-ranger" @click="toggleShowPrice" :style="{
          'border-radius': isPriceVisible ? '10px 10px 0 0' : '10px'
        }">Price
      </button>
      <div class="wrapper" id="priceText" :style="{
          height: isPriceVisible ? '200px' : '0px',
          opacity: isPriceVisible ? '1' : '0',
          zIndex: isPriceVisible ? '0' : -100,
          'border-radius': isPriceVisible ? '0 0 10px 10px' : '10px'
        }">
        <header>
          <h2>Price Range</h2>
        </header>
        <div class="price-input">
          <div class="field">
            <span>Min</span>
            <input type="number" class="input-min" value="2500">
          </div>
          <div class="separator">-</div>
          <div class="field">
            <span>Max</span>
            <input type="number" class="input-max" value="7500">
          </div>
        </div>
        <div class="slider">
          <div class="progress"></div>
        </div>
        <div class="range-input">
          <input type="range" class="range-min" min="0" max="10000" value="2500" step="100">
          <input type="range" class="range-max" min="0" max="10000" value="7500" step="100">
        </div>
      </div>

      <button class="price-ranger" @click="toggleShowDate" :style="{
          'border-radius': isDateVisible ? '10px 10px 0 0' : '10px'
        }">Date
      </button>
      <div class="wrapper" id="dateContainer" :style="{
          height: isDateVisible ? '200px' : '0px',
          opacity: isDateVisible ? '1' : '0',
          zIndex: isDateVisible ? '0' : -100,
          'border-radius': isDateVisible ? '0 0 10px 10px' : '10px'
        }">
        <header>
          <h2>Date</h2>
        </header>
      </div>

      <button class="price-ranger" @click="toggleShowProvider" :style="{
          'border-radius': isProviderVisible ? '10px 10px 0 0' : '10px'
        }">Provider
      </button>
      <div class="wrapper" id="providerContainer" :style="{
          height: isProviderVisible ? '200px' : '0px',
          opacity: isProviderVisible ? '1' : '0',
          zIndex: isProviderVisible ? '0' : -100,
          'border-radius': isProviderVisible ? '0 0 10px 10px' : '10px'
        }">
        <header>
          <h2>Provider</h2>
        </header>
      </div>

      <button class="price-ranger" @click="toggleShowDifficulty" :style="{
          'border-radius': isDifficultyVisible ? '10px 10px 0 0' : '10px'
        }">Difficulty
      </button>
      <div class="wrapper" id="difficultyContainer" :style="{
          height: isDifficultyVisible ? '200px' : '0px',
          opacity: isDifficultyVisible ? '1' : '0',
          zIndex: isDifficultyVisible ? '0' : -100,
          'border-radius': isDifficultyVisible ? '0 0 10px 10px' : '10px'
        }">
        <header>
          <h2>Difficulty</h2>
        </header>
      </div>

      <button class="price-ranger" @click="toggleShowDuration" :style="{
          'border-radius': isDurationVisible ? '10px 10px 0 0' : '10px'
        }">Duration
      </button>
      <div class="wrapper" id="durationContainer" :style="{
          height: isDurationVisible ? '200px' : '0px',
          opacity: isDurationVisible ? '1' : '0',
          zIndex: isDurationVisible ? '0' : -100,
          'border-radius': isDurationVisible ? '0 0 10px 10px' : '10px'
        }">
        <header>
          <h2>Duration</h2>
        </header>
      </div>

      <button class="price-ranger" @click="toggleShowCredit" :style="{
          'border-radius': isCreditVisible ? '10px 10px 0 0' : '10px'
        }">Credit
      </button>
      <div class="wrapper" id="creditContainer" :style="{
          height: isCreditVisible ? '200px' : '0px',
          opacity: isCreditVisible ? '1' : '0',
          zIndex: isCreditVisible ? '0' : -100,
          'border-radius': isCreditVisible ? '0 0 10px 10px' : '10px'
        }">
        <header>
          <h2>Credit</h2>
        </header>
      </div>

    </div>
    <div class="course-container">
    </div>
  </div>
</template>

<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";
import {currency, setDefaultCurrency} from "@/js/currency";

const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const searchQuery = ref('');

onMounted(() => {
  populateCourses('.course-container');
  currency(API_URL);
});

function searchCourses() {
  populateCourses('.course-container');
}

function populateCourses(selector) {
  document.querySelector(selector).innerHTML = '';
  const defaultCurrency = setDefaultCurrency() || 'USD';
  fetch(API_URL + '/courses')
      .then(response => response.json())
      .then(data => {
        fetch(API_URL + '/currency')
            .then(response => response.json())
            .then(currencies => {
              data.forEach(courseProvider => {
                if (courseProvider.course.title.toLowerCase().includes(searchQuery.value.toLowerCase()) || courseProvider.course.category.toLowerCase().includes(searchQuery.value.toLowerCase())) {
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
                    price.textContent = symbol + finalPrice.toFixed(2);
                  }


                  priceBox.appendChild(price);

                  const hr2 = document.createElement('hr');
                  descriptionBox.appendChild(hr2);

                  descriptionBox.appendChild(priceBox);

                  document.querySelector(selector).appendChild(contentBox.cloneNode(true));
                }
              })
            })
            .catch(error => console.error('Error:', error));
      })
      .catch(error => console.error('Error:', error));
}



const isCategoryVisible = ref(false);
const isPriceVisible = ref(false);
const isDateVisible = ref(false);
const isDifficultyVisible = ref(false);
const isProviderVisible = ref(false);
const isDurationVisible = ref(false);
const isCreditVisible = ref(false);


onMounted(() => {
  initiateComponents();
});

function toggleShowPrice() {
  isPriceVisible.value = !isPriceVisible.value;
}

function toggleShowCategory() {
  isCategoryVisible.value = !isCategoryVisible.value;
}

function toggleShowDate() {
  isDateVisible.value = !isDateVisible.value;
}

function toggleShowDifficulty() {
  isDifficultyVisible.value = !isDifficultyVisible.value;
}

function toggleShowProvider() {
  isProviderVisible.value = !isProviderVisible.value;
}

function toggleShowDuration() {
  isDurationVisible.value = !isDurationVisible.value;
}

function toggleShowCredit() {
  isCreditVisible.value = !isCreditVisible.value;
}


function initiateComponents() {
  const rangeInput = document.querySelectorAll(".range-input input"),
      priceInput = document.querySelectorAll(".price-input input"),
      range = document.querySelector(".slider .progress");
  let priceGap = 1000;

  priceInput.forEach(input => {
    input.addEventListener("input", e => {
      let minPrice = parseInt(priceInput[0].value),
          maxPrice = parseInt(priceInput[1].value);

      if ((maxPrice - minPrice >= priceGap) && maxPrice <= rangeInput[1].max) {
        if (e.target.className === "input-min") {
          rangeInput[0].value = minPrice;
          range.style.left = ((minPrice / rangeInput[0].max) * 100) + "%";
        } else {
          rangeInput[1].value = maxPrice;
          range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
        }
      }
    });
  });

  rangeInput.forEach(input => {
    input.addEventListener("input", e => {
      let minVal = parseInt(rangeInput[0].value),
          maxVal = parseInt(rangeInput[1].value);

      if ((maxVal - minVal) < priceGap) {
        if (e.target.className === "range-min") {
          rangeInput[0].value = maxVal - priceGap
        } else {
          rangeInput[1].value = minVal + priceGap;
        }
      } else {
        priceInput[0].value = minVal;
        priceInput[1].value = maxVal;
        range.style.left = ((minVal / rangeInput[0].max) * 100) + "%";
        range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
      }
    });
  });
}

let isCheckboxChecked = ref(false);

function onCheckboxChange() {
  if (isCheckboxChecked.value) {
    // Create container div
    let container = document.createElement('div');
    container.id = 'categoryComponent';

    // Create category text span
    let categoryText = document.createElement('span');
    categoryText.id = 'categoryText';
    categoryText.textContent = 'Category: ';

    // Create label name span
    let labelSpan = document.createElement('span');
    labelSpan.id = 'labelName';
    labelSpan.textContent = labelName;

    // Create remove button
    let removeButton = document.createElement('button');
    removeButton.id = 'removeButton';
    removeButton.textContent = 'x';
    removeButton.onclick = function() {
      container.parentNode.removeChild(container);
    };
  }
}
</script>

<style scoped>
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


.filter-container {
  padding: 0;
  width: 90%;
  display: flex;
  flex-direction: row;
  align-content: center;
  justify-content: center;
  margin: auto;
  flex-wrap: nowrap;
}

.range-container {
  margin-top: 20px;
  max-width: 20%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-content: center;
}


.course-container {
  background-color: white;
  border-radius: 10px;
  padding: 5px;

  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
  overflow-x: visible;
  margin: 30px;
}


@media (max-width: 1400px) {
  .course-container {
    grid-template-columns: repeat(3, 1fr);
  }
}


@media (max-width: 1000px) {
  .course-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

.course-card img {
  width: 100%;
  border-radius: 15px;
}

.course-card h3 {
  font-size: 20px;
  margin: 10px 0;
}

.course-card p {
  color: #606060;
  font-size: 16px;
  margin: 10px 0;
}


::selection {
  color: #fff;
  background: #17A2B8;
}

.wrapper {
  max-width: 300px;
  background: white;
  border-radius: 10px;
  padding: 0 20px;

}

header h2 {
  color: #0C0C0C;
  font-size: 24px;
  font-weight: 600;
}

.price-input {
  width: 100%;
  display: flex;
  margin: 30px 0 35px;
}

.price-input .field {
  display: flex;
  width: 100%;
  height: 45px;
  align-items: center;
}

.field input {
  width: 100%;
  height: 100%;
  outline: none;
  font-size: 19px;
  margin-left: 12px;
  border-radius: 5px;
  text-align: center;
  border: 1px solid #999;
  -moz-appearance: textfield;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
}

.price-input .separator {
  width: 130px;
  display: flex;
  font-size: 19px;
  align-items: center;
  justify-content: center;
}

.slider {
  height: 5px;
  position: relative;
  background: #ddd;
  border-radius: 5px;
}

.slider .progress {
  height: 100%;
  left: 25%;
  right: 25%;
  position: absolute;
  border-radius: 5px;
  background: #584BEB;
}

.range-input {
  position: relative;
}

.range-input input {
  position: absolute;
  width: 100%;
  height: 5px;
  top: -5px;
  background: none;
  pointer-events: none;
  -webkit-appearance: none;
  -moz-appearance: none;
}

input[type="range"]::-webkit-slider-thumb {
  height: 17px;
  width: 17px;
  border-radius: 50%;
  background: #584BEB;
  pointer-events: auto;
  -webkit-appearance: none;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
}

input[type="range"]::-moz-range-thumb {
  height: 17px;
  width: 17px;
  border: none;
  border-radius: 50%;
  background: #584BEB;
  pointer-events: auto;
  -moz-appearance: none;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
}

.price-ranger {
  max-width: 300px;
  background-color: white;
  padding: 5px 10px;
  margin: 10px 0 0 0;
  border-radius: 10px;
  border: none;
  color: black;
  font-size: 18px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.3s;

  &:hover {
    color: #727272;
  }
}

#priceText,
#categoryContainer,
#dateContainer,
#difficultyContainer,
#providerContainer,
#durationContainer,
#creditContainer {
  transition: height 0.2s ease-out, opacity 0.2s ease-out;
}

.category-list {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
}

.checkbox-wrapper {
  display: flex;
  flex-direction: row;
  align-content: flex-start;
  justify-content: space-between;
  justify-items: center;
  gap: 5px;
}

/*Todo: Make the categories dymanic based on the amount of categories available */
.inp-cbx {
  font-family: 'Inter', sans-serif;
  font-size: 20px;
  min-height: 20px;
  min-width: 20px;
  background-color: #eee;;
}

.active-filter-container {
  padding: 0;
  width: 90%;
  display: flex;
  flex-direction: row;
  align-content: center;
  justify-content: center;
  margin: auto;
  flex-wrap: nowrap;
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
  color: var(--dark-1);
  background: none;
  width: 100%;
  text-decoration: none;
  padding: 20px 20px 20px 100px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
}
</style>