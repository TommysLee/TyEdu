package com.ty.logic.sch.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ty.api.model.sch.WrongQueBankRefKnowledge;
import com.ty.api.sch.service.WrongQueBankRefKnowledgeService;
import com.ty.cm.utils.DataUtil;
import com.ty.logic.sch.dao.WrongQueBankRefKnowledgeDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 错题集题目知识点标签业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class WrongQueBankRefKnowledgeServiceImpl implements WrongQueBankRefKnowledgeService {

    @Autowired
    private WrongQueBankRefKnowledgeDao wrongQueBankRefKnowledgeDao;

    /**
     * 根据题目ID查询所有知识点标签数据
     *
     * @param qids 题目ID集合
     * @return List<WrongQueBankRefKnowledge>
     * @throws Exception
     */
    @Override
    public List<WrongQueBankRefKnowledge> getAll(Set<Integer> qids) throws Exception {
        List<WrongQueBankRefKnowledge> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(qids)) {
            list = wrongQueBankRefKnowledgeDao.findWrongQueBankRefKnowledge(qids);
        }
        return list;
    }

    /**
     * 根据题目ID查询所有知识点标签数据，以JSON格式返回
     *
     * @param qid 题目ID
     * @return String - JSON Format
     * @throws Exception
     */
    @Override
    public String getSimpleAllForJson(Integer qid) throws Exception {
        String jsonData = "[]";
        List<WrongQueBankRefKnowledge> list = Lists.newArrayList();
        if (null != qid) {
            list = this.getAll(Sets.newHashSet(qid));
        }
        if (CollectionUtils.isNotEmpty(list)) {
            jsonData = DataUtil.toJSON(list.stream().map(WrongQueBankRefKnowledge::getKid).collect(Collectors.toList()));
        }
        return jsonData;
    }

    /**
     * 批量保存题目知识点标签数据
     *
     * @param list 题目知识点标签数据列表
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Override
    public int saveBatch(List<WrongQueBankRefKnowledge> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            Integer qid = list.get(0).getQid();

            // 先删除原知识点标签
            this.delete(qid);

            // 再新增
            n = wrongQueBankRefKnowledgeDao.saveMultiWrongQueBankRefKnowledge(list);
        }
        return n;
    }

    /**
     * 根据ID删除题目知识点标签数据
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
            n = wrongQueBankRefKnowledgeDao.delWrongQueBankRefKnowledge(qid);
        }
        return n;
    }
}
