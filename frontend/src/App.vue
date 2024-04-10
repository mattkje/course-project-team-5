<script setup>
import Menubar from "@/components/Menubar.vue";
import Footer from "@/components/Footer.vue";
import "@/courseBox.css"
import "@/landing.js";
</script>

<template>

  <Menubar msg="Menubar"/>
  <div class="background">
    <div class="background-overlay">
      <div class="hero-container">
        <div class="hero-box">
          <h1 class="hero-text">Unleash&nbsp;Your Learning&nbsp;Journey</h1>
          <p class="hero-description">
            Access premium courses, explore diverse topics, and unleash your potential through our platform.
          </p>
          <img class="planet1" src="/logo.svg" alt="Logo">

        </div>
      </div>
    </div>
    <img class="bend" src="/bend.svg">
  </div>

  <div class="course-section">
    <div class="search-section" style="display: none">
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

  <div class="pro-box">
    <img class="bend-bottom" src="/bend.svg" alt="Connect">
    <img class="pro-icon" src="/pro.svg" alt="Connect">
    <h2>Unlock Premium Features with Learniverse Pro</h2>
    <p>Get access to exclusive courses, personalized learning paths, and more with Learniverse Pro.</p>
    <button class="fancy-button" id="showPro">Learn More</button>
    <div class="pro-features" id="proInfoText">
      <h2>Perks</h2>
      <ul>
        <li>Access to exclusive courses</li>
        <li>Personalized learning paths</li>
        <li>Priority support</li>
      </ul>
      <div class="pro-pricing">
        <h2>Starting at $9.99/month</h2>
        <button class="standard-button">Get Started</button>
      </div>
    </div>
    <img class="bend" src="/bend.svg" alt="Connect">
  </div>

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


  <Footer msg="Footer"/>
</template>



<style scoped>
:root {
  --base-1: #584BEB;
  --base-2: #6E67FC;
  --light-1: #eaeaea;
  --light-2: #adadad;
  --dark-1: #090909;
  --dark-2: #1a1a1a;
  --dark-3: #07002c;
}


.h3Center {
  display: flex;
  justify-content: center;
}

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


.background {
  top: 0;
  height: 800px;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: end;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

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

.planet1 {
  height: 250px;
  animation: ease-in-out orbit 5s infinite;
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

.search-section {
  padding: 1vw 0;
  margin: 0 10vw;
  background-color: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 17px;
  backdrop-filter: blur(5px);
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
  border: 2px solid #282828;
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
  filter: brightness(0);
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

#proInfoText {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 0;
  opacity: 0;
  overflow: hidden;
  transition: height 0.5s ease-out, opacity 0.5s ease-out;
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

.standard-button {
  background-color: var(--base-1);
  color: var(--light-1);
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  width: max-content;

  &:hover {
    background-color: var(--base-2);
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }
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
