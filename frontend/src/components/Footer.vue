<script setup>

</script>

<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <h5>Contact Us</h5>
        <p>Email: info@learniverse.com</p>
        <p>Phone: +47 70 70 70 70</p>
      </div>
      <div class="footer-section">
        <h5>Created by Team 5 at</h5>
        <img class="ntnu-logo" src="/ntnu2.svg" alt="NTNU Logo">
        <div class="footer-logo">
          <img class="logo-footer" src="/logo.svg" alt="Connect">
          <h4 class="logo-top">Learniverse&nbsp;</h4>
        </div>
      </div>
      <div class="footer-section">
        <h5>Useful Links</h5>
        <div class="links">
          <router-link to="/about" target="_blank">About us</router-link>
          <router-link to="/profile" target="_blank">Profile</router-link>
          <router-link to="/explore" target="_blank">Explore</router-link>
        </div>
      </div>
    </div>
    <img alt="rocket" class="rocket" src="/rocket.svg"/>
  </footer>
</template>

<style scoped>
.footer {
  font-family: 'Inter', sans-serif;
  background: rgb(16, 16, 23);
  color: #EAEAEA;
  padding: 50px 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  flex-wrap: wrap;
}

.footer-content {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-section {
  align-items: center;
  display: flex;
  flex-direction: column;
  padding: 15px;
  min-width: 200px;
}

.footer-section h5 {
  margin-bottom: 20px;
  color: #fff;
}

.footer-section p, .footer-section a {
  color: #EAEAEA;
  text-decoration: none;
  margin: 5px 0;
}

.footer-section a:hover {
  text-decoration: underline;
}

.ntnu-logo {
  width: 200px;
  margin-bottom: 15px;
}

.footer-logo {
  display: flex;
  align-items: center;
}

.logo-footer {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.logo-top {
  color: #EAEAEA;
}

.links {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.rocket {
  width: 200px;
  margin: 30px auto;
}
</style>

