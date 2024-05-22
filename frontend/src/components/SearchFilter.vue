<script setup>
import {getCurrentInstance, onMounted, ref, watchEffect} from "vue";
import {currency, setDefaultCurrency} from "@/js/currency";
import {createContentBox, fetchCourses, fetchCurrencies, fetchProviders} from "@/js/populationTools";
import {sendApiRequest} from "@/js/requests";
import flatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';


const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const searchQuery = ref('');
const creditValue = ref(0);
const isRangeContainerVisible = ref(window.innerWidth > 1250);
const isDarkOverlayVisible = ref(false);
const updateVisibility = () => {
  isRangeContainerVisible.value = window.innerWidth > 1250;
  isDarkOverlayVisible.value = window.innerWidth < 1250 && isRangeContainerVisible.value;
};

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

const date = ref(null);
const config = ref({
  mode: 'range',
  dateFormat: 'Y-m-d',
  altInput: true,
  altFormat: 'J of F',
  minDate: 'today',
  wrap: true,
});

const flatpickr = ref(null);

 const getDate = () => {
  const flatpickerInstance = flatpickr.value.fp;
  const inputValue = flatpickerInstance.input.value.split(' to ');
  const altInputValue = flatpickerInstance.altInput.value.split(' to ');
  const [startDateText, endDateText] = altInputValue;
  const [startDate, endDate] = inputValue;


  sortByDate(startDate, endDate, startDateText, endDateText);
};

onMounted(() => {
  courseContainer = document.querySelector('#courseContainer');
  children = courseContainer.children;
  populateCourses('.flexible-grid');
  currency(API_URL);
  window.addEventListener('resize', updateVisibility);
  updateVisibility();
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
    populateCheckboxes('#categoryList',coursesData,'course.category',isCategoryChecked,sortByCategory);
    populateCheckboxes('#providerList',providerData,'name',isProviderChecked,onProviderCheckboxChange);
  } catch (error) {
    console.error('Error:', error);
  }
}

function populateCheckboxes(selector, data,dataType,checkedRef, changeHandler) {
  const uniqueItems = new Set();

    const list = document.querySelector(selector);
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

      const labelCbx = document.createElement('label');
      labelCbx.className = 'lbl-cbx'
      const checkbox = document.createElement('input');
      const div = document.createElement('div');
      div.className = 'div-cbx'
      checkbox.className = 'inp-cbx';
      checkbox.id = itemName;
      checkbox.type = 'checkbox';
      checkbox.setAttribute('v-model', `${checkedRef}['${itemName}']`);
      checkbox.addEventListener('change', changeHandler);

      labelCbx.appendChild(checkbox);
      labelCbx.appendChild(div);

      checkboxWrapper.appendChild(labelCbx);

      list.appendChild(checkboxWrapper);

      // Initialize the checkbox state for this item
      checkedRef.value[itemName] = false;
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
      addFilterToCourse(id, checkboxId);
    }
  }

  updateActiveFilters(checkboxId,isSlider);
}

function setChildrenDisplay(children, display) {
  for (let child of children) {
    child.style.display = display;
  }
}

