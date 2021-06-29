package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.entity.PersonCourse;
import com.fzu.CloudClass.entity.SignIn;
import com.fzu.CloudClass.entity.SignRecord;
import com.fzu.CloudClass.mapper.PersonCourseMapper;
import com.fzu.CloudClass.service.impl.*;
import com.fzu.CloudClass.util.JwtTokenUtil;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * ǩ 前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/sign-in")
public class SignInController {

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    SignInServiceImpl signInService;

    @Autowired
    SignRecordServiceImpl signRecordService;

    @Autowired
    PersonCourseMapper personCourseMapper;

    @Autowired
    PersonCourseServiceImpl personCourseService;

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    PersonServiceImpl personService;

    //1.一键位置签到 2.限时签到
    @ResponseBody
    @RequestMapping(value = "/SignIn", method = RequestMethod.POST)
    public JSONObject addSign(@RequestBody JSONObject jsonParam, HttpServletRequest request){

        int type = Integer.parseInt((String) jsonParam.get("type"));
        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        LocalDateTime now = LocalDateTime.now();
        int c_id = Integer.parseInt((String) jsonParam.get("cId"));
        Double lng = Double.parseDouble((String) jsonParam.get("lng"));
        Double lat = Double.parseDouble((String) jsonParam.get("lat"));

        List<SignIn> signInList = signInService.getAllSignInByCId(c_id);
        int count = 0;

        for(int i = 0; i < signInList.size(); i++){
            SignIn one = signInList.get(i);
            if(one.getType() == 1 && one.getCode().equals("1"))
                count++;
            if(one.getType() == 2 && now.isBefore(one.getDate().plusSeconds(one.getTime())) && !one.getCode().equals("0"))
                count++;
        }
        JSONObject result = new JSONObject();
        if(count!=0){
            result.put("msg", "当前已存在签到，请先停止");
            result.put("code", "201");
            return result;
        }
        int data = 0;
        if(type == 1){
            data = signInService.addSignIn(type,"",lng,lat,now,pe_id,c_id,-1);
        }else if(type == 2){
            int time = Integer.parseInt((String) jsonParam.get("time"));
            data = signInService.addSignIn(type,"",lng,lat,now,pe_id,c_id,time);
        }

        result.put("StudentNum",personCourseService.countStudentByCId(c_id));
        result.put("siId", data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;

    }

    @ResponseBody
    @RequestMapping(value = "/SignIn", method = RequestMethod.GET)
    public JSONObject getAllSignInByCId(@RequestParam("cId") int c_id, HttpServletRequest request){

        List<SignIn> data = signInService.getAllSignInByCId(c_id);

        JSONObject result = new JSONObject();
        result.put("data",data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/ActiveSignIn", method = RequestMethod.GET)
    public JSONObject getAllActiveSignInByCId(@RequestParam("cId") int c_id, HttpServletRequest request){



        List<SignIn> data = signInService.getAllSignInByCId(c_id);
        LocalDateTime now = LocalDateTime.now();
        SignIn res = new SignIn();
        for(int i =0;i<data.size();i++){
            SignIn one = data.get(i);
            if(one.getCode().equals("1") && one.getType() == 1)
                res = one;
            if(one.getType() == 2 && now.isBefore(one.getDate().plusSeconds(one.getTime())) && !one.getCode().equals("0"))
                res = one;
        }

        JSONObject result = new JSONObject();
        result.put("StudentNum",personCourseService.countStudentByCId(c_id));
        result.put("data",res);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/NotActiveSignIn", method = RequestMethod.GET)
    public JSONObject getAllNotActiveSignInByCId(@RequestParam("cId") int c_id, HttpServletRequest request){

        List<SignIn> data = signInService.getAllSignInByCId(c_id);
        LocalDateTime now = LocalDateTime.now();
        List<SignIn> res = new ArrayList<>();
        for(int i = data.size()-1;i >= 0;i--){
            SignIn one = data.get(i);
            if(one.getCode().equals("0") && one.getType() == 1)
                res.add(one);
            if(one.getType() == 2 && (now.isAfter(one.getDate().plusSeconds(one.getTime())) || one.getCode().equals("0")))
                res.add(one);
        }

        JSONObject result = new JSONObject();
        result.put("data",res);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/SignInCode", method = RequestMethod.PUT)
    public JSONObject stopSignIn(@RequestBody JSONObject jsonParam, HttpServletRequest request){

        int si_id = Integer.parseInt((String) jsonParam.get("siId"));
        signInService.modifySignInCode(si_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/RemainTime", method = RequestMethod.GET)
    public JSONObject getRemainTime(@RequestParam("siId") int si_id){
        SignIn one = signInService.getSignInBySiId(si_id);
        int time = one.getTime();
        LocalDateTime date = one.getDate();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,date.plusSeconds(time));
        JSONObject result = new JSONObject();
        result.put("data",duration.toMillis()/1000);
        result.put("msg", "ok");
        result.put("code", "200");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/SignInStudent", method = RequestMethod.GET)
    public JSONObject getSignInStudent(@RequestParam("siId") int si_id){
        List<Map<String,Object>> datas = new ArrayList<>();

        //SignIn signIn = signInService.getSignInBySiId(si_id);
        List<SignRecord> signRecordList = signRecordService.getSignRecordListByPeId(si_id);
        for(int i = 0;i<signRecordList.size();i++){
            Person person = personService.getPersonById(signRecordList.get(i).getPeId());
            Map<String,Object> data = new HashMap<>();
            data.put("pe_id",person.getPeId());
            data.put("peName",person.getPeName());
            data.put("username",person.getUsername());
            datas.add(data);
        }

        JSONObject result = new JSONObject();
        result.put("data",datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/NotSignInStudent", method = RequestMethod.GET)
    public JSONObject getNotSignInStudent(@RequestParam("siId") int si_id){
        List<Map<String,Object>> datas = new ArrayList<>();

        SignIn signIn = signInService.getSignInBySiId(si_id);
        int c_id = signIn.getCId();
        List<PersonCourse> personCourseList = personCourseService.getByCId(c_id,2);
        List<SignRecord> signRecordList = signRecordService.getSignRecordListByPeId(si_id);

        Set<Integer> alreadySignIn = new HashSet<>();
        for(int j = 0;j<signRecordList.size();j++){
            alreadySignIn.add(signRecordList.get(j).getPeId());
        }

        for(int i = 0;i<personCourseList.size();i++){
            if(alreadySignIn.contains(personCourseList.get(i).getPeId()))
                continue;
            Person person = personService.getPersonById(personCourseList.get(i).getPeId());
            Map<String,Object> data = new HashMap<>();
            data.put("pe_id",person.getPeId());
            data.put("peName",person.getPeName());
            data.put("username",person.getUsername());
            datas.add(data);
        }

        JSONObject result = new JSONObject();
        result.put("data",datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }




}
