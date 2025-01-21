
<script setup>

</script>

<template>
  <div class="loading-page">
    <!-- 加载动画 -->
    <div class="loading-content">
      <div class="loader">
        <div class="dot"></div>
        <div class="dot"></div>
        <div class="dot"></div>
      </div>
      <p class="loading-text">加载中，请稍候...</p>
    </div>
  </div>
</template>

<style scoped>
.loading-page {
  opacity: 0.3;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  background: linear-gradient(135deg, #6a11cb, #2575fc);
  color: white;
  font-family: 'Arial', sans-serif;
}

.loading-content {
  text-align: center;
}

.loader {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}

.dot {
  width: 12px;
  height: 12px;
  background: white;
  border-radius: 50%;
  animation: bounce 1.4s infinite ease-in-out;
}

.dot:nth-child(2) {
  animation-delay: 0.2s;
}

.dot:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes bounce {
  0%,
  80%,
  100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-20px);
  }
}

.loading-text {
  margin-top: 20px;
  font-size: 1.2em;
  color: rgba(255, 255, 255, 0.8);
}

.loaded-content {
  text-align: center;
}

.loaded-content h1 {
  font-size: 2.5em;
  margin-bottom: 10px;
}

.loaded-content p {
  font-size: 1.2em;
  color: rgba(255, 255, 255, 0.9);
}
</style>