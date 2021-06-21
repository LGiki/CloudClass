package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cc_id", type = IdType.AUTO)
    private Integer ccId;

    private String ccName;



}
