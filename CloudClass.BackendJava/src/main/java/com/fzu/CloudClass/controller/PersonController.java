package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
import com.fzu.CloudClass.service.impl.SchoolServiceImpl;
import com.fzu.CloudClass.util.JwtTokenUtil;
import com.fzu.CloudClass.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    SchoolServiceImpl schoolService;

    @Autowired
    RedisUtil redisUtil;

    @Resource
    JwtTokenUtil jwtTokenUtil;

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
    public JSONObject getAllPerson(@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize){


        JSONObject result = new JSONObject();
        result.put("total",personService.countAllPerson(pageNo,pageSize));
        result.put("data", personService.getAllPerson(pageNo, pageSize));
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/PersonMan", method = RequestMethod.POST)
    public JSONObject addPerson(@RequestBody JSONObject jsonParam){

        Person person = new Person();

        String pe_name = (String) jsonParam.get("peName");
        int is_teacher = Integer.parseInt((String) jsonParam.get("isTeacher"));
        String username = (String) jsonParam.get("username");
        String phone = (String) jsonParam.get("phone");
        if(is_teacher==1)
            person.setType(2);
        else if(is_teacher==0)
            person.setType(3);
        else
            person.setType(1);
        person.setPeName(pe_name);
        person.setPhone(phone);
        person.setPassword("12345678");
        person.setUsername(username);
        person.setIsTeacher(is_teacher);
        personService.addPerson(person);

        JSONObject result = new JSONObject();

        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/PersonMan", method = RequestMethod.PUT)
    public JSONObject modifyPerson(@RequestBody JSONObject jsonParam){

        Person person = new Person();

        int pe_id = Integer.parseInt((String) jsonParam.get("peId"));
        String pe_name = (String) jsonParam.get("peName");
        int is_teacher = Integer.parseInt((String) jsonParam.get("isTeacher"));
        String username = (String) jsonParam.get("username");
        String phone = (String) jsonParam.get("phone");
        String password = (String) jsonParam.get("password");
        if(is_teacher==1)
            person.setType(2);
        else if(is_teacher==0)
            person.setType(3);
        else
            person.setType(1);
        person.setPeId(pe_id);
        person.setPeName(pe_name);
        person.setIsTeacher(is_teacher);
        person.setPhone(phone);
        person.setPassword(password);
        person.setUsername(username);
        personService.modifyPerson(person);

        JSONObject result = new JSONObject();

        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/personPwd",method = RequestMethod.PUT)
    public JSONObject modifyPwd(@RequestBody JSONObject jsonParam, HttpServletRequest request){
        JSONObject result = new JSONObject();
        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        Person person = personService.getPersonById(pe_id);
        String password = (String) jsonParam.get("password");
        if(!password.equals(person.getPassword())){
            result.put("msg", "原密码错误！");
            result.put("code", "201");
            return result;
        }

        String new_password = (String) jsonParam.get("newPassword");
        if(password.equals(new_password)){
            result.put("msg", "与原密码相同！");
            result.put("code", "202");
            return result;
        }

        person.setPassword(new_password);
        personService.modifyPerson(person);


        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/personPwdByPhone",method = RequestMethod.PUT)
    public JSONObject modifyPwdByPhone(@RequestBody JSONObject jsonParam, HttpServletRequest request){
        JSONObject result = new JSONObject();
        String phone = (String) jsonParam.get("phone");
        String verifycode = (String) jsonParam.get("verifyCode");

        String jsonString = (String) redisUtil.get(phone);
        JSONObject json = (JSONObject) JSON.parse(jsonString);
        String code = (String) json.get("code");
        String type = (String) json.get("type");

        if(!type.equals("findPwd")){
            result.put("msg", "验证码错误");
            result.put("code", "203");
            return result;
        }

        if(!code.equals(verifycode)){
            result.put("msg", "验证码错误");
            result.put("code", "203");
            return result;
        }

        Person person = personService.getPersonByPhone((String) jsonParam.get("phone"));
        //String password = (String) jsonParam.get("password");
        String new_password = (String) jsonParam.get("newPassword");


        person.setPassword(new_password);
        personService.modifyPerson(person);


        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/PersonInfo",method = RequestMethod.PUT)
    public JSONObject modifyStudentInfo(@RequestBody JSONObject jsonParam, HttpServletRequest request){

        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));

        Person person = new Person();

        try{ String pe_number = (String) jsonParam.get("peNumber");person.setPeNumber(pe_number); }catch(Exception e){ }
        try{ String pe_name = (String) jsonParam.get("peName");person.setPeName(pe_name); }catch(Exception e){ }
        try{ int gender = Integer.parseInt((String) jsonParam.get("gender"));person.setGender(gender); }catch(Exception e){ }
        try{ String grade = (String) jsonParam.get("grade");person.setGrade(grade); }catch(Exception e){ }
        try{ int major = Integer.parseInt((String) jsonParam.get("major"));person.setMajor(major); }catch(Exception e){ }
        try{ String classes = (String) jsonParam.get("classes");person.setClasses(classes); }catch(Exception e){ }
        try{ int isTeacher = Integer.parseInt((String) jsonParam.get("isTeacher"));person.setIsTeacher(isTeacher); }catch(Exception e){ }

        person.setPeId(pe_id);

        personService.modifyPerson(person);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/PersonInfo",method = RequestMethod.GET)
    public JSONObject getPersonInfo(HttpServletRequest request){

        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));

        Person data = personService.getPersonById(pe_id);
        int university_id = -1;
        try{
            university_id = schoolService.getSchoolById(data.getMajor()).getSId();
        }catch(Exception e){

        }


        JSONObject result = new JSONObject();
        try{
            result.put("universityName",schoolService.getSchoolById(university_id).getSName());
            result.put("collegeName",schoolService.getSchoolById(data.getMajor()).getSName());
        }catch(Exception e){
            result.put("universityName",-1);
            result.put("collegeName",-1);
        }

        result.put("data",data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;

    }
}
