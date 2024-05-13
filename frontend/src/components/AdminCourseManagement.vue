<script setup>
import {ref} from "vue";

const loading = ref(true);
</script>

<template>
  <div class="page-background" id="profileInformation">
    <div v-show="loading" class="three-body">
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
    </div>
    <div class="title" v-show="!changePassword">
      <h1>Courses</h1>
      <p>Manage your profile</p>
    </div>
    <div class="page-highlight" v-show="!loading">

    </div>

  </div>
</template>

<style scoped>
.page-background {
  background-color: var(--light-1);
  padding: 30px;
  border-radius: 30px;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: left;
  align-content: center;
  width: 60%;
  overflow: hidden;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
}

.page-highlight {
  background-color: var(--light-1);
  border-radius: 20px;
  padding: 20px;
  display: grid;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);
  margin: 20px 0;
  line-height: 50px;
  grid-template-rows: auto;
  justify-content: space-between;
}
</style>