package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.CloudClass.entity.SignRecord;
import com.fzu.CloudClass.entity.dto.StudentSignRecordDto;
import com.fzu.CloudClass.mapper.SignRecordMapper;
import com.fzu.CloudClass.service.ISignRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignRecordServiceImpl extends ServiceImpl<SignRecordMapper, SignRecord> implements ISignRecordService {

    @Autowired
    SignRecordMapper signRecordMapper;

    @Override
    public void addSignRecord(int si_id, int pe_id, int status, LocalDateTime sign_time, Double lng, Double lat) {
        SignRecord one = new SignRecord();
        one.setLat(lat);
        one.setLng(lng);
        one.setSignTime(sign_time);
        one.setPeId(pe_id);
        one.setSiId(si_id);
        one.setStatus(status);
        signRecordMapper.insert(one);
    }

    @Override
    public int countSignRecordBySiIdAndPeId(int si_id, int pe_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> one = new HashMap<>();
        one.put("si_id",si_id);
        one.put("pe_id",pe_id);
        queryWrapper.allEq(one);
        return signRecordMapper.selectCount(queryWrapper);
    }

    @Override
    public List<SignRecord> getSignRecordListByPeId(int si_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("si_id",si_id);
        return signRecordMapper.selectList(queryWrapper);
    }

    @Override
    public List<StudentSignRecordDto> getSignRecordByStudentAndCId(int c_id, int pe_id) {
        return signRecordMapper.getSignRecordByStudentAndCId(c_id,pe_id);
    }

    @Override
    public void deleteBySiIdAndPeId(int si_id, int pe_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Integer> one = new HashMap<>();
        one.put("si_id",si_id);
        one.put("pe_id",pe_id);
        queryWrapper.allEq(one);
        signRecordMapper.delete(queryWrapper);

    }

//    @Override
//    public List<StudentSignRecordDto> getSignRecordByStudentAndCId(int c_id, int pe_id) {
//        return signRecordMapper.getSignRecordByStudentAndCId(c_id,pe_id);
//    }
}
