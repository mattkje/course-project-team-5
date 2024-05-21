<template>
  <div class="search-container">

    <div class="search-bar">
      <input class="search-prompt" type="text" id="myTextBox" name="myTextBox"
             placeholder="Search for courses to boost your skills" v-model="searchQuery" @input="searchCourses">
      <img class="search-icon" src="/search.png" alt="Connect">
    </div>
  </div>
  <div class="filter-popup" v-show="showFilters">
    <div class="dark-overlay"></div>
    <div class="mobile-filter">
      <div class="styledHeader">
        <h3 id="filters">Filters</h3>

      </div>
      <div class="separator"></div>
      <div class="range-container" id="range-vertical-container">

        <button class="price-ranger" @click="toggleShowCategory" :style="{
          'border-radius': isCategoryVisible ? '10px 10px 0 0' : '10px'
        }">Category
        </button>
        <div class="wrapper" id="categoryContainer" :style="{
          height: isCategoryVisible ? '0px' : '120px',
          opacity: isCategoryVisible ? '0' : '1',
          zIndex: isCategoryVisible ? -100 : '0',
          'border-radius': isCategoryVisible ? '0 0 10px 10px' : '10px'
        }">
          <div class="category-list" id="categoryList2">
          </div>
        </div>

        <div class="separator"></div>

        <button class="price-ranger" @click="toggleShowPrice" :style="{
          'border-radius': isPriceVisible ? '10px 10px 0 0' : '10px'
        }">Price
        </button>
        <div class="wrapper" id="priceText" :style="{
          height: isPriceVisible ? '0px' : '100px',
          opacity: isPriceVisible ? '0' : '1',
          zIndex: isPriceVisible ? -100 :  '0',
          'border-radius': isPriceVisible ? '0 0 10px 10px' : '10px'
        }">
          <div class="price-input-container">
          </div>
        </div>

        <button class="price-ranger" @click="toggleShowDate" :style="{
          'border-radius': isDateVisible ? '10px 10px 0 0' : '10px'
        }">Date
        </button>

        <div class="wrapper" id="dateContainer" :style="{
          height: isDateVisible ? '0px' : '50px',
          opacity: isDateVisible ? '0' : '1',
          zIndex: isDateVisible ? -100 : '0',
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
          height: isProviderVisible ? '0px' : '400px',
          opacity: isProviderVisible ? '0' : '1',
          zIndex: isProviderVisible ?  -100 : '0',
          'border-radius': isProviderVisible ? '0 0 10px 10px' : '10px'
        }">
          <header>
            <div id="providerList1"></div>
          </header>
        </div>

        <button class="price-ranger" @click="toggleShowDifficulty" :style="{
          'border-radius': isDifficultyVisible ? '10px 10px 0 0' : '10px'
        }">Difficulty
        </button>
        <div class="wrapper" id="difficultyContainer" :style="{
          height: isDifficultyVisible ? '0px' : '150px',
          opacity: isDifficultyVisible ? '0' : '1',
          zIndex: isDifficultyVisible ? -100 : '0',
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
          height: isCreditVisible ? '0px' : '200px',
          opacity: isCreditVisible ? '0' : '1',
          zIndex: isCreditVisible ? -100 : '0',
          'border-radius': isCreditVisible ? '0 0 10px 10px' : '10px'
        }">
          <header>
            <div class="slider-container">
              <input type="range" min="0" max="5" class="slider" id="creditSlider" v-model="creditValue"
                     @change="sortByCredit">
              <div style="display: flex; justify-content: space-between;">
                <p>Minimum Credit:</p>
                <p>{{ creditValue }}</p>
              </div>
            </div>
          </header>
        </div>

      </div>
    </div>
  </div>
  <div class="filter-wrapper">
    <div class="active-filter-container">
      <button @click="toggleFilters">
        <img src="https://www.svgrepo.com/show/472431/bars-filter.svg" alt="Filter Icon">
        <p>Filters</p>
      </button>
    </div>

    <div class="filter-container">
      <div class="range-container">

        <button class="price-ranger" @click="toggleShowCategory" :style="{
          'border-radius': isCategoryVisible ? '10px 10px 0 0' : '10px'
        }">Category
        </button>
        <div class="wrapper" id="categoryContainer" :style="{
          height: isCategoryVisible ? '150px' : '0px',
          opacity: isCategoryVisible ? '1' : '0',
          zIndex: isCategoryVisible ? '0' : -100,
          'border-radius': isCategoryVisible ? '0 0 10px 10px' : '10px'
        }">
          <div class="category-list" id="categoryList1">

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
          <div class="price-input-container">
            <div class="price-input">
              <label for="min-price">Min Price:</label>
              <p>-</p>
              <input type="number" id="min-price" v-model="minPrice" min="0" placeholder="Minimum Price" @change="sortByPriceRange">
            </div>
            <div class="price-input">
              <label for="max-price">Max Price:</label>
              <p>-</p>
              <input type="number" id="max-price" v-model="maxPrice" min="1" placeholder="Maximum Price" @change="sortByPriceRange">
            </div>
          </div>
        </div>

        <button class="price-ranger" @click="toggleShowDate" :style="{
          'border-radius': isDateVisible ? '10px 10px 0 0' : '10px'
        }">Date
        </button>

        <div class="wrapper" id="dateContainer" :style="{
          height: isDateVisible ? '80px' : '0px',
          opacity: isDateVisible ? '1' : '0',
          zIndex: isDateVisible ? '0' : -100,
          'border-radius': isDateVisible ? '0 0 10px 10px' : '10px',
          textAlign: 'center'
        }">

          <span>Start date - End Date</span>
          <flat-pickr v-model="date" :config="config" class="form-control" placeholder="Select dates" name="date" @change="getDate" ref="flatpickr"></flat-pickr>
        </div>

        <button class="price-ranger" @click="toggleShowProvider" :style="{
          'border-radius': isProviderVisible ? '10px 10px 0 0' : '10px'
        }">Provider
        </button>
        <div class="wrapper" id="providerContainer" :style="{
          height: isProviderVisible ? '320px' : '0px',
          opacity: isProviderVisible ? '1' : '0',
          zIndex: isProviderVisible ? '0' : -100,
          'border-radius': isProviderVisible ? '0 0 10px 10px' : '10px'
        }">
          <header>
            <div id="providerList2"></div>
          </header>
        </div>

        <button class="price-ranger" @click="toggleShowDifficulty" :style="{
          'border-radius': isDifficultyVisible ? '10px 10px 0 0' : '10px'
        }">Difficulty
        </button>
        <div class="wrapper" id="difficultyContainer" :style="{
          height: isDifficultyVisible ? '80px' : '0px',
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
          height: isCreditVisible ? '70px' : '0px',
          opacity: isCreditVisible ? '1' : '0',
          zIndex: isCreditVisible ? '0' : -100,
          'border-radius': isCreditVisible ? '0 0 10px 10px' : '10px'
        }">
          <header>
            <div class="slider-container">
              <input type="range" min="0" max="5" class="slider" id="creditSlider" v-model="creditValue"
                     @change="sortByCredit">
              <div style="display: flex; justify-content: space-between;">
                <p>Minimum Credit:</p>
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
  </div>
