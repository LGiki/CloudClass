//获取已结束的签到
import request from "../utils/request";
//获取个人信息
export function getPersonInfo() {
    return request({
        url: "/PersonInfo",
        method: "get",
    });
}
//修改个人信息
export function setPersonInfo(name,classes,grade,isTeacher) {
    return request({
        url: "/PersonInfo",
        method: "put",
        data: {
            peName: name,//名字
            classes: classes,//班级
            grade: grade,//年级
            isTeacher: isTeacher,//是否教师

        },
    });
}
//修改密码
export function changePassword(newPassword) {
    return request({
        url: "personPwd",
        method: "put",
        data: { newPassword: newPassword },
    });
}
//找回密码
export function findPassword(phone,newPassword) {
    return request({
        url: "personPwdByPhone",
        method: "put",
        data: { phone:phone ,newPassword: newPassword },
    });
}


