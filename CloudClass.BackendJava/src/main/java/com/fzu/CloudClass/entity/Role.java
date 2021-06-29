package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "r_id", type = IdType.AUTO)
    private Integer rId;

    private String rName;

    private String creator;

    private LocalDateTime createTime;

    private String modifier;

    private LocalDateTime modify_time;

}
