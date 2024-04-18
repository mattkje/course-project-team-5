<script setup>
import {getAuthenticatedUser, getUser} from "@/js/authentication";
import {onMounted, ref} from "vue";
import {sendApiRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";

onMounted(() => {
  getUsername()
});

const user = getAuthenticatedUser();
const firstName = ref('');

function getUsername() {
  sendApiRequest("GET", "/users/" + user.username, onProfileDataSuccess).then(r =>
      showGreeting()
  );

}

function showGreeting() {
  document.getElementById("userFirstName").innerText = firstName.value;
  document.getElementById("part1").innerHTML = "Welcome Back&nbsp;";
  document.getElementById("part2").innerText = "!";
}

function onProfileDataSuccess(data) {
  console.log("Profile data loaded: ", data);
  firstName.value = data.firstName;
}

</script>

<template>
  <div class="content">
    <h2 id="part1" class="welcome-text"/>
    <h2 id="userFirstName" class="welcome-text"/>
    <h2 id="part2" class="welcome-text"/>
  </div>

</template>

<style scoped>
.content {
  display: flex;
  justify-content: center;
  align-content: center;
  height: 200px;
  background-color: #EAEAEA;
}

.welcome-text {
  padding: 50px 0;
  color: #282828;

}
</style>