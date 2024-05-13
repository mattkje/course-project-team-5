<template>
  <div class="search-container">

    <div class="search-bar">
      <label for="myTextBox"></label>
      <input class="search-prompt" type="text" id="myTextBox" name="myTextBox"
             placeholder="Search for courses to boost your skills" v-model="searchQuery" @input="searchCourses">
      <img class="search-icon" src="/search.png" alt="Connect">
    </div>
  </div>

  <div class="active-filter-container" id="active-filter-container">
    <button id="mobile-filter">Filter</button>
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
            <label class="cbx" for="itBox">Information Technology</label>
            <input class="inp-cbx" id="itBox" type="checkbox" v-model="isItChecked" @change="onCheckboxChange">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="dmBox">Digital Marketing</label>
            <input class="inp-cbx" id="dmBox" type="checkbox" v-model="isDmChecked" @change="onCheckboxChange">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="beBox">Business and Entrepreneurship</label>
            <input class="inp-cbx" id="beBox" type="checkbox" v-model="isBeChecked" @change="onCheckboxChange">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="dsBox">Data Science and Analytics</label>
            <input class="inp-cbx" id="dsBox" type="checkbox" v-model="isDsChecked" @change="onCheckboxChange">
          </div>
        </div>
      </div>


      <button class="price-ranger" @click="toggleShowPrice" :style="{
          'border-radius': isPriceVisible ? '10px 10px 0 0' : '10px'
        }">Price
      </button>
      <div class="wrapper" id="priceText" :style="{
          height: isPriceVisible ? '150px' : '0px',
          opacity: isPriceVisible ? '1' : '0',
          zIndex: isPriceVisible ? '0' : -100,
          'border-radius': isPriceVisible ? '0 0 10px 10px' : '10px'
        }">
        <div class="price-input">
          <div class="field">
            <span>Min</span>
            <input type="number" class="input-min" value="0" v-model="minValue">
          </div>
          <div class="separator">-</div>
          <div class="field">
            <span>Max</span>
            <input type="number" class="input-max" value="10000" v-model="maxValue">
          </div>
        </div>
        <div class="slider">
          <div class="progress"></div>
        </div>
        <div class="range-input">
          <input type="range" class="range-min" min="0" max="10000" v-model="minRangeValue" step="100">
          <input type="range" class="range-max" min="0" max="10000" v-model="maxRangeValue" step="100">
        </div>
      </div>

      <button class="price-ranger" @click="toggleShowDate" :style="{
          'border-radius': isDateVisible ? '10px 10px 0 0' : '10px'
        }">Date
      </button>

      <div class="wrapper" id="dateContainer" :style="{
          height: isDateVisible ? '50px' : '0px',
          opacity: isDateVisible ? '1' : '0',
          zIndex: isDateVisible ? '0' : -100,
          'border-radius': isDateVisible ? '0 0 10px 10px' : '10px'
        }">
        <span>Start date - End Date</span>
        <input id="datepicker" type="text" placeholder="Now - Forever"/>
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
          height: isDifficultyVisible ? '150px' : '0px',
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
    <div class="flexible-grid-container">
      <div class="flexible-grid" id="courseContainer">
      </div>
    </div>
  </div>
</template>


<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";
import {currency, setDefaultCurrency} from "@/js/currency";
import Litepicker from 'litepicker';
import {watch} from 'vue';

const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const searchQuery = ref('');
const isItChecked = ref(false);
const isDmChecked = ref(false);
const isBeChecked = ref(false);
const isDsChecked = ref(false);

const minValue = ref(0);
const maxValue = ref(10000);
const minRangeValue = ref(0);
const maxRangeValue = ref(10000);

let courseContainer;
let children;
let historicalChildren = new Map();
let selectedCategories = [];

