<script setup></script>

<template>
  <div class="collect-container">
    <div class="dev"></div>
  </div>
</template>

<style scoped>
.collect-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  padding: 10px;
  box-sizing: border-box;
}
.collect-container > .dev {
  width: 100%;
  height: 100%;
  background: url('@/assets/image/under-development.png') no-repeat;
  background-position: center center;
  background-repeat: no-repeat;
  background-size:contain;
}
</style>
