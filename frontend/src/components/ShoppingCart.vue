<script setup>
import {getCurrentInstance, onMounted} from 'vue';
import {fetchCourseById, fetchCurrencies} from "@/js/populationTools";
import {getCookie, setCookie} from "@/js/tools";
import {setDefaultCurrency} from "@/js/currency";

let price = 0;
let data = 0;

const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;


async function applyCoupon() {
  const couponCode = document.getElementsByClassName("couponText")[0].value;

  if (!couponCode || !couponCode.trim()) {
    alert("Please enter a valid coupon code");
    return;
  } else {
    setCookie("coupon", couponCode, 1)
    alert("Coupon code (" + couponCode.toUpperCase() +") applied successfully! You have received a 20% discount on your purchase.")
  }
  updateCartTotal();
}

onMounted(async () => {
  populateCart();
});


async function populateCart() {
  const allCookies = document.cookie;
  const { courseIds, providerIds, prices } = getCourseAndProviderIds(allCookies);
  const defaultCurrency = setDefaultCurrency() || 'USD';
  const currencies = await fetchCurrencies(API_URL);
  const cartTotal = document.getElementsByClassName("cartTotal")[0];
  cartTotal.innerText = "";

  let symbol = '';
  let rate = 0;

  for (let i = 0; i < currencies.length; i++) {
    if (currencies[i].code === defaultCurrency) {
      symbol = currencies[i].symbol;
      rate = currencies[i].rate;
      break;
    }
  }

  const courseList = document.getElementsByClassName("course-table")[0];
  clearCart(courseList);

  if (courseIds.length === 0) {
    displayEmptyCartMessage(courseList);
    return;
  }


  for (let i = 0; i < courseIds.length; i++) {
    const courseId = courseIds[i];
    const providerId = providerIds[i];
    let price = prices[i];



    const course = await fetchCourseById(API_URL, courseId);
    let provider = course.providers;
    let name = "";
    provider.forEach(prov => {
      if (Number(prov.courseProviderId) === Number(providerId)) {
        name = prov.name;
      }
    });

    price = price * rate;
    addCourseToCart(courseList, course, price, courseId, name, symbol);
    addCourseToCartTotal(course, price, symbol);
  }
}

