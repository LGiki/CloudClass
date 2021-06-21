package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.DictionaryDetail;
import com.fzu.CloudClass.service.impl.DictionaryDetailServiceImpl;
import com.fzu.CloudClass.service.impl.DictionaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/CloudClass/dictionary-detail")
public class DictionaryDetailController {

    @Autowired
    DictionaryServiceImpl dictionaryService;

    @Autowired
    DictionaryDetailServiceImpl dictionaryDetailService;



    @ResponseBody
    @RequestMapping(value = "/DictionaryDetail", method = RequestMethod.GET)
    public JSONObject getDictionaryDetail(@RequestParam("dId") int d_id){

        List<DictionaryDetail> data = new ArrayList<>();
        data = dictionaryDetailService.getAllDictionaryDetailById(d_id);
        //int count = dictionaryDetailService.getCountAllDictionaryDetailByDId(d_id);
        JSONObject result = new JSONObject();
        //result.put("total", count);
        result.put("data", data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    /*
    添加
     */
//    @ResponseBody
//    @RequestMapping(value = "/DictionaryDetail", method = RequestMethod.POST)
//    public JSONObject addDictionaryDetail(@RequestBody JSONObject jsonParam){
//
//        int d_id = Integer.parseInt ((String) jsonParam.get("dId"));
//        String dd_name = (String) jsonParam.get("ddName");
//
//        dictionaryDetailService.addDictionaryDetail(d_id,dd_name,0);
//
//        JSONObject result = new JSONObject();
//        result.put("msg", "ok");
//        result.put("code", "200");
//
//        return result;
//    }


    @ResponseBody
    @RequestMapping(value = "/DictionaryDetail", method = RequestMethod.DELETE)
    public JSONObject delDictionaryDetail(@RequestParam("ddId") int dd_id){

        //int dd_id = Integer.parseInt ((String) jsonParam.get("ddId"));


        dictionaryDetailService.delDictionaryDetail(dd_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/DictionaryDetail", method = RequestMethod.PUT)
    public JSONObject modifyDictionaryDetail(@RequestBody JSONObject jsonParam){

        int d_id = Integer.parseInt((String) jsonParam.get("dId"));
        String chinese_name = (String) jsonParam.get("chineseName");
        String english_name = (String) jsonParam.get("englishName");
        List<Map<String,String>> items = (List<Map<String, String>>) jsonParam.get("items");

        dictionaryService.modifyDictionary(d_id,chinese_name,english_name);
        for(int i = 0 ;i<items.size();i++){
            dictionaryDetailService.modifyDictionaryDetail(Integer.parseInt(items.get(i).get("ddId")),
                                                            items.get(i).get("ddName"),
                                                            Integer.parseInt(items.get(i).get("isDefault")),
                                                            Integer.parseInt(items.get(i).get("sort")));
        }

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    /*
    添加
     */
    @ResponseBody
    @RequestMapping(value = "/DictionaryAndDetail", method = RequestMethod.POST)
    public JSONObject addDictionaryAndDetail(@RequestBody JSONObject jsonParam){

        JSONObject result = new JSONObject();

        String chinese_name = (String) jsonParam.get("chineseName");
        String english_name = (String) jsonParam.get("englishName");
        List<Map<String,String>> items = (List<Map<String, String>>) jsonParam.get("items");
        int d_id = dictionaryService.addDictionary(chinese_name,english_name);

        int count = 0;
        for(int i = 0;i < items.size();i++){
            if(items.get(i).get("isDefault").equals("1")){
                count++;
            }
        }
        if(count==0){
            result.put("msg", "缺少默认值！");
            result.put("code", "201");
            return result;
        }

        for(int i = 0;i < items.size();i++){
            //System.out.println(Integer.parseInt(items.get(i).get("isDefault")));
            dictionaryDetailService.addDictionaryDetail(d_id,items.get(i).get("ddName"),
                                                        Integer.parseInt(items.get(i).get("isDefault")),
                                                        Integer.parseInt(items.get(i).get("sort")));
        }

        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }



}
