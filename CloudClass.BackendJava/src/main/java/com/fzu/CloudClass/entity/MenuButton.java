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
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuButton implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mb_id", type = IdType.AUTO)
    private Integer mbId;

    private Integer mId;

    private Integer bId;

    private Integer status;


}
