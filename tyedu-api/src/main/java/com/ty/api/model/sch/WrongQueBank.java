package com.ty.api.model.sch;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 错题集题目实体类
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Data
public class WrongQueBank extends BaseBO {

    @Serial
    private static final long serialVersionUID = 582728641151725568L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 学段 **/
    private String stage;

    /** 学科 **/
    private String subject;

    /** 年级 **/
    private String grade;

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

    /** 章节标 **/
    private Integer chptMarked;

    /** 知识点标 **/
    private Integer knowledgeMarked;

    /** 学生作答 **/
    private String response;

    /** 来源ID（现在为：考试ID） **/
    private Integer sourceId;

    /** 来源名称（现在为：考试名称） **/
    private String sourceName;

    /** 来源类型（现在为：考试类型） **/
    private String sourceType;

    /** 原题目索引号 **/
    private Integer oriIndex;
}
