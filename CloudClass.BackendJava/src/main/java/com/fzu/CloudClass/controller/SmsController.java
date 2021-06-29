package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.service.ISmsService;
import com.fzu.CloudClass.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/Sms")
public class SmsController {
    @Autowired
    private ISmsService smsService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public JSONObject sendSms(@RequestBody JSONObject jsonParam){
        String phone = (String) jsonParam.get("phone");
        String type = (String) jsonParam.get("type");
        String code = smsService.sendSms(phone);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("type", type);
        redisUtil.set(phone, jsonObject.toString(), 300);
        JSONObject result = new JSONObject();
        result.put("msg", "success");
        result.put("code", "200");
        return result;
    }

}
