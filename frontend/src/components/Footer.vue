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
        <a href="/categories" target="_blank">Categories</a>
        <a href="/explore" target="_blank">Explore</a>
        <a href="/about" target="_blank">About us</a>
      </div>
    </div>
  </footer>
</template>

<style scoped>

footer {
  font-family: 'Inter', sans-serif;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 100%, rgba(158, 150, 255, 0.14) 10%);
  color: #EAEAEA;
  text-align: center;
  padding: 20px;
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

</style>

