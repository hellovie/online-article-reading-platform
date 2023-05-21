<script setup></script>

<template>
  <div class="fans-container">
    <div class="dev"></div>
  </div>
</template>

<style scoped>
.fans-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  padding: 10px;
  box-sizing: border-box;
}
.fans-container > .dev {
  width: 100%;
  height: 100%;
  background: url('@/assets/image/under-development.png') no-repeat;
  background-position: center center;
  background-repeat: no-repeat;
  background-size:contain;
}
</style>
