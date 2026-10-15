package com.ty.api.model.content;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 知识库条目实体类
 *
 * @Author TyCode
 * @Date 2026/10/15
 */
@Data
public class ContentEntry extends BaseBO {

    @Serial
    private static final long serialVersionUID = 623495012130975744L;

    /** 条目ID (主键) **/
    private Integer eid;

    /** 分类ID（外键） **/
    private Integer ctgId;

    /** 标题 **/
    private String title;

    /** 内容 **/
    private String content;
}
