package com.fzu.CloudClass.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
public class StudentSignRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer srId;

    private LocalDateTime signTime;

    private Integer exp;

    private Integer type;


}
