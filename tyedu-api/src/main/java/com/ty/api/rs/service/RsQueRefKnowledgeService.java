package com.ty.api.rs.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.rs.RsKnowledge;
import com.ty.api.model.rs.RsQueRefKnowledge;

import java.util.List;
import java.util.Set;

/**
 * 题目知识点标签业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
public interface RsQueRefKnowledgeService extends BaseService<RsQueRefKnowledge> {

    /**
     * 根据题目ID查询所有题目知识点标签数据
     *
     * @param qid 题目ID
     * @return List<RsQueRefKnowledge>
     */
    List<RsQueRefKnowledge> getAll(Integer qid) throws Exception;

    /**
     * 查询题目的完整知识点标签数据
     * @param qids 题目ID集合
     * @return List<RsQueRefKnowledge>
     * @throws Exception
     */
    List<RsQueRefKnowledge> getFullAll(Set<Integer> qids) throws Exception;
}
