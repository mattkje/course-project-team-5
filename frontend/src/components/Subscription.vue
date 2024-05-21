<template>
  <div class="membership-wrap">
    <div class="learniverse-membership">
      <h3>Learniverse Pro Membership</h3>
      <div class="membership-plans">
        <div class="list-of-memberships">
          <div class="list-of-prices">
            <div class="ind-membership">
              <div class="ind-membership-purchase">
                <div class="ind-membership-purchase-col">
                  <div class="ind-membership-purchase-name">1 month (30 days)</div>
                  <div class="ind-membership-purchase-price">
                    <span class="ind-membership-purchase-price-value">38,00 kr</span>
                  </div>
                </div>
                <div class="ind-membership-purchase-button">
                  <button class="purchase-button" @click="add1MonthSubscription">
                    <span>Purchase Now</span>
                  </button>
                </div>
              </div>
              <div class="ind-membership-purchase">
                <div class="ind-membership-purchase-col">
                  <div class="ind-membership-purchase-name">3 months (90 days)</div>
                  <div class="ind-membership-purchase-price">
                    <span class="ind-membership-purchase-price-value">76,00 kr</span>
                  </div>
                </div>
                <div class="ind-membership-purchase-button">
                  <button class="purchase-button" @click="add3MonthsSubscription">
                    <span>Purchase Now</span>
                  </button>
                </div>
                <div class="purchase-price-info">
                  <div class="monthly-price-notice">
                    <p class="monthly-price-notice__monthly-price">
                      <span>Equivalent to: 26,00 kr/month</span>
                    </p>
                  </div>
                </div>
              </div>
              <div class="ind-membership-purchase">
                <div class="ind-membership-purchase-col">
                  <div class="ind-membership-purchase-name">12 months (365 days)</div>
                  <div class="ind-membership-purchase-price">
                    <span class="ind-membership-purchase-price-value">190,00 kr</span>
                  </div>
                </div>
                <div class="ind-membership-purchase-button">
                  <button class="purchase-button" @click="add12MonthsSubscription">
                    <span>Purchase Now</span>
                  </button>
                </div>
                <div class="purchase-price-info">
                  <div class="monthly-price-notice">
                    <p class="monthly-price-notice__monthly-price">
                      <span>Equivalent to: 16,00 kr/month</span>
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
<script>
import {sendApiRequest} from "@/js/requests";
import {addRole, getAuthenticatedUser} from "@/js/authentication";
import Alert from "@/components/Alert.vue";
import {getCurrentInstance} from "vue";

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
export default {
  name: 'Subscription',
  components: {Alert},
  methods: {
    add1MonthSubscription,
    add3MonthsSubscription,
    add12MonthsSubscription
  }
}

async function add1MonthSubscription() {
  if(getAuthenticatedUser()) await sendApiRequest(API_URL,'PUT', '/users/purchase-pro/1-month', successfulExecution, error);
  else this.$emit('show-alert');
}

async function add3MonthsSubscription() {
  if(getAuthenticatedUser()) await sendApiRequest(API_URL,'PUT', '/users/purchase-pro/3-month', successfulExecution, error);
  else this.$emit('show-alert');
}

async function add12MonthsSubscription() {
  if(getAuthenticatedUser()) await sendApiRequest(API_URL,'PUT', '/users/purchase-pro/12-month', successfulExecution, error);
  else this.$emit('show-alert');
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
@media (max-width: 600px) {

}

.background h2 {
  color: whitesmoke;
  font-size: 40px;
  margin: 0;
  padding-bottom: 30px;
  animation: ease-in-out fadeIn 1.5s;
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
  background-color: hsla(0, 0%, 45.1%, 0.2);
}

.membership-wrap {
  margin-left: auto;
  margin-right: auto;
  max-width: 1024px;
  box-sizing: border-box;
  line-height: 1.5;

}

.membership-plans {
  padding: 0 32px 16px;
  box-shadow: inset 0 0 20px 0 rgba(0, 0, 0, 0.075);
}

.list-of-prices {
  padding-left: 72px;
  padding-right: 72px;
}

.ind-membership {
  margin-top: auto;
}

.ind-membership-purchase {
  font-size: 1rem;
  margin: 0 auto;
  max-width: none;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 0 16px 0;
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
  align-items: center;
  justify-content: center;
  width: 260px;
  height: 44px;
  border-radius: 15px;
  cursor: pointer;
  margin-top: 10px;
  transition: all .5s;

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}


.purchase-price-info {
  -webkit-box-ordinal-group: 3;
  order: 2;
  width: 100%;
  margin-top: 8px;
  margin-bottom: 0;
  text-align: left;
}

.monthly-price-notice {

  background-color: #f0f0f0;
  padding: 8px 16px;
  font-size: 0.7rem;
}

.monthly-price-notice__monthly-price {
  margin-bottom: 0;
}
</style>