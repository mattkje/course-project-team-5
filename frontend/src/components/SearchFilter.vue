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
            <input class="inp-cbx" id="itBox" type="checkbox" v-model="isItChecked" @change="sortByCategory">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="dmBox">Digital Marketing</label>
            <input class="inp-cbx" id="dmBox" type="checkbox" v-model="isDmChecked" @change="sortByCategory">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="beBox">Business and Entrepreneurship</label>
            <input class="inp-cbx" id="beBox" type="checkbox" v-model="isBeChecked" @change="sortByCategory">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="dsBox">Data Science and Analytics</label>
            <input class="inp-cbx" id="dsBox" type="checkbox" v-model="isDsChecked" @change="sortByCategory">
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
          <span>Max Price</span>
          <div class="field">
            <input type="number" class="input-max" value="50000" v-model="maxValue" @input="maxRangeValue = maxValue" @change="sortByPriceRange">
          </div>
        </div>
        <div class="range-input">
          <input type="range" class="range-max" min="0" max="50000" v-model="maxRangeValue" step="100"
                 @input="maxValue = maxRangeValue" @change="sortByPriceRange">
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
          <div id="providerList"></div>
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
          <div class="checkbox-wrapper">
            <label class="cbx" for="beginnerBox">Beginner</label>
            <input class="inp-cbx" id="beginnerBox" type="checkbox" v-model="isBeginnerChecked"
                   @change="sortByDifficulty">
          </div>

          <div class="checkbox-wrapper">
            <label class="cbx" for="intermediateBox">Intermediate</label>
            <input class="inp-cbx" id="intermediateBox" type="checkbox" v-model="isIntermediateChecked"
                   @change="sortByDifficulty">
          </div>

          <div class="checkbox-wrapper">
            <label class="cbx" for="expertBox">Expert</label>
            <input class="inp-cbx" id="expertBox" type="checkbox" v-model="isExpertChecked"
                   @change="sortByDifficulty">
          </div>
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
          <div class="slider-container">
            <input type="range" min="0" max="5" class="slider" id="creditSlider" v-model="creditValue"
                   @change="sortByCredit">
            <div style="display: flex; justify-content: space-between;">
              <p>Minimum Credit</p>
              <p>{{ creditValue }}</p>
            </div>
          </div>
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
import {createContentBox, fetchCourses, fetchCurrencies} from "@/js/populationTools";
import {sendApiRequest} from "@/js/requests";

const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const searchQuery = ref('');
const isItChecked = ref(false);
const isDmChecked = ref(false);
const isBeChecked = ref(false);
const isDsChecked = ref(false);
const creditValue = ref(0);
const isBeginnerChecked = ref(false);
const isIntermediateChecked = ref(false);
const isExpertChecked = ref(false);

const isCategoryVisible = ref(false);
const isPriceVisible = ref(false);
const isDateVisible = ref(false);
const isDifficultyVisible = ref(false);
const isProviderVisible = ref(false);
const isDurationVisible = ref(false);
const isCreditVisible = ref(false);

const minValue = ref(0);
const maxValue = ref(50000);
const minRangeValue = ref(0);
const maxRangeValue = ref(50000);

let courseContainer;
let children;
let childrenIdList = [];
let filterMap = new Map();
let activeFilters = new Map();
let Matching = [];
let searchedChildren = new Map();

onMounted(() => {
  courseContainer = document.querySelector('#courseContainer');
  children = courseContainer.children;
  populateCourses('.flexible-grid');
  populateProviders();
  currency(API_URL);

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
      moduleRanges: false,
    });
    picker.on('hide', function (startDate, endDate) {
      console.log(startDate, endDate)
      sortByDate(startDate, endDate);
    });
  });

});


// Populate the courses in the given selector
async function populateCourses(selector) {
  document.querySelector(selector).innerHTML = '';
  const defaultCurrency = setDefaultCurrency() || 'USD';
  try {
    const [data, currencies] = await Promise.all([fetchCourses(API_URL), fetchCurrencies(API_URL)]);

    data.forEach(courseProvider => {

      if (courseProvider.course.title.toLowerCase().includes(searchQuery.value.toLowerCase()) || courseProvider.course.category.toLowerCase().includes(searchQuery.value.toLowerCase())) {
        const contentBox = createContentBox(courseProvider, currencies, defaultCurrency);
        document.querySelector(selector).appendChild(contentBox.cloneNode(true));
      }
    });
  } catch (error) {
    console.error('Error:', error);
  }
}

