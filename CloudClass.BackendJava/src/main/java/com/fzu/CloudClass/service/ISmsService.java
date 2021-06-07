package com.fzu.CloudClass.service;


import com.baomidou.mybatisplus.extension.service.IService;

public interface ISmsService {
    String sendSms(String phone);
}
