<!-- 封装 Icon 组件 -->
<script setup>
import { computed } from 'vue'

const props = defineProps({
  iconClass: {
    type: String,
    required: true
  },
  iconSize: {
    type: Number,
    required: false,
    default: 40
  },
  isPointer: {
    type: Boolean,
    require: false,
    default: false
  }
})
const iconName = computed(() => {
  // Vite 的处理方式
  return new URL(`/src/assets/icon/icon-${props.iconClass}.svg`, import.meta.url).href
})
const iconStyleVar = computed(() => {
  const cursorStyle = props.isPointer ? 'pointer' : 'default'
  return {
    '--icon-size': props.iconSize + 'px',
    '--cursor-style': cursorStyle
  }
})
const emit = defineEmits(['click'])
</script>

<template>
  <img class="eiv-icon" :src="iconName" :style="iconStyleVar" @click="emit('click')" />
</template>

<style scoped>
.eiv-icon {
  width: var(--icon-size);
  height: var(--icon-size);
  cursor: var(--cursor-style);

  -webkit-user-select: none;
  -ms-user-select: none;
  -moz-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}
</style>
