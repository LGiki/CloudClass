package com.fzu.CloudClass.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fzu.CloudClass.entity.Course;
import com.fzu.CloudClass.entity.PersonCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface PersonCourseMapper extends BaseMapper<PersonCourse> {

    @Update("<script>\n"+
            "UPDATE person_course \n" +
            "SET exp = exp + ${exp} \n" +
            "WHERE c_id = ${c_id} AND pe_id = ${pe_id}\n" +
            "</script>")
    void addExp(@Param("pe_id")int pe_id,@Param("c_id")int c_id,@Param("exp")int exp);



}
