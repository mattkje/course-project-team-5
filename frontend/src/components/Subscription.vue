<template>
  <meta name="description" content="Prices of different subscription possibilities for Learniverse users">
  <div class="membership-wrap">
    <div class="learniverse-membership">
      <h3>Learniverse Pro Membership</h3>
      <p class="message">Learniverse Pro courses are not hosted by Learniverse Connect, but with close partners borrowing our brand</p>
      <div class="membership-plans">
        <div class="list-of-memberships">
          <div class="list-of-prices">
            <div class="ind-membership">
              <div class="ind-membership-purchase">
                <div class="ind-membership-purchase-col">
                  <div class="ind-membership-purchase-name">1 month (30 days)</div>
                  <div class="ind-membership-purchase-price">
                    <span class="ind-membership-purchase-price-value" id="monthly">10 USD</span>
                  </div>
                </div>
                <div class="ind-membership-purchase-button">
                  <button class="purchase-button" id="monthly-purchase-button" @click="add1MonthSubscription">
                    <span>Purchase Now</span>
                  </button>
                </div>
              </div>
              <div class="ind-membership-purchase">
                <div class="ind-membership-purchase-col">
                  <div class="ind-membership-purchase-name">3 months (90 days)</div>
                  <div class="ind-membership-purchase-price">
                    <span class="ind-membership-purchase-price-value" id="quarterly">25 USD</span>
                  </div>
                </div>
                <div class="ind-membership-purchase-button">
                  <button class="purchase-button" @click="add3MonthsSubscription">
                    <span>Purchase Now</span>
                  </button>
                  <div class="monthly-price-notice">
                    <p class="monthly-price-notice__monthly-price">
                      <span id ="quarterly-equivalent">Equivalent to: 26,00 kr/month</span>
                    </p>
                  </div>
                </div>
              </div>
              <div class="ind-membership-purchase" id="bottom-ind-membership-purchase">
                <div class="ind-membership-purchase-col">
                  <div class="ind-membership-purchase-name">12 months (365 days)</div>
                  <div class="ind-membership-purchase-price">
                    <span class="ind-membership-purchase-price-value" id="yearly">100 USD</span>
                  </div>
                </div>
                <div class="ind-membership-purchase-button">
                  <button class="purchase-button" @click="add12MonthsSubscription">
                    <span>Purchase Now</span>
                  </button>
                  <div class="monthly-price-notice">
                    <p class="monthly-price-notice__monthly-price">
                      <span id="yearly-equaivalent">Equivalent to: 16,00 kr/month</span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {sendApiRequest} from "@/js/requests";
import {addRole, getAuthenticatedUser} from "@/js/authentication";
import Alert from "@/components/Alert.vue";
import {getCurrentInstance, defineEmits, onMounted} from "vue";
import {currency, setDefaultCurrency} from "@/js/currency";

const emit = defineEmits(['show-alert']);
const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;


onMounted(() => {
  document.title = 'Learniverse - Subscription';
  getCurrencyRates();
});

async function getCurrencyRates() {
  const defaultCurrency = setDefaultCurrency() || 'USD'
  console.log(defaultCurrency)
  await sendApiRequest(API_URL, 'GET', '/currency', (response) => {
    console.log(response)
    response.forEach(currency => {
      if (currency.code == defaultCurrency) {
        document.getElementById('monthly').innerText = (10 * currency.rate).toFixed(0 ) + ' ' + currency.code;
        document.getElementById('quarterly').innerText = (25 * currency.rate).toFixed(0 ) + ' ' + currency.code;
        document.getElementById('quarterly-equivalent').innerText = ('Equivalent to: ' + (25 * currency.rate / 3).toFixed(0) + ' ' + currency.code + '/month')
        document.getElementById('yearly').innerText = (100 * currency.rate).toFixed(0 ) + ' ' + currency.code;
        document.getElementById('yearly-equaivalent').innerText = ('Equivalent to: ' + (100 * currency.rate / 12).toFixed(0) + ' ' + currency.code + '/month')
      }
    });
  }, (error) => {
    console.log(error);
  });
}

async function add1MonthSubscription() {
  if(getAuthenticatedUser()) await sendApiRequest(API_URL,'PUT', '/users/purchase-pro/1-month', successfulExecution, error);
  else emit('show-alert');
}

