<script setup>
import Alert from "@/components/Alert.vue";

const handleButtonClick = (button) => {
  console.log(`Button "${button}" was clicked.`);
};
</script>

<template>
  <div id="background" class="background">
    <img class="planet" src="/logo.svg" alt="Blue Planet">
  </div>
  <div class="about-us-page">


    <div class="about-us-container">
      <div class="title">
        <h1>About Us</h1>
        <p class="description">Get to know our vision</p>
      </div>

      <div class="greeting-box">
        <img class="about-us-icon" src="/learning.svg" alt="Knowledge">
        <p>
          Welcome to Learniverse Connect, your premier destination for unlocking a world of knowledge and skills
          through our dynamic online course marketplace. At Learniverse, we believe that learning knows no
          bounds, and our platform is designed to empower individuals like you to embark on a journey of lifelong
          learning. As a marketplace, we bring together a diverse array of courses from passionate and expert
          third-party providers, ensuring that you have access to a comprehensive range of subjects and skills to
          fuel your personal and professional growth.
        </p>
      </div>

      <div class="greeting-box">
        <p>
          Our commitment to quality is unwavering, and we meticulously curate our course offerings to guarantee
          a premium learning experience. Whether you're a budding entrepreneur looking to master the
          intricacies of business strategy or someone seeking to delve into the realms of creative arts, Learniverse
          Connect is your trusted companion on the path to success. Join our vibrant community of learners,
          connect with top-notch instructors, and explore a rich tapestry of knowledge that awaits you. At
          Learniverse, we envision a world where learning is not just a destination but a continuous, enriching
          journey, and we invite you to be a part of this transformative experience.
        </p>
        <img class="about-us-icon" src="/rocket.svg" alt="Quality">
      </div>

      <div class="greeting-box">
        <img class="about-us-icon" src="/cert.svg" alt="Certification">
        <p>
          At Learniverse Connect, we pride ourselves on offering courses that not only equip you with valuable
          knowledge and skills but also pave the way for tangible recognition through certifications. Upon
          successfully completing any course on our platform, you gain the opportunity to take the corresponding
          certification exam, validating your newfound expertise. We understand the importance of certifications
          in today's competitive landscape, and that's why we stand behind our courses with a robust money-back
          guarantee. If, for any reason, you don't pass the certification exam after diligently completing the
          course, we ensure a hassle-free refund, underscoring our commitment to your success and confidence
          in the quality of our educational offerings. Your journey with Learniverse is not just about learning; it's
          about achieving and celebrating your milestones with the assurance that your investment in education is
          backed by our unwavering support.
        </p>
      </div>

      <div class="greeting-box">
        <p>
          While our courses predominantly take place in the virtual realm, we take pride in providing a unique
          blend of online learning and real-time engagement. Each course is facilitated by a dedicated physical
          instructor who not only guides you through the material but also ensures an interactive and dynamic
          learning experience. To further enrich your educational journey, we offer workshop sessions, adding a
          hands-on dimension to the online courses. While these courses are organized at specific dates to
          accommodate the workshop sessions, we understand the importance of flexibility. Rest assured, our
          commitment to your convenience is paramount, and the courses are strategically repeated several times
          a year, offering ample opportunities for you to participate and thrive in your learning pursuits.
        </p>
        <img class="about-us-icon" src="/space.svg" alt="Workshop">
      </div>
    </div>
  </div>
</template>

<style scoped>

.background {
  top: 0;
  height: min-content;
  background: linear-gradient(180deg, rgba(21, 16, 82, 0.14) 0%, rgba(158, 150, 255, 0.14) 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin: 0;

}


.planet {
  height: 300px;
  animation: ease-out pulse 5s infinite;
}


@keyframes pulse {
  0% {
    transform: scale(0.5);
  }
  50% {
    transform: scale(0.6);
  }
  100% {
    transform: scale(0.5);
  }
}

.title {
  border: none;
  border-radius: 15px;
  width: 60%;
  justify-content: center;
  align-items: center;
  text-decoration: none;
  margin: 20px auto;
}

.description {
  color: #282828;
  font-size: 20px;
  margin: 0 auto;
  text-align: center;
}
h1 {
  font-size: 40px;
  font-weight: 900;
  color: var(--dark-1);
  text-align: center;
}

.about-us-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: var(--light-3);
  margin: 0;
}

.about-us-pic {
  width: 100%;
  height: 200px;
  object-fit: contain;
  margin: 100px auto;
}

.about-us-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1300px;
}

.greeting {
  text-align: center;
  margin-bottom: 20px;
}

.greeting-box {
  background-color: var(--light-1);
  border-radius: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 30px;
  margin-bottom: 50px;
  display: flex;
  align-items: flex-start;
  width: 100%;
}

.greeting-box img.about-us-icon {
  height: 150px;
  max-width: 300px;
  min-width: 300px;
  margin: auto 20px;
}

.greeting-box p {
  font-size: 1.2em;
  color: var(--dark-1);
  margin: 0;
}
</style>
