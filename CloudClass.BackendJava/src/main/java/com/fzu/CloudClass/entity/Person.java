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
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pe_id", type = IdType.AUTO)
    private Integer peId;

    private Integer uId;

    private Integer sId;

    private String peNumber;

    private String peName;

    private Integer gender;

    private String grade;

    /**
     * רҵ
     */
    private String major;

    private Integer classes;

    private Integer isTeacher;


}
