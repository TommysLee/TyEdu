package com.ty.api.sch.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.sch.WrongQueBankRefChapter;

import java.util.List;
import java.util.Set;

/**
 * 错题集题目章节标签业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
public interface WrongQueBankRefChapterService extends BaseService<WrongQueBankRefChapter> {

    /**
     * 根据题目ID查询所有章节标签数据
     *
     * @param qids 题目ID集合
     * @return List<WrongQueBankRefChapter>
     * @throws Exception
     */
    List<WrongQueBankRefChapter> getAll(Set<Integer> qids) throws Exception;

    /**
     * 根据题目ID查询所有章节标签数据，以JSON格式返回
     *
     * @param qid 题目ID
     * @return String - JSON Format
     * @throws Exception
     */
    String getSimpleAllForJson(Integer qid) throws Exception;
}
