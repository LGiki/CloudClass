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
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "c_id", type = IdType.AUTO)
    private Integer cId;

    private String cNumber;

    private String cName;

    private String description;

    /**
     * ѧ
     */
    private String term;

    /**
     * ѧ
     */
    private Integer date;

    /**
     * ѧ
     */
    private Double score;

    private Double dailyWeight;

    private Double finalWeight;


}
