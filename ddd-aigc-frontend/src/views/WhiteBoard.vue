<template>
  <div class="container">
    <!-- 白板区域在上方 -->
    <div
        class="whiteboard"
        @drop="handleDrop"
        @dragover.prevent
    >
      <div
          v-for="sticker in boardStickers"
          :key="sticker.id"
          class="sticker-on-board"
          :style="stickerStyle(sticker)"
      >
        <div class="participant">{{ sticker.participant }}</div>
        <div class="event">{{ sticker.event }}</div>
      </div>
    </div>

    <!-- 贴纸列表在下方 -->
    <div class="sticker-palette">
      <div
          v-for="sticker in stickerList"
          :key="sticker.id"
          class="sticker-item"
          :style="{ backgroundColor: sticker.color }"
          draggable="true"
          @dragstart="handleDragStart($event, sticker)"
      ></div>
    </div>

    <!-- 输入表单弹窗 -->
    <el-dialog
        v-model="showForm"
        title="填写信息"
        width="30%"
    >
      <el-form :model="formData">
        <el-form-item label="时间">
          <el-time-picker
              v-model="formData.occurrenceTimeStr"
              placeholder="选择发生的时间"
              style="width: 100%"
              value-format="HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="参与者">
          <el-input v-model="formData.participants"/>
        </el-form-item>
        <el-form-item label="事件">
          <el-input v-model="formData.event"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showForm = false">取消</el-button>
        <el-button type="primary" @click="confirmSticker">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {createStickyNote} from "@/api/stickynote.js";
import {useInfoStore} from '@/store/info';

const infoStore = useInfoStore();

// 贴纸数据
const stickerList = ref([
  {id: 1, color: '#FFB3BA'},
  {id: 2, color: '#BAFFC9'},
  {id: 3, color: '#BAE1FF'},
  {id: 4, color: '#FFFFBA'},
  {id: 5, color: '#E0BAFF'}
]);

// 白板数据
const boardStickers = ref([]);
const currentSticker = ref(null);
const showForm = ref(false);
const formData = ref({
  occurrenceTimeStr: '',
  participants: '',
  event: ''
});

// 拖拽处理
const handleDragStart = (e, sticker) => {
  currentSticker.value = sticker;
  e.dataTransfer.setData('text/plain', '');
};

const handleDrop = (e) => {
  if (!currentSticker.value) return;
  showForm.value = true;
  formData.value = {
    x: e.offsetX - 50,
    y: e.offsetY - 25,
    participant: '',
    event: ''
  };
};

// 表单确认
const confirmSticker = () => {
  boardStickers.value.push({
    ...currentSticker.value,
    ...formData.value,
    id: Date.now()
  });
  let uid = infoStore.uid;
  let diary = infoStore.diary;
  debugger
  let participants = formData.value.participants;
  let event = formData.value.event;
  let occurrenceTimeStr=formData.value.occurrenceTimeStr;
  var param = {uid, diaryId: diary, participants, content: event,occurrenceTimeStr}
  createStickyNote(param)
  showForm.value = false;
  currentSticker.value = null;
  formData.value = {participants: '', event: ''};
};

// 贴纸样式
const stickerStyle = (sticker) => ({
  left: `${sticker.x}px`,
  top: `${sticker.y}px`,
  backgroundColor: sticker.color
});
</script>

<style scoped>
.container {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.whiteboard {
  flex: 1;
  background: #f8f9fa;
  border: 2px dashed #ddd;
  margin: 20px;
  position: relative;
}

.sticker-palette {
  height: 120px;
  padding: 20px;
  background: #fff;
  display: flex;
  gap: 15px;
  overflow-x: auto;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
}

.sticker-item {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  cursor: move;
  flex-shrink: 0;
  transition: transform 0.2s;
}

.sticker-item:hover {
  transform: scale(1.1);
}

.sticker-on-board {
  position: absolute;
  width: 100px;
  height: 100px;
  border-radius: 12px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16);
  cursor: move;
}

.participant {
  font-weight: bold;
  font-size: 14px;
  margin-bottom: 5px;
}

.event {
  font-size: 12px;
  text-align: center;
  word-break: break-word;
}
</style>
