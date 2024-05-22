<script setup>
import Alert from "@/components/Alert.vue";
import {getCurrentInstance, ref} from "vue";
import {setDefaultCurrency} from "@/js/currency";
import {fetchCourseById, fetchCurrencies} from "@/js/populationTools";

const {appContext} = getCurrentInstance();

const showAlert = ref(true);
const cardNumber = ref("");
const cardHolder = ref("");
const expiryDate = ref("");
const cvv = ref("");


const API_URL = appContext.config.globalProperties.$apiAddress;

populateCart();

const handleButtonClick = () => {
  showAlert.value = false;
};

const validateCardNumber = (number) => {
  const regex = /^\d{4} \d{4} \d{4} \d{4}(?: \d{3})?$/; // Example for 16-digit card numbers with optional 3-digit ending
  return regex.test(number);
};

const validateExpiryDate = (date) => {
  const regex = /^(0[1-9]|1[0-2])\/([0-9]{2})$/;
  if (!regex.test(date)) return false;

  const today = new Date();
  const month = parseInt(date.split('/')[0], 10);
  const year = parseInt(date.split('/')[1], 10) + 2000; // Assuming 20xx format

  if (year < today.getFullYear()) return false;
  if (year === today.getFullYear() && month < (today.getMonth() + 1)) return false;

  return true;
};

const validateCVV = (cvv) => {
  const regex = /^[0-9]{3,4}$/;
  return regex.test(cvv);
};

const formatCardNumber = (event) => {
  const input = event.target.value.replace(/\s+/g, '').replace(/[^0-9]/gi, '');
  const formatted = input.match(/.{1,4}/g)?.join(' ') ?? '';
  cardNumber.value = formatted;
};

const formatExpiryDate = (event) => {
  const input = event.target.value.replace(/\D/g, '');
  if (input.length >= 2) {
    expiryDate.value = `${input.substring(0, 2)}/${input.substring(2, 4)}`;
  } else {
    expiryDate.value = input;
  }
};

const validateForm = () => {
  if (!validateCardNumber(cardNumber.value)) {
    alert("Invalid card number");
    return false;
  }
  if (!validateExpiryDate(expiryDate.value)) {
    alert("Invalid expiry date");
    return false;
  }
  if (!validateCVV(cvv.value)) {
    alert("Invalid CVV");
    return false;
  }
  alert("Payment details are valid");
  // Further form submission logic goes here
};

