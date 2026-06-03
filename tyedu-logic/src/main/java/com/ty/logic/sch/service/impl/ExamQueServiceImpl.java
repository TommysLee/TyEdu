package com.ty.logic.sch.service.impl;

import com.google.common.collect.Lists;
import com.ty.api.model.sch.ExamQue;
import com.ty.api.model.sch.ExamQueRefKnowledge;
import com.ty.api.sch.service.ExamQueRefKnowledgeService;
import com.ty.api.sch.service.ExamQueService;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.sch.dao.ExamQueDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 考试题目业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ExamQueServiceImpl implements ExamQueService {

    @Autowired
    private ExamQueDao examQueDao;

    @Autowired
    private ExamQueRefKnowledgeService queRefKnowledgeService;

    /**
     * 根据条件查询所有考试题目数据
     *
     * @param examQue 考试题目
     * @return List<ExamQue>
     * @throws Exception
     */
    @Override
    public List<ExamQue> getAll(ExamQue examQue) throws Exception {
        if (null == examQue) {
            examQue = new ExamQue();
        }
        List<ExamQue> list = examQueDao.findExamQue(examQue);
        list.sort(Comparator.comparing(ExamQue::getSeq, Comparator.nullsLast(Comparator.naturalOrder())));
        this.getQueRefKnowledges(list);
        return list;
    }

    /**
     * 保存考试题目数据
     *
     * @param examQue 考试题目
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(ExamQue examQue) throws Exception {
        int n = 0;
        if (null != examQue) {
            examQue.precheck().setCreateTime(DateUtils.nowText());
            n = examQueDao.saveExamQue(examQue);
        }
        return n;
    }

    /**
     * 根据条件查询单条考试题目数据
     *
     * @param examQue 考试题目
     * @return ExamQue
     * @throws Exception
     */
    @Override
    public ExamQue getOne(ExamQue examQue) throws Exception {
        if (examQue != null) {
            List<ExamQue> examQueList = examQueDao.findExamQue(examQue);
            if (!examQueList.isEmpty()) {
                return examQueList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询考试题目数据
     *
     * @param id 考试题目ID
     * @return ExamQue
     * @throws Exception
     */
    @Override
    public ExamQue getById(Integer id) throws Exception {
        ExamQue examQue = null;
        if (null != id) {
            examQue = examQueDao.findExamQueById(id);
        }
        return examQue;
    }

    /**
     * 更新考试题目数据
     *
     * @param examQue 考试题目
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(ExamQue examQue) throws Exception {
        int n = 0;
        if (null != examQue) {
            examQue.precheck().setUpdateTime(DateUtils.nowText());
            n = examQueDao.updateExamQue(examQue);
        }
        return n;
    }

    /**
     * 根据ID删除考试题目数据
     *
     * @param id 考试题目ID
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int delete(Integer id) throws Exception {
        int n = 0;
        if (null != id) {
            n = examQueDao.delExamQue(id);
        }
        return n;
    }

    /**
     * 计算考试科目的卷面分
     *
     * @param examId 考试ID
     * @return Integer
     */
    @Override
    public Integer calcMaxScore(Integer examId) {
        if (null != examId) {
            return examQueDao.calcMaxScore(examId);
        }
        return null;
    }

    /**
     * 计算考试科目的得分
     *
     * @param examId 考试ID
     * @return Integer
     */
    @Override
    public Double calcScore(Integer examId) {
        if (null != examId) {
            return examQueDao.calcScore(examId);
        }
        return null;
    }

    /*
     * 获取题目的知识点标签数据
     */
    void getQueRefKnowledges(List<ExamQue> list) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            // 抽取题目ID集合
            Set<Integer> qidSet = list.stream().map(ExamQue::getQid).collect(Collectors.toSet());

            // 查询知识点标签数据后，按题目ID分组
            List<ExamQueRefKnowledge> ktags = queRefKnowledgeService.getAll(qidSet);
            Map<Integer,List<ExamQueRefKnowledge>> ktagsMap = ktags.stream().collect(Collectors.groupingBy(ExamQueRefKnowledge::getQid));

            // 将知识点标签数据，添加到题目对象中
            for (ExamQue q : list) {
                q.setKtags(ktagsMap.getOrDefault(q.getQid(), Lists.newArrayListWithCapacity(0)));
            }
        }
    }
}