</template>


<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";
import {currency, setDefaultCurrency} from "@/js/currency";
import {createContentBox, fetchCourses, fetchCurrencies, fetchProviders} from "@/js/populationTools";
import {sendApiRequest} from "@/js/requests";
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';


const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const searchQuery = ref('');
const creditValue = ref(0);
const isBeginnerChecked = ref(false);
const isIntermediateChecked = ref(false);
const isExpertChecked = ref(false);

const isCategoryVisible = ref(false);
const isPriceVisible = ref(false);
const isDateVisible = ref(false);
const isDifficultyVisible = ref(false);
const isProviderVisible = ref(false);
const isCreditVisible = ref(false);

const minPrice = ref(null);
const maxPrice = ref(null);

const coursesData = ref(null);
const currenciesData = ref(null);
const providerData = ref(null);

let isCategoryChecked = ref({});
let isProviderChecked = ref({});
let Matching = [];

let courseContainer;
let children;
let childrenIdList = [];
let filterMap = new Map();
let activeFilters = new Map();
let searchedChildren = new Map();
let pricedChildren = new Map();

let showFilters = ref(false);

const date = ref(null);

const config = ref({
  mode: 'range',
  dateFormat: 'J of F',
  altInput: true,
  altFormat: 'Y-m-d',
  minDate: 'today',
});


