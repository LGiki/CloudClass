package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Class;
import com.fzu.CloudClass.entity.Course;
import com.fzu.CloudClass.service.impl.*;
import com.fzu.CloudClass.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/course")
public class CourseController {

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    PersonCourseServiceImpl personCourseService;

    @Autowired
    ClassServiceImpl classService;

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    SchoolServiceImpl schoolService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @ResponseBody
    @RequestMapping(value = "/Course", method = RequestMethod.GET)
    public JSONObject getAllCourseByTeacher(@RequestParam("pageNo") int pageNo,
                                            @RequestParam("pageSize") int pageSize,
                                            HttpServletRequest request){
        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        List<Course> data = new ArrayList<>();
        data = courseService.getAllCourseByTeacher(pe_id,pageNo,pageSize);
        Long count = courseService.getAllCourseCountByTeacher(pe_id,pageNo,pageSize);

        List<Map<String,Object>> datas = new ArrayList<>();

        for(int i = 0; i < data.size(); i++){
            Map<String,Object> one = new HashMap<>();
            one.put("cNumber",data.get(i).getCNumber());
            one.put("cName",data.get(i).getCName());
            one.put("cId",data.get(i).getCId());
            one.put("ccName",classService.getNameById(data.get(i).getCcId()));
            one.put("ccId",data.get(i).getCcId());
            one.put("term",data.get(i).getTerm());
            one.put("peName",personService.getPersonById(pe_id).getPeName());
            one.put("universityId",data.get(i).getUniversityId());
            one.put("collegeId",data.get(i).getCollegeId());
            one.put("date",data.get(i).getDate());
            datas.add(one);
        }

        JSONObject result = new JSONObject();
        result.put("total", count);
        result.put("data", datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/AllCourse", method = RequestMethod.GET)
    public JSONObject getAllCourse(@RequestParam("pageNo") int pageNo,
                                   @RequestParam("pageSize") int pageSize,
                                   HttpServletRequest request){
        List<Course> data = new ArrayList<>();
        data = courseService.getAllCourse(pageNo,pageSize);
        Long count = courseService.countAllCourse(pageNo,pageSize);

        List<Map<String,Object>> datas = new ArrayList<>();

        for(int i = 0; i < data.size(); i++){
            Map<String,Object> one = new HashMap<>();
            one.put("cNumber",data.get(i).getCNumber());
            one.put("cName",data.get(i).getCName());
            one.put("cId",data.get(i).getCId());
            one.put("ccName",classService.getNameById(data.get(i).getCcId()));
            one.put("ccId",data.get(i).getCcId());
            one.put("term",data.get(i).getTerm());
            try{
                one.put("peName",personService.getPersonById(personCourseService.getByCId(data.get(i).getCId(),1).get(0).getPeId()).getPeName());
            }catch(Exception e){
                one.put("peName","");
            }
            one.put("universityId",data.get(i).getUniversityId());
            one.put("collegeId",data.get(i).getCollegeId());
            one.put("date",data.get(i).getDate());
            datas.add(one);
        }
        JSONObject result = new JSONObject();
        result.put("total", count);
        result.put("data", datas);
        result.put("msg", "ok");
        result.put("code", "200");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Course", method = RequestMethod.POST)
    public JSONObject addCourse(@RequestBody JSONObject jsonParam,HttpServletRequest request){
        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        String c_name = (String) jsonParam.get("cName");
        String c_number = "";
        while(true){
            Random r = new Random( System.currentTimeMillis() );
            c_number = 10000 + r.nextInt(89999)+"";
            if(courseService.countMulClass(c_number) == 0){
                break;
            }
        }
        String term = (String) jsonParam.get("term");
        int date = Integer.parseInt ((String) jsonParam.get("date"));
        String cc_name = (String) jsonParam.get("ccName");
        int university_id = Integer.parseInt ((String) jsonParam.get("universityId"));
        int college_id = Integer.parseInt ((String) jsonParam.get("collegeId"));
        int count = classService.countByName(cc_name);
        int cc_id = 0;
        if(count == 0){
            cc_id = classService.addClass(cc_name);
        }else{
            cc_id = classService.getIdByName(cc_name);
        }

        int c_id = courseService.addCourse(c_number,c_name,term,date,cc_id,university_id,college_id);
        personCourseService.addPersonCourse(pe_id,c_id,1);
        JSONObject result = new JSONObject();
        result.put("cNumber",c_number);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/CourseDetail", method = RequestMethod.GET)
    public JSONObject getCourseDetail(@RequestParam("cNumber") String c_number){
        JSONObject result = new JSONObject();
        Course course = new Course();
        if(courseService.getCourseByNumber(c_number)==null){
            result.put("msg", "班课不存在");
            result.put("code", "201");
            return result;
        }
        course = courseService.getCourseByNumber(c_number);
        Map<String,Object> data = new HashMap<>();
        data.put("cId",course.getCId());
        data.put("teacher",personService.getPersonById(personCourseService.getByCId(course.getCId(),1).get(0).getPeId()).getPeName());
        data.put("cName",course.getCName());
        data.put("ccName",classService.getNameById(course.getCcId()));
        data.put("term",course.getTerm());
        data.put("date",course.getDate());
        data.put("university",schoolService.getSchoolById(course.getUniversityId()).getSName());
        data.put("college",schoolService.getSchoolById(course.getCollegeId()).getSName());
        data.put("canAttend",course.getCanAttend());
        data.put("isEnd",course.getIsEnd());
        result.put("data",data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/CourseCanAttend", method = RequestMethod.PUT)
    public JSONObject modifyCourseCanAttend(@RequestBody JSONObject jsonParam){

        int c_id = Integer.parseInt((String) jsonParam.get("cId"));
        int can_attend = Integer.parseInt((String) jsonParam.get("canAttend"));
        courseService.modifyCanAttend(c_id,can_attend);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/CourseIsEnd", method = RequestMethod.PUT)
    public JSONObject modifyCourseIsEnd(@RequestBody JSONObject jsonParam){

        int c_id = Integer.parseInt((String) jsonParam.get("cId"));
        int is_end = Integer.parseInt((String) jsonParam.get("IsEnd"));
        courseService.modifyIsEnd(c_id,is_end);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/CourseCanAttend", method = RequestMethod.GET)
    public JSONObject getCourseCanAttend(@RequestParam("cNumber") String c_number){


        int canAttend = courseService.getCanAttend(c_number);

        JSONObject result = new JSONObject();
        result.put("canAttend",canAttend);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/CourseIsEnd", method = RequestMethod.GET)
    public JSONObject getCourseIsEnd(@RequestParam("cNumber") String c_number){

        int isEnd = courseService.getIsEnd(c_number);

        JSONObject result = new JSONObject();
        result.put("isEnd",isEnd);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/CourseDetail", method = RequestMethod.PUT)
    public JSONObject modifyCourseDetail(@RequestBody JSONObject jsonParam){

        int c_id = Integer.parseInt((String) jsonParam.get("cId"));
        String c_name = (String) jsonParam.get("cName");
        String term = (String) jsonParam.get("term");
        int date = Integer.parseInt((String) jsonParam.get("date"));
        String cc_name = (String) jsonParam.get("ccName");
        int cc_id = Integer.parseInt((String) jsonParam.get("ccId"));
        int university_id = Integer.parseInt((String) jsonParam.get("universityId"));
        int college_id = Integer.parseInt((String) jsonParam.get("collegeId"));
        Course one = new Course();
        one.setCId(c_id);
        one.setDate(date);
        one.setTerm(term);
        one.setCName(c_name);
        one.setCollegeId(college_id);
        one.setUniversityId(university_id);

        Class thisClass = classService.getById(cc_id);
        thisClass.setCcName(cc_name);
        courseService.modifyCourse(one);
        classService.modify(thisClass);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }


}
