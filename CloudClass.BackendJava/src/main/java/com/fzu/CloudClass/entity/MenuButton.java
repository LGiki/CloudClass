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


    public Integer getMbId() {
        return mbId;
    }

    public void setMbId(Integer mbId) {
        this.mbId = mbId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
