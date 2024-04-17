<script setup>
import {getCurrentInstance, onMounted} from "vue";
import {getAuthenticatedUser, isOfRoleUser} from "@/js/authentication";
const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;


onMounted(() => {
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
  <div id="menubar" class="menubar">
    <div id="desktop" class="header">
      <div class="content">
        <div class="left-content">
          <router-link to="/" class="logo-button">
            <img id="logo" class="logo" src="/pro.svg" v-if="isOfRoleUser('ROLE_PRO')">
            <img id="logo" class="logo" src="/logo.svg" v-else>
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom-pro" v-if="isOfRoleUser('ROLE_PRO')">Pro</h4>
            <h4 class="logo-bottom" v-else>Connect</h4>
          </router-link>
          <div id="menu-links" class="menu-links">
            <router-link to="/explore" class="header-button">Explore</router-link>
            <router-link to="/community" class="header-button">Community</router-link>
            <router-link to="/pro" class="header-button" v-if="!isOfRoleUser('ROLE_PRO')">Learniverse&nbsp;Pro</router-link>
          </div>
        </div>

        <div class="right-content">
          <select class="fancy-button" id="currencySelect">
          </select>
          <router-link to="/cart" class="fancy-button-round">
            <img class="cart" src="/cart.svg" alt="Cart">
          </router-link>
          <router-link v-if="getAuthenticatedUser() === null" to="/register" class="standard-button">Log in</router-link>
          <router-link to="/profile" v-else class="fancy-button-round">
            <img class="cart" src="/account.svg" alt="Cart">
          </router-link>
        </div>
      </div>
    </div>

    <div id="mobile">
      <div class="header">
        <div class="content-mobile">
          <router-link to="/" id="mobileHeader" class="header-button">
            <img class="logo" src="/logo.svg" alt="Connect">
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom">Connect</h4>
          </router-link>
        </div>
      </div>
      <div class="button-container">
        <router-link to="/" class="button">
          <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 1024 1024" stroke-width="0"
               fill="currentColor" stroke="currentColor" class="icon">
            <path
                d="M946.5 505L560.1 118.8l-25.9-25.9a31.5 31.5 0 0 0-44.4 0L77.5 505a63.9 63.9 0 0 0-18.8 46c.4 35.2 29.7 63.3 64.9 63.3h42.5V940h691.8V614.3h43.4c17.1 0 33.2-6.7 45.3-18.8a63.6 63.6 0 0 0 18.7-45.3c0-17-6.7-33.1-18.8-45.2zM568 868H456V664h112v204zm217.9-325.7V868H632V640c0-22.1-17.9-40-40-40H432c-22.1 0-40 17.9-40 40v228H238.1V542.3h-96l370-369.7 23.1 23.1L882 542.3h-96.1z"></path>
          </svg>
        </router-link>
        <router-link to="/explore" class="button">
          <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" aria-hidden="true" viewBox="0 0 24 24"
               stroke-width="2" fill="none" stroke="currentColor" class="icon">
            <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" stroke-linejoin="round" stroke-linecap="round"></path>
          </svg>
        </router-link>
        <router-link to="/community" class="button">
          <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="0"
               fill="currentColor" stroke="currentColor" class="icon">
            <path
                d="M12 2.5a5.5 5.5 0 0 1 3.096 10.047 9.005 9.005 0 0 1 5.9 8.181.75.75 0 1 1-1.499.044 7.5 7.5 0 0 0-14.993 0 .75.75 0 0 1-1.5-.045 9.005 9.005 0 0 1 5.9-8.18A5.5 5.5 0 0 1 12 2.5ZM8 8a4 4 0 1 0 8 0 4 4 0 0 0-8 0Z"></path>
          </svg>
        </router-link>

        <router-link to="/cart" class="button">
          <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" stroke-linejoin="round"
               stroke-linecap="round" viewBox="0 0 24 24" stroke-width="2" fill="none" stroke="currentColor"
               class="icon">
            <circle r="1" cy="21" cx="9"></circle>
            <circle r="1" cy="21" cx="20"></circle>
            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
          </svg>
        </router-link>
      </div>
    </div>

  </div>
</template>


<style scoped>
@media (max-width: 600px) {
  #menubar {
    background-color: rgb(0, 0, 0);
    backdrop-filter: none;
    border: none;
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
}

@media (min-width: 600px) {
  #menubar {
    background-color: rgba(6, 6, 16, 0.9);
    backdrop-filter: blur(5px);
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  }

  #desktop {
    display: flex;
  }

  #mobile {
    display: none;
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
  align-items: center;
  height: 70px;
  width: 100%;
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
  background: linear-gradient(180deg, #FFFFFF -0%, #9087EF 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  color: transparent;
  font-size: 15px;
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

.logo {
  width: 60px;
  height: 60px;
  padding-left: 20px;
  transform: translateY(-3px);
}

.logo-top {
  font-weight: 900;
  padding-left: 5px;
}

.logo-bottom {
  font-weight: 900;
  color: #584BEB;
}

.logo-bottom-pro {
  font-weight: 900;
  color: #C99A63;
}

.cart {
  width: 20px;
  height: 20px;
  margin: 0;
  padding: 0;
}

.fancy-button {
  color: #EAEAEA;
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  font-size: 14px;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  margin: 0 5px;
  max-height: 40px;
  min-height: 40px;
  background: #0C0C0C;
  border: 0.5px solid #252525;
  transition: all 0.3s ease-in-out;
}

.fancy-button:hover {
  background-color: #262626;
  box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
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
  background: #0C0C0C;
  border: 0.5px solid #252525;
  transition: all 0.3s ease-in-out;

  &:hover {
    background-color: #262626;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }
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

</style>
