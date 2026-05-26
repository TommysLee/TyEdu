package com.ty.api.model.sch;

import com.ty.api.model.BaseBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 考试题目实体类
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Data
@Accessors(chain = true)
public class ExamQue extends BaseBO {

    @Serial
    private static final long serialVersionUID = 571823555835817984L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 考试ID **/
    private Integer examId;

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

    /** 章节标 **/
    private Integer chptMarked;

    /** 知识点标 **/
    private Integer knowledgeMarked;

    /** 题目分值 **/
    private Integer maxScore;

    /** 得分 **/
    private Double score;

    /** 学生作答 **/
    private String response;

    /**
     * 数据前置处理
     */
    @Override
    public ExamQue precheck() {
        this.stage = null != this.stage? this.stage.toUpperCase() : null;
        this.subject = null != this.subject? this.subject.toUpperCase() : null;
        this.type = null != this.type? this.type.toUpperCase() : null;
        return this;
    }
}
