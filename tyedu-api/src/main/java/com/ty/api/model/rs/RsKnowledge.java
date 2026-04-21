package com.ty.api.model.rs;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 知识点实体类
 *
 * @Author TyCode
 * @Date 2026/04/21
 */
@Data
public class RsKnowledge extends BaseBO {

    @Serial
    private static final long serialVersionUID = 559313382311424000L;

    /** 知识点ID (主键) **/
    private Integer kId;

    /** 父ID **/
    private Integer parentId;

    /** 知识点名称 **/
    private String kName;

    /** 是否叶子节点 **/
    private Integer isLeaf;

    /** 学段 **/
    private String stage;

    /** 学科 **/
    private String subject;
}
