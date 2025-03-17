import axios from "axios";

// 创建 Axios 实例
const service = axios.create({
    baseURL: "http://localhost:5173/api/", // 接口基础路径
    timeout: 500000, // 超时时间，调试方便，搞长点
});

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        // 可在此处添加 token（示例）
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        // 统一处理响应数据格式
        const res = response.data;
        if (res.code != 0) {
            alert(res.message || "请求失败");
            return Promise.reject(res);
        }
        return res;
    },
    (error) => {
        alert("网络错误");
        return Promise.reject(error);
    }
);

export default service;