function updateChildrenDisplayForActiveFilters() {
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

function hideSearchedAndPricedChildren() {
  for (let child of children) {
    if (searchedChildren.has(child) || pricedChildren.has(child)) {
      child.style.display = 'none';
    }
  }
}

function updateView() {
  if (activeFilters.size === 0) {
    setChildrenDisplay(children, 'block');
  } else {
    setChildrenDisplay(children, 'none');
    updateChildrenDisplayForActiveFilters();
  }
  hideSearchedAndPricedChildren();
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
}

function checkActiveFilters(checkboxId) {
  if (activeFilters.has("credit") && checkboxId.includes("credit") || activeFilters.has("date") && checkboxId.includes("date")) {
    for (let key of filterMap.keys()) {
      let existingFilter = filterMap.get(key);
      if (existingFilter.has("credit")) {
        existingFilter.delete("credit");
      }
      if (existingFilter.has("date")) {
        console.log("pepepopop")
        existingFilter.delete("date");
      }
    }
  }
}

async function sortByCategory(event) {
  let nameId = getCheckboxId(event);
  let category = nameId.labelName
  let checkboxId = "category " + nameId.labelName;
  onCheckboxChange(checkboxId, category,false);
  await sendApiRequest(API_URL,"GET", '/courses/category/' + category, (data) => isMatch(data, checkboxId, false), onFailure);
}

async function sortByDate(startDate, endDate, startDateText, endDateText) {
  let checkboxId = "date ";
  if (startDate === undefined || endDate === undefined) {
    if (startDate === "" && endDate === undefined) {
      onCheckboxChange(checkboxId, "All Dates",false);
      startDate = '1900-01-01'
      endDate = '2100-01-01'
    } else {
      return;
    }
  } else{
    onCheckboxChange(checkboxId, startDateText + " to " + endDateText,true);
  }
  await sendApiRequest(API_URL,"GET", '/courses/date-range?startDate=' + startDate + '&endDate=' + endDate, (data) =>
      isMatch(data, checkboxId, true), onFailure);
}

async function onProviderCheckboxChange(event) {
  let nameId = getCheckboxId(event);
  let provider = nameId.labelName;
  let checkboxId = "provider " + nameId.checkboxId;
  onCheckboxChange(checkboxId, provider,false);
  await sendApiRequest(API_URL,"GET", '/courses/provider/' + provider, (data) => isMatch(data, checkboxId, false), onFailure);
}

async function sortByDifficulty(event) {
  let nameId = getCheckboxId(event);
  console.log(nameId)
  let difficulty = nameId.labelName;
  let checkboxId = "difficulty " + nameId.checkboxId;
  onCheckboxChange(checkboxId, difficulty,false);
  await sendApiRequest(API_URL,"GET", '/courses/level/' + difficulty, (data) => isMatch(data, checkboxId, false), onFailure);
}

function getSliderValues(event) {
  let checkboxId = event.target.id;
  let sliderValue = event.target.value;
  return {sliderValue, checkboxId};
}

function onCheckboxChange(checkboxid,type,isSlider) {
  // Create a new div element
  const newElement = document.createElement('div');
  newElement.textContent = `${type}`;
  newElement.id = checkboxid;

  // Apply CSS style
  newElement.style.display = 'inline-block';
  newElement.style.padding = '7px 15px';
  newElement.style.backgroundColor = '#5666ff';
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

  const existingElement = Array.from(activeFilterContainer.children).find(child => child.id === newElement.id);

  if (existingElement) {
    // If it exists, remove it
    activeFilterContainer.removeChild(existingElement);
    if (isSlider) {
      onCheckboxChange(checkboxid,type,false);
    }
  } else {
    // If it doesn't exist, append it
    activeFilterContainer.appendChild(newElement);
  }
}

async function sortByCredit(event) {
  let nameId = getSliderValues(event);
  let credit = nameId.sliderValue;
  let checkboxId = "credit " + "credit";
  onCheckboxChange(checkboxId, "Minimum Credit: " + credit,true);
  await sendApiRequest(API_URL,"GET", '/courses/course_size/' + credit, (data) => isMatch(data, checkboxId, true), onFailure);
}

async function sortByPriceRange() {
  let max = maxPrice.value;
  let min = minPrice.value;
  if (max === null || max === ''){
    max = Infinity;
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
  switch (true) {
    case min === 0 && max === Infinity:
      onCheckboxChange("price", "All Prices",true);
      break;
    case min === 0:
      onCheckboxChange("price", "Up to: " + max,true);
      break;
    case max === Infinity:
      onCheckboxChange("price", "From: " + min,true);
      break;
    default:
      onCheckboxChange("price", "From: " +  min + " - To: " + max,true);
  }
  updateView();
}

function searchCourses() {
  for (let child of children) {
    let childTitle = child.querySelector('.content-box-title').textContent.toLowerCase();
    if (!childTitle.includes(searchQuery.value.toLowerCase())) {
      searchedChildren.set(child, 1);
    } else if (searchedChildren.has(child)) {
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
  if (window.innerWidth < 1250) {
    isRangeContainerVisible.value = !isRangeContainerVisible.value;
    isDarkOverlayVisible.value = !isDarkOverlayVisible.value;
    isCategoryVisible.value = true;
    isPriceVisible.value = true;
    isDateVisible.value = true;
    isDifficultyVisible.value = true;
    isProviderVisible.value = true;
    isCreditVisible.value = true;
  }
}

watchEffect(() => {
  if (isRangeContainerVisible.value && window.innerWidth < 1250) {
    // If isRangeContainerVisible is true, set body overflow to hidden
    document.body.style.overflow = 'hidden';
  } else {
    // If isRangeContainerVisible is false, set body overflow to auto
    document.body.style.overflow = 'auto';
  }
});
</script>

<template>
  <div class="search-container">

    <div class="search-bar">
      <input class="search-prompt" type="text" id="myTextBox" name="myTextBox"
             placeholder="Search for courses to boost your skills" v-model="searchQuery" @input="searchCourses">
      <img class="search-icon" src="/search.png" alt="Connect">
    </div>
  </div>
  <div class="dark-overlay" @click="toggleFilters" v-show="isDarkOverlayVisible"></div>
  <div class="filter-wrapper">
    <div class="active-filter-container">
      <button @click="toggleFilters" class="filterButton">
        <svg xmlns="http://www.w3.org/2000/svg" width="15px" height="15px" viewBox="0 0 24 24" fill="none">
          <path fill-rule="evenodd" clip-rule="evenodd" d="M3 7C3 6.44772 3.44772 6 4 6H20C20.5523 6 21 6.44772 21 7C21 7.55228 20.5523 8 20 8H4C3.44772 8 3 7.55228 3 7ZM6 12C6 11.4477 6.44772 11 7 11H17C17.5523 11 18 11.4477 18 12C18 12.5523 17.5523 13 17 13H7C6.44772 13 6 12.5523 6 12ZM9 17C9 16.4477 9.44772 16 10 16H14C14.5523 16 15 16.4477 15 17C15 17.5523 14.5523 18 14 18H10C9.44772 18 9 17.5523 9 17Z" fill="#000000"/>
        </svg>
        <span style="font-weight: bold">Filters</span>
      </button>
    </div>

    <div class="filter-container">
      <transition name="slide">
        <div class="range-container" v-show="isRangeContainerVisible">
          <div class="mobile-filter-header">
            <h3 class="mobile-h3">Filters</h3>
            <button type="button" class="exitButton" @click="toggleFilters">
              <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" viewBox="0 0 50 50">
                <line x1="10" y1="10" x2="30" y2="30" stroke="black" stroke-width="2"/>
                <line x1="30" y1="10" x2="10" y2="30" stroke="black" stroke-width="2"/>
              </svg>
            </button>
          </div>
          <div class="separator" id="headerSeparator"></div>

          <div class="overflowContainer">
            <button class="price-ranger" @click="toggleShowCategory" :style="{
          'border-radius': isCategoryVisible ? '10px 10px 0 0' : '10px'
        }">Category
            </button>

            <div class="separator"></div>

            <div class="wrapper" id="categoryContainer" :class="{'visible': isCategoryVisible, 'hidden': !isCategoryVisible}">
              <div class="category-list" id="categoryList"></div>
            </div>

            <button class="price-ranger" @click="toggleShowPrice" :style="{
          'border-radius': isPriceVisible ? '10px 10px 0 0' : '10px'
        }">Price
            </button>

            <div class="separator"></div>

            <div class="wrapper" id="priceText" :class="{'visible': isPriceVisible, 'hidden': !isPriceVisible}">
              <div class="price-input-container">
                <div class="price-input" id="min-price-input">
                  <label for="min-price">Min Price</label>
                  <p style="color:#5666ff">-</p>
                  <input type="number" id="min-price" v-model="minPrice" min="0" placeholder="Minimum Price" @change="sortByPriceRange" @input="minPrice = $event.target.value.replace(/\D/g, '')">
                </div>
                <p id ="mobile-separator">-</p>
                <div class="price-input" id="max-price-input">
                  <label for="max-price">Max Price</label>
                  <p style="color:#5666ff">-</p>
                  <input type="number" id="max-price" v-model="maxPrice" min="1" placeholder="Maximum Price" @change="sortByPriceRange" @input="maxPrice = $event.target.value.replace(/\D/g, '')">
                </div>
              </div>
            </div>

            <button class="price-ranger" @click="toggleShowDate" :style="{
          'border-radius': isDateVisible ? '10px 10px 0 0' : '10px'
        }">Date
            </button>

            <div class="separator"></div>

            <div class="wrapper" id="dateContainer" :class="{'visible': isDateVisible, 'hidden': !isDateVisible}">
              <div class="dateBoxContainer">
                <div class="svgContainer" data-toggle>
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 25 25"><path d="M22.5 3H21V2a1 1 0 0 0-1-1h-1a1 1 0 0 0-1 1v1h-4V2a1 1 0 0 0-1-1h-1a1 1 0 0 0-1 1v1H7V2a1 1 0 0 0-1-1H5a1 1 0 0 0-1 1v1H2.5A1.5 1.5 0 0 0 1 4.5v18A1.5 1.5 0 0 0 2.5 24h20a1.5 1.5 0 0 0 1.5-1.5v-18A1.5 1.5 0 0 0 22.5 3zM19 2h1v3h-1zm-7 0h1v3h-1zM5 2h1v3H5zM2.5 4H4v1a1 1 0 0 0 1 1h1a1 1 0 0 0 1-1V4h4v1a1 1 0 0 0 1 1h1a1 1 0 0 0 1-1V4h4v1a1 1 0 0 0 1 1h1a1 1 0 0 0 1-1V4h1.5a.5.5 0 0 1 .5.5V8H2V4.5a.5.5 0 0 1 .5-.5zm20 19h-20a.5.5 0 0 1-.5-.5V9h21v13.5a.5.5 0 0 1-.5.5z" style="fill:#5666ff"/></svg>
                </div>
                <flat-pickr data-input v-model="date" :config="config" class="form-control" placeholder="Select dates" name="date" @change="getDate" ref="flatpickr"></flat-pickr>
                <button id="clearButton" type="button" title="clear" data-clear @change="getDate">Clear</button>
              </div>
            </div>

            <button class="price-ranger" @click="toggleShowProvider" :style="{
          'border-radius': isProviderVisible ? '10px 10px 0 0' : '10px'
        }">Provider
            </button>

            <div class="separator"></div>

            <div class="wrapper" id="providerContainer" :class="{'visible': isProviderVisible, 'hidden': !isProviderVisible}">
              <div id="providerList"></div>
            </div>

            <button class="price-ranger" @click="toggleShowDifficulty" :style="{
          'border-radius': isDifficultyVisible ? '10px 10px 0 0' : '10px'
        }">Difficulty
            </button>

            <div class="separator"></div>

            <div class="wrapper" :class="{'visible': isDifficultyVisible, 'hidden': !isDifficultyVisible}" id="difficultyContainer">
              <div class="checkbox-wrapper">
                <label class="cbx" for="beginner">Beginner</label>
                <label class="lbl-cbx" >
                  <input class="inp-cbx" id="beginner" type="checkbox" @change="sortByDifficulty">
                  <div class="div-cbx"></div>
                </label>
              </div>
              <div class="checkbox-wrapper">
                <label class="cbx" for="intermediate">Intermediate</label>
                <label class="lbl-cbx">
                  <input class="inp-cbx" id="intermediate" type="checkbox" @change="sortByDifficulty">
                  <div class="div-cbx"></div>
                </label>
              </div>
              <div class="checkbox-wrapper">
                <label class="cbx" for="advanced">Advanced</label>
                <label class="lbl-cbx">
                  <input class="inp-cbx" id="advanced" type="checkbox" @change="sortByDifficulty">
                  <div class="div-cbx"></div>
                </label>
              </div>
              <div class="checkbox-wrapper">
                <label class="cbx" for="expert">Expert</label>
                <label class="lbl-cbx">
                  <input class="inp-cbx" id="expert" type="checkbox" @change="sortByDifficulty">
                  <div class="div-cbx"></div>
                </label>
              </div>
            </div>

            <button class="price-ranger" @click="toggleShowCredit" :style="{
          'border-radius': isCreditVisible ? '10px 10px 0 0' : '10px'
        }">Credit
            </button>

            <div class="separator"></div>

            <div class="wrapper" id="creditContainer" :class="{'visible': isCreditVisible, 'hidden': !isCreditVisible}">
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
      </transition>
      <div class="flexible-grid-container">
        <div class="flexible-grid" id="courseContainer">
        </div>
      </div>
    </div>
  </div>
