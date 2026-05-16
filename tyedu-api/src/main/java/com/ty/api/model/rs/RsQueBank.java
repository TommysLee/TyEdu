package com.ty.api.model.rs;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 题库实体类
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Data
public class RsQueBank extends BaseBO {

    @Serial
    private static final long serialVersionUID = 560772516785385472L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 学段 **/
    private String stage;

    /** 学科 **/
    private String subject;

    /** 题型 **/
    private String type;

    /** 难度 **/
    private Integer difficulty;

    /** 题干 **/
    private String stem;

    /** 答案 **/
    private String answer;

    /** 解析 **/
    private String analysis;

    /** 章节标记 **/
    private Integer chptMarked;

    /** 知识点标记 **/
    private Integer knowledgeMarked;
}