onMounted(() => {
  courseContainer = document.querySelector('#courseContainer');
  children = courseContainer.children;
  populateCourses('.flexible-grid');
  currency(API_URL);
});


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
                if (courseProvider.course.title.toLowerCase().includes(searchQuery.value.toLowerCase()) || courseProvider.course.category.toLowerCase().includes(searchQuery.value.toLowerCase())
                ) {
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

function searchCourses() {
  for (let i = 0; i < children.length; i++) {
    let child = children[i];
    let childTitle = child.querySelector('.content-box-title').textContent.toLowerCase();

    if (!childTitle.includes(searchQuery.value.toLowerCase())) {
        let count = historicalChildren.get(child);
        historicalChildren.set(child, count + 1);
        console.log("count" + count)
    } else {
      if (historicalChildren.has(child) && historicalChildren.get(child) > 0) {
        let count = historicalChildren.get(child);
        historicalChildren.set(child, count - 1);
        if (count - 1 < 1) {
          historicalChildren.delete(child);
        }
      }
    }

    console.log("real children: " + children.length)
    console.log("dead children: " + historicalChildren.size)


  }

  filterStatus()
}

function filterStatus() {
  for (let i = 0; i < children.length; i++) {
    if (historicalChildren.has(children[i])) {
      children[i].style.display = 'none';
    } else {
      children[i].style.display = 'block';
    }
  }
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


function onCheckboxChange(event) {
  const checkboxId = event.target.id;
  const isChecked = event.target.checked;

  if (isChecked) {
    localStorage.setItem(checkboxId, 'false');

    // Select the label element inside .checkbox-wrapper
    let label = document.querySelector(`label[for="${checkboxId}"]`);

    // Get the text content of the label
    let labelName = label.textContent;

    // Create container div
    let container = document.createElement('div');
    container.id = checkboxId + "container";

    // Create label name span
    let labelSpan = document.createElement('span');
    labelSpan.id = 'labelName';
    labelSpan.textContent = labelName;

    // Create remove button
    let removeButton = document.createElement('button');
    removeButton.id = 'removeButton';
    removeButton.textContent = 'x';
    removeButton.onclick = function () {
      container.parentNode.removeChild(container);
      document.getElementById(checkboxId).checked = false;
    };

    container.appendChild(labelSpan);
    container.appendChild(removeButton);

    let activeFilterContainer = document.getElementById('active-filter-container');
    activeFilterContainer.appendChild(container);

    categorizeCourses(labelName);

  } else {
    localStorage.setItem(checkboxId, 'true');
    let activeFilterContainer = document.getElementById('active-filter-container');
    activeFilterContainer.removeChild(document.getElementById(checkboxId + "container"));

    let label = document.querySelector(`label[for="${checkboxId}"]`);

    categorizeCourses(label.textContent);
  }

}

function categorizeCourses(category) {
  for (let child of children) {
    let childCategory = child.querySelector('.content-box-text').textContent;
    let categoryMatch = childCategory === category;
    let childInHistory = historicalChildren.has(child);

    if (selectedCategories.length === 0 && !categoryMatch) {
      updateHistoricalChildren(child, true);
    } else if (selectedCategories.length > 1 && selectedCategories.includes(category) && categoryMatch) {
      updateHistoricalChildren(child, true );
    } else if (selectedCategories.length > 0 && !selectedCategories.includes(category) && categoryMatch && childInHistory) {
      updateHistoricalChildren(child, false );
    } else if (selectedCategories.length === 1 && selectedCategories.includes(category) && categoryMatch) {
      updateHistoricalChildren(child, false);
      console.log("check 1")
    }
  }

  // End of process, update selected categories
  if (selectedCategories.includes(category)) {
    const index = selectedCategories.indexOf(category);
    if (index > -1) {
      selectedCategories.splice(index, 1);
    }
  } else {
    selectedCategories.push(category);
  }

  filterStatus();
}

function updateHistoricalChildren(child, add) {
  if (add) {
    if (historicalChildren.has(child)) {
      let count = historicalChildren.get(child);
      historicalChildren.set(child, count + 1);
    } else {
      historicalChildren.set(child, 1);
    }
  } else {
    if (historicalChildren.has(child)) {
      let count = historicalChildren.get(child);
      historicalChildren.set(child, count - 1);
      console.log("count" + count)
      if (count - 1 < 1) {
        historicalChildren.delete(child);
      }
    } else {
      historicalChildren.forEach((count, child) => {
        count -= 1;
        if (count < 1) {
          historicalChildren.delete(child);
        } else {
          historicalChildren.set(child, count);
        }
      });
    }
  }
}


let picker;
document.addEventListener('DOMContentLoaded', function () {
  picker = new Litepicker({
    element: document.getElementById('datepicker'),
    format: "DD MMM",
    minDate: new Date() - 1,
    useResetBtn: false,
    buttonText: {
      apply: 'Done',
      reset: 'Reset'
    },
    TooltipText: {
      one: 'day',
      other: 'days'
    },
    mobileFriendly: true,
    numberOfMonths: 2,
    numberOfColumns: 2,
    singleMode: false,
    moduleNavKeyboard: true,
    moduleRanges: false
  });
});

function filterPrices(value) {
  for (let i = 0; i < children.length; i++) {
    let child = children[i];
    let childPrice = child.querySelector('.price').textContent;
    let price = parseFloat(childPrice.split(' ')[0]);

    console.log(price)
    console.log(value)
    if (price > value) {
      child.style.display = 'none';
    } else {
      child.style.display = 'block';
    }
  }
}

watch(minValue, (newVal) => {
  filterPrices(newVal)
});

watch(maxValue, (newVal) => {
  filterPrices(newVal)
});
watch(minRangeValue, (newVal) => {
  filterPrices(newVal)
});
watch(maxRangeValue, (newVal) => {
  filterPrices(newVal)
});


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

.flexible-grid-container {
  background-color: var(--light-1);
  border-radius: 10px;
  display: flex;
  justify-content: flex-start;
  margin: 30px;
  padding: 5px;
  min-height: 90%;
  min-width: 90%;
}

.flexible-grid {
  background-color: var(--light-1);
  border-radius: 10px;
  padding: 5px;
  display: flex;
  flex-wrap: wrap;
  margin: 30px;
  justify-content: space-around;
}

.flexible-grid > * {
  flex: 1;
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
  background: var(--light-1);
  border-radius: 10px;
  padding: 0 20px;
}

#mobile-filter {
  display: none;
}

@media (max-width: 600px) {
  .filter-container {
    flex-direction: column;
  }

  .range-container {
    display: none;
  }

  #mobile-filter {
    display: flex;
  }
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
  justify-content: center;
}

.price-input .field {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  height: 45px;
  align-items: center;
  justify-content: center;
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
  align-self: flex-end;
}

.slider {
  height: 5px;
  position: relative;
  background: #ddd;
  border-radius: 5px;
}

.slider .progress {
  height: 100%;
  left: 0%;
  right: 0%;
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
  background-color: var(--light-1);
  padding: 5px 10px;
  margin: 10px 0 0 0;
  border-radius: 10px;
  border: none;
  color: var(--dark-3);
  font-family: Inter, sans-serif;
  font-size: 18px;
  font-weight: bold;
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

  width: 90%;
  display: flex;
  flex-direction: row;
  align-content: center;
  justify-content: left;
  flex-wrap: nowrap;
  margin: auto auto 30px auto;

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
  background-color: var(--light-1);
  border-radius: 20px;
  position: relative; /* Make it a positioning context for the absolute position of the button */
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.05);
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

#dateContainer {
  display: flex;
  flex-direction: column;
  justify-content: left;
  align-content: center;
}


</style>
