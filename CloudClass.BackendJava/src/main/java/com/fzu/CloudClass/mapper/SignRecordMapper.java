package com.fzu.CloudClass.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fzu.CloudClass.entity.Course;
import com.fzu.CloudClass.entity.SignRecord;
import com.fzu.CloudClass.entity.dto.StudentSignRecordDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SignRecordMapper extends BaseMapper<SignRecord> {


    @Select("<script>\n"+
            "SELECT sr_id,sign_time,exp,type FROM sign_record AS sr,person_course AS pc,sign_in AS si\n" +
            "WHERE sr.si_id = si.si_id AND pc.c_id = si.c_id AND si.c_id = ${c_id} AND pc.pe_id = ${pe_id}\n" +
            "</script>")
    List<StudentSignRecordDto> getSignRecordByStudentAndCId(@Param("c_id")int c_id,@Param("pe_id")int pe_id);
}
