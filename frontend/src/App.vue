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

</style>