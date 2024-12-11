<template>
  <svg :class="svgClass" aria-hidden="true" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <use :xlink:href="iconClassName" :fill="iconColor" />
  </svg>
</template>

<script setup lang="ts">
import {computed, ref} from 'vue';

const props = defineProps({
  iconName: {
    type: String,
    required: true
  },
  className: {
    type: String,
    default: ''
  },
  color: {
    type: String,
    default: '#409eff'
  },
  hoverColor:{
    type:String,
    default:'#409eff'
  }
});

// 图标在 iconfont 中的名字
const iconClassName = `#${props.iconName}`;

// 给图标添加上类名
const svgClass = computed(() => {
  return props.className ? `svg-icon ${props.className}` : 'svg-icon';
});

// 默认颜色和悬浮后的颜色
const iconColor = ref(props.color);

// 鼠标悬浮事件
const handleMouseOver = () => {
  iconColor.value = props.hoverColor; // 鼠标悬浮时变为白色
};

// 鼠标移出事件
const handleMouseLeave = () => {
  iconColor.value = props.color; // 鼠标移出时恢复原色
};
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  position: relative;
  fill: currentColor;
  vertical-align: -2px;
}
</style>
