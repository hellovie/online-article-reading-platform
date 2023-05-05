<script setup>
import LayoutComponent from './components/LayoutComponent.vue'
</script>

<template>
  <LayoutComponent v-if="this.$route.path != '/login'" />
  <router-view v-else />
</template>

<style scoped>

</style>