</template>

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

.dark-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
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
  display: none;
  min-height: 1px;
  width: 100%;
  background-color: grey;
  margin: 10px 0;
  padding: 0 40px;
  text-align: center;
}

#headerSeparator {
  width: 100%;
  margin: 0;
  padding: 0;
  min-height: 2px;
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
  background: var(--light-1);
  border-radius: 10px;
  padding: 0 20px;
}

.wrapper.visible {
  height: auto;
  padding-bottom: 20px;
  opacity: 1;
  z-index: 0;
  border-radius: 0 0 10px 10px;
}

.wrapper.hidden {
  height: 0;
  opacity: 0;
  z-index: -100;
  border-radius: 10px;
  padding-bottom: 0px;

}

@media (max-width: 769px) {
  .flexible-grid {
    margin: 0;
    justify-content: center;
    align-items: center;
    gap: 10px;
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
  font-size: 16px;
  text-align: center;
  border: 1px solid #5666ff;
  border-radius: 5px;
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
  width: 100%;
  max-width: 300px;
  background-color: var(--light-1);
  padding: 5px 10px;
  margin: 10px 0 0 0;
  border-radius: 10px;
  border: none;
  color: #656565FF;
  font-family: Inter, sans-serif;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s;

  &:hover {
    color: var(--dark-3);
    border: none;
    transform: scale(1);
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
  width: 100%;
  min-height: 40px;
  display: flex;
  flex-direction: row;
  align-content: flex-start;
  justify-content: flex-start;
  flex-wrap: wrap;
  padding: 0 11% 0 calc(250px + 13%);
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

.dateBoxContainer {
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-content: center;
}

#dateContainer svg {
  width: 20px;
  height: 20px;
}

#clearButton {
  background-color: #5666ff;
  color: white;
  border: none;
  border-radius: 0 5px 5px 0;
}
.svgContainer {
  display: flex;
  justify-content: center;
  align-items: center;

  border: 1px solid #5666ff;
  border-right:none;
  border-radius: 5px 0 0 5px;

  min-height: 40px;
  padding-left: 5px;

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

#creditSlider::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 15px;
  height: 15px;
  background: #5666ff;
  border-radius: 50%;
  cursor: pointer;
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

.mobile-filter-header {
  display: none;
}

.overflowContainer {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-content: center;
}

Button {
  cursor: pointer;
  transition: all 0.2s ease;
}

button:hover {
  transform : scale(1.1);
  border: #5666ff solid 1px;
  path {
    fill: #5666ff;
  };
  color: #5666ff;
}

Button:active {
  transform: scale(0.8);
}

#mobile-separator{
  display: none;
}


@media (max-width: 1250px) {

  .overflowContainer {
    overflow-y: scroll;
    overflow-x: hidden;
    padding: 20px 20px 100px 20px;
  }

  .mobile-filter-header {
    padding: 20px 20px 10px 20px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    width: 100%;
  }

  .mobile-h3 {
    font-size: 24px;
    font-weight: bold;
    color: #656565FF;
  }

  .filter-container {
    flex-direction: column;
    max-width: 90%;
    padding: 0;
  }

  .exitButton{
    display: inline-block;
    border: none;
    background-color: transparent;
    text-align: center;
  }

  .exitButton:hover {
    line {
      stroke: #5666ff;
    }
    transform: scale(1.1);
    border: none;
  }

  .range-container  {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    max-width: 90%;
    height: 50vh;
    margin: 0 5% 0 5%;
    border-radius: 20px 20px 0 0;

    background-color: var(--light-1);

    z-index: 100;
    overflow-y: auto;
    overflow-x: hidden;

    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;

    gap: 5px;
  }

  .slide-enter-active, .slide-leave-active {
    transition: transform 0.7s;
  }

  .slide-enter, .slide-leave-to {
    transform: translateY(100%);
  }

  .price-ranger {
    margin: 0;
    padding: 0;
    text-align: left;
  }

  .wrapper {
    padding: 0;
    width: 100%;
  }

  .checkbox-wrapper {
    display: flex;
    flex-direction: row-reverse;
    justify-content: flex-end;
    width: 100%;
  }

  .active-filter-container {
    width: 90%;
    margin: 1.5% 5% auto 5%;
    padding: 0;
  }

  .active-filter-container button {
    display: flex;
  }

  .search-bar {
    width: 90%;
    margin: 0 5% 0 5%;
  }

  .search-prompt {
    padding: 20px 20px 20px 70px;
  }

  .separator {
    display: block;
  }

  .price-input-container {
    flex-direction: row;
    align-items: baseline;
    padding: 0 20px;
  }

  #mobile-separator {
    display: block;
    color: #5666ff;
  }

 #min-price-input {
  justify-content: flex-start;
  }

 #max-price-input {
   justify-content: flex-end;
 }

  .price-input p {
    display: none;
  }

  .price-input label {
    display: none;
  }

  .price-input input {
    width: 85%;
    border: 1px solid #5666ff;
    border-radius: 5px;
  }

  .dateBoxContainer {
    padding: 0 20px;
  }

}
</style>
