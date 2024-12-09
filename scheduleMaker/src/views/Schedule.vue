<template>
  <div class="about">
    <h1 class="text-2xl font-bold mb-4">Schedule List</h1>
    
    <!-- Add department filter -->
    <div class="mb-4">
      <label class="mr-2">Filter by Department:</label>
      <select v-model="selectedDepartment" class="border p-2 rounded">
        <option value="">All Departments</option>
        <option v-for="dept in departments" :key="dept" :value="dept">
          {{ dept }}
        </option>
      </select>
    </div>

    <table class="min-w-full bg-white border border-gray-300">
      <thead>
        <tr class="bg-gray-100">
          <th class="border px-4 py-2">Section</th>
          <th class="border px-4 py-2">Schedule</th>
          <th class="border px-4 py-2 w-[15%]">Time</th>
          <th class="border px-4 py-2">Subject</th>
          <th class="border px-4 py-2">Department</th>
          <th class="border px-4 py-2">Room</th>
          <th class="border px-4 py-2">Instructor</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in filteredSchedule" :key="item.sectionPk" class="hover:bg-gray-50">
          <td class="border px-4 py-2">{{ item.sectionName }}</td>
          <td class="border px-4 py-2">{{ item.dayOfTheWeekSchedule }}</td>
          <td class="border px-4 py-2">{{ formatTime(item.timeStart) }} - {{ formatTime(item.timeEnd) }}</td>
          <td class="border px-4 py-2">
            {{ item.subject.subjectCode }}<br>
            <span class="text-sm text-gray-600">{{ item.subject.courseTitle }}</span>
          </td>
          <td class="border px-4 py-2">{{ item.subject.department }}</td>
          <td class="border px-4 py-2">{{ item.room.roomName }}</td>
          <td class="border px-4 py-2">
            <div v-for="instructor in item.instructors" :key="instructor.instructorPk">
              {{ instructor.instructorName }}
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

const schedule = ref([]);
const selectedDepartment = ref('');
const departments = ref([]);

const formatTime = (time) => {
  if (!time) return '';
  return `${time.substring(0, 2)}:${time.substring(2)}`;
};

const filteredSchedule = computed(() => {
  if (!selectedDepartment.value) {
    return schedule.value;
  }
  return schedule.value.filter(item => 
    item.subject.department === selectedDepartment.value
  );
});

onMounted(() => {
  axios
    .get('http://127.0.0.1:9999/section/retrieveAll')
    .then((response) => {
      schedule.value = response.data;
      departments.value = [...new Set(response.data.map(item => item.subject.department))].sort();
    })
    .catch((error) => {
      console.error('Error fetching posts:', error);
    });
});
</script>

<style scoped>

select {
  min-width: 150px;
}

.mb-4 {
  margin-bottom: 1rem;
}

.mr-2 {
  margin-right: 0.5rem;
}

.rounded {
  border-radius: 0.25rem;
}

.p-2 {
  padding: 0.5rem;
}
</style>
