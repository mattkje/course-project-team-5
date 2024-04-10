<script setup>

import ProBox from "@/components/ProBox.vue";
import HeroSection from "@/components/HeroSection.vue";
import "@/landing.js"
import "@/courseBox.css"
</script>

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

.content-box {
  display: flex;
  flex-direction: column;
  padding: 20px;
  justify-content: space-between;
  min-width: 230px;
  max-width: 230px;
  height: 350px;
  background-color: rgba(255, 255, 255);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 27px;
  text-decoration: none;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  transition: transform 0.5s ease;
  margin: 30px;

  &:hover {
    transform: scale(1.05);
  }
}

* {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
}

</style>