async function populateCart() {
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

  for (let i = 0; i < courseIds.length; i++) {
    const courseId = courseIds[i];
    let price = prices[i];
    const course = await fetchCourseById(API_URL, courseId);
    price = price * rate;
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

async function addCourseToCartTotal(course, price, symbol) {
  const cartTotal = document.getElementsByClassName("totalItems")[0];
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
  await updateCartTotal();
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

  const totalCostElement = document.createElement("p");
  totalCostElement.innerText = "Total: " + symbol + " " + totalCost.toFixed(2);
  totalCostElement.style.fontWeight = "bold";
  totalPrice.appendChild(totalCostElement);
}

</script>

<template>
  <Alert v-show="showAlert === true" title="Disclaimer" message=" This page is for demonstration purposes only. It is not functional, and any information entered will not be processed or stored." :buttons="['Understood']" @buttonClicked="handleButtonClick"></Alert>
<div class="parent-container">
  <div class="left-content">
    <h4>LEARNIVERSE</h4>
    <div class="center-container">
      <div class="cwc-box">
        <hr>
        <p>EXPRESS CHECKOUT</p>
        <hr>
      </div>
      <div class="express-payment">
        <button style="background-color: #fd5a24; color: white">
          <img src="/pay-vipps.png"/>
        </button>
        <button style="background-color: #F3CB5E; color: white">
          <img style="margin-top: 8px" src="/pay-amazon.png"/>
        </button>
        <button style="background-color: #000000; color: white">
          <img style="filter: brightness(0) invert(1); width: 60px; margin-top: 3px" src="/pay-apple.png"/>
        </button>
        <button style="background-color: #F6B61D; color: white">
          <img src="/pay-paypal.png"/>
        </button>
      </div>
      <div class="cwc-box">
        <hr>
        <p>OR CONTINUE BELOW TO PAY WITH CARD</p>
        <hr>
      </div>
      <h2>Payment Details</h2>
      <form @submit.prevent="validateForm">
        <div class="form-group">
          <label for="cardNumber">Card Number</label>
          <input type="text" v-model="cardNumber" id="cardNumber" name="cardNumber" maxlength="19" placeholder="1234 5678 9012 3456" @input="formatCardNumber" required>
        </div>
        <div class="form-group">
          <label for="cardHolder">Cardholder Name</label>
          <input type="text" v-model="cardHolder" id="cardHolder" name="cardHolder" placeholder="John Doe" required>
        </div>
        <div class="edandcvv">
          <div class="form-group">
            <label for="expiryDate">Expiry Date</label>
            <input type="text" v-model="expiryDate" id="expiryDate" name="expiryDate" placeholder="MM/YY" maxlength="5" @input="formatExpiryDate" required>
          </div>
          <div class="form-group">
            <label for="cvv">CVV</label>
            <input type="text" v-model="cvv" id="cvv" name="cvv" maxlength="4" placeholder="123" required>
          </div>
        </div>
        <button class="pay-button" type="submit">Pay Now</button>
      </form>
    </div>
  </div>
  <div class="right-content">
    <p>cart items</p>
    <p class="totalItems"></p>
    <p class="totalPrice"></p>
  </div>
</div>
</template>

<style scoped>

h4 {
  margin: 50px 0 20px 0;
  font-size: 50px;
  color: black;
}

h5 {
  background-color: black;
  color: white;
  padding: 10px;
  border-radius: 15px;
  margin: 30px 0;
}

.parent-container {
  background-color: var(--light-3);
  margin: 0;
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}

.left-content {
  display: flex;
  flex-direction: column;
  width: 1000px;
  margin: auto;
}

.right-content {
  display: flex;
  flex-direction: row;
  background-color: white;
  width: 600px;
}

.express-payment {
  display: flex;
  margin: 40px auto;
  padding: 20px;
  border: 2px solid var(--light-2);
  border-radius: 20px;
}

.express-payment button {
  align-content: center;
  font-weight: bold;
}

.center-container {

}

.cwc-box {
  display: flex;
  align-items: center;
  margin: 20px 0;
  width: 100%;
}

.cwc-box hr {
  flex-grow: 1;
  border: none;
  border-top: 1px solid #ccc;
  margin: 0 10px;
}

.cwc-box p {
  margin: 0;
  white-space: nowrap;
}
.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #333;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 12px;
  background: #000000;
  border: none;
  height: 60px;
  margin: 0 10px;
  color: #fff;
  font-size: 16px;
  border-radius: 20px;
  cursor: pointer;
  &:hover {
    transform: scale(1.02);
    transition-duration: .5s;
  }
  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }

}

.pay-button {
  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: white;
  font-size: 20px;
  background: #000000;
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

.pay-button:hover {
  transform: scale(1.02);
  transition-duration: .5s;
}

.pay-button:active {
  transform: scale(0.95);
  transition-duration: .5s;
}

input[type="text"],
textarea {
  font-family: Inter, sans-serif;
  width: 100%;
  height: 60px;
  padding: 10px;
  border: 2px solid var(--light-2);
  border-radius: 15px;
  font-size: 16px;
}

.edandcvv {
  display: flex;
  width: 100%;
  justify-content: space-between;
}

.edandcvv input[type="text"] {
  width: 480px;
  margin: 0;
}

.express-payment img {
  width: 50%;
  margin: auto;
}
</style>