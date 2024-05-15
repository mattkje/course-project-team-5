<template>
  <div class="background">
    <transition name="slide">
      <div class="course-display">
        <img :src="currentImage" alt="Course image" v-show="currentImage" key="currentImage">
        <div class="course-display-info">
          <h1 id="currentTitle"/>
          <hr>
          <p>$100 </p>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import {ref, onMounted, onUnmounted} from 'vue';

export default {
  name: 'ImageSlider',
  setup() {
    const images = ref([]);
    const titles = ref([]);
    const currentImage = ref(null);
    let intervalId = null;

    const fetchImages = async () => {
      const response = await fetch('http://localhost:8080/api/courses');
      const coursesData = await response.json();
      console.log('coursesData:', coursesData); // Debug log
      images.value = coursesData.map(courseData => courseData.course.image);
      titles.value = coursesData.map(courseData => courseData.course.title);
      console.log('images.value:', images.value); // Debug log
    };

    const selectRandomImage = () => {
      if (images.value.length > 0) {
        const randomIndex = Math.floor(Math.random() * images.value.length);
        if (images.value[randomIndex] === null) {
          currentImage.value = 'https://raw.githubusercontent.com/mattkje/course-project-team-5/221e131a84afbf8c00f93295b720fb25071db6b0/frontend/public/noImage.svg?token=AJS2STSZ5A3GUGWLM7HQI43GISG4A';
        } else {
          currentImage.value = images.value[randomIndex];
        }

        document.getElementById('currentTitle').innerText = titles.value[randomIndex];
        console.log('currentImage.value:', currentImage.value); // Debug log
      }
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
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.course-display-info {
  background-color: var(--light-1);
  padding: 20px;
  height: 40vh;
  width: 600px;
  margin: 0;

}

img {
  width: 100%;
  object-fit: cover;
  height: 40vh;
}

/* Add these styles for the transition */
.slide-enter-active, .slide-leave-active {
  transition: transform 1s;
}

.slide-enter, .slide-leave-to {
  transform: translateX(100%);
}
</style>