package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
import com.fzu.CloudClass.util.JwtTokenUtil;
import com.fzu.CloudClass.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/authentication")
public class AuthenticationController {

    @Autowired
    PersonServiceImpl personService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject loginByUsername(@RequestBody JSONObject jsonParam){

        JSONObject result = new JSONObject();
        String account = (String) jsonParam.get("username");
        String password = (String) jsonParam.get("password");

        Person person1 = personService.getPersonByUsername(account);
        Person person2 = personService.getPersonByPhone(account);
        if(person1 == null && person2 == null){
            result.put("msg", "用户不存在");
            result.put("code", "201");
            return result;
        }
        Person person = new Person();
        if(person1 != null){
            person = person1;
        }else{
            person = person2;
        }

        if(person.getPassword().equals(password) == true){
            result.put("role",person.getIsTeacher());
            result.put("token", jwtTokenUtil.generateToken(String.valueOf(person.getPeId())));
            result.put("msg", "ok");
            result.put("code", "200");
        }else{
            result.put("msg", "密码错误");
            result.put("code", "202");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
    public JSONObject loginByVerifyCode(@RequestBody JSONObject jsonParam){

        JSONObject result = new JSONObject();
        String phone = (String) jsonParam.get("phone");
        String verifyCode = (String) jsonParam.get("verifyCode");

        String jsonString = (String) redisUtil.get(phone);
        JSONObject json = (JSONObject) JSON.parse(jsonString);
        String code = (String) json.get("code");
        String type = (String) json.get("type");

        if(!type.equals("login")){
            result.put("msg", "验证码错误");
            result.put("code", "202");
            return result;
        }

        if(personService.getPersonByPhone(phone) == null){
            result.put("msg", "用户不存在");
            result.put("code", "201");
            return result;
        }

        if(code.equals(verifyCode)){
            result.put("role",personService.getPersonByPhone(phone).getIsTeacher());
            result.put("token", jwtTokenUtil.generateToken(personService.getPersonByPhone(phone).getPeId().toString()));
            result.put("msg", "ok");
            result.put("code", "200");
        }else{
            result.put("msg", "验证码错误");
            result.put("code", "202");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/phone", method = RequestMethod.POST)
    public JSONObject loginByPhone(@RequestBody JSONObject jsonParam){

        JSONObject result = new JSONObject();
        String phone = (String) jsonParam.get("phone");
        String password = (String) jsonParam.get("password");

        if(personService.getPersonByPhone(phone) == null){
            result.put("msg", "用户不存在");
            result.put("code", "201");
            return result;
        }
        Person person = personService.getPersonByPhone(phone);
        if(person.getPassword().equals(password) == true){
            result.put("role",person.getIsTeacher());
            result.put("token", jwtTokenUtil.generateToken(person.getPeId().toString()));
            result.put("msg", "ok");
            result.put("code", "200");
        }else{
            result.put("msg", "密码错误");
            result.put("code", "202");
        }
        return result;
    }

}
