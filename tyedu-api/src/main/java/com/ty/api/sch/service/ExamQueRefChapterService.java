package com.ty.api.sch.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.sch.ExamQueRefChapter;

import java.util.List;

/**
 * 考试题目章节标签业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
public interface ExamQueRefChapterService extends BaseService<ExamQueRefChapter> {

    /**
     * 根据题目ID查询所有章节标签数据
     *
     * @param qid 题目ID
     * @return List<ExamQueRefChapter>
     * @throws Exception
     */
    List<ExamQueRefChapter> getAll(Integer qid) throws Exception;

    /**
     * 根据题目ID查询所有章节标签数据，以JSON格式返回
     *
     * @param qid 题目ID
     * @return String - JSON Format
     * @throws Exception
     */
    String getSimpleAllForJson(Integer qid) throws Exception;
}
