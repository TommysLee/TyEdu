package com.ty.api.rs.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.rs.RsQueRefChapter;

import java.util.List;

/**
 * 题目章节标签业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
public interface RsQueRefChapterService extends BaseService<RsQueRefChapter> {

    /**
     * 根据题目ID查询所有题目章节标签数据
     *
     * @param qid 题目ID
     * @return List<RsQueRefChapter>
     */
    List<RsQueRefChapter> getAll(Integer qid) throws Exception;
}
