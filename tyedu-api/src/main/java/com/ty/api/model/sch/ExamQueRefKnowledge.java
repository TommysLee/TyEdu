package com.ty.api.model.sch;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 考试题目知识点标签实体类
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Data
public class ExamQueRefKnowledge extends BaseBO {

    @Serial
    private static final long serialVersionUID = 571896879165173760L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 知识点ID (主键) **/
    private Integer kid;

    /** 知识点名称 **/
    private String kname;
}
