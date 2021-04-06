package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
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
@RequestMapping("/CloudClass/person")
public class PersonController {

    @Autowired
    PersonServiceImpl personService;

    @ResponseBody
    @PostMapping(value = "/register")
    public JSONObject register(@RequestBody JSONObject jsonParam){

        Person person = new Person();
        person.setPeNumber((String) jsonParam.get("pe_number"));
        person.setGender((Integer.parseInt((String) jsonParam.get("gender"))));
        person.setIsTeacher((Integer.parseInt((String) jsonParam.get("is_teacher"))));

        personService.addPerson(person);
        int id = person.getPeId();
        System.out.println(id);
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public JSONObject login(@RequestBody JSONObject jsonParam){

        String username = (String) jsonParam.get("username");
        String pwd = (String) jsonParam.get("password");

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @PostMapping(value = "/modifyPwd")
    public JSONObject modifyPwd(@RequestBody JSONObject jsonParam){

        int u_id = Integer.parseInt((String) jsonParam.get("u_id"));
        String password = (String) jsonParam.get("password");
        String new_password = (String) jsonParam.get("new_password");

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }




}
