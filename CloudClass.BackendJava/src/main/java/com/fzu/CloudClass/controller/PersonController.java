package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
import com.fzu.CloudClass.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * ʦ 前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/CloudClass")
public class PersonController {

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public JSONObject register(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        String phone = (String) jsonParam.get("phone");
        String username = (String) jsonParam.get("username");
        String verifycode = (String) jsonParam.get("verifyCode");

        String jsonString = (String) redisUtil.get(phone);
        JSONObject json = (JSONObject) JSON.parse(jsonString);
        String code = (String) json.get("code");
        String type = (String) json.get("type");

        if(!type.equals("register")){
            result.put("msg", "验证码错误");
            result.put("code", "203");
            return result;
        }

        if(!code.equals(verifycode)){
            result.put("msg", "验证码错误");
            result.put("code", "203");
            return result;
        }

        if(personService.getPersonByPhone(phone) != null){
            result.put("msg", "手机号已注册");
            result.put("code", "201");
            return result;
        }
        if(personService.getPersonByUsername(username) != null){
            result.put("msg", "用户名已注册");
            result.put("code", "202");
            return result;
        }

        Person person = new Person();
        person.setUsername((String) jsonParam.get("username"));
        person.setPassword((String) jsonParam.get("password"));
        person.setPhone((String) jsonParam.get("phone"));
//        person.setGender((Integer.parseInt((String) jsonParam.get("gender"))));
        person.setIsTeacher((Integer.parseInt((String) jsonParam.get("is_teacher"))));
        person.setType(0);

        personService.addPerson(person);
        int id = person.getPeId();
        System.out.println(id);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/fast", method = RequestMethod.POST)
    public JSONObject fastRegister(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        String phone = (String) jsonParam.get("phone");
        String verifycode = (String) jsonParam.get("verifyCode");
        if(personService.getPersonByPhone(phone) != null){
            result.put("msg", "手机号已注册");
            result.put("code", "201");
            return result;
        }
        String jsonString = (String) redisUtil.get(phone);
        JSONObject json = (JSONObject) JSON.parse(jsonString);
        String code = (String) json.get("code");
        String type = (String) json.get("type");

        if(!type.equals("login")){
            result.put("msg", "验证码错误");
            result.put("code", "201");
            return result;
        }
        if(verifycode.equals(code)){
            result.put("msg", "ok");
            result.put("code", "200");
        }else{
            result.put("msg", "验证码错误");
            result.put("code", "201");
            return result;
        }

        Person person = new Person();
        person.setPhone((String) jsonParam.get("phone"));
        person.setIsTeacher((Integer.parseInt((String) jsonParam.get("is_teacher"))));
        person.setType(0);
        personService.addPerson(person);
        int id = person.getPeId();
        //System.out.println(id);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/allPerson", method = RequestMethod.GET)
    public JSONObject getAllPerson(@RequestBody JSONObject jsonParam){

        int pageNo = Integer.parseInt((String) jsonParam.get("pageNo"));
        int pageSize = Integer.parseInt((String) jsonParam.get("pageSize"));



        JSONObject result = new JSONObject();
        result.put("data", personService.getAllPerson(pageNo, pageSize));
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @PostMapping(value = "/modifyPwd")
    public JSONObject modifyPwd(@RequestBody JSONObject jsonParam){

        int u_id = Integer.parseInt((String) jsonParam.get("u_id"));
        String password = (String) jsonParam.get("password");
        String new_password = (String) jsonParam.get("newPassword");

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

}
