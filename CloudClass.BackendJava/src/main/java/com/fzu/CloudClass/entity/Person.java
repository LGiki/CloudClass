package com.fzu.CloudClass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ʦ
 * </p>
 *
 * @author djk
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pe_id", type = IdType.AUTO)
    private Integer peId;

    private Integer uId;

    private Integer sId;

    private String peNumber;

    private String peName;

    private Integer gender;

    private String grade;

    /**
     * רҵ
     */
    private String major;

    private Integer classes;

    private Integer isTeacher;


    public Integer getPeId() {
        return peId;
    }

    public void setPeId(Integer peId) {
        this.peId = peId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getPeNumber() {
        return peNumber;
    }

    public void setPeNumber(String peNumber) {
        this.peNumber = peNumber;
    }

    public String getPeName() {
        return peName;
    }

    public void setPeName(String peName) {
        this.peName = peName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getClasses() {
        return classes;
    }

    public void setClasses(Integer classes) {
        this.classes = classes;
    }

    public Integer getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Integer isTeacher) {
        this.isTeacher = isTeacher;
    }
}
