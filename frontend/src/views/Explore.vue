<template>
  <div id="background" class="background">
    <div class="hero-container">
      <div class="hero-box">
        <img id="planet" class="planet1" src="/bluePlanet.svg" alt="Logo">

      </div>
    </div>
    <img class="bend" src="/bend.svg">
  </div>
  <div class="course-section">

    <div class="title">
      <h2>Explore</h2>

    </div>
    <p class="description" >Explore our library</p>


    <div class="search-container">

      <div class="search-bar">
        <label for="myTextBox"></label><input class="search-prompt" type="text" id="myTextBox" name="myTextBox"
                                              placeholder="Search for courses to boost your skills">
        <img class="search-icon" src="/search.png" alt="Connect">
      </div>
    </div>
  </div>

  <div class="filter-container">

    <div class="category-container">
      <div class="singleCategory">
        <input type="radio" class="radio-button" id="it-radio" name="category">
        <label class="radio-label" for="it-radio">Information Technologies</label>
      </div>
      <div class="singleCategory">
        <input type="radio" class="radio-button" id="dm-radio" name="category">
        <label class="radio-label" for="dm-radio">Digital Marketing</label>
      </div>
      <div class="singleCategory">
        <input type="radio" class="radio-button" id="be-radio" name="category">
        <label class="radio-label" for="be-radio">Business and Entrepreneurship</label>
      </div>
      <div class="singleCategory">
        <input type="radio" class="radio-button" id="ds-radio" name="category">
        <label class="radio-label" for="ds-radio">Data Science and Analytics</label>
      </div>
    </div>

    <div class="range-container">
      <button class="price-ranger" type="button">Price</button>
      <div class="wrapper">
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
      <div class="date-picker">Date</div>
      <div class="level-picker">Difficulty</div>
      <div class="provider-picker"></div>
      <div class="hour-picker"></div>
      <div class="credit-ranger"></div>
      <div class="additional-filters"></div>

    </div>
  </div>
</template>

<script setup>
import {getCurrentInstance} from "vue";

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

const rangeInput = document.querySelectorAll(".range-input input"),
    priceInput = document.querySelectorAll(".price-input input"),
    range = document.querySelector(".slider .progress");
let priceGap = 1000;

priceInput.forEach(input =>{
  input.addEventListener("input", e =>{
    let minPrice = parseInt(priceInput[0].value),
        maxPrice = parseInt(priceInput[1].value);

    if((maxPrice - minPrice >= priceGap) && maxPrice <= rangeInput[1].max){
      if(e.target.className === "input-min"){
        rangeInput[0].value = minPrice;
        range.style.left = ((minPrice / rangeInput[0].max) * 100) + "%";
      }else{
        rangeInput[1].value = maxPrice;
        range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
      }
    }
  });
});

rangeInput.forEach(input =>{
  input.addEventListener("input", e =>{
    let minVal = parseInt(rangeInput[0].value),
        maxVal = parseInt(rangeInput[1].value);

    if((maxVal - minVal) < priceGap){
      if(e.target.className === "range-min"){
        rangeInput[0].value = maxVal - priceGap
      }else{
        rangeInput[1].value = minVal + priceGap;
      }
    }else{
      priceInput[0].value = minVal;
      priceInput[1].value = maxVal;
      range.style.left = ((minVal / rangeInput[0].max) * 100) + "%";
      range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
    }
  });
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

.hero-container {
  display: flex;
  justify-content: center;
}

.hero-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.bend {
  width: 100%;
  margin: 0;
  padding: 0;

}

.title {
  border: none;
  border-radius: 15px;
  width: 70%;
  justify-content: center;
  align-content: center;
  text-decoration: none;
  display: flex;
  margin: 0 auto;
}

.description {
  color: #282828;
  font-size: 20px;
  text-align: center;
  margin: 0 auto;
  width: 70%;
}

.course-section {
  background-color: #EAEAEA;
  margin: 0;
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
  color: var(--light-1);
  background: none;
  width: 60%;
  padding: 20px 20px 20px 100px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
}

.course-block {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  margin: 0 auto;
  width: 70%;
}

.course-card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 20px;
  padding: 20px;
  border-radius: 15px;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 300px;
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

.standard-button {
  padding: 10px 20px;
  margin: 10px 0;
  border-radius: 5px;
  border: solid 2px #584BEB;
  background: #584BEB;
  color: white;
  font-size: 16px;
  cursor: pointer;
}

.standard-button:hover {
  background: #6E67FC;
}

.planet1 {
  height: 150px;
  animation: ease-out slideIn 1s forwards, ease-out pulse 5s infinite 1s;
}

@keyframes slideIn {
  0% {
    transform: translatex(-1000px) translateY(-100px) scale(1.2);
  }
  100% {
    transform:  translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes pulse {
  0% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
  50% {
    transform: translatex(0) translateY(120px) scale(1.7);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }}

.category-container {
  display: flex;
  flex-wrap: wrap;
  padding: 2%;
  grid-template-columns: repeat(2, 2fr);
  justify-content: space-between; /* Add this line */
  align-items: center;
  margin: 5%;
}

.radio-button {
  display: none;
}

.radio-label {
  font-size: 24px;
  text-wrap: none;
  display: inline-block;
  padding: 10px 20px;
  border: 1px solid #ccc;
  border-radius: 20px;
  cursor: pointer;
  text-align: center;
  transition: border-color 0.3s, background-color 0.3s, color 0.3s;
  background-color: #fff;
  color: #000;
}

.radio-button:checked + .radio-label {
  background-color: #007bff;
  color: #fff;
  border-color: #ff4d4d; /* Change border color to red */
}

::selection{
  color: #fff;
  background: #17A2B8;
}
.wrapper{
  width: 400px;
  background: #fff;
  border-radius: 10px;
  padding: 20px 25px 40px;
  box-shadow: 0 12px 35px rgba(0,0,0,0.1);
}
header h2{
  color: #0C0C0C;
  font-size: 24px;
  font-weight: 600;
}
.price-input{
  width: 100%;
  display: flex;
  margin: 30px 0 35px;
}
.price-input .field{
  display: flex;
  width: 100%;
  height: 45px;
  align-items: center;
}
.field input{
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
.price-input .separator{
  width: 130px;
  display: flex;
  font-size: 19px;
  align-items: center;
  justify-content: center;
}
.slider{
  height: 5px;
  position: relative;
  background: #ddd;
  border-radius: 5px;
}
.slider .progress{
  height: 100%;
  left: 25%;
  right: 25%;
  position: absolute;
  border-radius: 5px;
  background: #584BEB;
}
.range-input{
  position: relative;
}
.range-input input{
  position: absolute;
  width: 100%;
  height: 5px;
  top: -5px;
  background: none;
  pointer-events: none;
  -webkit-appearance: none;
  -moz-appearance: none;
}
input[type="range"]::-webkit-slider-thumb{
  height: 17px;
  width: 17px;
  border-radius: 50%;
  background: #584BEB;
  pointer-events: auto;
  -webkit-appearance: none;
  box-shadow: 0 0 6px rgba(0,0,0,0.05);
}
input[type="range"]::-moz-range-thumb{
  height: 17px;
  width: 17px;
  border: none;
  border-radius: 50%;
  background: #584BEB;
  pointer-events: auto;
  -moz-appearance: none;
  box-shadow: 0 0 6px rgba(0,0,0,0.05);
}

button {
  padding: 10px 20px;
  margin: 10px 0;
  border-radius: 5px;
  border: solid 2px #584BEB;
  background: #584BEB;
  color: black;
  font-size: 18px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.3s;
}
</style>