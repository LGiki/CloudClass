package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class Button implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "b_id", type = IdType.AUTO)
    private Integer bId;

    private String bName;

    private String icon;

    private String description;

    private Integer creator;

    private LocalDateTime createTime;

    private Integer lastModifier;

    private LocalDateTime lastModifyDate;


}
