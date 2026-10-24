package com.ty.api.content.service;

import com.ty.api.base.service.BaseService;
import com.ty.api.model.content.ContentCategory;

/**
 * 知识库分类业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/10/15
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {

    /**
     * 获取最大排序号
     *
     * @return Integer
     * @throws Exception
     */
    Integer maxSeq() throws Exception;

    /**
     * 交换两者的排序号
     *
     * @param ctgId1 知识库分类ID_1
     * @param ctgId2 知识库分类ID_2
     * @return boolean
     * @throws Exception
     */
    boolean swapSeq(Integer ctgId1, Integer ctgId2) throws Exception;
}

