<script setup>
import
{getCurrentInstance, onMounted} from "vue";
import {getAuthenticatedUser, hasRole, isAuthorized} from "@/js/authentication";
import {computed} from "vue";
import {useStore} from "vuex";

import myStore from '@/js/store.js';
import {getCookie, isTokenAboutToExpire} from "@/js/tools";
import {sendApiRequest, sendTokenRefreshRequest} from "@/js/requests";

const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const user = getAuthenticatedUser();

const store = useStore();
const cartItemCount = computed(() => myStore.getters.cartItemCount);

function onTokenRefreshSuccess() {
  console.log("Token has been refreshed.");
  sendApiRequest(API_URL,"GET", "/users/" + user.username, onProfileDataSuccess);
}

function onTokenRefreshError(error) {
  console.error("Error refreshing token: ", error);
  window.location.href = ("/no-access");
}

async function loadProfileData() {
  if(getAuthenticatedUser()) {
    console.log("Loading profile data from API...");
    const jwt = getCookie("jwt");
    if (jwt && isTokenAboutToExpire(jwt)) {
      sendTokenRefreshRequest(onTokenRefreshSuccess, onTokenRefreshError);
    } else {
      await sendApiRequest(API_URL,"GET", "/users/" + user.username, onProfileDataSuccess);
    }
  }
}

function onProfileDataSuccess(data) {
  document.getElementById("profile-picture").src = 'data:image/jpeg;base64,' + data.user.image;
  document.getElementById("mobile-profile-picture").src = 'data:image/jpeg;base64,' + data.user.image;
}


const integertest = 0;




onMounted(() => {
  loadProfileData();
  currency();
});


function currency(){
  document.getElementById('currencySelect').innerHTML = '';
  fetch(API_URL + '/currency')
      .then(response => response.json())
      .then(currencies => {
        const select = document.getElementById('currencySelect');
        currencies.forEach(currency => {
          const option = document.createElement('option');
          option.value = currency.code;
          option.text = currency.code + ' - ' + currency.symbol;
          select.appendChild(option);
        });

        const defaultCurrency = setDefaultCurrency();
        if (defaultCurrency) {
          select.value = defaultCurrency;
        }
        document.getElementById('currencySelect').addEventListener('change', function() {
          document.cookie = `defaultCurrency=${this.value}; path=/; max-age=31536000`;
          location.reload();
        });
      })
      .catch(error => console.error('Error:', error));

}

function setDefaultCurrency() {
  const cookies = document.cookie.split('; ');
  const defaultCurrencyCookie = cookies.find(row => row.startsWith('defaultCurrency='));
  if (defaultCurrencyCookie) {
    return defaultCurrencyCookie.split('=')[1];
  }
}
</script>

<template>
  <meta name="description" content="Menubar that includes explore, community, pro, profile and login page">
  <div id="menubar" class="menubar">
    <div id="desktop" class="header">
      <div class="content">
        <div class="left-content">
          <router-link to="/" class="logo-button">
            <img alt="logo" id="logo" class="logo" src="/logo.svg">
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom">Connect</h4>
          </router-link>
          <div id="menu-links" class="menu-links" v-if="hasRole('ROLE_ADMIN')">
            <router-link to="/admin" class="header-button" v-if="hasRole('ROLE_ADMIN')">Manage</router-link>
            <router-link to="/explore" class="header-button">Explore</router-link>
            <router-link to="/community" class="header-button">Community</router-link>
          </div>
          <div id="menu-links" class="menu-links" v-else>
            <router-link to="/admin" class="header-button" v-if="hasRole('ROLE_ADMIN')">Manage</router-link>
            <router-link to="/explore" class="header-button">Explore</router-link>
            <router-link to="/community" class="header-button">Community</router-link>
            <router-link to="/pro" class="header-button" v-if="!hasRole('ROLE_PRO') && !hasRole('ROLE_ADMIN')">Learniverse&nbsp;Pro</router-link>
          </div>
        </div>

        <div class="right-content">
            <select aria-label="Currency" class="fancy-button" id="currencySelect">
            </select>
          <router-link to="/cart" class="fancy-button-round" v-show="isAuthorized()">
            <img class="cart" src="/cart.svg" alt="Cart">
            <span class="cart-count" v-if="integertest > 0">{{ integertest }}</span>
          </router-link>
          <router-link v-if="getAuthenticatedUser() === null" to="/login" class="fancy-button">Log&nbsp;in</router-link>
          <router-link to="/profile" v-else class="profile">
            <img id="profile-picture" alt="Cart">
          </router-link>
        </div>
      </div>
    </div>
    <div id="mobile">
      <div class="header">
        <div class="content-mobile">
          <div class="left-content">
            <button class="mobile-header-button">
              <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-three-bars Button-visual">
                <path d="M1 2.75A.75.75 0 0 1 1.75 2h12.5a.75.75 0 0 1 0 1.5H1.75A.75.75 0 0 1 1 2.75Zm0 5A.75.75 0 0 1 1.75 7h12.5a.75.75 0 0 1 0 1.5H1.75A.75.75 0 0 1 1 7.75ZM1.75 12h12.5a.75.75 0 0 1 0 1.5H1.75a.75.75 0 0 1 0-1.5Z"></path>
              </svg>
            </button>
            <router-link to="/" id="mobileHeader" class="header-button">
              <img class="logo" src="/logo.svg" alt="Connect">
            </router-link>
          </div>
          <div class="right-content">
            <router-link to="/cart" class="mobile-header-button" v-show="isAuthorized()">
              <img src="/cart.svg" alt="Cart">
              <span class="cart-count" v-if="integertest > 0">{{ integertest }}</span>
            </router-link>
            <router-link v-if="getAuthenticatedUser() === null" to="/login" class="mobile-login-button">Log&nbsp;in</router-link>
            <router-link to="/profile" v-else class="mobile-profile">
              <img id="mobile-profile-picture" src="/account.svg">
            </router-link>
          </div>
        </div>
      </div>

    </div>

  </div>
