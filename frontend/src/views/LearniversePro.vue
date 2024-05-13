<script setup>

import NewComponent from "@/components/Subscription.vue";
</script>

<template>
  <div id="background" class="background">
    <img class="planet" src="/pro.svg">
    <h2>Learniverse Pro</h2>
  </div>
  <NewComponent/>
</template>

<style scoped>
@media (max-width: 600px) {

  #planet {
    height: 120px;
    animation: ease-in-out pulse 5s infinite;
  }
}

.background {
  top: 0;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;
}

.background h2 {
  color: whitesmoke;
  font-size: 40px;
  margin: 0;
  padding-bottom: 30px;
  animation: ease-in-out fadeIn 1.5s;
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
  }}

.learniverse-membership h3 {
  padding: 16px 56px;
  font-size: 1.5rem;
  text-align: left;
  font-weight: 500;
  background-color: hsla(0, 0%, 45.1%, 0.2);
}

.purchase-button span {
  font-weight: 700;
}

</style>
