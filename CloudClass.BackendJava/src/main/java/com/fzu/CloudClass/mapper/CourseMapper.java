package com.fzu.CloudClass.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fzu.CloudClass.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface CourseMapper extends BaseMapper<Course> {

    @Select("<script>\n"+
            "SELECT course.c_id,c_number,c_name,term,date,cc_id,university_id,college_id FROM course,person_course\n" +
            "WHERE course.c_id = person_course.c_id AND person_course.pe_id = ${pe_id}\n" +
            "</script>")
    IPage<Course> getAllCourseByTeacher(@Param("pe_id")int pe_id, IPage<Course> page);

    @Select("<script>\n"+
            "SELECT course.c_id,c_number,c_name,term,date,cc_id,university_id,college_id FROM course,person_course\n" +
            "WHERE course.c_id = person_course.c_id\n" +
            "</script>")
    IPage<Course> getAllCourse(IPage<Course> page);

}
