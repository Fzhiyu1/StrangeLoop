<template>
  <svg :class="svgClass" aria-hidden="true" >
    <use :xlink:href="iconClassName"  :fill="props.hoverColor || props.color" />
  </svg>
</template>

<script setup lang="ts">
import {computed} from 'vue';

const emit = defineEmits(['over','leave'])

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
  },
  hoverColor: {
    type: [String ,null],
  }
});

// 图标在 iconfont 中的名字
const iconClassName = `#${props.iconName}`;

// 给图标添加上类名
const svgClass = computed(() => {
  return props.className ? `svg-icon ${props.className}` : 'svg-icon';
});
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  position: relative;
  fill: currentColor;
  vertical-align: -2px;
}

.activeStyle{
  background-color: rgba(255,255,255,0.5);
  border-radius: 50%;
}
</style>
