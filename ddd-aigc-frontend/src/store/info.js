// store/info.js
import { defineStore } from 'pinia';
import storage from '@/utils/storage';

export const useInfoStore = defineStore('info', {
    state: () => ({
        uid: storage.get('uid') || '',
        diary: storage.get('diary') || null
    }),
    actions: {
        setUid(uid) {
            this.uid = uid;
            storage.set('uid', uid);
        },
        setDiary(diary) {
            this.diary = diary;
            storage.set('diary', diary);
        },
        clearInfo() {
            this.uid = '';
            this.diary = null;
            storage.remove('uid');
            storage.remove('diary'); // 注意：原代码错误地移除了 'user'
        }
    },
    persist: true
});
