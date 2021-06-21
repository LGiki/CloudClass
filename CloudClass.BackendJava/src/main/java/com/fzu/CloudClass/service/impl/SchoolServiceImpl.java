package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzu.CloudClass.entity.School;
import com.fzu.CloudClass.mapper.SchoolMapper;
import com.fzu.CloudClass.service.ISchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

    @Autowired
    SchoolMapper schoolMapper;

    @Override
    public List<School> getAllUniversity() {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",-1);
        return schoolMapper.selectList(queryWrapper);
    }

    @Override
    public List<School> getAllCollegeByParentId(int parent_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",parent_id);
        return schoolMapper.selectList(queryWrapper);
    }

    @Override
    public School getSchoolById(int s_id) {
        return schoolMapper.selectById(s_id);
    }
}
