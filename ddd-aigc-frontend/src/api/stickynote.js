import request from "@/utils/request";

export function createStickyNote(data) {
    return request({
        url: "/stickyNote/create",
        method: "POST",
        data,
    });
}
