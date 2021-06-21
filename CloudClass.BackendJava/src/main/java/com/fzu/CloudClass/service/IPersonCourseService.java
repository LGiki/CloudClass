package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.PersonCourse;
import com.baomidou.mybatisplus.extension.service.IService;

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

    PersonCourse getByCId(int c_id,int status);

}
