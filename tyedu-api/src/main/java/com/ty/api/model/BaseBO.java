package com.ty.api.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 业务实体类的基类
 *
 * @Author Tommy
 * @Date 2026/4/21
 */
@Data
public class BaseBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8181610377834111322L;

    /** 备注 **/
    private String remark;

    /** 创建时间 **/
    private String createTime;

    /** 更新时间 **/
    private String updateTime;

    /** ID集合 **/
    private Set<String> ids;

    /** 是否模糊查询 **/
    private Boolean isLike;

    /**
     * 置空不重要的属性值
     */
    public BaseBO clean() {
        this.setCreateTime(null);
        this.setUpdateTime(null);
        return this;
    }
}
