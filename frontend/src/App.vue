<script setup>
import Menubar from "@/components/Menubar.vue";
import Footer from "@/components/Footer.vue";
</script>

<template>
  <Menubar msg="Menubar"/>
  <router-view/>
  <Footer msg="Footer"/>
</template>

<style>

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>