const flatpickr = ref(null);

 const getDate = () => {
  const flatpickerInstance = flatpickr.value.fp;
  const AltInputValue = flatpickerInstance.altInput.value.split(' to ');
  const [startDate, endDate] = AltInputValue;

  sortByDate(startDate, endDate);
};

onMounted(() => {
  courseContainer = document.querySelector('#courseContainer');
  children = courseContainer.children;
  populateCourses('.flexible-grid');
  currency(API_URL);
});

// Populate the courses in the given selector
async function populateCourses(selector) {
  document.querySelector(selector).innerHTML = '';
  const defaultCurrency = setDefaultCurrency() || 'USD';
  try {
    const [data, currencies, providers] = await Promise.all([fetchCourses(API_URL), fetchCurrencies(API_URL), fetchProviders(API_URL)]);

    coursesData.value = data;
    currenciesData.value = currencies;
    providerData.value = providers;

    data.forEach(courseProvider => {
      if (courseProvider.course.title.toLowerCase().includes(searchQuery.value.toLowerCase()) || courseProvider.course.category.toLowerCase().includes(searchQuery.value.toLowerCase())) {
        const contentBox = createContentBox(courseProvider, currencies, defaultCurrency);
        document.querySelector(selector).appendChild(contentBox.cloneNode(true));
      }
    });
    populateCheckboxes(['#categoryList1', '#categoryList2'],coursesData,'course.category',isCategoryChecked,sortByCategory);
    populateCheckboxes(['#providerList1', '#providerList2'],providerData,'name',isProviderChecked,onProviderCheckboxChange);
  } catch (error) {
    console.error('Error:', error);
  }
}

function populateCheckboxes(selectors, data,dataType,checkedRef, changeHandler) {
  const uniqueItems = new Set();

  selectors.forEach(selector => {
    const list = document.querySelector(selector);

    console.log(dataType)
    data.value.forEach(item => {
      uniqueItems.add(getNestedProperty(item, dataType));
    });

    uniqueItems.forEach(itemName => {
      const checkboxWrapper = document.createElement('div');
      checkboxWrapper.className = 'checkbox-wrapper';

      const label = document.createElement('label');
      label.className = 'cbx';
      label.setAttribute('for', itemName);
      label.textContent = itemName;
      checkboxWrapper.appendChild(label);

      const checkbox = document.createElement('input');
      checkbox.className = 'inp-cbx';
      checkbox.id = itemName;
      checkbox.type = 'checkbox';
      checkbox.setAttribute('v-model', `${checkedRef}['${itemName}']`);
      checkbox.addEventListener('change', changeHandler);
      checkboxWrapper.appendChild(checkbox);

      list.appendChild(checkboxWrapper);

      // Initialize the checkbox state for this item
      checkedRef.value[itemName] = false;
    });
  });
}

