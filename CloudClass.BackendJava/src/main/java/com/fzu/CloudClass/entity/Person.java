package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ʦ
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pe_id", type = IdType.AUTO)
    private Integer peId;

    private String peNumber;

    private String peName;

    private Integer gender;

    private String grade;

    private Integer major;

    private String classes;

    private Integer isTeacher;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Integer type;

    private String oauth;


}
