<template>
    <h1></h1>
    <ul>
      <li class="title" v-for="course in courses" :key="course.id">
        {{ course.name }}
      </li>
    </ul>
  <div class="parent-container">
    <div class="flexible-grid-container">
      <div class="course-table">
        <h1>hei</h1>
        <div class="courseContainer"></div>
        <div class="cartTotal"></div>
      </div>
    </div>
    <div class="flex-couponCart-container">
      <div class="couponContainer">
        <h2>Coupon Code</h2>
        <p>You can apply a coupon code to get a discount on your purchase.</p>
        <input type="text" placeholder="Enter coupon code here"/>
        <br>
        <button @click="applyCoupon">Apply</button>
      </div>
      <div class="cartTotalContainer">
        <h2>Cart Total</h2>
        <p>Here is the total cost of your cart.</p>
        <div class="totalAmount">{{ totalItems }}</div>
      </div>
  </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {doLogout, getAuthenticatedUser, hasRole, removeRole} from "@/js/authentication";
import {sendApiRequest, sendTokenRefreshRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";
import {getCookie, isTokenAboutToExpire} from "@/js/tools";


//Cookie test
function applyCoupon() {
  getCookie('CourseId');
  console.log("Applying coupon");
  console.log(document.cookie);
}

async function loadShoppingCart() {
  const user = getAuthenticatedUser();
  if (user) {
    await sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
  }
}


function onProfileDataError() {
  console.log("Error fetching user data");
}

onMounted(async () => {
  const user = getAuthenticatedUser();
  if (user) {
    await sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
  }
});

function onProfileDataSuccess(data) {
  if (data.courses.length > 0) {
    addCourses(data.courses);
  }
}


//Temporary function to add courses to the shopping cart
function addCourses(courses) {
  let courseProviderId = getCookie('courseProviderId');
  const courseList = document.getElementsByClassName("course-table")[0];
  courseList.children[0].remove();
  const courseBody = document.createElement("tbody");
  courseBody.classList.add("course-block");
  const line = document.createElement("hr");
  line.style.maxWidth = "600px";
  line.style.margin = "20px";
  courseBody.appendChild(line);
  courseList.appendChild(courseBody);
  console.log(courses);
  for (const course of courses) {
    if (course.courseProviderId === courseProviderId) {
      const row = document.createElement("tr");
      const courseName = document.createElement("p");
      const coursePrice = document.createElement("p");
      const courseImg = document.createElement("img");
      courseImg.classList.add("course-image");
      row.classList.add("course-card");
      row.style.cursor = "pointer";
      courseName.innerText = course.course.title;
      coursePrice.innerText = course.course.price;
      courseName.style.paddingLeft = "20px";
      courseImg.src = course.course.image || '/noImageCom.svg';
      row.appendChild(courseImg);
      row.appendChild(courseName);
      row.appendChild(coursePrice);
      editCourseCard(row, course);
      courseBody.appendChild(row);
      const line = document.createElement("hr");
      line.style.maxWidth = "600px";
      line.style.margin = "20px";
      courseBody.appendChild(line);
      console.log(courses.length);
    }
  }
}



function editCourseCard(object, course) {
  object.style.minWidth = "500px";
  object.style.minHeight = "50px";
  object.onclick = function () {
    redirectTo("/courses/?id=" + course.course.courseId);
  };


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
  min-width: 50%;
  align-items: center;
}

.flex-couponCart-container {
  display: flex;
  flex-direction: column;
  background-color: DodgerBlue;
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
  border: 1px solid #000;
  padding: 10px;
  margin-bottom: 10px;
}

.course-table {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 70%;
}

.course-card {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 40px;
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

.course-name {
  margin-right: 20px;
}

.remove-button {
  align-self: flex-end;
  color: red;
  background: none;
  border: none;
}

.couponContainer {
  border: 1px solid black;
  border-radius: 10px;
  background-color: #656565;
  padding: 20px;
  margin-left: 20px;
  margin-bottom: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 50%;
}

.cartTotalContainer {
  border: 1px solid black;
  border-radius: 10px;
  background-color: #656565;
  padding: 20px;
  margin-left: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 50%;
}



.couponContainer button {
  background-color: black;
  color: white;
  padding: 10px 20px;
  margin-top: 10px;
  cursor: pointer;
  width: 100%;
  border-radius: 10px;
}

.couponContainer input {
  margin-top: 10px;
  padding: 10px 20px;
  width: 100%;
  border-radius: 10px;
}

.cartTotalContainer {
  border: 1px solid black;
  border-radius: 10px;
  background-color: #656565;
  padding: 20px;
  margin-left: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.cartTotalContainer .totalAmount {
  margin-top: 10px;
  padding: 10px 20px;
  width: 100%;
  border-radius: 10px;
}

.parent-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.flex-couponCart-container {
  background-color: var(--light-1);
  border-radius: 10px;
  display: flex;
  justify-content: flex-start;
  margin: 30px;
  padding: 5px;
  min-height: 90%;
  min-width: 20%;
  align-items: center;
}

</style>