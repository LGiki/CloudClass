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

    /**
     * ǩ
     */
    private Integer state;

    /**
     * ǩ
     */
    private LocalDateTime date;

    /**
     * ǩ
     */
    private String position;

    private Integer peId;

    private Integer cId;


}
