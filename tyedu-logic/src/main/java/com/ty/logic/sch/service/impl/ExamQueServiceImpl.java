package com.ty.logic.sch.service.impl;

import com.google.common.collect.Lists;
import com.ty.api.model.sch.Exam;
import com.ty.api.model.sch.ExamQue;
import com.ty.api.model.sch.ExamQueRefChapter;
import com.ty.api.model.sch.ExamQueRefKnowledge;
import com.ty.api.model.sch.WrongQueBank;
import com.ty.api.model.sch.WrongQueBankRefChapter;
import com.ty.api.model.sch.WrongQueBankRefKnowledge;
import com.ty.api.sch.service.ExamQueRefChapterService;
import com.ty.api.sch.service.ExamQueRefKnowledgeService;
import com.ty.api.sch.service.ExamQueService;
import com.ty.api.sch.service.ExamService;
import com.ty.api.sch.service.WrongQueBankRefChapterService;
import com.ty.api.sch.service.WrongQueBankRefKnowledgeService;
import com.ty.api.sch.service.WrongQueBankService;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.sch.dao.ExamQueDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    @Autowired
    private ExamQueRefChapterService examQueRefChapterService;

    @Autowired
    @Lazy
    private ExamService examService;

    @Autowired
    @Lazy
    private WrongQueBankService wrongQueService;

    @Autowired
    @Lazy
    private WrongQueBankRefChapterService wrongQueRefChapterService;

    @Autowired
    @Lazy
    private WrongQueBankRefKnowledgeService wrongQueRefKnowledgeService;

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
     * 更新题目的错题集标记
     *
     * @param qid    题目ID
     * @param marked 标记值
     * @return Integer
     * @throws Exception
     */
    @Transactional
    @Override
    public Integer updateWrongMarked(Integer qid, Integer marked) throws Exception {
        return this.update(new ExamQue().setQid(qid).setWrongMarked(marked));
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

    /**
     * 将题目加入到错题集
     *
     * @param qid   考试题目ID
     * @param index 考试题目的显示顺序号
     * @return Integer
     * @throws Exception
     */
    @Transactional
    @Override
    public Integer copyToWrong(Integer qid, Integer index) throws Exception {
        int n = 0;
        if (null != qid && null != index) {
            // 判断错题集中是否已存在此题目，若不存在则执行
            if (wrongQueService.getCount(new WrongQueBank().setQid(qid)) == 0) {
                ExamQue examQue = this.getById(qid);
                if (null != examQue) {
                    // 复制题目到错题集
                    Exam exam = examService.getById(examQue.getExamId());
                    n = wrongQueService.save(new WrongQueBank().copyFrom(exam, examQue, index));

                    // 复制章节标到错题集
                    List<ExamQueRefChapter> chptList = examQueRefChapterService.getAll(qid);
                    List<WrongQueBankRefChapter> wrongChptList = Lists.newArrayListWithCapacity(chptList.size());
                    for(ExamQueRefChapter c : chptList) {
                        wrongChptList.add(new WrongQueBankRefChapter().copyFrom(c));
                    }
                    wrongQueRefChapterService.saveBatch(wrongChptList);

                    // 复制知识点标到错题集
                    List<ExamQueRefKnowledge> kList = queRefKnowledgeService.getAll(qid);
                    List<WrongQueBankRefKnowledge> wrongKList = Lists.newArrayListWithCapacity(kList.size());
                    for (ExamQueRefKnowledge k : kList) {
                        wrongKList.add(new WrongQueBankRefKnowledge().copyFrom(k));
                    }
                    wrongQueRefKnowledgeService.saveBatch(wrongKList);

                    // 更新此题目的错题集标记
                    this.updateWrongMarked(qid, 1);
                }
            }
        }
        return n;
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
