<script setup>
import {ref, onMounted, getCurrentInstance} from 'vue';
import {getCookie} from "@/js/tools";
import {sendApiRequest} from "@/js/requests";

const users = ref([]);
const loading = ref(true);
const {appContext} = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;
const userAction = ref(false);
const activeUser = ref('');

onMounted(async () => {
  const response = await fetch(API_URL + '/users', {
    headers: {
      'Authorization': 'Bearer ' + getCookie('jwt'),
    },

  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  users.value = await response.json();
  loading.value = false;
});

async function deleteUser(user) {
  const token = getCookie('jwt');
  const response = await fetch(API_URL + '/users/' + user.username, {
    method: 'DELETE',
    headers: {
      'Authorization': 'Bearer ' + token,
    },
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(`HTTP error! status: ${response.status}, message: ${errorData}`);
  }


  location.reload();
  return "User successfully deleted";
}

function userActions(user) {
  userAction.value = true;
  activeUser.value = user;
}

function deactivateUser() {
  userAction.value = false;
}

function addRole() {
  const role = document.getElementById('user-select-add').value
  sendApiRequest(API_URL, 'POST', '/users/' + activeUser.value.username + '/add-role', onRoleSuccess, role, error)
}

function deleteRole() {
  const role = document.getElementById('user-select-remove').value
  console.log(role);
  sendApiRequest(API_URL, 'DELETE', '/users/' + activeUser.value.username + '/delete-role', onRoleSuccess, role, error)
}

function onRoleSuccess(data) {
  console.log(data)
  alert("Role added successfully");
}

function error() {
  alert("An error occurred");
}
</script>

<template>
  <div class="page-background" id="profileInformation">
    <div class="user-roles" v-show="userAction">
      <button class="fancy-button" style="width: 100px" @click="deactivateUser()">< Go back</button>
      <div class="role-manager">
        <select class="user-select" id="user-select-add">
          <option value="ROLE_ADMIN">Admin</option>
          <option value="ROLE_USER">User</option>
          <option value="ROLE_PRO">Pro</option>
      </select>
      <button class="fancy-button" style="width: 100px" @click="addRole()">Add Role</button>
      </div>
      <div class="role-manager">
        <select class="user-select" id="user-select-remove">
          <option value="ROLE_ADMIN">Admin</option>
          <option value="ROLE_USER">User</option>
          <option value="ROLE_PRO">Pro</option>
        </select>
        <button class="fancy-button" style="width: 100px" @click="deleteRole()">Delete Role</button>
      </div>
    </div>
    <div v-show="loading === true" class="three-body">
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
    </div>
    <div v-show="!userAction" class="title">
      <h1>User Management</h1>
      <p>User Account</p>
    </div>
    <div class="page-highlight" v-show="!loading && userAction === false">
      <div v-for="user in users" :key="user.id" class="user-block">
        <p>{{ user.username }}</p>
        <div class="right-content">
          <button class="fancy-button" @click="userActions(user)">Actions</button>
          <button class="fancy-button" style="background-color: orangered; color: white" @click="deleteUser(user)">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@media (max-width: 769px) {
  .page-background {
    background-color: var(--light-1);
    padding: 30px 20px;
    margin: 10px auto;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-content: center;
    width: 400px;
    overflow: hidden;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .page-highlight {
    display: flex;
    flex-direction: column;
    width: 100%;
    margin: 20px 0;
    grid-template-rows: auto;
    justify-content: space-between;
  }
  .user-block {
    width: 370px;
    display: flex;
    align-items: center;
    height: 60px;
    justify-content: space-between;
    padding: 10px;
    border: 1px solid var(--light-1);
    border-radius: 10px;
    margin-bottom: 5px;
    background-color: var(--light-3);
  }
}
@media (min-width: 769px) {
  .page-background {
    background-color: var(--light-1);
    padding: 30px 20px 30px 20px;
    border-radius: 30px;
    margin: 10px 10px 10px 10px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-content: center;
    max-width: 1100px;
    width: 55%;
    overflow: hidden;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
  }

  .page-highlight {
    background-color: var(--light-1);
    border-radius: 20px;
    padding: 20px;
    display: grid;
    width: 100%;
    min-height: 50vh;
    grid-template-columns: repeat(2, 1fr);
    margin: 20px 0;
    line-height: 50px;
    grid-template-rows: auto;
    justify-content: space-between;
  }

  .user-block {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px;
    border: 1px solid var(--light-1);
    border-radius: 5px;
    margin-bottom: 10px;
    background-color: var(--light-3);
  }
}
@media (max-width: 1100px) {
  .page-highlight {
    display: flex;
    flex-direction: column;
    width: 100%;
    margin: 20px 0;
    grid-template-rows: auto;
    justify-content: space-between;
  }
}
.user-select {
  width: 100px;
  height: 30px;
  border-radius: 10px;
  border: 1px solid var(--light-1);
  background-color: var(--light-3);
  color: var(--dark-3);
  font-size: 15px;
  font-weight: bold;
  margin: 10px;

}

.user-roles {
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: start;
}

.role-manager {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.right-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>