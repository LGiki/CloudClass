package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ǩ
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SignIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "si_id", type = IdType.AUTO)
    private Integer siId;

    private Integer type;

    private LocalDateTime date;

    private Double lng;

    private Double lat;

    private Integer peId;

    private Integer cId;

    private String code;

    //限时签到时长
    private Integer time;


}
