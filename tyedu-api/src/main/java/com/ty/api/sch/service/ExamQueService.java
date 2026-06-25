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

    /**
     * 更新题目的错题集标记
     *
     * @param qid    题目ID
     * @param marked 标记值
     * @return Integer
     * @throws Exception
     */
    Integer updateWrongMarked(Integer qid, Integer marked) throws Exception;

    /**
     * 将题目加入到错题集
     *
     * @param qid   考试题目ID
     * @param index 考试题目的显示顺序号
     * @return Integer
     * @throws Exception
     */
    Integer copyToWrong(Integer qid, Integer index) throws Exception;
}
