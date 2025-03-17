<script setup>
import {ref} from "vue";

const initData = ref({
  uid: '',
  diary: '',
});

const generated = ref('');
const userInput = ref('');
const eventSource = ref(null)

import {onMounted} from "vue";
import {useInfoStore} from '@/store/info';
import {saveContent} from '@/api/diary.js'
import {ElMessage} from 'element-plus'

const infoStore = useInfoStore();

onMounted(() => {
  console.log(infoStore.uid)
  console.log(infoStore.diary)
  initData.value.uid = infoStore.uid;
  initData.value.diary = infoStore.diary;
});

function generateDiaryContent() {
  generated.value = null
  // 创建 SSE 连接
  var url = "http://localhost:5173/api/stickyNote/generateDiaryContentSse?diaryId="
      + initData.value.diary + "&uid=" + initData.value.uid + "&userInput=" + userInput.value
  eventSource.value = new EventSource(url)

  // 监听消息事件
  eventSource.value.onmessage = (event) => {
    generated.value += event.data;
  }

  // 错误处理
  eventSource.value.onerror = (error) => {
    console.error('SSE 连接错误:', error)
    eventSource.value?.close()
  }
}

async function doSaveContent() {
  let param = {uid: initData.value.uid, diaryId: initData.value.diary, content: generated.value}
  await saveContent(param);
  ElMessage({
    message: '执行成功',
    type: 'success',
  })
}
</script>

<template>

  <el-container>
    <el-header>
      <el-row :gutter="20">
        <el-col :span="6">
          当前用户：
          <el-input v-model="initData.uid" placeholder="修改当前用户"/>
        </el-col>
        <el-col :span="6">
          日记ID：
          <el-input v-model="initData.diary" placeholder="修改日记ID"/>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <el-input
          v-model="generated"
          :rows="6"
          type="textarea"
          placeholder="此处生成日记正文"
      />
    </el-main>
    <el-footer>
      <el-input
          v-model="userInput"
          :rows="2"
          type="textarea"
          placeholder="此处用户输入信息"
      />
      <el-button type="primary" @click="generateDiaryContent">生成</el-button>
      <el-button type="success" @click="doSaveContent">保存</el-button>
    </el-footer>
  </el-container>
</template>

<style scoped>

</style>
