package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.School;
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
public interface ISchoolService extends IService<School> {

    List<School> getAllUniversity();

    List<School> getAllCollegeByParentId(int parent_id);

    School getSchoolById(int s_id);

}
