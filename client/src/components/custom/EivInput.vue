<!-- 封装 Icon 组件 -->
<script setup>
import { computed, ref } from 'vue'
import EivIcon from './EivIcon.vue'
const props = defineProps({
  value: {
    type: String,
    default: ''
  },
  inputType: {
    type: String,
    required: false,
    default: 'text',
    validator (inputType) {
      return ['text', 'password'].includes(inputType)
    }
  },
  rules: {
    type: Array,
    required: false,
    default: () => {
      return [{
        // 是否校验
        required: false,
        // 提示信息
        message: '',
        min: 0,
        max: 0,
        // 校验正则
        regex: '',
        // 自定义校验规则
        validator: () => {}
      }]
    }
  },
  prefixIcon: {
    type: String,
    required: false,
    default: ''
  },
  suffixIcon: {
    type: String,
    required: false,
    default: ''
  },
  // 除了密码框外, 其他类型输入框可以添加可清除图标, 用户自定义 suffixIcon 后将不生效.
  clearable: {
    type: Boolean,
    required: false,
    default: false
  },
  // 如果是密码框, 可以添加小眼睛图标, 显示或隐藏密码, 用户自定义 suffixIcon 后将不生效.
  showPassword: {
    type: Boolean,
    required: false,
    default: false
  },
  placeholder: {
    type: String,
    required: false
  },
  // border-box
  inputBoxWidth: {
    type: Number,
    required: false,
    default: 200
  },
  inputBoxHeight: {
    type: Number,
    required: false,
    default: 30
  },
  borderRadius: {
    type: Number,
    required: false,
    default: 5
  }
})

const emit = defineEmits([
  'update:value',
  'prefixIconClick',
  'suffixIconClick',
  'validHandle'
])

const inputStyleVar = computed(() => {
  return {
    '--input-width': props.inputBoxWidth + 'px',
    '--input-height': props.inputBoxHeight + 'px',
    '--input-border-radius': props.borderRadius + 'px'
  }
})
// input type
const inputTypeFromProps = ref(props.inputType)
// 校验提示信息
const errorMsg = ref('')
const flag = ref(false)
// 输入框的值
const inputValue = ref('')
// 输入框键入值
const inputValueToInput = (e) => {
  inputValue.value = e.target.value
  emit('update:value', inputValue.value)
}
const validator = (valid) => {
  // 需要校验
  if (valid.required) {
    if (valid.min && !(valid.min <= inputValue.value.length)) {
      return false
    } if (valid.max && !(valid.max >= inputValue.value.length)) {
      return false
    } if (valid.regex && !(valid.regex.test(inputValue.value))) {
      return false
    }
    return true
  }
  return true
}
// 失去焦点
const handleBlur = () => {
  for (let i = 0; i < props.rules.length; i++) {
    // 用户自定义了校验器或内置校验器校验失败
    if (props.rules[i].validator || !validator(props.rules[i])) {
      // 校验失败, 直接显示
      errorMsg.value = props.rules[i].message
      flag.value = true
      emit('validHandle', false)
      return
    }
  }
  emit('validHandle', true)
}
// 获得焦点
const handleFocus = (e) => {
  inputValue.value = e.target.value
  inputTypeFromProps.value = props.inputType
  flag.value = false
  errorMsg.value = ''
}
// 显示或隐藏密码
const isShowPassword = ref(false)
const showInputPassword = () => {
  isShowPassword.value = !isShowPassword.value
  // 如果是显示密码的话, 修改为 'text' 类型
  if (isShowPassword.value) {
    // console.log('text')
    inputTypeFromProps.value = 'text'
  } else {
    // console.log('password')
    inputTypeFromProps.value = 'password'
  }
}
// 清除输入框
const clearInput = () => {
  // console.log('clear')
  inputValue.value = ''
  emit('update:value', inputValue.value)
}
</script>
<template>
  <!-- 默认大小 (+margin): 200px * (30 + 20)px -->
  <!-- border-radius: 5px -->
  <!-- type: text -->
  <div class="input-box" :style="inputStyleVar">
    <EivIcon v-if="prefixIcon !== ''" :icon-class="prefixIcon" :icon-size="inputBoxHeight - 10"
      v-on:click="emit('prefixIconClick')" />

    <input class="eiv-input" :type="inputTypeFromProps" :placeholder="placeholder" :value="value"
      @input="inputValueToInput" @blur="handleBlur" @focus="handleFocus" />

    <!-- 用户设置后图标 -->
    <EivIcon v-if="suffixIcon !== ''" :icon-class="suffixIcon" :icon-size="inputBoxHeight - 10"
      v-on:click="emit('suffixIconClick')" />
    <!-- 用户未设置后图标, 且不是密码框类型和输入框有值, 清空图标 -->
    <EivIcon v-else-if="clearable && inputType !== 'password' && inputValue !== ''"
      icon-class="clear"
      :icon-size="inputBoxHeight - 20"
      v-on:click="clearInput"
      :is-pointer="true" />
    <!-- 用户未设置后图标, 且是密码框类型和输入框有值, 显示密码图标 -->
    <EivIcon v-else-if="showPassword && inputType === 'password' && inputValue !== ''"
      :icon-class="isShowPassword ? 'open-eye' : 'close-eye'"
      :icon-size="inputBoxHeight - 20"
      v-on:click="showInputPassword"
      :is-pointer="true" />

    <div class="error-message" v-show="flag">
      <EivIcon icon-class="warning" :icon-size="20" />
      <span style="margin-left: 5px;">{{ errorMsg }}</span>
    </div>
  </div>
</template>

<style scoped>
.input-box {
  min-width: 200px;
  min-height: 30px;
  padding: 5px;
  margin: 10px 0px;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: medium;

  width: var(--input-width);
  height: var(--input-height);
  border-radius: var(--input-border-radius);
  background: #eff0f1;
  position: relative;
}

.input-box>input {
  width: 100%;
  height: 100%;
  margin: 0px 5px;
  background: transparent;
  border-bottom: 2px solid transparent;
  box-sizing: border-box;
  transition: border 0.3s;
}

.input-box>input:focus {
  border-bottom: 2px var(--main-primary--color) solid;
}

.input-box>.error-message:before {
  width: 0px;
  height: 0px;
  position: absolute;
  top: -16px;
  left: 10px;
  padding: 0;
  border: 8px solid transparent;
  border-color: transparent transparent #fff transparent;
  content: '';
  z-index: 12;
}

.input-box>.error-message:after {
  width: 0px;
  height: 0px;
  position: absolute;
  top: -18px;
  left: 9px;
  padding: 0;
  border: 9px solid transparent;
  border-color: transparent transparent var(--main-tips-border--color) transparent;
  content: '';
  z-index: 10
}

.input-box>.error-message {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  min-width: 60px;
  min-height: 30px;
  position: absolute;
  top: 50px;
  left: 40px;
  z-index: 999;
  padding: 5px;
  border-radius: 3px;
  border: 1px solid var(--main-tips-border--color);
  background: var(--main-tips-bg--color);
  color: var(--main-tips-font--color);
  font-size: small;
  box-shadow: 0px 0px 10px 5px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}
</style>
