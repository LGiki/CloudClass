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
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pa_id", type = IdType.AUTO)
    private Integer paId;

    private String paName;

    private String description;

    private Double value;

    private Integer status;


}
