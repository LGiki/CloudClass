package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class PersonCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pc_id", type = IdType.AUTO)
    private Integer pcId;

    private Integer exp;

    private Integer status;

    private Integer peId;

    private Integer cId;


}
