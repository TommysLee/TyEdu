package com.ty.api.sch.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.sch.Exam;

/**
 * 考试业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
public interface ExamService extends BaseService<Exam> {

    /**
     * 更新考试的发布状态
     *
     * @param examId 考试ID
     * @param status 状态值
     * @return int
     */
    int updatePublishStatus(Integer examId, int status) throws Exception;

    /**
     * 更新考试的批阅状态
     *
     * @param examId 考试ID
     * @param status 状态值
     * @return int
     */
    int updateReviewStatus(Integer examId, int status) throws Exception;
}
