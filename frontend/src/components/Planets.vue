<script>

</script>

<template>
  <div id="background" class="background" :class="{ 'animate-out': animateOut, 'animate-in': animateIn }">
    <div class="hero-container">
      <div class="hero-box">
        <div class="planet-box">
          <img id="planet" class="planet1" src="/logo.svg" alt="Logo">
          <img id="planet2" class="planet2" src="/bluePlanet.svg" alt="Explore">
          <img id="planet3" class="planet3" src="/greenPlanet.svg" alt="Community">
          <img id="planet4" class="planet4" src="/pro.svg" alt="Pro">
        </div>
      </div>
    </div>
    <img class="bend" src="/bend.svg">
  </div>
</template>

<style scoped>


@media (max-width: 600px) {
  #background {
    height: 600px;
  }

  #hero-text {
    font-size: 40px;
    margin-top: 100px;
  }

  #hero-description {
    font-size: 16px;
  }

  #planet {
    height: 120px;
    animation: ease-in-out pulse 5s infinite;
  }
}

.background {
  top: 0;
  height: 800px;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;
  animation: ease-in-out slideUp 1s;

}

@keyframes slideUp {
  0% {
    transform: scaleY(150%);
  }
  100% {
    transform: scaleY(100%);
  }
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
  text-align: center;
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

.planet-box {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin: 0;
}
.planet1 {
  height: 250px;
  animation: ease-in-out slideIn2 1s ;
  position: relative;
  transform:  translatex(1110px) translateY(-220px) scale(0.6);
}

.planet2 {
  height: 150px;
  position: relative;
  animation: ease-in-out slideIn1 1s;
  transform:  translatex(70px) translateY(100px) scale(2);
}
@keyframes slideIn1 {
  0% {
    transform: translateX(-900px) translateY(-220px);
  }
  100% {
    transform:  translatex(70px) translateY(100px) scale(2);
  }
}

@keyframes slideIn2 {
  0% {
    transform: translateX(300px) translateY(100px);
  }
  100% {
    transform:  translatex(1110px) translateY(-220px) scale(0.6);
  }
}

@keyframes slideIn3 {
  0% {
    transform: translateX(500px) translateY(-220px);
  }
  100% {
    transform:  translateX(1000px) translateY(-400px);
  }
}

@keyframes slideIn4 {
  0% {
    transform: translateX(-2100px) translateY(-400px) scale(0.6);
  }
  100% {
    transform: translateX(-1100px) translateY(-220px) scale(0.6);
  }
}

.planet3 {
  height: 150px;
  transform: translateX(1000px) translateY(-400px);
  position: relative;
  animation: ease-in-out slideIn3 1s;

}
.planet4 {
  height: 250px;
  animation: ease-in-out slideIn4 1s;
  position: relative;
  transform: translateX(-1100px) translateY(-220px) scale(0.6);
}

.bend {
  width: 100%;
  margin: 0;
  padding: 0;

}

@keyframes pulse {
  0% {
    transform: translateX(350px) translateY(70px) scale(1.2);
  }
  50% {
    transform: translateX(350px) translateY(70px) scale(1.4);
  }
  100% {
    transform: translateX(350px) translateY(70px) scale(1.2);
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
