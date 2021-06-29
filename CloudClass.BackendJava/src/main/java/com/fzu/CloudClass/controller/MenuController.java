package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.DictionaryDetail;
import com.fzu.CloudClass.entity.Menu;
import com.fzu.CloudClass.service.impl.MenuServiceImpl;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
import com.fzu.CloudClass.service.impl.RoleMenuServiceImpl;
import com.fzu.CloudClass.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
@RequestMapping("/CloudClass/menu")
public class MenuController {

    @Autowired
    MenuServiceImpl menuService;

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    RoleMenuServiceImpl roleMenuService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @ResponseBody
    @RequestMapping(value = "/Menu", method = RequestMethod.GET)
    public JSONObject getAllMenu(){

        List<Map<String, Object>> datas = new ArrayList<>();

        List<Menu> menus = menuService.getAllMenu();
        for(int i = 0; i < menus.size(); i++){
            Map<String, Object> data = new HashMap<>();
            Menu one = menus.get(i);
            data.put("Link",one.getLink());
            data.put("menuName",one.getMenuName());
            data.put("sort",one.getSort());
            data.put("mId",one.getMId());
            List<Menu> ones =  menuService.getMenuListByParentId(one.getMId());
            data.put("items",ones);
            datas.add(data);
        }


        JSONObject result = new JSONObject();
        result.put("data", datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/allMenu", method = RequestMethod.GET)
    public JSONObject getMenuList(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){

        List<Map<String, Object>> datas = new ArrayList<>();

        List<Menu> menus = menuService.getMenuList(pageNo,pageSize);
        Long total = menuService.countMenuList(pageNo,pageSize);
        for(int i = 0; i < menus.size(); i++){
            Map<String, Object> data = new HashMap<>();
            Menu one = menus.get(i);
            data.put("menuName",one.getMenuName());
            if(one.getParentId() != -1)
                data.put("parentName",menuService.getNameById(one.getParentId()));
            else
                data.put("parentName","");
            data.put("sort",one.getSort());
            data.put("Link",one.getLink());
            data.put("mId",one.getMId());
            data.put("isPage",one.getIsPage());
            data.put("isDisplay",one.getIsDisplay());
            data.put("creator",personService.getPersonById(one.getCreator()).getUsername());
            data.put("createTime",one.getCreateTime());
            datas.add(data);
        }


        JSONObject result = new JSONObject();
        result.put("total",total);
        result.put("data", datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Menu", method = RequestMethod.PUT)
    public JSONObject modifyMenu(@RequestBody JSONObject jsonParam, HttpServletRequest request){

        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        LocalDateTime now = LocalDateTime.now();

        Menu menu = new Menu();
        menu.setMId(Integer.parseInt((String) jsonParam.get("mId")));
        menu.setLink((String) jsonParam.get("Link"));
        menu.setMenuName((String) jsonParam.get("menuName"));
        menu.setSort(Integer.parseInt((String) jsonParam.get("sort")));
        menu.setIsDisplay((Boolean) jsonParam.get("isDisplay"));
        menu.setIsPage((Boolean) jsonParam.get("isPage"));
        menu.setLastModifier(pe_id);
        menu.setLastModifyDate(now);

        menuService.modifyMenuById(menu);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/parentMenu", method = RequestMethod.GET)
    public JSONObject getAllParentMenu(){


        List<Menu> data = menuService.getAllMenu();

        JSONObject result = new JSONObject();
        result.put("data",data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Menu", method = RequestMethod.POST)
    public JSONObject addMenu(@RequestBody JSONObject jsonParam, HttpServletRequest request){

        int pe_id = Integer.parseInt(jwtTokenUtil.getTokenInfo(request.getHeader("Authorization").substring(7)));
        LocalDateTime now = LocalDateTime.now();

        Menu menu = new Menu();
        menu.setLink((String) jsonParam.get("Link"));
        menu.setMenuName((String) jsonParam.get("menuName"));
        menu.setSort(Integer.parseInt((String) jsonParam.get("sort")));
        menu.setIsDisplay((Boolean) jsonParam.get("isDisplay"));
        menu.setIsPage((Boolean) jsonParam.get("isPage"));
        menu.setCreator(pe_id);
        menu.setCreateTime(now);
        menu.setParentId(Integer.parseInt((String) jsonParam.get("parentId")));


        int m_id = menuService.addMenu(menu);
        roleMenuService.addRoleMenu(1,m_id);
        roleMenuService.addRoleMenu(-1,m_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Menu", method = RequestMethod.DELETE)
    public JSONObject delMenu(@RequestBody JSONObject jsonParam){

        int m_id = Integer.parseInt((String) jsonParam.get("mId"));

        int parent_id = menuService.getById(m_id).getParentId();

        if(parent_id != -1){
            menuService.delMenuByParentId(parent_id);
        }
        menuService.delMenuById(m_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

}


