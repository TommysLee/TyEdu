package com.ty.logic.sch.service.impl;

import com.google.common.collect.Lists;
import com.ty.api.model.sch.ExamQue;
import com.ty.api.model.sch.ExamQueRefChapter;
import com.ty.api.sch.service.ExamQueRefChapterService;
import com.ty.api.sch.service.ExamQueService;
import com.ty.logic.sch.dao.ExamQueRefChapterDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考试题目章节标签业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ExamQueRefChapterServiceImpl implements ExamQueRefChapterService {

    @Autowired
    private ExamQueRefChapterDao examQueRefChapterDao;

    @Autowired
    @Lazy
    private ExamQueService examQueService;

    /**
     * 根据题目ID查询所有章节标签数据
     *
     * @param qid 题目ID
     * @return List<ExamQueRefChapter>
     * @throws Exception
     */
    @Override
    public List<ExamQueRefChapter> getAll(Integer qid) throws Exception {
        List<ExamQueRefChapter> list = Lists.newArrayList();
        if (null != qid) {
            list = examQueRefChapterDao.findExamQueRefChapter(qid);
        }
        return list;
    }

    /**
     * 批量保存考试题目章节标签数据
     *
     * @param list 考试题目章节标签数据列表
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int saveBatch(List<ExamQueRefChapter> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            Integer qid = list.get(0).getQid();

            // 先删除原章节标签数据
            this.delete(qid);

            // 再新增
            n = examQueRefChapterDao.saveMultiExamQueRefChapter(list);

            // 更新题目的章节标记
            examQueService.update(new ExamQue().setQid(qid).setChptMarked(1));
        }
        return n;
    }

    /**
     * 根据题目ID删除考试题目章节标签数据
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
            n = examQueRefChapterDao.delExamQueRefChapter(qid);

            // 更新题目的章节标记
            examQueService.update(new ExamQue().setQid(qid).setChptMarked(0));
        }
        return n;
    }
}
