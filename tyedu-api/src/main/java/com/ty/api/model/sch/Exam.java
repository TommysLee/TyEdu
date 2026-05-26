package com.ty.api.model.sch;

import com.ty.api.model.BaseBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 考试实体类
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Data
@Accessors(chain = true)
public class Exam extends BaseBO {

    @Serial
    private static final long serialVersionUID = 571704639272742912L;

    /** 考试ID (主键) **/
    private Integer examId;

    /** 考试名称 **/
    private String title;

    /** 考试类型 **/
    private String examType;

    /** 考试时间 **/
    private String examTime;

    /** 学段 **/
    private String stage;

    /** 学科 **/
    private String subject;

    /** 年级 **/
    private String grade;

    /** 卷面分 **/
    private Integer maxScore;

    /** 得分 **/
    private Double score;

    /** 年级排名 **/
    private Integer gradeRank;

    /** 班级排名 **/
    private Integer classRank;

    /** 发布状态（0=未发布；1=已发布） **/
    private Integer published;

    /** 批阅状态（0=未批阅；1=已批阅） **/
    private Integer reviewed;

    /**
     * 数据清洗
     */
    @Override
    public Exam clean() {
        this.maxScore = this.published = this.reviewed = null;
        this.score = null;
        return this;
    }
}
