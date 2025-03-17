import request from "@/utils/request";

export function createDiary(data) {
    return request({
        url: "/diary/create",
        method: "POST",
        data,
    });
}

export function saveContent(data) {
    return request({
        url: "/diary/saveContent",
        method: "POST",
        data,
    });
}
