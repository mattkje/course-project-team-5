<script setup>
import {getCurrentInstance, onMounted, ref} from 'vue';
import {doLogout, getAuthenticatedUser, hasRole, removeRole} from "@/js/authentication";
import {sendApiRequest, sendTokenRefreshRequest} from "@/js/requests";
import {createContentBox, fetchCourseById, fetchCourses, fetchCurrencies, fetchProviders} from "@/js/populationTools";
import {getCookie, isTokenAboutToExpire} from "@/js/tools";
import {currency, setDefaultCurrency} from "@/js/currency";

let price = 0;
let data = 0;

const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;


//Cookie test
async function applyCoupon() {
  getCookie('courseProviderId');
  getCookie('courseId');
  console.log("Applying coupon");
  console.log(document.cookie);
}

async function loadShoppingCart() {
  const user = getAuthenticatedUser();
  if (user) {
    await sendApiRequest(API_URL,"GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
  }
}


function onProfileDataError() {
  console.log("Error fetching user data");
}

onMounted(async () => {
  console.log("hei");
  populateCart();
  const user = getAuthenticatedUser();
  if (user) {
    await sendApiRequest(API_URL,"GET", "/users/" + user.username, onProfileDataSuccess, onProfileDataError);
  }
});

function onProfileDataSuccess(data) {
  if (data.courses.length > 0) {
    console.log(data.courses.length);
  } else {

  }
}

async function populateCart() {
  const allCookies = document.cookie;
  const { courseIds, providerIds } = getCourseAndProviderIds(allCookies);
  const defaultCurrency = setDefaultCurrency() || 'NOK';
  console.log(defaultCurrency + "hehehe");

  const courseList = document.getElementsByClassName("course-table")[0];
  clearCart(courseList);

  if (courseIds.length === 0) {
    displayEmptyCartMessage(courseList);
    return;
  }


  for (let i = 0; i < courseIds.length; i++) {
    const courseId = courseIds[i];
    const providerId = providerIds[i];

    const course = await fetchCourseById(API_URL, courseId);;
    let provider = course.providers;
    let name = "";
    provider.forEach(prov => {
      if (Number(prov.courseProviderId) === Number(providerId)) {
        console.log("hehehe" + prov.name);
        name = prov.name;
      }
      console.log(name);
    });

    const providers = course.providers;
    
    let finalPrice = getFinalPrice(course.providers, providerId);
    console.log(finalPrice);
    addCourseToCart(courseList, course, finalPrice, courseId, name);
  }
}

function getCourseAndProviderIds(allCookies) {
  const cookieArray = allCookies.split('; ');
  let courseIds = [];
  let providerIds = [];

  cookieArray.forEach(cookieStr => {
    const [name, value] = cookieStr.split('=');
    if (name.startsWith('courseId_')) {
      courseIds.push(value);
    }
    if (name.startsWith('providerId_')) {
      providerIds.push(value);
    }
  });

  console.log(courseIds);
  return { courseIds, providerIds };
}

function clearCart(courseList) {
  if (courseList.children.length > 0) {
    courseList.removeChild(courseList.children[0]);
  }
}

function displayEmptyCartMessage(courseList) {
  const emptyCartMessage = document.createElement("h2");
  emptyCartMessage.innerText = "Your cart is empty";
  courseList.appendChild(emptyCartMessage);
}

function getFinalPrice(providers, providerId) {
  let finalPrice = 0;
  providers.forEach(provider => {
    if (Number(provider.courseProviderId) === Number(providerId)) {
      finalPrice = provider.price;
    }
  });
  return finalPrice;
}
function addCourseToCart(courseList, course, finalPrice, courseId, name) {
  const courseBody = document.createElement("tbody");
  courseBody.classList.add("course-block");
  const line = document.createElement("hr");
  courseBody.appendChild(line);
  courseList.appendChild(courseBody);

  const row = document.createElement("tr");
  row.classList.add("course-card");
  row.style.cursor = "pointer";

  const courseName = document.createElement("p");
  courseName.innerText = course.course.title;
  courseName.style.paddingLeft = "20px";

  const providerName = document.createElement("p");
  providerName.innerText = name;
  providerName.style.paddingLeft = "20px";

  const coursePrice = document.createElement("p");
  coursePrice.innerText = finalPrice;
  coursePrice.style.paddingLeft = "100px";

  const courseImg = document.createElement("img");
  courseImg.classList.add("course-image");
  courseImg.src = course.course.image || '/noImageCom.svg';



  row.appendChild(courseImg);
  row.appendChild(courseName);
  row.appendChild(providerName);
  row.appendChild(coursePrice);
  courseBody.appendChild(row);

  editCourseCard(row, course);

  const removeButton = createRemoveButton(courseId, courseBody);
  courseBody.appendChild(removeButton);
}

function createRemoveButton(courseId, courseBody) {
  const removeButton = document.createElement("button");
  removeButton.innerText = "Remove";
  removeButton.onclick = function() {
    document.cookie = 'courseId_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    document.cookie = 'providerId_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    courseBody.remove();
  };
  return removeButton;
}

function editCourseCard(object, course) {
  object.style.minWidth = "500px";
  object.style.minHeight = "50px";
  object.onclick = function () {
    window.location.href = ("/courses/?id=" + course.course.courseId);
  };


}

function onFailure() {
  console.log("Failed to fetch courses");
}

function showPrice(data) {
  console.log(data);
  return data;
}



</script>

<template>
    <h1></h1>
    <ul>
      <li class="title" v-for="course in courses" :key="course.id">
        {{ course.name }}
      </li>
    </ul>
  <div class="parent-container">
    <div class="left-items">
      <div class="title">
        <h1>Cart</h1>
        <p class="itemCountText"> {{ cartText }}</p>
      </div>
      <div class="flexible-grid-container">
        <div class="cart-label-box">
          <p>Course</p>
          <p>Price</p>
        </div>
        <div class="course-table">
          <h2>Your cart is empty</h2>
        </div>
      </div>
    </div>
    <div class="flex-couponCart-container">
      <div class="couponContainer">
        <h1>Coupon Code</h1>
        <p>You can apply a coupon code to get a discount on your purchase.</p>
        <div class="options">
          <input type="text" placeholder="Enter coupon code here"/>
          <br>
          <button @click="applyCoupon">Apply</button>
        </div>

      </div>
      <div class="couponContainer">
        <h1>Cart Total</h1>
        <p>Here is the total cost of your cart.</p>
        <div class="totalAmount">{{ totalItems }}</div>
      </div>
      <button class="checkout-button">Checkout</button>
  </div>
  </div>
</template>

<style scoped>

@media (max-width: 769px) {
  .parent-container {
    margin: 0;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
  }

  .flexible-grid-container {
    background-color: var(--light-1);
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: 50px auto;
    padding: 5px;
    height: 50%;
    width: 400px;
    align-items: center;
  }

  .left-items {
    width: 400px;
    margin: 0 auto;
  }

  .flex-couponCart-container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin: 0;
    width: 400px;
    align-items: center;
  }
}

