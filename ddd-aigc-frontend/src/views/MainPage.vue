<template>
  <div class="container">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="白板" name="whiteboard">
        <div
            class="whiteboard"
            @dragover.prevent
            @drop="handleDrop"
        >
          <transition-group name="list" tag="div" class="sticker-container">
            <Sticker
                v-for="item in whiteboardItems"
                :key="item.id"
                :time="item.time"
                :color="item.color"
                :editable="true"
                :event="item.event"
                :participants="item.participants"
            />
          </transition-group>
        </div>
      </el-tab-pane>
      <el-tab-pane label="正文" name="content">
        <div class="content-pane">正文内容区域</div>
      </el-tab-pane>
    </el-tabs>

    <div class="sticker-pool">
      <Sticker
          v-for="time in 24"
          :key="time"
          :time="formatTime(time - 1)"
          :color="calculateColor(time - 1)"
          draggable="true"
          @dragstart="handleDragStart($event, time - 1)"
      />
    </div>

    <el-dialog v-model="showDialog" title="添加事件" width="30%">
      <el-form :model="form">
        <el-form-item label="事件">
          <el-input v-model="form.event" autocomplete="off" />
        </el-form-item>
        <el-form-item label="参与者">
          <el-input v-model="form.participants" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmEvent">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import Sticker from '@/components/Sticker.vue'

const activeTab = ref('whiteboard')
const whiteboardItems = ref([])
const showDialog = ref(false)
const form = ref({ event: '', participants: '' })
let currentTime = 0

const formatTime = (hour) => {
  return `${hour.toString().padStart(2, '0')}:00`
}

const calculateColor = (hour) => {
  const hue = (hour / 24) * 360
  return `hsl(${hue}, 70%, 50%)`
}

const handleDragStart = (e, hour) => {
  e.dataTransfer.setData('text/plain', hour)
  currentTime = hour
}

const handleDrop = () => {
  showDialog.value = true
}

const confirmEvent = () => {
  whiteboardItems.value.push({
    id: Date.now(),
    time: formatTime(currentTime),
    color: calculateColor(currentTime),
    event: form.value.event,
    participants: form.value.participants.split(',').map(s => s.trim())
  })
  showDialog.value = false
  form.value = { event: '', participants: '' }
}
</script>

<style scoped>
.container {
  padding: 20px;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.whiteboard {
  flex: 1;
  min-height: 400px;
  border: 2px dashed #ccc;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}

.sticker-container {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.sticker-pool {
  display: flex;
  overflow-x: auto;
  gap: 10px;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 8px;
}

.content-pane {
  padding: 20px;
  min-height: 400px;
}

.list-move {
  transition: transform 0.3s ease;
}
</style>
