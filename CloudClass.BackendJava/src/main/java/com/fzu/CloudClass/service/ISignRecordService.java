package com.fzu.CloudClass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.CloudClass.entity.SignRecord;
import com.fzu.CloudClass.entity.dto.StudentSignRecordDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ISignRecordService extends IService<SignRecord> {

    void addSignRecord(int si_id, int pe_id, int status, LocalDateTime sign_time,Double lng,Double lat);

    int countSignRecordBySiIdAndPeId(int si_id,int pe_id);

    List<SignRecord> getSignRecordListByPeId(int si_id);

    List<StudentSignRecordDto> getSignRecordByStudentAndCId(int c_id,int pe_id);

    void deleteBySiIdAndPeId(int si_id,int pe_id);

}
