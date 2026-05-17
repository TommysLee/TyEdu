package com.ty.logic.rs.service.impl;

import com.google.common.collect.Lists;
import com.ty.api.model.rs.RsQueRefKnowledge;
import com.ty.api.rs.service.RsQueRefKnowledgeService;
import com.ty.logic.rs.dao.RsQueRefKnowledgeDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 题目知识点标签业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RsQueRefKnowledgeServiceImpl implements RsQueRefKnowledgeService {

    @Autowired
    private RsQueRefKnowledgeDao queRefKnowledgeDao;

    /**
     * 根据题目ID查询所有题目知识点标签数据
     *
     * @param qid 题目ID
     * @return List<RsQueRefKnowledge>
     */
    @Override
    public List<RsQueRefKnowledge> getAll(Integer qid) throws Exception {
        List<RsQueRefKnowledge> list = Lists.newArrayList();
        if (null != qid) {
            list = queRefKnowledgeDao.findRsQueRefKnowledge(qid);
        }
        return list;
    }

    /**
     * 批量保存题目知识点标签数据
     *
     * @param list 题目知识点标签数据列表
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int saveBatch(List<RsQueRefKnowledge> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            // 先删除原知识点标签
            this.delete(list.get(0).getQid());

            // 再新增
            n = queRefKnowledgeDao.saveMultiRsQueRefKnowledge(list);
        }
        return n;
    }

    /**
     * 根据题目ID删除题目知识点标签数据
     *
     * @param qid 题目ID
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int delete(Integer qid) throws Exception {
        int n = 0;
        if (null != qid) {
            n = queRefKnowledgeDao.delRsQueRefKnowledge(qid);
        }
        return n;
    }
}
