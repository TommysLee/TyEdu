package com.ty.api.model.sch;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 考试题目章节标签实体类
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Data
public class ExamQueRefChapter extends BaseBO {

    @Serial
    private static final long serialVersionUID = 571897085021614080L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 章节ID (主键) **/
    private Integer chptId;

    /** 章节名称 **/
    private String chptName;
}
