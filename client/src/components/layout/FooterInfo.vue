<script setup>
</script>

<template>
  <div>
    <ul class="footer">
      <li><a href="https://blog.csdn.net/thehellovie" target="_blank">CSDN</a></li>|
      <li><a href="https://gitee.com/hellovie" target="_blank">Gitee</a></li>|
      <li><a href="https://hellovie.github.io" target="_blank">Copyright © 2023 Hellovie. All rights reserved.</a></li>
    </ul>
  </div>
</template>

<style scoped>
.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 50px;
  min-width: 1200px;
  color: var(--main-font-inactive--color);
  background: transparent;
}
.footer>li {
  padding: 0 10px;
}
.footer>li>a {
  color: var(--main-font-inactive--color);
}
</style>