function getNestedProperty(obj, path) {
  return path.split('.').reduce((obj, prop) => obj[prop], obj);
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

function addFilterToCourse(id, checkboxId) {
  let key = checkboxId.split(" ")[0];

  if (filterMap.has(id)) {
    let existingFilter = filterMap.get(id);
    if (existingFilter.has(key)) {
      if (existingFilter.get(key).includes(checkboxId) && existingFilter.get(key).length === 1) {
          existingFilter.delete(key);
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


function updateActiveFilters(checkboxId,isSlider) {
  let key = checkboxId.split(" ")[0];

  if (!activeFilters.has(key)) {
    activeFilters.set(key, [checkboxId]);
  } else {
    let existingValues = activeFilters.get(key);
    if (existingValues.includes(checkboxId)) {
      if (existingValues.length === 1) {
        if(!isSlider) {
          activeFilters.delete(key);
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
      addFilterToCourse(id, checkboxId);
    }
  }

  updateActiveFilters(checkboxId,isSlider);
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
    if (pricedChildren.has(child)) {
      child.style.display = 'none';
    }
  }
}

function isMatch(courses, checkboxId, isSlider) {
  if (childrenIdList.length === 0) {
    createChildrenIdList();
  }

 checkActiveFilters(checkboxId)
  Matching = [];

  for (let course of courses) {
    if (childrenIdList.includes(course.courseId)) {
      Matching.push(course.courseId);
    }
  }
  filterCourse(Matching, checkboxId, isSlider);
  updateView();
  console.log(activeFilters)
}

function checkActiveFilters(checkboxId) {
  if (activeFilters.has("credit") && checkboxId.includes("credit") || activeFilters.has("date") && checkboxId.includes("date")) {
    console.log(activeFilters.size)
    for (let key of filterMap.keys()) {
      let existingFilter = filterMap.get(key);
      if (existingFilter.has("credit")) {
        console.log("check")
      }
      if (existingFilter.has("date")) {
        existingFilter.delete("date");
      }
    }
  }
}

async function sortByCategory(event) {
  let nameId = getCheckboxId(event);
  let category = nameId.labelName
  let checkboxId = "category " + nameId.labelName;
  onCheckboxChange(checkboxId, category);
  await sendApiRequest("GET", '/courses/category/' + category, (data) => isMatch(data, checkboxId, false), onFailure);
}

async function sortByDate(startDate, endDate) {
  let checkboxId = "date " + startDate + " to " + endDate;
  onCheckboxChange(checkboxId, startDate + " to " + endDate);
  await sendApiRequest("GET", '/courses/date-range?startDate=' + startDate + '&endDate=' + endDate, (data) =>
    isMatch(data, checkboxId, true), onFailure);
}

async function onProviderCheckboxChange(event) {
  let nameId = getCheckboxId(event);
  let provider = nameId.labelName;
  let checkboxId = "provider " + nameId.checkboxId;
  console.log(provider)
  console.log(nameId.checkboxId)
  await sendApiRequest("GET", '/courses/provider/' + provider, (data) => isMatch(data, checkboxId, false), onFailure);
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

function onCheckboxChange(checkboxid, type) {
  // Create a new div element
  const newElement = document.createElement('div');

  // Set the text content
  newElement.textContent = `${type}`;

  // Apply CSS style
  newElement.style.display = 'inline-block';
  newElement.style.padding = '7px';
  newElement.style.backgroundColor = '#270e98';
  newElement.style.color = '#fafaff';
  newElement.style.textAlign = 'center';
  newElement.style.textDecoration = 'none';
  newElement.style.fontSize = '12px';
  newElement.style.margin = '2px';
  newElement.style.borderRadius = '50px';
  newElement.style.border = 'none';
  newElement.style.fontWeight = 'bold';
  newElement.style.height = '30px';

  // Append the new element to the active-filter-container
  const activeFilterContainer = document.querySelector('.active-filter-container');

  const existingElement = Array.from(activeFilterContainer.children).find(child => child.textContent === newElement.textContent);

  if (existingElement) {
    // If it exists, remove it
    activeFilterContainer.removeChild(existingElement);
  } else {
    // If it doesn't exist, append it
    activeFilterContainer.appendChild(newElement);
  }
}

async function sortByCredit(event) {
  let nameId = getSliderValues(event);
  let credit = nameId.sliderValue;
  let checkboxId = "credit " + "credit";
  await sendApiRequest("GET", '/courses/course_size/' + credit, (data) => isMatch(data, checkboxId, true), onFailure);
}

async function sortByPriceRange() {
  let max = maxPrice.value;
  let min = minPrice.value;
  if (max === null || max === ''){
    max = 1000000;
  } else if (min === null || min === ''){
    min = 0;
  }

  for (let child of children) {
    let childPrice = child.querySelector('.finalPriceBox').textContent;
    let price = parseFloat(childPrice.split(' ')[0]);
    if (price < min || price > max) {
      pricedChildren.set(child, 1);
    } else if (pricedChildren.has(child)) {
      pricedChildren.delete(child);
    }
  }
  updateView();
}


function searchCourses() {
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

function toggleFilters() {
  console.log(showFilters)
  showFilters.value = !showFilters.value;
}

</script>

<style scoped>

.filter-container {
  padding: 0 11% 0 11%;
  max-width: 100%;
  display: flex;
  flex-direction: row;
  align-content: center;
  justify-content: center;
  flex-wrap: nowrap;
  gap: 2%;
  width: 100%;
}


.range-container {
  min-width: 250px;
  max-width: 250px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-content: center;
}


.filter-popup {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  max-width: 100%;
  height: 60%;
  background-color: var(--light-1);
  z-index: 100;
  overflow-y: auto;
  overflow-x: hidden;
  transition: bottom 0.3s ease;
}

.mobile-filter {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 10px;
  width: 100%;
}

.dark-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 40%;
  background-color: rgb(88, 75, 235, 0.1);
  z-index: 99;
}

.styledHeader {
  padding: 10px;
  width: 100%;
}

#range-vertical-container .checkbox-wrapper {
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-end;
}

#range-vertical-container .wrapper {
  padding-left: 10px;
  gap: 5px;
}

#range-vertical-container .cbx {
  font-size: 14px;
}

#range-vertical-container .span {
  font-size: 14px;
}


#filters {
  color: var(--dark-3);
  font-family: Inter, sans-serif;
  font-size: 18px;
  font-weight: bold;
}

.separator {
  height: 1px;
  width: 100%;
  background-color: grey;
  margin: 5px 0;
  padding: 0 40px;
}

.flexible-grid-container {
  margin-top: 10px;
  background-color: var(--light-1);
  border-radius: 20px;
  display: flex;
  justify-content: flex-start;
  padding: 5px;
  min-height: 1000px;
  width: 100%; /* Set width to 100% */
  max-width: 100%; /* Ensure it doesn't exceed 100% */
}

.flexible-grid {
  background-color: var(--light-1);
  border-radius: 10px;
  padding: 5px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: flex-start;
  align-content: flex-start;
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


@media (max-width: 769px) {
  .filter-container {
    flex-direction: column;
    max-width: 90%;
    margin: 0 5% 0 5%;
  }

  .range-container {
    display: none;
  }

  .flexible-grid {
    margin: 0;
    justify-content: center;
    align-items: center;
  }
}

header h2 {
  color: #0C0C0C;
  font-size: 24px;
  font-weight: 600;
}

.price-input {
  width: 100%;
  gap: 10px;
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.price-input input {
  min-height: 40px;
  width: 100%;
  text-align: center;
}

.price-input-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
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

.slider {
  height: 5px;
  position: relative;
  background: #ddd;
  border-radius: 5px;
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
#creditContainer {
  transition: height 0.2s ease-out, opacity 0.2s ease-out;
}

.category-list {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
}


.active-filter-container {
  margin-top: 20px;
  margin-left: 19%;
  width: 81%;
  min-height: 40px;
  display: flex;
  flex-direction: row;
  align-content: flex-start;
  justify-content: flex-start;
  flex-wrap: wrap;
}

.filter-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.search-container {
  width: 100vw;
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


.active-filter-container button {
  padding: 7px 4.5vw 7px 4.5vw;
  background-color: #fafaff;
  color: #282828;
  text-align: center;
  text-decoration: none;
  margin: 2px 5px 2px 2px;
  border-radius: 50px;
  border: none;
  height: 30px;
  justify-content: flex-start;
  align-items: center;
  gap: 5px;
  display: none;
}

.active-filter-container p {
  font-size: 12px;
  font-weight: bold;
}

.active-filter-container img {
  max-width: 15px;
  max-height: 15px;
}

body, html {
  max-width: 100%;
  overflow-x: hidden;
  overflow-y: auto;
}

.form-control input {
  width: 100%;
  font-family: sans-serif;
  min-height: 40px;
  text-align: center;
}


@media (max-width: 1250px) {
  .filter-container {
    flex-direction: column;
    max-width: 90%;
    margin: 0 5% 0 5%;
    padding: 0;
  }

  .range-container {
    display: none;
  }

  .active-filter-container {
    width: 90%;
    margin: 0 5% 0 5%;
  }

  .active-filter-container button {
    display: flex;
  }

  #range-vertical-container {
    display: flex;
    align-content: flex-start;
    align-items: flex-start;
    min-width: 100%;
  }

  .search-bar {
    width: 90%;
    margin: 0 5% 0 5%;
  }

  .search-prompt { 
    padding: 20px 20px 20px 70px;
  }
}
</style>
