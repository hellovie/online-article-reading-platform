<script setup>
import HeaderComponent from '../components/HeaderComponent.vue'
</script>

<template>
  <HeaderComponent />
  <div class="main-container">
    <router-view />
  </div>
</template>

<style scoped>
.main-container {
  background: var(--main-bg-color);
  height: calc(100% - 50px);
  width: 100%;
  position: absolute;
  top: 50px;
  right: 0px;
  left: 0px;
  min-width: 1200px;
}
</style>
