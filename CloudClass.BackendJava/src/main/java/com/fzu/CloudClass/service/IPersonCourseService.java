package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Course;
import com.fzu.CloudClass.entity.PersonCourse;
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
public interface IPersonCourseService extends IService<PersonCourse> {

    void addPersonCourse(int pe_id,int c_id,int status);

    int countByCIdAndPeId(int c_id,int pe_id);

    List<PersonCourse> getByCId(int c_id,int status);

    void addExp(int c_id,int pe_id,int exp);

    List<PersonCourse> getPersonCourseByCId(int c_id);

    int countStudentByCId(int c_id);

    void delByCIdAndPeId(int c_id,int pe_id);

    PersonCourse getByCIdAndPeId(int c_id,int pe_id);

    void modifyPersonCourse(PersonCourse personCourse);

}