</template>


<style scoped>
@media (max-width: 769px) {
  #menubar {
    background-color: #f4f6f8;
    border-bottom: 1px solid #d3d3d3;
    backdrop-filter: none;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);

  }

  #desktop {
    display: none;
  }

  #mobile {
    display: block;
  }

  #mobileHeader {
    justify-content: center;
    align-content: center;
  }

  .logo {
    width: 50px;
    height: 50px;
  }

  .left-content {
    display: flex;
    align-items: center;
  }

  .right-content {
    display: flex;
    max-width: min-content;
    align-items: center;
  }

}

@media (min-width: 769px) {
  #menubar {
    background: linear-gradient(180deg, #f8f8ff -0%, #f1f0fd 100%);
    backdrop-filter: blur(5px);
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }

  #desktop {
    display: flex;
  }

  #mobile {
    display: none;
  }

  .logo {
    width: 60px;
    height: 60px;
    padding-left: 20px;
    transform: translateY(-3px);
  }

  .left-content {
    max-height: 70px;
    padding: 0 10px;
    display: flex;
    align-items: center;
  }

  .right-content {
    max-height: 70px;
    margin: 10px;
    display: flex;
    max-width: min-content;
    align-items: center;
  }
}

.menubar {
  position: sticky;
  top: 0;
  width: 100%;
  z-index: 100;
}

.content {
  display: flex;
  justify-content: space-between;
  height: 70px;
  width: 100%;
}



.logo-button {
  height: 70px;
  font-size: 15px;
  border: none;
  padding: 10px 1%;
  text-decoration: none;
  display: flex;
  align-items: center;
  margin-right: 3vw;
  transition: all 0.3s ease-in-out;
}

.header-button {
  height: 70px;
  background: linear-gradient(180deg, #000000 -0%, #9087EF 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  color: transparent;
  font-size: 15px;
  font-weight: bold;
  border: none;
  padding: 10px 1%;
  text-decoration: none;
  display: flex;
  align-items: center;
  margin-right: 3vw;
  transition: all 0.3s ease-in-out;
}

.right-content .header-button {
  margin-right: 0;
}

.header-button:hover {
  opacity: 60%;
}

.menu-links {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 50px;

}



.logo-top {
  font-weight: 900;
  padding-left: 5px;
  color: #3c3c54;
}

.logo-bottom {
  font-weight: 900;
  color: #584BEB;
}

.logo-bottom-pro {
  font-weight: 900;
  color: #efb94f;
}

.cart {
  width: 20px;
  height: 20px;
  margin: 0;
  padding: 0;
}

.profile {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.profile img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.mobile-profile {
  margin: 10px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.mobile-profile img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.cart-count {

  padding: 2px 2px;
  background-color: #ff0000;
  color: #ffffff;
  border-radius: 50%;
  font-weight: bold;
}


.fancy-button-round {
  color: #EAEAEA;
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  font-size: 14px;
  padding: 10px;
  border-radius: 50%;
  text-decoration: none;
  display: flex;
  margin: 0 5px;
  max-height: 40px;
  min-height: 40px;
  background: var(--light-1);
  border: 0.5px solid #ffffff;
  transition: all 0.3s ease-in-out;

  &:hover {
    background-color: var(--light-3);
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }
}

.content-mobile {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-content: center;
}

.mobile-header-button {
  background-color: var(--light-1);
  border: 1px solid var(--light-2);
  border-radius: 7px;
  margin: 10px;
  width: 40px;
  height: 40px;
}

.mobile-header-button img {
  filter: brightness(0);
  opacity: 0.7;
  margin: 9px;
  width: 20px;
  height: 20px;
}

.mobile-header-button svg {
  margin: 3px auto auto auto;
  width: 20px;
  height: 20px;
  opacity: 0.5;
}

.mobile-login-button {
  font-family: Inter, sans-serif;
  font-weight: bold;
  color: black;
  text-decoration: none;
  background-color: var(--light-1);
  border: 1px solid var(--light-2);
  border-radius: 7px;
  text-align: center;
  align-content: center;
  margin: 10px;
  width: 80px;
  height: 40px;
}

.standard-button {
  background-color: #584BEB;
  color: #EAEAEA;
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  font-size: 14px;
  max-height: 40px;
  min-height: 40px;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  margin: 0 5px;
  width: max-content;
  transition: all 0.3s ease-in-out;

  &:hover {
    background-color: #6E67FC;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }
}

.button-container {
  display: flex;
  background-color: var(--base-1);
  width: 100%;
  height: 40px;
  align-items: center;
  justify-content: space-around;
  border-radius: 0 0 10px 10px;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px,
  rgba(0, 0, 0, 0.5) 5px 10px 15px;
}

.button {
  outline: 0 !important;
  border: 0 !important;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  transition: all ease-in-out 0.3s;
  cursor: pointer;
}

@media (max-width: 1000px) {
  .logo-top,
  .logo-bottom {
    display: none;
  }
  .menu-links {
    padding: 0;
  }
  .logo-button {
    margin: 0;
  }
  .tent {
    gap: 10px;
    padding: 0 4%;
  }
  .logo {
    padding: 0;
  }
}


</style>
