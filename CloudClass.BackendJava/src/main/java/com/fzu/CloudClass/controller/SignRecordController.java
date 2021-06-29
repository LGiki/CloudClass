package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.SignIn;
import com.fzu.CloudClass.mapper.PersonCourseMapper;
import com.fzu.CloudClass.service.impl.SignInServiceImpl;
import com.fzu.CloudClass.service.impl.SignRecordServiceImpl;
import com.fzu.CloudClass.util.CalDistance;
import com.fzu.CloudClass.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <p>
 * ǩ 前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/CloudClass/sign-record")
public class SignRecordController {

    @Autowired
    SignRecordServiceImpl signRecordService;

    @Autowired
    SignInServiceImpl signInService;

    @Autowired
    PersonCourseMapper personCourseMapper;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @ResponseBody
    @RequestMapping(value = "/SignRecord", method = RequestMethod.POST)
    public JSONObject addSignRecord(@RequestBody JSONObject jsonParam, HttpServletRequest request){

        JSONObject result = new JSONObject();

        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        //int c_id = Integer.parseInt((String) jsonParam.get("cId"));
        int si_id = Integer.parseInt((String) jsonParam.get("si_id"));
        LocalDateTime now = LocalDateTime.now();
        Double lng = Double.parseDouble((String) jsonParam.get("lng"));
        Double lat = Double.parseDouble((String) jsonParam.get("lat"));

        int count = signRecordService.countSignRecordBySiIdAndPeId(si_id,pe_id);
        if(count != 0){
            result.put("msg", "请勿重复签到");
            result.put("code", "203");
            return result;
        }

        SignIn thisSignIn = signInService.getSignInBySiId(si_id);
        if(thisSignIn.getType() == 2 && thisSignIn.getDate().plusSeconds(thisSignIn.getTime()).isBefore(now)){
            result.put("msg", "签到时间已结束");
            result.put("code", "201");
            return result;
        }

        if(thisSignIn.getType() == 1 && CalDistance.getDistance(lat,lng,thisSignIn.getLat(),thisSignIn.getLng()) > 100){
            result.put("msg", "签到超出范围");
            result.put("code", "202");
            return result;
        }

        signRecordService.addSignRecord(si_id,pe_id,1,now,lng,lat);
        personCourseMapper.addExp(pe_id,thisSignIn.getCId(),2);

        //Duration duration = Duration.between(thisSignIn.getDate(),now);
        //result.put("timeDif",duration.toMillis()/1000);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/SignRecordTime", method = RequestMethod.GET)
    public JSONObject getSignRecordTimeDif(@RequestParam("siId")int si_id){
        SignIn thisSignIn = signInService.getSignInBySiId(si_id);

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(thisSignIn.getDate(),now);
        JSONObject result = new JSONObject();
        result.put("timeDif",duration.toMillis()/1000);
        result.put("msg", "ok");
        result.put("code", "200");
        return result;
    }

    //补签
    @ResponseBody
    @RequestMapping(value = "/SupplementSignRecord", method = RequestMethod.POST)
    public JSONObject SupplementSignRecord(@RequestBody JSONObject jsonParam){

        int si_id = Integer.parseInt((String) jsonParam.get("siId"));
        int pe_id = Integer.parseInt((String) jsonParam.get("peId"));


        signRecordService.addSignRecord(si_id,pe_id,1,LocalDateTime.now(),0.0,0.0);
        personCourseMapper.addExp(pe_id,signInService.getSignInBySiId(si_id).getCId(),2);
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/StudentSignRecord", method = RequestMethod.GET)
    public JSONObject getSignRecordListByCId(@RequestParam("cId") int c_id, HttpServletRequest request){

        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));


        JSONObject result = new JSONObject();
        result.put("data",signRecordService.getSignRecordByStudentAndCId(c_id,pe_id));
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

//    @ResponseBody
//    @RequestMapping(value = "/StudentSignRecord", method = RequestMethod.GET)
//    public JSONObject getSignRecordListByPeId(HttpServletRequest request){
//
//        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
//
//
//        JSONObject result = new JSONObject();
//        result.put("data",signRecordService.getSignRecordByStudentAndCId(c_id,pe_id));
//        result.put("msg", "ok");
//        result.put("code", "200");
//
//        return result;
//    }




//    @ResponseBody
//    @RequestMapping(value = "/AlreadySign", method = RequestMethod.GET)
//    public JSONObject getAlreadySign(@RequestParam("siId") int si_id, HttpServletRequest request){
//
//
//
//    }

}
