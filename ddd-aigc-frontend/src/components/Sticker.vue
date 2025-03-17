<template>
  <div
      class="sticker"
      :style="{
      backgroundColor: color,
      cursor: draggable ? 'grab' : 'default'
    }"
      @dragstart="$emit('dragstart', $event)"
  >
    <div class="time">{{ time }}</div>
    <div v-if="editable" class="content">
      <div class="event">{{ event }}</div>
      <div class="participants">
        <span v-for="(p, index) in participants" :key="index">👤{{ p }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  time: String,
  color: String,
  draggable: Boolean,
  editable: Boolean,
  event: String,
  participants: Array
})
</script>

<style scoped>
.sticker {
  width: 120px;
  min-height: 80px;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  user-select: none;
}

.time {
  font-weight: bold;
  color: white;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
}

.content {
  margin-top: auto;
  color: white;
}

.event {
  font-size: 14px;
  margin: 5px 0;
}

.participants {
  font-size: 12px;
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}
</style>
