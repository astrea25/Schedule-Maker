<template>
  <div class="register-container">
    <form @submit.prevent="register" class="register-form">
      <h2>Register</h2>
      <div class="form-group">
        <input type="text" v-model="username" placeholder="Username" required>
      </div>
      <div class="form-group">
        <input type="password" v-model="password" placeholder="Password" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="name" placeholder="Full Name" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="course" placeholder="Course" required>
      </div>
      <div class="form-group">
        <input type="tel" v-model="phoneNumber" placeholder="Phone Number" required>
      </div>
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const username = ref('')
const password = ref('')
const name = ref('')
const course = ref('')
const phoneNumber = ref('')

const register = async () => {
  try {
    const formData = new URLSearchParams()
    formData.append('un', username.value)
    formData.append('pw', password.value)
    formData.append('n', name.value)
    formData.append('c', course.value)
    formData.append('pn', phoneNumber.value)

    await axios.post('http://127.0.0.1:9997/user/create', formData)
    router.push('/login')
  } catch (error) {
    alert(error.response?.data || 'Registration failed')
  }
}
</script>

<style scoped>
.register-container {
 display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}

.register-form {
  width: 100%;
  max-width: 400px;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background: var(--color-background-soft);
}

.form-group {
  margin-bottom: 1rem;
}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: var(--color-background);
  color: var(--color-text);
}

button {
  width: 100%;
  padding: 0.5rem;
  background: var(--vt-c-green);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  opacity: 0.9;
}
</style> 