<template>
  <div>
    <!-- Overlay -->
    <div v-if="isMenuOpen" class="overlay" @click="toggleMenu"></div>

    <!-- Side Menu -->
    <div :class="['side-menu', { 'open': isMenuOpen }]">
      <button @click="toggleMenu" class="close-btn">Close</button>
      <ul>
        <li><a href="#">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Services</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
    </div>

    <!-- Toggle Button -->
    <button @click="toggleMenu" class="menu-btn">Menu</button>
  </div>
</template>

<script>
export default {
  name: "MobileSideMenu",
  data() {
    return {
      isMenuOpen: false,
    };
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
  },
};
</script>

<style scoped>
/* Basic styling for the side menu and overlay */
.side-menu {
  position: fixed;
  top: 0;
  left: 0;
  width: 250px;
  height: 100%;
  background-color: #333;
  color: white;
  transform: translateX(-100%);
  transition: transform 0.3s ease;
  z-index: 1000;
}

.side-menu.open {
  transform: translateX(0);
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 999;
}

.menu-btn, .close-btn {
  background-color: #333;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
}

.menu-btn {
  position: fixed;
  top: 10px;
  left: 10px;
  z-index: 1001;
}

.close-btn {
  margin: 10px;
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

li {
  margin: 20px 0;
}

a {
  color: white;
  text-decoration: none;
  display: block;
  padding: 10px 20px;
}
</style>
