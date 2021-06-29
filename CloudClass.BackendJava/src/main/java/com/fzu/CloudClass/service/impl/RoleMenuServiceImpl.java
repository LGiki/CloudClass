package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.CloudClass.entity.PersonCourse;
import com.fzu.CloudClass.entity.Role;
import com.fzu.CloudClass.entity.RoleMenu;
import com.fzu.CloudClass.mapper.PersonCourseMapper;
import com.fzu.CloudClass.mapper.RoleMenuMapper;
import com.fzu.CloudClass.service.IPersonCourseService;
import com.fzu.CloudClass.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleMenu> getRoleMenuByRId(int r_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> one = new HashMap<>();
        one.put("status",1);
        one.put("r_id",r_id);
        queryWrapper.allEq(one);
        return roleMenuMapper.selectList(queryWrapper);
    }

    @Override
    public void modifyRoleMenuStatus(int r_id,int m_id, int status) {

        RoleMenu one = new RoleMenu();
        one.setStatus(status);
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> oneMap = new HashMap<>();
        oneMap.put("r_id",r_id);
        oneMap.put("m_id",m_id);
        queryWrapper.allEq(oneMap);
        roleMenuMapper.update(one,queryWrapper);
    }

    @Override
    public void addRoleMenu(int r_id, int m_id) {
        RoleMenu one = new RoleMenu();
        one.setRId(r_id);
        one.setMId(m_id);
        one.setStatus(1);
        roleMenuMapper.insert(one);
    }
}
