package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzu.CloudClass.entity.SignIn;
import com.fzu.CloudClass.mapper.SignInMapper;
import com.fzu.CloudClass.service.ISignInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ǩ 服务实现类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Service
public class SignInServiceImpl extends ServiceImpl<SignInMapper, SignIn> implements ISignInService {

    @Autowired
    SignInMapper signInMapper;

    @Override
    public int addSignIn(int type, String code, Double lng, Double lat, LocalDateTime dateTime, int pe_id, int c_id,int time) {
        SignIn signIn = new SignIn();
        signIn.setCode(code);
        signIn.setType(type);
        signIn.setLng(lng);
        signIn.setLat(lat);
        signIn.setPeId(pe_id);
        signIn.setCId(c_id);
        signIn.setDate(dateTime);
        signIn.setTime(time);
        signIn.setCode("1");
        signInMapper.insert(signIn);
        return signIn.getSiId();
    }

    @Override
    public List<SignIn> getAllSignInByCId(int c_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("c_id",c_id);
        return signInMapper.selectList(queryWrapper);
    }

    @Override
    public SignIn getSignInBySiId(int si_id) {
        return signInMapper.selectById(si_id);
    }

    @Override
    public void modifySignInCode(int si_id) {
        SignIn signIn = new SignIn();
        signIn.setSiId(si_id);
        signIn.setCode("0");
        signInMapper.updateById(signIn);

    }

    @Override
    public List<SignIn> getSignInByCIdAndPeId(int c_id, int pe_id) {
        Map<String,Integer> one = new HashMap<>();
        one.put("c_id",c_id);
        one.put("pe_id",pe_id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(one);
        return signInMapper.selectList(queryWrapper);
    }
}
