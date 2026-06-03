package com.ty.logic.sch.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ty.api.model.sch.ExamQue;
import com.ty.api.model.sch.ExamQueRefKnowledge;
import com.ty.api.sch.service.ExamQueRefKnowledgeService;
import com.ty.api.sch.service.ExamQueService;
import com.ty.cm.utils.DataUtil;
import com.ty.logic.sch.dao.ExamQueRefKnowledgeDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 考试题目知识点标签业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ExamQueRefKnowledgeServiceImpl implements ExamQueRefKnowledgeService {

    @Autowired
    private ExamQueRefKnowledgeDao examQueRefKnowledgeDao;

    @Autowired
    @Lazy
    private ExamQueService examQueService;

    /**
     * 根据题目ID查询所有知识点标签数据
     *
     * @param qid 题目ID
     * @return List<ExamQueRefKnowledge>
     * @throws Exception
     */
    @Override
    public List<ExamQueRefKnowledge> getAll(Integer qid) throws Exception {
        List<ExamQueRefKnowledge> list = Lists.newArrayList();
        if (null != qid) {
            list = this.getAll(Sets.newHashSet(qid));
        }
        return list;
    }

    /**
     * 根据题目ID查询所有知识点标签数据
     *
     * @param qids 题目ID集合
     * @return List<ExamQueRefKnowledge>
     * @throws Exception
     */
    @Override
    public List<ExamQueRefKnowledge> getAll(Set<Integer> qids) throws Exception {
        List<ExamQueRefKnowledge> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(qids)) {
            list = examQueRefKnowledgeDao.findExamQueRefKnowledge(qids);
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
        List<ExamQueRefKnowledge> list = this.getAll(qid);
        if (CollectionUtils.isNotEmpty(list)) {
            jsonData = DataUtil.toJSON(list.stream().map(ExamQueRefKnowledge::getKid).collect(Collectors.toList()));
        }
        return jsonData;
    }

    /**
     * 批量保存考试题目知识点标签数据
     *
     * @param list 考试题目知识点标签数据列表
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int saveBatch(List<ExamQueRefKnowledge> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            Integer qid = list.get(0).getQid();

            // 先删除原知识点标签
            this.delete(qid);

            // 再新增
            n = examQueRefKnowledgeDao.saveMultiExamQueRefKnowledge(list);

            // 更新题目的知识点标记
            examQueService.update(new ExamQue().setQid(qid).setKnowledgeMarked(1));
        }
        return n;
    }

    /**
     * 根据ID删除考试题目知识点标签数据
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
            n = examQueRefKnowledgeDao.delExamQueRefKnowledge(qid);

            // 更新题目的知识点标记
            examQueService.update(new ExamQue().setQid(qid).setKnowledgeMarked(0));
        }
        return n;
    }
}
