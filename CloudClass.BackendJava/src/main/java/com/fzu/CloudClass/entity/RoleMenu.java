package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rm_id", type = IdType.AUTO)
    private Integer rmId;

    private Integer rId;

    private Integer mId;

    private Integer status;
}