@media (min-width: 769px) {
  .parent-container {
    margin: 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .flexible-grid-container {
    background-color: var(--light-1);
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: 50px auto;
    padding: 5px;
    height: 50%;
    width: 100%;
    align-items: center;
  }

  .left-items {
    height: 93vh;
    width: 1000px;
    margin: 0 auto;
  }

  .flex-couponCart-container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    background-color: var(--light-1);
    border-radius: 10px;
    margin: 0;
    padding: 0 50px;
    height: 93vh;
    width: 500px;
    align-items: center;
  }
}

.title {
  text-decoration: none;
  margin: 50px auto 0 auto;
  width: 100%;
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
  justify-content: flex-end;
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
  border: none;
  border-radius: 20px;
  background-color: #000000;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  text-align: center;
  margin: 30px auto 0 auto;
  height: 40%;
  width: 100%;
}


.couponContainer h1 {
  color: white;
  font-size: 30px;
  text-align: left;
}

.couponContainer p {
  font-size: 16px;
  color: var(--light-2);
  text-align: left;
}

.couponContainer button {

  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: #000000;
  font-size: 20px;
  background: #ffffff;
  border: none;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 70px;
  border-radius: 15px;
  cursor: pointer;
  margin-top: 10px;
  transition: all .5s;

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}


.couponContainer input {
  font-size: 17px;
  font-weight: bold;
  margin-top: 10px;
  padding: 10px 20px;
  width: 100%;
  height: 70px;
  border-radius: 15px;
  border: none;
}

.cartTotalContainer .totalAmount {
  margin-top: 10px;
  padding: 10px 20px;
  width: 100%;
  border-radius: 10px;
}


.options{
  width: 100%;
}



.checkout-button {
  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: white;
  font-size: 20px;
  background: #5649e7;
  border: none;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 80px;
  border-radius: 20px;
  cursor: pointer;
  margin: 30px auto;
  transition: all .5s;
}

.checkout-button:hover {
  background-color: #000000;
}

.checkout-button:active {
  transform: scale(0.95);
  transition-duration: .5s;
}

.cart-label-box {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
}

.cart-label-box p{
  font-family: Inter, sans-serif;
  font-weight: bold;
  font-size: 20px;
  margin: auto;
}
</style>