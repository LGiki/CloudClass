package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzu.CloudClass.entity.Course;
import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.mapper.CourseMapper;
import com.fzu.CloudClass.service.ICourseService;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public int addCourse(String c_number,String c_name,String term,int date,int cc_id,int university_id,int college_id) {
        Course course = new Course();
        course.setCName(c_name);
        course.setCNumber(c_number);
        course.setTerm(term);
        course.setDate(date);
        course.setCcId(cc_id);
        course.setUniversityId(university_id);
        course.setCollegeId(college_id);
        course.setCanAttend(1);
        course.setIsEnd(0);
        courseMapper.insert(course);
        return course.getCId();
    }

    @Override
    public List<Course> getAllCourseByTeacher(int pe_id,int pageNo,int pageSize) {

        IPage<Course> page = new Page<>(pageNo,pageSize);
        page = courseMapper.getAllCourseByTeacher(pe_id, page);

        return page.getRecords();
    }

    @Override
    public Long getAllCourseCountByTeacher(int pe_id, int pageNo, int pageSize) {
        IPage<Course> page = new Page<>(pageNo,pageSize);
        page = courseMapper.getAllCourseByTeacher(pe_id, page);

        return page.getTotal();
    }

    @Override
    public int countMulClass(String c_number) {
        QueryWrapper<Course> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("c_number",c_number);
        return courseMapper.selectCount(queryWrapper);
    }

    @Override
    public int getIdByNumber(String c_number) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("c_number",c_number);
        return courseMapper.selectOne(queryWrapper).getCId();
    }

    @Override
    public Course getCourseByNumber(String c_number) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("c_number",c_number);
        return courseMapper.selectOne(queryWrapper);
    }

    @Override
    public void modifyCanAttend(int c_id,int can_attend) {

        Course course = new Course();
        course.setCId(c_id);
        course.setCanAttend(can_attend);
        courseMapper.updateById(course);
    }

    @Override
    public void modifyIsEnd(int c_id,int is_end) {
        Course course = new Course();
        course.setCId(c_id);
        course.setIsEnd(is_end);
        courseMapper.updateById(course);
    }

    @Override
    public int getCanAttend(String c_number) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cNumber",c_number);
        return courseMapper.selectOne(queryWrapper).getCanAttend();
    }

    @Override
    public int getIsEnd(String c_number) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cNumber",c_number);
        return courseMapper.selectOne(queryWrapper).getIsEnd();
    }
}
