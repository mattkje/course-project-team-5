<template>
  <div class="background">
    <a id="currentLink" class="course-display">
      <img :src="currentImage" alt="Course image" v-show="currentImage" key="currentImage">
      <div class="course-display-info">
        <h1 id="currentTitle"/>
        <hr>
        <h2 id="currentDifficulty"/>
      </div>
    </a>
  </div>
  <div id="curtain" class="curtain"/>
  <img class="logo" src="/logo.svg"/>
</template>

<script>
import {ref, onMounted, onUnmounted} from 'vue';

export default {
  name: 'ImageSlider',
  setup() {
    const images = ref([]);
    const titles = ref([]);
    const links = ref([]);
    const difficulty = ref([]);
    const currentImage = ref(null);
    let intervalId = null;

    const fetchImages = async () => {
      const response = await fetch('http://localhost:8080/api/courses');
      const coursesData = await response.json();
      console.log('coursesData:', coursesData); // Debug log
      images.value = coursesData.map(courseData => courseData.course.image);
      titles.value = coursesData.map(courseData => courseData.course.title);
      links.value = coursesData.map(courseData => courseData.course.courseId);
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
          document.getElementById('currentLink').href = '/courses?id=' + links.value[randomIndex];
          document.getElementById('currentDifficulty').innerText = difficulty.value[randomIndex];
          console.log('currentImage.value:', currentImage.value); // Debug log
        }

      }); // Delay should match the duration of the CSS animation
    };

    onMounted(async () => {
      await fetchImages();
      selectRandomImage();
      intervalId = setInterval(selectRandomImage, 10000);
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
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  background-color: var(--light-3);
}

.course-display {
  text-decoration: none;
  width: 1300px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin: 75px auto auto auto;
  border-radius: 20px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  &:hover {
    box-shadow: 0 4px 5px rgba(0, 0, 0, 0.33);
  }
}

.course-display-info {
  background-color: var(--light-1);
  padding: 20px;
  height: 500px;
  width: 600px;
  margin: 0;
  border-radius: 0 20px 20px 0;
}

img {
  width: 100%;
  object-fit: cover;
  height: 500px;
  border-radius: 20px 0 0 20px;
}

.curtain {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  top: 146px;
  height: 500px;
  width: 1300px;
  left: 0;
  right: 0;
  margin-left: auto;
  margin-right: auto;
  border-radius: 20px;
  background-color: #0e0e0e;
  animation: slide 10s ease-in-out infinite;
}


.logo {
  width: 200px;
  height: auto;
  position: absolute;
  top: 325px;
  left: 0;
  right: 0;
  margin-left: auto;
  margin-right: auto;
  animation: logoshow 10s ease-in-out infinite;
}


@keyframes slide {
  0% {
    transform: scaleX(100%);
  }
  5% {
    transform: scaleX(100%);
  }
  25% {
    transform: scaleX(0);
  }
  80% {
    transform: scaleX(0);
  }
  100% {
    transform: scaleX(100%);
  }
}

@keyframes logoshow {
  0% {
    opacity: 1;
  }
  15% {
    opacity: 0;
  }
  50% {
    opacity: 0;
  }
  85% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

</style>