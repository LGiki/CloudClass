package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.entity.User;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
import com.fzu.CloudClass.service.impl.UserServiceImpl;
import com.fzu.CloudClass.util.JwtTokenUtil;
import com.fzu.CloudClass.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/CloudClass/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PersonServiceImpl personService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisUtil redisUtil;



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
            result.put("code", "203");
            return result;
        }

        if(personService.getManPersonByPhone(phone) == null){
            result.put("msg", "用户不存在");
            result.put("code", "201");
            return result;
        }

        if(code.equals(verifyCode)){
            result.put("token", jwtTokenUtil.generateToken(personService.getManPersonByPhone(phone).getPeId().toString()));
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


        if(userService.getUserByPhone(phone) == null){
            result.put("msg", "用户不存在");
            result.put("code", "201");
            return result;
        }
        User user = userService.getUserByPhone(phone);
        //Person user = personService.getManPersonByPhone(phone);
        if(user.getPassword().equals(password)){
            result.put("token", jwtTokenUtil.generateToken(user.getUId().toString()));
            result.put("msg", "ok");
            result.put("code", "200");
        }else{
            result.put("msg", "密码错误");
            result.put("code", "202");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public JSONObject registerUser(@RequestBody JSONObject jsonParam){

        JSONObject result = new JSONObject();
        String phone = (String) jsonParam.get("phone");
        String password = (String) jsonParam.get("password");

        if(personService.getManPersonByPhone(phone) == null){
            result.put("msg", "用户不存在");
            result.put("code", "201");
            return result;
        }
        Person user = personService.getManPersonByPhone(phone);;
        if(user.getPassword().equals(password)){
            result.put("token", jwtTokenUtil.generateToken(user.getPeId().toString()));
            result.put("msg", "ok");
            result.put("code", "200");
        }else{
            result.put("msg", "密码错误");
            result.put("code", "202");
        }
        return result;
    }


}
