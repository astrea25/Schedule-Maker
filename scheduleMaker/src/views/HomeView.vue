<script setup>
</script>

<template>
  <main class="home-container">
    <div class="content-wrapper">
      <h1 class="title">Class Schedule Maker</h1>
      <p class="subtitle">For all your AISIS enlistment needs</p>
    </div>
  </main>
</template>

<style scoped>
.home-container {
  min-height: 90vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: var(--color-background);
}

.content-wrapper {
  text-align: center;
  padding: 2rem;
}

.title {
  font-size: 3.5rem;
  font-weight: 700;
  color: var(--color-heading);
  margin-bottom: 1rem;
  letter-spacing: -0.5px;
}

.subtitle {
  font-size: 1.5rem;
  color: var(--color-text);
  opacity: 0.9;
}
</style>
