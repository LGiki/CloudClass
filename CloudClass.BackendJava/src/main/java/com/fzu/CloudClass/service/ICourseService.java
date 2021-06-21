package com.fzu.CloudClass.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fzu.CloudClass.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface ICourseService extends IService<Course> {

    int addCourse(String c_number,String c_name,String term,int date,int cc_id,int university_id,int college_id);

    List<Course> getAllCourseByTeacher(int pe_id,int pageNo,int pageSize);

    Long getAllCourseCountByTeacher(int pe_id,int pageNo,int pageSize);

    //找重复课程号
    int countMulClass(String c_number);

    int getIdByNumber(String c_number);

    Course getCourseByNumber(String c_number);

    void modifyCanAttend(int c_id,int can_attend);

    void modifyIsEnd(int c_id,int is_end);

    int getCanAttend(String c_number);

    int getIsEnd(String c_number);







}
