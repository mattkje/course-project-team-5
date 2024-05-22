<template>
  <div class="profile-box" id="profileInformation">
    <div v-show="loading" class="three-body">
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
      <div class="three-body__dot"></div>
    </div>
    <div class="title" v-show="!changePassword">
      <h1>Account Details</h1>
      <p>Manage your profile</p>
    </div>
    <hr>
    <img id="image"/>
    <div class="profile-information" v-show="!loading">
      <div class="profile-item">
        <p>Username</p>
        <label for="username" id="username"></label>
      </div>
      <div class="profile-item">
        <p>Email</p>
        <label for="email" id="email"></label>
      </div>

      <div class="profile-item">
        <p>First name</p>
        <label for="firstName" id="firstName"></label>
      </div>
      <div class="profile-item">
        <p>Last name</p>
        <label for="lastName" id="lastName"></label>
      </div>
      <div class="profile-item">
        <p>Phone number</p>
        <label for="phoneNumber" id="phoneNumber"></label>
      </div>

    </div>
    <hr>
    <div class="course-options">
      <input type="file" id="imageInput" accept="image/*">
      <button class="fancy-button" @click="changeProfilePicture">Add profile picture</button>
      <button class="fancy-button" @click="newPassword">Change password</button>
      <button class="fancy-button" @click="doLogoutToHome">Log out</button>
    </div>
  </div>
</template>
<script>
import {defineComponent} from "vue";

export default defineComponent({
  name: 'AccoundDetails',
  setup(_, context) {
    return {
      newPassword: () => context.emit("newPassword"),
      doLogoutToHome: () => context.emit("doLogoutToHome"),
      changeProfilePicture: () => context.emit("changeProfilePicture"),
    };
  },
});
</script>
<style scoped>

@media (max-width: 769px) {
  .profile-information {
    background-color: var(--light-1);
    width: 100%;
    margin-bottom: 20px;
    justify-content: space-between;
  }
  .profile-item {
    width: 100%;
    height: 90px;
    overflow: hidden;
    white-space: nowrap;
  }
  .profile-item label {
    padding: 5px 20px;
    display: inline-block;
    border-radius: 15px;
    color: var(--dark-3);
    width: 100%;
    max-width: 100%;
    align-content: center;
    height: 60px;
    border: 2px solid var(--light-2);
    overflow: hidden;
  }

}

@media (min-width: 769px) {
  .profile-information {
    background-color: var(--light-1);
    display: grid;
    width: 100%;
    grid-template-columns: repeat(2, 1fr);
    margin-bottom: 20px;
    line-height: 50px;
    grid-template-rows: auto;
    justify-content: space-between;
    grid-gap: 20px;
  }
  .profile-item {
    width: 100%;
    overflow: hidden;
    white-space: nowrap;
  }
  .profile-item label {
    padding: 5px 20px;
    display: inline-block;
    border-radius: 15px;
    color: var(--dark-3);
    width: 100%;
    max-width: 100%;
    border: 2px solid var(--light-2);
    overflow: hidden;
  }

}




.courses tr:nth-child(odd) {
  background-color: #fff;
}

.courses tr:nth-child(even) {
  background-color: lightgrey;
}

.course-options {
  padding-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: left;
  gap: 20px;
  width: 175px;
}

@keyframes slideIn {
  0% {
    transform: translatex(-1000px) translateY(-100px) scale(1.2);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

@keyframes pulse {
  0% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
  50% {
    transform: translatex(0) translateY(120px) scale(1.7);
  }
  100% {
    transform: translatex(0) translateY(120px) scale(1.5);
  }
}

.title {
  border: none;
  border-radius: 15px;
  width: 100%;
  text-align: left;
  justify-content: left;
  text-decoration: none;
  margin: 0 auto;
}

.title p {
  color: var(--dark-3);
  font-size: 1.3em;
  margin: 0;
  padding: 0;
}

.navigation-bar p {
  font-weight: bold;
  color: var(--light-2);
  font-size: 17px;
  margin-left: 70px;
}



.fancy-button {
  background-color: var(--light-3);
}

.fancy-button:hover {
  cursor: pointer;
}

hr {
  max-width: 100%;
  margin: 20px;
}

.title {
  margin-bottom: 20px;
}

.fancy-button:nth-child(4) {
  background-color: #fad8d8;
}
</style>