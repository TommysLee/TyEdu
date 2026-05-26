package com.ty.logic.sch.service.impl;

import com.github.pagehelper.Page;
import com.ty.api.model.sch.ExamQue;
import com.ty.api.sch.service.ExamQueRefChapterService;
import com.ty.api.sch.service.ExamQueRefKnowledgeService;
import com.ty.api.sch.service.ExamQueService;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.sch.dao.ExamQueDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ty.cm.constant.Ty.DATA;
import static com.ty.cm.constant.Ty.PAGES;
import static com.ty.cm.constant.Ty.TOTAL;

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
    private ExamQueRefKnowledgeService examQueRefKnowledgeService;

    @Autowired
    private ExamQueRefChapterService examQueRefChapterService;

    /**
     * 根据条件获取考试题目的总记录数
     *
     * @param examQue 考试题目
     * @return int
     * @throws Exception
     */
    @Override
    public int getCount(ExamQue examQue) throws Exception {
        if (null == examQue) {
            examQue = new ExamQue();
        }
        return examQueDao.findExamQueCount(examQue);
    }

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
        return examQueDao.findExamQue(examQue);
    }

    /**
     * 根据条件分页查询考试题目数据
     *
     * @param examQue 考试题目
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return Map<String, Object> 返回满足条件的数据集合与记录数
     * @throws Exception
     */
    @Override
    public Map<String, Object> query(ExamQue examQue, String pageNum, String pageSize) throws Exception {
        Page<ExamQue> page = (Page<ExamQue>) this.queryData(examQue, pageNum, pageSize);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(TOTAL, page.getTotal());
        resultMap.put(PAGES, page.getPages());
        resultMap.put(DATA, page);
        return resultMap;
    }

    /**
     * 根据条件分页查询考试题目数据
     *
     * @param examQue 考试题目
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return List<ExamQue> 返回满足条件的数据集合
     * @throws Exception
     */
    @Override
    public List<ExamQue> queryData(ExamQue examQue, String pageNum, String pageSize) throws Exception {
        Page<ExamQue> page = new Page<>();
        if (StringUtils.isNumeric(pageNum) && StringUtils.isNumeric(pageSize)) {
            page = examQueDao.findExamQue(new RowBounds(Integer.parseInt(pageNum), Integer.parseInt(pageSize)), examQue);
        }
        return page;
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
            // 先删除打标数据
            examQueRefKnowledgeService.delete(id);
            examQueRefChapterService.delete(id);

            // 再执行删除
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
}
