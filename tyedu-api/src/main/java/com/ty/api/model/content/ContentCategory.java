package com.ty.api.model.content;

import com.ty.api.model.BaseBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 知识库分类实体类
 *
 * @Author TyCode
 * @Date 2026/10/15
 */
@Data
@Accessors(chain = true)
public class ContentCategory extends BaseBO {

    @Serial
    private static final long serialVersionUID = 623494929251528704L;

    /** 分类ID (主键) **/
    private Integer ctgId;

    /** 分类名称 **/
    private String ctgName;

    /** 排序号 **/
    private Integer seq;

    /** 是否启用 **/
    private Integer enabled;
}