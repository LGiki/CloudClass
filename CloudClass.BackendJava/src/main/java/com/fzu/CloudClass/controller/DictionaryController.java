package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.DictionaryDetail;
import com.fzu.CloudClass.service.impl.DictionaryDetailServiceImpl;
import com.fzu.CloudClass.service.impl.DictionaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/CloudClass/dictionary")
public class DictionaryController {

    @Autowired
    DictionaryServiceImpl dictionaryService;

    @Autowired
    DictionaryDetailServiceImpl dictionaryDetailService;

    @ResponseBody
    @RequestMapping(value = "/allDictionary", method = RequestMethod.GET)
    public JSONObject getAllDictionary(@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize){

        List<Map<String, Object>> datas = new ArrayList<>();

        List<Dictionary> dictionaries = dictionaryService.getAllDictionary(pageNo,pageSize);
        int count = dictionaryService.getCountAll();
        for(int i =0;i<dictionaries.size();i++){
            Map<String, Object> data = new HashMap<>();
            Dictionary one = dictionaries.get(i);
            data.put("d_id",one.getDId());
            data.put("ChineseName",one.getChineseName());
            data.put("EnglishName",one.getEnglishName());
            int d_id = one.getDId();
            List<DictionaryDetail> ones =  dictionaryDetailService.getAllDictionaryDetailById(d_id);
            data.put("items",ones);
            datas.add(data);
        }


        JSONObject result = new JSONObject();
        result.put("total",count);
        result.put("data", datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    /*
    添加
     */
    @ResponseBody
    @RequestMapping(value = "/Dictionary", method = RequestMethod.POST)
    public JSONObject addDictionary(@RequestBody JSONObject jsonParam){

        String chineseName = (String) jsonParam.get("chineseName");
        String englishName = (String) jsonParam.get("englishName");

        dictionaryService.addDictionary(chineseName,englishName);


        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Dictionary", method = RequestMethod.DELETE)
    public JSONObject delDictionary(@RequestParam("dId") int d_id){

        //int d_id = Integer.parseInt((String) jsonParam.get("dId"));
//        System.out.println(d_id);
        dictionaryDetailService.delDictionaryDetailBydId(d_id);
        dictionaryService.delDictionary(d_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Dictionary", method = RequestMethod.PUT)
    public JSONObject modifyDictionary(@RequestBody JSONObject jsonParam){

        int d_id = Integer.parseInt((String) jsonParam.get("dId"));
        String chineseName = (String) jsonParam.get("chineseName");
        String englishName = (String) jsonParam.get("englishName");
        dictionaryService.modifyDictionary(d_id,chineseName,englishName);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }



}
