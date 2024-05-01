<template>
  <h1>Change password</h1>
  <div class="profile-information">
    <label for="currentPassword">Current password:</label>
    <input class="passwordField" type="password" id="currentPassword" maxlength="64">
    <label for="newPassword">New password:</label>
    <input class="passwordField" type="password" id="newPassword" maxlength="64">
    <label for="confirmPassword">Confirm new password:</label>
    <input class="passwordField" type="password" id="confirmPassword" maxlength="64">
  </div>
</template>
<script>
export default {
  name: 'PasswordChange'
}
</script>
<style scoped>

.profile-information {
  background-color: var(--light-1);
  border-radius: 20px;
  padding: 20px;
  display: grid;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);
  margin: 50px 0;
  line-height: 50px;
  grid-template-rows: auto;
  justify-content: space-between;
}

.passwordField {
  width: 100%;
  height: 40px;
  border-radius: 10px;
  border: 1px solid var(--base-1);
  padding: 10px;
  margin: 10px 0;
}

#currentPassword {
  margin-bottom: 30px;
}

.courses tr:nth-child(odd) {
  background-color: #fff;
}

.courses tr:nth-child(even) {
  background-color: lightgrey;
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

.profile-item label {
  padding: 5px 20px;
  display: inline-block;
  border-radius: 5px;
  color: var(--dark-3);
  border: 2px solid var(--light-2);
  width: 100%;
}
</style>