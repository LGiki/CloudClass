package com.fzu.CloudClass.entity;

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
public class PersonCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pcId;

    private Integer peId;

    private Integer cId;

    private Integer value;

    private Integer status;


}