async function add3MonthsSubscription() {
  if(getAuthenticatedUser()) await sendApiRequest(API_URL,'PUT', '/users/purchase-pro/3-months', successfulExecution, error);
  else emit('show-alert');
}

async function add12MonthsSubscription() {
  if(getAuthenticatedUser()) await sendApiRequest(API_URL,'PUT', '/users/purchase-pro/12-months', successfulExecution, error);
  else emit('show-alert');
}

function successfulExecution() {
  addRole("ROLE_PRO");
  alert('Subscription purchased successfully!');
  window.location.reload();
}

function error() {
  alert('An error occurred while purchasing the subscription. Please try again later.');
}
</script>
<style scoped>
@media (max-width: 1201px) {

  .membership-wrap {
    border-radius: 5px;
  }

  .ind-membership-purchase {
    flex-direction: column !important;
    padding-bottom: 32px !important;
  }

  .ind-membership-purchase-col {
    padding-right: 0;
    text-align: center;
  }
  .purchase-button {
    border-radius: 15px 15px 0 0 !important;
  }
  #monthly-purchase-button {
    border-radius: 15px !important;
    min-width: 188px;
  }
  .monthly-price-notice {
    border-radius: 0 0 15px 15px;
    margin-top: 8px;
  }

  .learniverse-membership h3 {
    border-radius: 10px 10px 0 0;
  }
}

.background h2 {
  color: whitesmoke;
  font-size: 40px;
  margin: 0;
  padding-bottom: 30px;
  animation: ease-in-out fadeIn 1.5s;
}

.message {
  margin: 0 0 0 30px;
}

.course-card img {
  width: 100%;
  border-radius: 15px;
}

.course-card h3 {
  font-size: 20px;
  margin: 10px 0;
}

.course-card p {
  color: #606060;
  font-size: 16px;
  margin: 10px 0;
}


@keyframes pulse {
  0% {
    transform: scale(0.5);
  }
  50% {
    transform: scale(0.6);
  }
  100% {
    transform: scale(0.5);
  }
}


@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.learniverse-membership {
  margin-top: 48px;
  margin-bottom: 16px;
  background: #f9f9f9;
  border-radius: 20px;
}

.learniverse-membership h3 {
  padding: 16px 56px;
  font-size: 1.5rem;
  text-align: left;
  font-weight: 500;
  background-color: hsl(44, 93%, 66%);
}

.membership-wrap {
  margin: 0 auto;
  width: 93%;
  max-width: 900px;
  box-sizing: border-box;
  line-height: 1.5;
  border-radius: 10px ;
}

.membership-plans {
  box-shadow: inset 0 0 20px 0 rgba(0, 0, 0, 0.075);
  border-radius: 0 0 20px 20px;
}

.ind-membership {
  margin-top: auto;
}

.ind-membership-purchase {
  font-size: 1rem;
  margin: 0 auto;
  width: 100%;
  max-width: 900px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 7% 16px 7%;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  -webkit-box-pack: justify;
  -webkit-box-align: center;
  flex-flow: row wrap;
  border-bottom: 1px solid hsla(0, 0%, 45.1%, 0.2);
}

.ind-membership-purchase-col {
  padding-right: 16px;
}

.ind-membership-purchase-name {
  margin-bottom: 8px;
  text-align: left;
  font-size: 1rem;
}

.ind-membership-purchase-price-value {
  font-weight: 700;
  font-size: 1.2rem;
  white-space: nowrap;
  text-align: left;
}

.ind-membership-purchase-button {
  -webkit-box-ordinal-group: 2;
  order: 1;
  text-align: right;
}

.purchase-button {
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  color: #000000;
  font-size: 20px;
  background: #ffd042;
  border: none;
  width: 100%;
  max-width: 260px;
  height: 44px;
  cursor: pointer;
  margin-top: 10px;
  transition: all .5s;

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}
#monthly-purchase-button {
  min-width: 188px;
}


.monthly-price-notice {

  background-color: #f0f0f0;
  padding: 8px 16px;
  font-size: 0.7rem;
  border-radius: 0 0 10px 10px;
}

.monthly-price-notice__monthly-price {
  margin-bottom: 0;
}

#bottom-ind-membership-purchase {
  border-bottom: none;
}
</style>