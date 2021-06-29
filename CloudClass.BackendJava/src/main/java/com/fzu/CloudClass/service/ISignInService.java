package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.SignIn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * ǩ 服务类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface ISignInService extends IService<SignIn> {

    int addSignIn(int type, String code, Double lng, Double lat, LocalDateTime dateTime,int pe_id,int c_id,int time);

    List<SignIn> getAllSignInByCId(int c_id);

    SignIn getSignInBySiId(int si_id);

    void modifySignInCode(int si_id);

    List<SignIn> getSignInByCIdAndPeId(int c_id,int pe_id);



}
