package com.ty.api.sch.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.sch.ExamQue;

/**
 * 考试题目业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
public interface ExamQueService extends BaseService<ExamQue> {

    /**
     * 计算考试科目的卷面分
     *
     * @param examId 考试ID
     * @return Integer
     */
    Integer calcMaxScore(Integer examId);

    /**
     * 计算考试科目的得分
     *
     * @param examId 考试ID
     * @return Integer
     */
    Double calcScore(Integer examId);
}
