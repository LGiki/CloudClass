package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.CloudClass.entity.Class;
import com.fzu.CloudClass.mapper.ClassMapper;
import com.fzu.CloudClass.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    @Autowired
    ClassMapper classMapper;

    @Override
    public List<Class> getAllClass() {
        QueryWrapper queryWrapper = new QueryWrapper();
        return classMapper.selectList(queryWrapper);
    }

    @Override
    public int addClass(String cc_name) {
        Class one = new Class();
        one.setCcName(cc_name);
        classMapper.insert(one);
        return one.getCcId();
    }

    @Override
    public int countByName(String cc_name) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cc_name",cc_name);

        return classMapper.selectCount(queryWrapper);
    }

    @Override
    public int getIdByName(String cc_name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cc_name",cc_name);
        return classMapper.selectOne(queryWrapper).getCcId();
    }

    @Override
    public String getNameById(int cc_id) {
        return classMapper.selectById(cc_id).getCcName();
    }

    @Override
    public void modify(Class one) {
        classMapper.updateById(one);
    }
}
