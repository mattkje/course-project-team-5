<template>
  <div class="background">
    <div class="course-display">
      <img :src="currentImage" alt="Course image" v-show="currentImage" key="currentImage">
      <div class="course-display-info">
        <h1 id="currentTitle"/>
        <hr>
        <h2 id="currentDifficulty"/>
      </div>
    </div>
  </div>
  <div id="curtain" class="curtain-before">
    <img id="logo" class="logo" src="/logo.svg"/>
  </div>
</template>

<script>
import {ref, onMounted, onUnmounted} from 'vue';

export default {
  name: 'ImageSlider',
  setup() {
    const images = ref([]);
    const titles = ref([]);
    const difficulty = ref([]);
    const currentImage = ref(null);
    let intervalId = null;

    const fetchImages = async () => {
      const response = await fetch('http://localhost:8080/api/courses');
      const coursesData = await response.json();
      console.log('coursesData:', coursesData); // Debug log
      images.value = coursesData.map(courseData => courseData.course.image);
      titles.value = coursesData.map(courseData => courseData.course.title);
      difficulty.value = coursesData.map(courseData => courseData.course.level);
      console.log('images.value:', images.value); // Debug log
    };

    const selectRandomImage = () => {

      setTimeout(() => {
        if (images.value.length > 0) {
          const randomIndex = Math.floor(Math.random() * images.value.length);
          if (images.value[randomIndex] === null) {
            currentImage.value = '/noImage.svg';
          } else {
            currentImage.value = images.value[randomIndex];
          }

          document.getElementById('currentTitle').innerText = titles.value[randomIndex];
          document.getElementById('currentDifficulty').innerText = difficulty.value[randomIndex];
          console.log('currentImage.value:', currentImage.value); // Debug log
        }
        const curtain = document.getElementById('curtain')
        curtain.className = 'curtain';
        document.body.appendChild(curtain);
      }, 1000); // Delay should match the duration of the CSS animation
    };

    onMounted(async () => {
      await fetchImages();
      selectRandomImage();
      intervalId = setInterval(selectRandomImage, 5000); // Change image every 5 seconds
    });

    onUnmounted(() => {
      if (intervalId) {
        clearInterval(intervalId);
      }
    });

    return { currentImage };
  },
};
</script>


<style scoped>
.background {
  background-color: var(--light-3);
}

.course-display {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.course-display-info {
  background-color: var(--light-1);
  padding: 20px;
  height: 500px;
  width: 600px;
  margin: 0;

}

img {
  width: 100%;
  object-fit: cover;
  height: 500px;
}

.curtain-before {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  top: 71px;
  height: 500px;
  width: 100%;
  right: 0;
  background-color: var(--light-1);
}

.curtain {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  top: 71px;
  height: 500px;
  width: 100%;
  right: 0;
  background-color: var(--light-1);
  animation: slide 5s ease-in-out;
}


.logo {
  width: 200px;
  height: auto;
  position: absolute;
  margin: auto;
  animation: logofix 5s ease-in-out;
}

@keyframes slide {
  0% {
    transform: scaleX(100%);
  }
  20% {
    transform: scaleX(0);
  }
  80% {
    transform: scaleX(0);
  }
  100% {
    transform: scaleX(100%);
  }
}

@keyframes logofix {
  0% {
    transform: scaleX(100%);
  }
  20% {
    opacity: 0;
  }
  50% {
    transform: scaleX(1000%);
  }
  80% {
    opacity: 0;
  }
  100% {
    transform: scaleX(100%);
  }
}

</style>