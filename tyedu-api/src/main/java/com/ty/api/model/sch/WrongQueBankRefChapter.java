package com.ty.api.model.sch;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 错题集题目章节标签实体类
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Data
public class WrongQueBankRefChapter implements Serializable {

    @Serial
    private static final long serialVersionUID = 582728790192123904L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 章节ID (主键) **/
    private Integer chptId;

    /** 章节名称 **/
    private String chptName;
}
