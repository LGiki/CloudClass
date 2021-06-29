package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzu.CloudClass.entity.PersonCourse;
import com.fzu.CloudClass.mapper.PersonCourseMapper;
import com.fzu.CloudClass.service.IPersonCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Service
public class PersonCourseServiceImpl extends ServiceImpl<PersonCourseMapper, PersonCourse> implements IPersonCourseService {

    @Autowired
    PersonCourseMapper personCourseMapper;

    @Override
    public void addPersonCourse(int pe_id, int c_id,int status) {
        PersonCourse one = new PersonCourse();
        one.setCId(c_id);
        one.setPeId(pe_id);
        one.setStatus(status);
        one.setExp(0);
        personCourseMapper.insert(one);
    }

    @Override
    public int countByCIdAndPeId(int c_id, int pe_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> one = new HashMap<>();
        one.put("pe_id",pe_id);
        one.put("c_id",c_id);
        queryWrapper.allEq(one);
        return personCourseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<PersonCourse> getByCId(int c_id,int status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> one = new HashMap<>();
        one.put("c_id",c_id);
        one.put("status",status);
        queryWrapper.allEq(one);
        return personCourseMapper.selectList(queryWrapper);
    }

    @Override
    public void addExp(int c_id, int pe_id,int exp) {
        personCourseMapper.addExp(pe_id,c_id,exp);

    }

    @Override
    public List<PersonCourse> getPersonCourseByCId(int c_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("c_id",c_id);
        queryWrapper.orderByDesc("exp");
        return personCourseMapper.selectList(queryWrapper);
    }

    @Override
    public int countStudentByCId(int c_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("c_id",c_id);
        return (personCourseMapper.selectCount(queryWrapper)-1);
    }

    @Override
    public void delByCIdAndPeId(int c_id, int pe_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> one = new HashMap<>();
        one.put("c_id",c_id);
        one.put("pe_id",pe_id);
        queryWrapper.allEq(one);
        personCourseMapper.delete(queryWrapper);
    }

    @Override
    public PersonCourse getByCIdAndPeId(int c_id, int pe_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> one = new HashMap<>();
        one.put("c_id",c_id);
        one.put("pe_id",pe_id);
        queryWrapper.allEq(one);
        return personCourseMapper.selectOne(queryWrapper);
    }

    @Override
    public void modifyPersonCourse(PersonCourse personCourse) {
        personCourseMapper.updateById(personCourse);
    }


}
