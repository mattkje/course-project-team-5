<template>
  <div>
    <h1></h1>
    <ul>
      <li class="title" v-for="course in courses" :key="course.id">
        {{ course.name }}
      </li>
    </ul>
    <div class="flexible-grid-container">
      <div class="flexible-grid" id="courseContainer">
      </div>
    </div>
  </div>
</template>

<script setup>
import {getCurrentInstance, onMounted, ref} from 'vue';
import {currency, setDefaultCurrency} from "@/js/currency";
import {createContentBox, fetchCourses, fetchCurrencies} from "@/js/populationTools";

const courses = ref([]);
const {appContext} = getCurrentInstance();


let courseContainer;
let children;
const API_URL = appContext.config.globalProperties.$apiAddress;

onMounted(() => {
  courseContainer = document.querySelector('#courseContainer');
  children = courseContainer.children;
  populateCourses('.flexible-grid');
  currency(API_URL);
});


//Temporary function to populate courses
async function populateCourses(selector) {
  document.querySelector(selector).innerHTML = '';
  const defaultCurrency = setDefaultCurrency() || 'USD';
  try {
    const [data, currencies] = await Promise.all([fetchCourses(API_URL), fetchCurrencies(API_URL)]);

    // Limit to 3 courses for now
    const limitedData = data.slice(0, 3);

    limitedData.forEach(courseProvider => {
      const courseItem = document.createElement('div');
      courseItem.className = 'course-item';

      const img = document.createElement('img');
      img.src = courseProvider.course.picture ? `${API_URL}/${courseProvider.course.picture}` : 'default-image-url';
      img.className = 'course-img';
      courseItem.appendChild(img);

      const description = document.createElement('span');
      description.textContent = courseProvider.course.description;
      description.className = 'course-description';
      courseItem.appendChild(description);

      const price = document.createElement('span');
      price.textContent = `${courseProvider.course.price} ${defaultCurrency}`;
      price.className = 'course-price';
      courseItem.appendChild(price);

      const removeButton = document.createElement('button');
      removeButton.textContent = 'X';
      removeButton.className = 'remove-button';
      removeButton.style.color = 'red';
      removeButton.style.background = 'none';
      removeButton.style.border = 'none';
      removeButton.addEventListener('click', () => {
        courseItem.remove();
      });
      courseItem.appendChild(removeButton);

      document.querySelector(selector).appendChild(courseItem);
    });

    const checkoutButton = document.createElement('button');
    checkoutButton.textContent = 'Check out';
    checkoutButton.className = 'checkout-button';
    document.querySelector(selector).appendChild(checkoutButton);
  } catch (error) {
    console.error('Error:', error);
  }
}
</script>

<style scoped>

.title {
  border: none;
  border-radius: 15px;
  width: 60%;
  justify-content: center;
  align-items: center;
  text-decoration: none;
  margin: 0 auto;
}

.flexible-grid-container {
  background-color: var(--light-1);
  border-radius: 10px;
  display: flex;
  justify-content: flex-start;
  margin: 30px;
  padding: 5px;
  min-height: 90%;
  min-width: 90%;
}

.flexible-grid {
  background-color: var(--light-1);
  border-radius: 10px;
  padding: 5px;
  display: flex;
  flex-wrap: wrap;
  margin: 30px;
  justify-content: space-around;
}

.checkout-button {
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  color: white;
  font-size: 1.04em;
  background: #584BEB;
  background-color: grey;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70%;
  height: 50px;
  border-radius: 12px;
  cursor: pointer;
  transition: all .5s;
  margin-right: 10px;
}

.course-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid #000; /* Add this line */
  padding: 10px; /* Add this line */
  margin-bottom: 10px; /* Add this line */
}

.course-img {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.course-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-grow: 1;
  margin-left: 20px;
}

.course-description {
  flex-grow: 1;
}

.course-price {
  margin-right: auto;
}

.remove-button {
  align-self: flex-end;
  color: red;
  background: none;
  border: none;
}

</style>