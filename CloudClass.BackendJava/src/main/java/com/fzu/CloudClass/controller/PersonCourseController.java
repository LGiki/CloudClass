package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Course;
import com.fzu.CloudClass.service.impl.CourseServiceImpl;
import com.fzu.CloudClass.service.impl.PersonCourseServiceImpl;
import com.fzu.CloudClass.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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



}
