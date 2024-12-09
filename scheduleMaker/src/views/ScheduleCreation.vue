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
          <th class="border px-4 py-2">Section Pk</th>
          <th class="border px-4 py-2">Section</th>
          <th class="border px-4 py-2">Schedule</th>
          <th class="border px-4 py-2 w-[15%]">Time</th>
          <th class="border px-4 py-2">Subject</th>
          <th class="border px-4 py-2">Department</th>
          <th class="border px-4 py-2">Room</th>
          <th class="border px-4 py-2">Instructor</th>
          <th class="border px-4 py-2">Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in filteredSchedule" :key="item.sectionPk" class="hover:bg-gray-50">
          <td class="border px-4 py-2">{{ item.sectionPk }}</td>
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
          <td class="border px-4 py-2">
            <button 
              @click="selectClass(item)" 
              class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            >
              Select
            </button>
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
const userData = ref(null);
const userPk = ref(null);


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

const isTimeOverlap = (newSection) => {
  if (!userData.value?.sections) return false;

  const hasOverlap = userData.value.sections.some(existingSection => {
    const existingStart = parseInt(existingSection.timeStart);
    const existingEnd = parseInt(existingSection.timeEnd);
    const newStart = parseInt(newSection.timeStart);
    const newEnd = parseInt(newSection.timeEnd);

    const daysOverlap = existingSection.dayOfTheWeekSchedule.split('-').some(day =>
      newSection.dayOfTheWeekSchedule.includes(day)
    );

    if (!daysOverlap) return false;

    const startsAtSameTime = newStart === existingStart;
    const endsAtSameTime = newEnd === existingEnd;
    const startsWithinExisting = newStart > existingStart && newStart < existingEnd;
    const endsWithinExisting = newEnd > existingStart && newEnd < existingEnd;
    const encompassesExisting = newStart <= existingStart && newEnd >= existingEnd;

    const hasTimeOverlap = startsAtSameTime || endsAtSameTime || 
                          startsWithinExisting || endsWithinExisting || 
                          encompassesExisting;

    if (hasTimeOverlap) {
      console.log('Schedule conflict detected:', {
        existing: `${existingSection.subject.subjectCode} (${formatTime(existingSection.timeStart)}-${formatTime(existingSection.timeEnd)})`,
        new: `${newSection.subject.subjectCode} (${formatTime(newSection.timeStart)}-${formatTime(newSection.timeEnd)})`
      });
    }

    return hasTimeOverlap;
  });

  if (hasOverlap) {
    alert(`Schedule Conflict: This class overlaps with your existing schedule.
Please check your current schedule and choose a different section.`);
  }

  return hasOverlap;
};

onMounted(async () => {
  try {
    const sessionId = localStorage.getItem('sessionId');
    
    const validateResponse = await axios.get('http://127.0.0.1:9997/user/validate', {
      params: { sid: sessionId }
    });
    userPk.value = validateResponse.data.userPk;

    const [scheduleResponse, userResponse] = await Promise.all([
      axios.get('http://127.0.0.1:9999/section/retrieveAll'),
      axios.get('http://127.0.0.1:9997/user/retrieve', { 
        params: { pk: userPk.value } 
      })
    ]);

    schedule.value = scheduleResponse.data;
    userData.value = userResponse.data;

    departments.value = [...new Set(schedule.value.map(item => item.subject.department))].sort();
  } catch (error) {
    console.error('Error fetching data:', error);
    if (error.response?.status === 401) {
      console.error('Unauthorized access, please login again');
    }
  }
});

const selectClass = async (item) => {
  if (isTimeOverlap(item)) {
    return;
  }

  try {
    const formData = new URLSearchParams();
    formData.append('stupk', userPk.value);
    formData.append('secpk', item.sectionPk);
    
    console.log('Sending data:', formData.toString());

    const response = await axios.post('http://127.0.0.1:9997/user/addSection', 
      formData,
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
    );
    
    console.log('Response:', response.data);
    
    const userResponse = await axios.get('http://127.0.0.1:9997/user/retrieve', {
      params: { pk: userPk.value }
    });
    userData.value = userResponse.data;
    
    alert('Class successfully added to your schedule!');
  } catch (error) {
    console.error('Error:', error);
    alert('Failed to add class. Please try again.');
  }
};
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

button {
  transition: background-color 0.3s ease;
}

button:hover {
  transform: scale(1.05);
}
</style>
