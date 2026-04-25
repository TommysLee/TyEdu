package com.ty.api.model.rs;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 教材章节实体类
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Data
public class RsBookChapter extends BaseBO {

    @Serial
    private static final long serialVersionUID = 560768881313673216L;

    /** 章节ID (主键) **/
    private Integer chptId;

    /** 教材ID **/
    private Integer bId;

    /** 父ID **/
    private Integer parentId;

    /** 章节名称 **/
    private String chptName;

    /** 是否叶子节点 **/
    private Integer isLeaf;
}
