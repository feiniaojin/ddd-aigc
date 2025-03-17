<template>
  <el-tabs v-model="activeTab" @tab-click="handleClick">
    <el-tab-pane label="基本信息" name="basic">
    </el-tab-pane>
    <el-tab-pane label="白板" name="whiteboard">
    </el-tab-pane>
    <el-tab-pane label="正文" name="content">
    </el-tab-pane>
  </el-tabs>
  <router-view/>
</template>
<script setup lang="ts">
import {ref} from "vue";

const activeTab = ref('whiteboard')
import {useRouter} from 'vue-router'
import {TabsPaneContext} from "element-plus";

const router = useRouter()

// 点击标签页时跳转路由

const handleClick = (tab: TabsPaneContext, event: Event) => {
  router.push("/" + activeTab.value)
}
</script>
