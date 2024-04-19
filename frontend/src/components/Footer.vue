<script setup>

</script>

<template>
  <footer>
    <div class="footer-content">
      <div class="footer-section">
        <h5>Contact Us</h5>
        <p>Email: info@learniverse.com</p>
        <p>Phone: +47 70 70 70 70</p>
      </div>
      <div class="footer-section">
        <div class="footer-section">
          <h5>Created by Team 5 at</h5>
          <img width="200px" src="/ntnu2.svg" alt="NTNU Logo">
        </div>
        <div class="footer-logo">
          <img class="logo-footer" src="/logo.svg" alt="Connect">
          <h4 class="logo-top">Learniverse&nbsp;</h4>
        </div>
      </div>
      <div class="footer-section">
        <h5>Useful Links</h5>
        <router-link to="/categories" target="_blank">Categories</router-link>
        <router-link to="/explore" target="_blank">Explore</router-link>
        <router-link to="/about" target="_blank">About us</router-link>
      </div>
    </div>
  </footer>
</template>

<style scoped>

footer {
  font-family: 'Inter', sans-serif;
  background: rgb(16, 16, 23);
  color: #EAEAEA;
  text-align: center;
  padding: 50px 20px;
  margin: 0;
}

.footer-content {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.footer-section {
  width: 220px;
  margin: 10px 0;
}

footer a {
  font-family: 'Inter', sans-serif;
  color: #EAEAEA;
  text-decoration: none;
  display: block;
  margin: 5px 0;
}

footer .logo-top {
  color: #EAEAEA;
  text-decoration: none;
  display: block;
  margin: 5px 0;
}

footer a:hover {
  text-decoration: underline;
}

.footer-logo {
  border: none;
  text-decoration: none;
  display: flex;
  align-items: center;
}

.logo-footer {
  width: 40px;
  height: 40px;
}

.bend-bottom {
  rotate: 180deg;
  width: 100%;
  margin: 0;
  padding: 0;
}
</style>

