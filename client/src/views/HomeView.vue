<script setup>
import { useCounterStore } from '@/stores/counter'

const counter = useCounterStore()
const count = () => {
  counter.increment()
}
</script>

<template>
  <div>
    <h2>在线文章阅读系统</h2>
    Counter: <button @click="count()">{{ counter.count }}</button>
  </div>
</template>

<style scoped>
button {
  padding: 1px 20px;
}
</style>
