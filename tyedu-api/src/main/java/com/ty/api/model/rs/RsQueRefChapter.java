package com.ty.api.model.rs;

import com.ty.api.model.BaseBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 题目章节标签实体类
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@Data
@Accessors(chain = true)
public class RsQueRefChapter extends BaseBO {

    @Serial
    private static final long serialVersionUID = 568761881847361536L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 章节ID (主键) **/
    private Integer chptId;

    /*
     * 辅助字段
     */

    // 章节名称
    private String chptName;
}
