<script setup>

</script>

<template>
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
</template>

<style scoped>
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

.hero-text {
  font-family: 'Inter', sans-serif;
  font-size: 60px;
  text-transform: uppercase;
  font-style: normal;
  font-weight: 700;
  background: linear-gradient(180deg, #FFFFFF -148.55%, #9087EF 236.16%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  color: transparent;
  margin-top: 200px;
  animation: ease-in-out fadeIn 2s;
}

.hero-description {
  font-family: 'Inter', sans-serif;
  font-size: 18px;
  line-height: 1.5;
  margin-bottom: 20px;
  font-style: normal;
  font-weight: 500;
  text-align: center;
  color: #8F98EC;
  max-width: 470px;
  animation: ease-in-out fadeIn 2s;
}

.planet1 {
  height: 250px;
  animation: ease-in-out pulse 5s infinite;
}

.bend {
  width: 100%;
  margin: 0;
  padding: 0;

}

@keyframes pulse {
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

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
}}

</style>