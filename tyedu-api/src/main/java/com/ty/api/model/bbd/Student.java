package com.ty.api.model.bbd;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 学生实体类
 *
 * @Author TyCode
 * @Date 2026/05/25
 */
@Data
public class Student extends BaseBO {

    @Serial
    private static final long serialVersionUID = 571699480769556480L;

    /** 学生ID (主键) **/
    private Integer sid;

    /** 学校 **/
    private String school;

    /** 姓名 **/
    private String name;

    /** 学段 **/
    private String stage;

    /** 年级 **/
    private String grade;

    /** 年级显示名称 **/
    private String gradeTitle;

    /**
     * 数据前置处理
     */
    @Override
    public Student precheck() {
        this.stage = null == this.stage? null : this.stage.toUpperCase();
        this.grade = null == this.grade? null : this.grade.toUpperCase();
        return this;
    }
}
