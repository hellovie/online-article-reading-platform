<script setup>
import BaseLayout from '@/components/layout/BaseLayout.vue'

</script>

<template>
  <BaseLayout v-if="this.$route.path != '/login'" />
  <router-view v-else />
</template>

<style scoped>

</style>
