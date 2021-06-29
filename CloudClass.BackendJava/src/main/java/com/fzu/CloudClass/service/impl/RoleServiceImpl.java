package com.fzu.CloudClass.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.CloudClass.entity.Role;
import com.fzu.CloudClass.mapper.RoleMapper;
import com.fzu.CloudClass.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> getAllRole() {
        QueryWrapper queryWrapper = new QueryWrapper();
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public void modifyRole(Role role) {
        roleMapper.updateById(role);
    }
}
