package com.ty.logic.sch.service.impl;

import com.github.pagehelper.Page;
import com.ty.api.model.sch.Exam;
import com.ty.api.model.sch.ExamQue;
import com.ty.api.sch.service.ExamQueService;
import com.ty.api.sch.service.ExamService;
import com.ty.cm.constant.enums.PublishType;
import com.ty.cm.constant.enums.ReviewType;
import com.ty.cm.exception.CustomException;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.sch.dao.ExamDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ty.cm.constant.Messages.RELATED_DATA_DELETE;
import static com.ty.cm.constant.Numbers.ZERO;
import static com.ty.cm.constant.Ty.DATA;
import static com.ty.cm.constant.Ty.PAGES;
import static com.ty.cm.constant.Ty.TOTAL;

/**
 * 考试业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Autowired
    private ExamQueService examQueService;

    /**
     * 根据条件查询所有考试数据
     *
     * @param exam 考试
     * @return List<Exam>
     * @throws Exception
     */
    @Override
    public List<Exam> getAll(Exam exam) throws Exception {
        if (null == exam) {
            exam = new Exam();
        }
        return examDao.findExam(exam);
    }

    /**
     * 根据条件分页查询考试数据
     *
     * @param exam 考试
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return Map<String, Object> 返回满足条件的数据集合与记录数
     * @throws Exception
     */
    @Override
    public Map<String, Object> query(Exam exam, String pageNum, String pageSize) throws Exception {
        Page<Exam> page = (Page<Exam>) this.queryData(exam, pageNum, pageSize);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(TOTAL, page.getTotal());
        resultMap.put(PAGES, page.getPages());
        resultMap.put(DATA, page);
        return resultMap;
    }

    /**
     * 根据条件分页查询考试数据
     *
     * @param exam 考试
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return List<Exam> 返回满足条件的数据集合
     * @throws Exception
     */
    @Override
    public List<Exam> queryData(Exam exam, String pageNum, String pageSize) throws Exception {
        Page<Exam> page = new Page<>();
        if (StringUtils.isNumeric(pageNum) && StringUtils.isNumeric(pageSize)) {
            page = examDao.findExam(new RowBounds(Integer.parseInt(pageNum), Integer.parseInt(pageSize)), exam);
        }
        return page;
    }

    /**
     * 保存考试数据
     *
     * @param exam 考试
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(Exam exam) throws Exception {
        int n = 0;
        if (null != exam) {
            exam.setStage(StringUtils.upperCase(exam.getStage()));
            exam.setSubject(StringUtils.upperCase(exam.getSubject()));
            exam.setGrade(StringUtils.upperCase(exam.getGrade()));
            exam.setCreateTime(DateUtils.nowText());
            n = examDao.saveExam(exam);
        }
        return n;
    }

    /**
     * 根据条件查询单条考试数据
     *
     * @param exam 考试
     * @return Exam
     * @throws Exception
     */
    @Override
    public Exam getOne(Exam exam) throws Exception {
        if (exam != null) {
            List<Exam> examList = examDao.findExam(exam);
            if (!examList.isEmpty()) {
                return examList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询考试数据
     *
     * @param id ID
     * @return Exam
     * @throws Exception
     */
    @Override
    public Exam getById(Integer id) throws Exception {
        Exam exam = null;
        if (id != null) {
            exam = examDao.findExamById(id);
        }
        return exam;
    }

    /**
     * 更新考试数据
     *
     * @param exam 考试
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(Exam exam) throws Exception {
        int n = 0;
        if (null != exam) {
            exam.setStage(StringUtils.upperCase(exam.getStage()));
            exam.setSubject(StringUtils.upperCase(exam.getSubject()));
            exam.setGrade(StringUtils.upperCase(exam.getGrade()));
            exam.setUpdateTime(DateUtils.nowText());
            n = examDao.updateExam(exam);
        }
        return n;
    }

    /**
     * 根据ID删除考试数据
     *
     * @param id ID
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int delete(Integer id) throws Exception {
        int n = 0;
        if (null != id) {
            // 判断待删除的数据，是否存在依赖关系
            if (examQueService.getCount(new ExamQue().setExamId(id)) > ZERO) {
                throw new CustomException(RELATED_DATA_DELETE);
            }

            // 执行删除操作
            n = examDao.delExam(id);
        }
        return n;
    }

    /**
     * 更新考试的发布状态
     *
     * @param examId 考试ID
     * @param status 状态值
     * @return int
     */
    @Override
    public int updatePublishStatus(Integer examId, int status) throws Exception {
        int n = 0;
        if (null != examId) {
            Exam exam = new Exam().setExamId(examId).setPublished(status);

            // 统计卷面分
            if (PublishType.PUBLISHED.eq(status)) {
                exam.setMaxScore(examQueService.calcMaxScore(examId));
            }

            // 执行更新
            return this.update(exam);
        }
        return n;
    }

    /**
     * 更新考试的批阅状态
     *
     * @param examId 考试ID
     * @param status 状态值
     * @return int
     */
    @Override
    public int updateReviewStatus(Integer examId, int status) throws Exception {
        int n = 0;
        if (null != examId) {
            Exam exam = new Exam().setExamId(examId).setReviewed(status);

            // 统计得分
            if (ReviewType.REVIEWED.eq(status)) {
                exam.setScore(examQueService.calcScore(examId));
            }

            // 执行更新
            return this.update(exam);
        }
        return n;
    }
}
