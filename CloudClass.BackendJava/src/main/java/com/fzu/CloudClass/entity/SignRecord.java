package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author djk
 * @since 2021-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SignRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sr_id", type = IdType.AUTO)
    private Integer srId;

    private Integer siId;

    private Integer peId;

    private Integer status;

    private LocalDateTime signTime;

    private Double lng;

    private Double lat;
}
