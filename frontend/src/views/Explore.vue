<template>
  <div id="background" class="background">
    <img class="planet" src="/bluePlanet.svg" alt="Blue Planet">
  </div>
  <div class="course-section">

    <div class="title">
      <h1 id="explore">Explore</h1>
      <p class="description">Explore our library</p>
    </div>
    <SearchFilter/>
  </div>
</template>

<script setup>

import SearchFilter from "@/components/SearchFilter.vue";

</script>

<style scoped>
.background {
  top: 0;
  height: min-content;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

}

.title {
  border: none;
  border-radius: 15px;
  width: 60%;
  justify-content: center;
  align-items: center;
  text-decoration: none;
  margin: 0 auto;
}

.description {
  color: #282828;
  font-size: 20px;
  margin: 0 auto;
  text-align: center;
}
#explore {

  text-align: center;
}

.course-section {
  padding-top: 20px;
  background-color: var(--light-3);
  padding-bottom: 200px;
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

.planet {
  height: 300px;
  animation: ease-out pulse 5s infinite;
}


@keyframes pulse {
  0% {
    transform: scale(0.5);
  }
  50% {
    transform: scale(0.6);
  }
  100% {
    transform: scale(0.5);
  }
}


@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}




::selection {
  color: #fff;
  background: #17A2B8;
}



header h2 {
  color: #0C0C0C;
  font-size: 24px;
  font-weight: 600;
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
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
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


</style>