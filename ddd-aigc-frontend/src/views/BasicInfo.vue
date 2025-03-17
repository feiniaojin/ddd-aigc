<template>
  <el-form :model="form" label-width="120px">
    <el-form-item label="用户id">
      <el-input v-model="form.uid" placeholder="输入用户id，模拟已登录用户"/>
    </el-form-item>

    <el-form-item label="选择时间">
      <el-col :span="11">
        <el-date-picker
            v-model="form.diaryDate"
            type="date"
            placeholder="模拟用户初始化某天的日志"
            style="width: 100%"
            format="YYYYMMDD"
            value-format="YYYYMMDD"
        />
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">Create</el-button>
      <el-button>Cancel</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import {reactive} from 'vue'
import {createDiary} from '@/api/diary'
import {ElMessage} from 'element-plus'
import {useInfoStore} from '@/store/info';

const infoStore = useInfoStore();

// do not use same name with ref
const form = reactive({
  uid: '',
  diaryDate: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

async function onSubmit() {
  console.log('submit!')
  let res = await createDiary(form);
  console.log(res.data)
  ElMessage({
    message: '执行成功',
    type: 'success',
  })
  infoStore.uid = form.uid;
  console.log(res.data.diaryId)
  infoStore.diary = res.data.diaryId;
  console.log(infoStore.diary)
}
</script>
<style>

</style>