let isProviderChecked = ref({}); // Initialize as an empty object

function populateProviders() {
  fetch(API_URL + '/providers')
      .then(response => response.json())
      .then(data => {
        const providerList = document.getElementById('providerList');
        data.forEach(provider => {
          const checkboxWrapper = document.createElement('div');
          checkboxWrapper.className = 'checkbox-wrapper';

          const label = document.createElement('label');
          label.className = 'cbx';
          label.setAttribute('for', `provider${provider.providerId}`);
          label.textContent = provider.name;
          checkboxWrapper.appendChild(label);

          const checkbox = document.createElement('input');
          checkbox.className = 'inp-cbx';
          checkbox.id = `provider${provider.providerId}`;
          checkbox.type = 'checkbox';
          checkbox.setAttribute('v-model', `isProviderChecked['${provider.name}']`);
          checkbox.addEventListener('change', onProviderCheckboxChange);
          checkboxWrapper.appendChild(checkbox);

          providerList.appendChild(checkboxWrapper);

          // Initialize the checkbox state for this provider
          isProviderChecked.value[provider.name] = false;
        });
      })
      .catch(error => console.error('Error:', error));
}

function createChildrenIdList() {
  for (let i = 0; i < children.length; i++) {
    childrenIdList.push(i + 1);
  }
}

function getCheckboxId(event) {
  let checkboxId = event.target.id;
  let label = document.querySelector(`label[for="${checkboxId}"]`);
  let labelName = label.textContent;
  return {labelName, checkboxId};
}

function handleSliderFilter(checkboxId, id) {

  let key = checkboxId.split(" ")[0];

  console.log("key " + key)
  if (filterMap.has(id)) {
    console.log(id + " exists in filterMap")
    let existingFilter = filterMap.get(id);
    console.log("existingFilter " + existingFilter)
    console.log("existingFilter.get(key) " + existingFilter.get(key))
    if (existingFilter.get(key).includes(checkboxId)) {
      console.log("should delete " + checkboxId + " from " + id)
      existingFilter.delete(key);
    }
  }
}

function addFilterToCourse(id, checkboxId, isSlider) {
  let key = checkboxId.split(" ")[0];

  if (filterMap.has(id)) {
    let existingFilter = filterMap.get(id);
    if (existingFilter.has(key)) {
      if (existingFilter.get(key).includes(checkboxId) && existingFilter.get(key).length === 1) {
        if (!isSlider) {
          existingFilter.delete(key);
        }
      } else if (existingFilter.get(key).includes(checkboxId) && existingFilter.get(key).length > 1) {
        let filterArray = existingFilter.get(key);
        filterArray.splice(filterArray.indexOf(checkboxId), 1);
        existingFilter.set(key, filterArray); // Set the updated array back to the Map
      } else {
        existingFilter.get(key).push(checkboxId);
      }
    } else {
      existingFilter.set(key, [checkboxId]);
    }
  } else {
    let newFilter = new Map();
    newFilter.set(key, [checkboxId]);
    filterMap.set(id, newFilter);
  }
}


function updateActiveFilters(checkboxId, isSlider) {
  let key = checkboxId.split(" ")[0];

  if (!activeFilters.has(key)) {
    activeFilters.set(key, [checkboxId]);
  } else {
    let existingValues = activeFilters.get(key);
    if (existingValues.includes(checkboxId)) {
      if (existingValues.length === 1) {
        if (!isSlider) {
          activeFilters.delete(key);
        } else {
          activeFilters.set(key, [checkboxId]);
        }
      } else {
        existingValues.splice(existingValues.indexOf(checkboxId), 1);
      }
    } else {
      existingValues.push(checkboxId);
    }
  }

}

function filterCourse(Matching, checkboxId, isSlider) {

  for (let id of childrenIdList) {
    if (Matching.includes(id)) {
      console.log("addfilter")
      addFilterToCourse(id, checkboxId, isSlider);
    } else if (!Matching.includes(id) && isSlider) {
      console.log("handleSliderFilter")
      handleSliderFilter(checkboxId, id);
    }
  }

  updateActiveFilters(checkboxId, isSlider);
}

