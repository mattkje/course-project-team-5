<script>

export default {
  name: "PaymentSuccess",
  methods: {
    returnHome() {
      // Assuming you are using Vue Router, this navigates to the home page
      this.$router.push('/');
    },
    removeCookies(courseId) {
      document.cookie = 'courseId_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      document.cookie = 'providerId_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      document.cookie = 'price_' + courseId + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    },
    returnHomeAndRemoveCookies() {
      const allCookies = document.cookie;
      const { courseIds } = this.getCourseAndProviderIds(allCookies);
      courseIds.forEach(courseId => {
        this.removeCookies(courseId);
      });
      this.returnHome();
    },
    getCourseAndProviderIds(allCookies) {
      const cookieArray = allCookies.split('; ');
      let courseIds = [];

      cookieArray.forEach(cookieStr => {
        const [name, value] = cookieStr.split('=');
        if (name.startsWith('courseId_')) {
          courseIds.push(value);
        }
      });

      return { courseIds };
    }
  }
}


</script>


<template>
  <div class="payment-success-container-parent">
    <div class="payment-success-container">
      <p class="checkmark">&#10003;</p>
      <h1>Payment Successful</h1>
      <p>This is a simulated payment successful page</p>
    </div>
    <button @click="returnHomeAndRemoveCookies">Return Home</button>
  </div>

</template>


<style scoped>

.payment-success-container-parent {
  background-color: var(--light-3);
  height: 100vh;
  width: 100vw;
}

.payment-success-container {
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  top: -100px;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  text-align: center;
  animation: slideUp 1s ease-in-out;
}

@keyframes slideUp {
  0% {
    transform: translateY(100vh);
  }
  100% {
    transform: translateY(0);
  }
}



.checkmark {
  background-color: #282828;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  align-content: center;
  text-align: center;
  font-family: Roboto, sans-serif;
  font-size: 100px;
  font-weight: 900;
  color: #4caf50; /* Green color for the check mark */
  margin-bottom: 10px;
}

h1 {
  font-size: 35px;
  color: #333;
}

button {
  position: absolute;
  font-family: 'Inter', sans-serif;
  font-weight: 900;
  color: #ffffff;
  font-size: 20px;
  background: #000000;
  top: 300px;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  border: none;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 70px;
  border-radius: 15px;
  cursor: pointer;
  transition: all .5s;
  animation: fadeIn 2s ease-in-out;

  &:active {
    transform: scale(0.95);
    transition-duration: .5s;
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

</style>
