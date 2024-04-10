<script setup>

</script>

<template>
  <div class="menubar">
    <div class="header">
      <div class="content">
        <div class="left-content">
          <router-link to="/" class="header-button">
            <img class="logo" src="/logo.svg" alt="Connect">
            <h4 class="logo-top">Learniverse&nbsp;</h4>
            <h4 class="logo-bottom">Connect</h4>
          </router-link>
          <div class="menu-links">
            <router-link to="/explore" class="header-button">Explore</router-link>
            <router-link to="/community" class="header-button">Community</router-link>
            <router-link to="/pro" class="header-button">Learniverse&nbsp;Pro</router-link>
          </div>
        </div>

        <div class="right-content">
          <router-link to="/cart" class="fancy-button">
            <img class="cart" src="/cart.svg" alt="Cart">
          </router-link>
          <select class="fancy-button" id="currencySelect">
          </select>
          <router-link to="/register" class="standard-button">Get Started</router-link>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>

.menubar {
  background-color: rgba(6, 6, 16, 0.9);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, 0.08);
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
}

.left-content {
  max-height: 70px;
  padding: 0 2vw;
  display: flex;
  align-items: center;
}

.right-content {
  max-height: 70px;
  margin: 3vw;
  display: flex;
  max-width: min-content;
  align-items: center;
}

.header-button {
  height: 70px;
  color: white;
  font-size: 15px;
  border: none;
  padding: 10px 1%;
  text-decoration: none;
  display: flex;
  align-items: center;
  margin-right: 3vw;
}

.right-content .header-button {
  margin-right: 0;
  margin-left: 3vw;
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

.cart {
  width: 25px;
  height: 25px;
  margin: 0;
  padding: 0;
}

.fancy-button {
  color: #EAEAEA;
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  margin: 0 10px;
  max-height: 50px;
  background: #0C0C0C;
  border: 0.5px solid #252525;

}

.fancy-button:hover {
  background-color: #262626;
  box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
}

.standard-button {
  background-color: #584BEB;
  color: #EAEAEA;
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  text-decoration: none;
  display: flex;
  width: max-content;

  &:hover {
    background-color: #6E67FC;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }
}
</style>