function updateView() {

  if (activeFilters.size === 0) {
    for (let child of children) {
      child.style.display = 'block';
    }
  } else {
    for (let child of children) {
      child.style.display = 'none';
    }
    for (let key of filterMap.keys()) {
      let existingFilter = filterMap.get(key);
      for (let filter of existingFilter.keys()) {
        let filterList = existingFilter.get(filter);
        if (filterList.length > 0 && existingFilter.size === activeFilters.size) {
          children[key - 1].style.display = 'block';
        }
      }
    }
  }
  for (let child of children) {
    if (searchedChildren.has(child)) {
      child.style.display = 'none';
    }
  }
}

function isMatch(courses, checkboxId, isSlider) {
  if (childrenIdList.length === 0) {
    createChildrenIdList();
  }

  let Matching = [];


  for (let course of courses) {
    if (childrenIdList.includes(course.courseId)) {
      Matching.push(course.courseId);
    }
  }
  filterCourse(Matching, checkboxId, isSlider);
  updateView();

}

async function sortByCategory(event) {
  let nameId = getCheckboxId(event);
  let category = nameId.labelName
  let checkboxId = "category " + nameId.labelName;
  await sendApiRequest("GET", '/courses/category/' + category, (data) => isMatch(data, checkboxId, false), onFailure);
}

async function onProviderCheckboxChange(event) {
  let nameId = getCheckboxId(event);
  let provider = nameId.labelName;
  let checkboxId = "provider " + nameId.checkboxId.split("provider")[1];
  await sendApiRequest("GET", '/courses/provider/' + checkboxId.split(" ")[1], (data) => isMatch(data, checkboxId, false), onFailure);
}

async function sortByDifficulty(event) {
  let nameId = getCheckboxId(event);
  let difficulty = nameId.labelName;
  let checkboxId = "difficulty " + nameId.checkboxId;
  await sendApiRequest("GET", '/courses/level/' + difficulty, (data) => isMatch(data, checkboxId, false), onFailure);
}

function getSliderValues(event) {
  let checkboxId = event.target.id;
  let sliderValue = event.target.value;
  return {sliderValue, checkboxId};
}

async function sortByCredit(event) {
  let nameId = getSliderValues(event);
  let credit = nameId.sliderValue;
  let checkboxId = "credit " + "credit";
  await sendApiRequest("GET", '/courses/course_size/' + credit, (data) => isMatch(data, checkboxId, true), onFailure);
}

async function sortByPriceRange(event) {
  let nameId = getSliderValues(event);
  let checkboxId = "priceRange " + "priceRange";
  let priceRangeFilter = [];

  for (let child of children) {
    let childId = child.querySelector('.course-id').textContent;
    let childPriceElement = child.querySelector('.finalPriceBox').textContent;
    let price = parseFloat(childPriceElement.split(' ')[0]);
    let priceMatch = price <= nameId.sliderValue;

    if (priceMatch) {
      priceRangeFilter.push(childId);
    }
  }
  console.log("priceRangeFilter: ", priceRangeFilter.length)
  priceRangeFilter = priceRangeFilter.join(',');

  await sendApiRequest("GET", '/courses/ids?ids=' + priceRangeFilter, (data) => isMatch(data, checkboxId, true), onFailure);
}


function searchCourses() {
  console.log("test")
  for (let child of children) {
    let childTitle = child.querySelector('.content-box-title').textContent.toLowerCase();
    if (!childTitle.includes(searchQuery.value.toLowerCase())) {
      searchedChildren.set(child, 1);
      console.log("test 2")
    } else if (searchedChildren.has(child)) {
      console.log("test 3")
      searchedChildren.delete(child);
    }
  }
  updateView();
}

function onFailure() {
  console.log("Failed to fetch courses");
}


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

function toggleShowCredit() {
  isCreditVisible.value = !isCreditVisible.value;
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

#creditSlider {
  -webkit-appearance: none;
  -moz-appearance: none;
  width: 100%;
  height: 10px;
  border-radius: 5px;
  background: #dde5ff;
  outline: none;
  opacity: 0.7;
  transition: opacity .2s;
  -webkit-transition: .2s;
}


</style>
