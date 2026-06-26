package com.ty.api.model.sch;

import com.ty.api.model.BaseBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.List;

/**
 * 错题集题目实体类
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Data
@Accessors(chain = true)
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

    /*
     * 辅助字段
     */

    // 知识点标签数据集合
    private List<WrongQueBankRefKnowledge> ktags;

    /**
     * 从考试与考试题目中拷贝数据
     *
     * @param exam      考试
     * @param examQue   考试题目
     * @param index     显示的顺序号
     * @return WrongQueBank
     */
    public WrongQueBank copyFrom(Exam exam, ExamQue examQue, Integer index) {
        if (null != exam && null != examQue) {
            this.qid = examQue.getQid();
            this.stage = examQue.getStage();
            this.subject = examQue.getSubject();
            this.grade = exam.getGrade();
            this.type = examQue.getType();
            this.difficulty = examQue.getDifficulty();
            this.stem = examQue.getStem();
            this.answer = examQue.getAnswer();
            this.analysis = examQue.getAnalysis();
            this.chptMarked = examQue.getChptMarked();
            this.knowledgeMarked = examQue.getKnowledgeMarked();
            this.response = examQue.getResponse();
            this.sourceId = exam.getExamId();
            this.sourceName = exam.getTitle();
            this.sourceType = exam.getExamType();
            this.oriIndex = index;
        }
        return this;
    }

    /**
     * 数据前置处理
     */
    @Override
    public WrongQueBank precheck() {
        this.stage = null == this.stage? null : this.stage.toUpperCase();
        this.subject = null == this.subject? null : this.subject.toUpperCase();
        this.grade = null == this.grade? null : this.grade.toUpperCase();
        this.type = null == this.type? null : this.type.toUpperCase();
        return this;
    }
}
