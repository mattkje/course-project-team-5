<script setup>
import {getCurrentInstance} from "vue";

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

</script>

<template>
  <div class="search-section">
    <div class="h3Center">
      <h3>Explore a large selection of courses</h3>
    </div>



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

<style scoped>
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