function getCourseAndProviderIds(allCookies) {
  const cookieArray = allCookies.split('; ');
  let courseIds = [];
  let providerIds = [];
  let prices = [];

  cookieArray.forEach(cookieStr => {
    const [name, value] = cookieStr.split('=');
    if (name.startsWith('courseId_')) {
      courseIds.push(value);
    }
    if (name.startsWith('providerId_')) {
      providerIds.push(value);
    }
    if (name.startsWith('price_')) {
      prices.push(parseFloat(value));
    }
  });

  return { courseIds, providerIds, prices };
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

async function addCourseToCartTotal(course, price, symbol) {
  const cartTotal = document.getElementsByClassName("cartTotal")[0];
  const courseInfo = document.createElement("p");
  courseInfo.innerText = course.course.title + ": " + symbol + " " + price.toFixed(2);
  cartTotal.appendChild(courseInfo);

  const defaultCurrency = setDefaultCurrency() || 'USD';
  const currencies = await fetchCurrencies(API_URL);
  let rate = 0;

  for (let i = 0; i < currencies.length; i++) {
    if (currencies[i].code === defaultCurrency) {
      rate = currencies[i].rate;
      break;
    }
  }
}


async function addCourseToCart(courseList, course, price, courseId, name, symbol) {
  const courseBody = document.createElement("tbody");
  courseBody.classList.add("course-block-cart");

  courseList.appendChild(courseBody);

  const row = document.createElement("tr");
  row.classList.add("course-card-cart");
  row.style.cursor = "pointer";

  const courseName = document.createElement("h3");
  courseName.innerText = course.course.title;

  const providerName = document.createElement("p");
  providerName.innerText = 'Provider: ' + name;

  const leftInfo = document.createElement("div")
  leftInfo.className = "left-cart-info";
  leftInfo.append(courseName, providerName);



  const coursePrice = document.createElement("p");
  coursePrice.innerText = symbol + " " + price.toFixed(2);
  coursePrice.className = "cart-item-price";

  const courseImg = document.createElement("img");
  courseImg.classList.add("course-image");
  courseImg.src = course.course.image || '/noImageCom.svg';

  const leftSection = document.createElement("div")
  leftSection.className = "left-cart-section";
  leftSection.append(courseImg, leftInfo);



  const removeButton = createRemoveButton(courseId, courseBody);
  removeButton.className = "cart-item-remove";

  const rightSection = document.createElement("div")
  rightSection.className = "right-cart-section";
  rightSection.append(coursePrice, removeButton);

  row.appendChild(leftSection);
  courseBody.appendChild(row);
  courseBody.append(rightSection)

  editCourseCard(row, course);

  await updateCartTotal();
}

function createRemoveButton(courseId, courseBody) {
  const removeButton = document.createElement("button");
  removeButton.innerText = "Remove";
  removeButton.onclick = async function() {
    document.cookie = 'courseId_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    document.cookie = 'providerId_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    document.cookie = 'price_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    courseBody.remove();

    const allCookies = document.cookie;
    const { courseIds } = getCourseAndProviderIds(allCookies);
    if (courseIds.length === 0) {
      const courseList = document.getElementsByClassName("course-table")[0];
      displayEmptyCartMessage(courseList);
    }
    await updateCourseToCartTotal();
    await updateCartTotal();
  };
  return removeButton;
}

async function updateCartTotal() {
  const totalPrice = document.getElementsByClassName("totalPrice")[0];
  while (totalPrice.firstChild) {
    totalPrice.removeChild(totalPrice.firstChild);
  }

  const allCookies = document.cookie;
  const { prices } = getCourseAndProviderIds(allCookies);
  const defaultCurrency = setDefaultCurrency() || 'USD';
  const currencies = await fetchCurrencies(API_URL);
  let symbol = '';
  let rate = 0;

  for (let i = 0; i < currencies.length; i++) {
    if (currencies[i].code === defaultCurrency) {
      symbol = currencies[i].symbol;
      rate = currencies[i].rate;
      break;
    }
  }

  let totalCost = 0;

  for (let i = 0; i < prices.length; i++) {
    let price = prices[i] * rate;
    totalCost += price;
  }

  const appliedCouponCode = getCookie("coupon");

  if (appliedCouponCode !== null) {
    totalCost *= 0.8;
  }

  const couponCodeAppliedMessage = document.createElement("p");
  couponCodeAppliedMessage.innerText = "Coupon applied: " + appliedCouponCode.toUpperCase() + " (20% off)";
  totalPrice.appendChild(couponCodeAppliedMessage);


  const totalCostElement = document.createElement("p");
  totalCostElement.innerText = "Total: " + symbol + " " + totalCost.toFixed(2);
  totalPrice.appendChild(totalCostElement);
}

async function updateCourseToCartTotal() {
  const cartTotal = document.getElementsByClassName("cartTotal")[0];

  cartTotal.innerText = "";

  while (cartTotal.firstChild) {
    cartTotal.removeChild(cartTotal.firstChild);
  }

  const allCookies = document.cookie;
  const { courseIds, providerIds, prices } = getCourseAndProviderIds(allCookies);

  const defaultCurrency = setDefaultCurrency() || 'USD';
  const currencies = await fetchCurrencies(API_URL);

  let symbol = '';
  let rate = 0;

  for (let i = 0; i < currencies.length; i++) {
    if (currencies[i].code === defaultCurrency) {
      symbol = currencies[i].symbol;
      rate = currencies[i].rate;
      break;
    }
  }

  let totalCost = 0;

  for (let i = 0; i < courseIds.length; i++) {
    const courseId = courseIds[i];
    const providerId = providerIds[i];
    let price = prices[i] * rate;

    const course = await fetchCourseById(API_URL, courseId);
    let provider = course.providers;
    let name = "";
    provider.forEach(prov => {
      if (Number(prov.courseProviderId) === Number(providerId)) {
        name = prov.name;
      }
    });

    const courseInfo = document.createElement("p");
    courseInfo.innerText = course.course.title + ": " + symbol + " " + price.toFixed(2);
    cartTotal.appendChild(courseInfo);

    totalCost += price;
  }
}

function editCourseCard(object, course) {
  object.style.minWidth = "500px";
  object.style.minHeight = "50px";
  object.onclick = function () {
    window.location.href = ("/courses/?id=" + course.course.courseId);
  };
}
</script>

<template>
    <h1>
    </h1>
  <div class="parent-container">
    <div class="title">
      <h1>Cart</h1>
    </div>
    <div class="center-container">
      <div class="left-items">
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
            <input type="text" class="couponText" placeholder="Enter coupon code here"/>
            <br>
            <button @click="applyCoupon">Apply</button>
          </div>

        </div>
        <div class="couponContainer">
          <h1>Cart Total</h1>
          <p class="cartTotal"></p>
          <p class="totalPrice"></p>
          <div class="totalAmount"></div>
        </div>
        <router-link to="/payment" class="checkout-button">Checkout</router-link>
      </div>
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
    height: max-content;
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
    flex-direction: column;
    height: 100%;
    justify-content: center;
    align-items: center;
  }

  .flexible-grid-container {
    background-color: var(--light-1);
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: 0;
    padding: 5px;
    width: 1000px;
    align-items: center;
  }

  .center-container {
    display: flex;
    justify-content: center;
    align-content: center;
    margin: auto;
    height: 100%;
  }

  .left-items {
    height: 100%;
    width: 1000px;
    margin: 30px 10px;
    display: flex;
    flex-direction: column;
  }

  .flex-couponCart-container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    background-color: var(--light-1);
    border-radius: 20px;
    margin: 30px 10px;
    padding: 0 50px;
    height: 100%;
    width: 500px;
    align-items: center;
  }

}

.title {
  display: flex;
  justify-content: center;
  text-decoration: none;
  margin: 20px 1000px auto 0;
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
  text-decoration: none;
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
  width: 90%;
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