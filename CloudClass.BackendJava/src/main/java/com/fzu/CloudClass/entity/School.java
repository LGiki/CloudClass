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
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer sId;

    private String sName;


}
