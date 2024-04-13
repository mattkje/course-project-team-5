<template>
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
        <header>
          <h2>Category</h2>
        </header>
        <div class="category-list">
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Category 1</label>
            <input class="inp-cbx" id="morning" type="checkbox">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Category 2</label>
            <input class="inp-cbx" id="morning" type="checkbox">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Category 3</label>
            <input class="inp-cbx" id="morning" type="checkbox">
          </div>
          <div class="checkbox-wrapper">
            <label class="cbx" for="morning">Category 4</label>
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


const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
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
  display: flex;
  flex-direction: row;
  align-content: space-between;
  justify-content: center;
  margin: auto;
}

.range-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-content: center;
  margin: 20px;
}


.course-container {
  background-color: white;
  border-radius: 10px;
  width: 900px;
  height: 600px;
  padding: 20px;
  display: flex;
  flex-direction: row;
  align-content: flex-start;
  justify-content: center;
  margin: 30px;
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
  width: 400px;
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
  width: 400px;
  background-color: white;
  padding: 10px 20px;
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
  display: flex;
  flex-direction: column;
}

.checkbox-wrapper {
  display: flex;
  flex-direction: row;
  align-content: flex-start;
  justify-content: space-between;
}

.inp-cbx {
  font-family: 'Inter', sans-serif;
  font-size: 15px;
}

.cbx {

}
</style>