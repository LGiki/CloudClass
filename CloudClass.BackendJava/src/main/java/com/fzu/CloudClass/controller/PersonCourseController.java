package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Course;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.entity.PersonCourse;
import com.fzu.CloudClass.entity.SignIn;
import com.fzu.CloudClass.service.impl.*;
import com.fzu.CloudClass.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/person-course")
public class PersonCourseController {

    @Autowired
    PersonCourseServiceImpl personCourseService;

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    SignInServiceImpl signInService;

    @Autowired
    SignRecordServiceImpl signRecordService;

    @Autowired
    SchoolServiceImpl schoolService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @ResponseBody
    @RequestMapping(value = "/personCourse", method = RequestMethod.POST)
    public JSONObject attendCourse(@RequestBody JSONObject jsonParam,
                                   HttpServletRequest request){

        JSONObject result = new JSONObject();
        System.out.println(request.getHeader("Authorization").substring(7));
        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        String c_number = (String) jsonParam.get("cNumber");


        int c_id;
        try{
            c_id = courseService.getIdByNumber(c_number);
        }catch(Exception e){
            result.put("msg", "班课不存在");
            result.put("code", "201");
            return result;
        }

        if(personCourseService.countByCIdAndPeId(c_id,pe_id) == 1){
            result.put("cNumber",c_number);
            result.put("msg", "已加入该班课");
            result.put("code", "202");
            return result;
        }
        Course course = courseService.getById(c_id);
        if(course.getCanAttend() == 0){
            result.put("msg", "班课不允许加入");
            result.put("code", "203");
            return result;
        }
        if(course.getIsEnd() == 1){
            result.put("msg", "班课已结束");
            result.put("code", "204");
            return result;
        }

        personCourseService.addPersonCourse(pe_id,c_id,2);

        //result.put("data",c_id);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;


    }

    @ResponseBody
    @RequestMapping(value = "/CourseMember", method = RequestMethod.GET)
    public JSONObject getPersonByCId(@RequestParam("cId") int c_id,@RequestParam(name = "type", defaultValue = "1") int type){

        List<Map<String,Object>> datas = new ArrayList<>();

        List<PersonCourse> personCourseList = personCourseService.getPersonCourseByCId(c_id);

        if(type == 1){
            for(int i = personCourseList.size()-1 ; i >= 0; i--){
                PersonCourse thisPersonCourse = personCourseList.get(i);
                Person thisPerson = personService.getPersonById(thisPersonCourse.getPeId());
                if(thisPerson.getIsTeacher()!=0)
                    continue;
                Map<String,Object> data = new HashMap<>();
                data.put("peId",thisPerson.getPeId());
                data.put("peName",thisPerson.getPeName());
                data.put("exp",thisPersonCourse.getExp());
                datas.add(data);
            }
        }else{
            for(int i = 0 ; i < personCourseList.size(); i++){
                PersonCourse thisPersonCourse = personCourseList.get(i);
                Person thisPerson = personService.getPersonById(thisPersonCourse.getPeId());
                if(thisPerson.getIsTeacher()!=0)
                    continue;
                Map<String,Object> data = new HashMap<>();
                data.put("peId",thisPerson.getPeId());
                data.put("peName",thisPerson.getPeName());
                data.put("exp",thisPersonCourse.getExp());
                datas.add(data);
            }
        }

        JSONObject result = new JSONObject();

        result.put("data",datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;

    }

    @ResponseBody
    @RequestMapping(value = "/personCourse", method = RequestMethod.PUT)
    public JSONObject quitCourse(@RequestBody JSONObject jsonParam,
                                 HttpServletRequest request){

        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        int c_id = Integer.parseInt((String) jsonParam.get("cId"));

        List<SignIn> signInList = signInService.getSignInByCIdAndPeId(c_id,pe_id);

        for(int i=0;i<signInList.size();i++){
            int si_id = signInList.get(i).getSiId();
            signRecordService.deleteBySiIdAndPeId(si_id,pe_id);
        }

        personCourseService.delByCIdAndPeId(c_id,pe_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;

    }

    //type = 1 学生获取  2 老师获取
    @ResponseBody
    @RequestMapping(value = "/StudentInfo", method = RequestMethod.GET)
    public JSONObject getStudentInfoInCourse(@RequestParam("peId") int pe_id,
//                                             @RequestParam("type") int type,
                                             HttpServletRequest request){
//        if(type == 1){
//            pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
//        }

        Person person = personService.getPersonById(pe_id);


        Map<String,Object> data = new HashMap<>();

        data.put("peName",person.getPeName());
        data.put("peNumber",person.getPeNumber());
        data.put("classes",person.getClasses());
        data.put("grade",person.getGrade());
        try{
            data.put("Major",schoolService.getSchoolById(person.getMajor()).getSName());
        }catch(Exception e){
            data.put("Major",-1);
        }


        JSONObject result = new JSONObject();
        result.put("data",data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;

    }

    @ResponseBody
    @RequestMapping(value = "/StudentExp", method = RequestMethod.PUT)
    public JSONObject modifyStudentExp(@RequestBody JSONObject jsonParam){

        int pe_id = Integer.parseInt((String) jsonParam.get("peId"));
        int c_id = Integer.parseInt((String) jsonParam.get("cId"));
        int exp = Integer.parseInt((String) jsonParam.get("exp"));

        PersonCourse one = personCourseService.getByCIdAndPeId(c_id,pe_id);
        one.setExp(exp);

        personCourseService.modifyPersonCourse(one);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;


    }



}
