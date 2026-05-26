package com.ty.api.sch.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.sch.ExamQueRefKnowledge;

import java.util.List;

/**
 * 考试题目知识点标签业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
public interface ExamQueRefKnowledgeService extends BaseService<ExamQueRefKnowledge> {

    /**
     * 根据题目ID查询所有知识点标签数据
     *
     * @param qid 题目ID
     * @return List<ExamQueRefKnowledge>
     * @throws Exception
     */
    List<ExamQueRefKnowledge> getAll(Integer qid) throws Exception;
}
