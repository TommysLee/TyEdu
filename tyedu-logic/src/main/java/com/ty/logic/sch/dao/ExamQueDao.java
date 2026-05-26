package com.ty.logic.sch.dao;

import com.github.pagehelper.Page;
import com.ty.api.model.sch.ExamQue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 考试题目数据访问层
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Mapper
public interface ExamQueDao {

    /**
     * 根据条件查询考试题目记录数
     *
     * @param examQue 考试题目
     * @return int
     */
    int findExamQueCount(ExamQue examQue);

    /**
     * 根据条件查询所有考试题目数据
     *
     * @param examQue 考试题目
     * @return List<ExamQue>
     */
    List<ExamQue> findExamQue(ExamQue examQue);

    /**
     * 根据条件分页查询考试题目数据
     *
     * @param rowBounds 分页参数
     * @param examQue 考试题目
     * @return Page<ExamQue>
     */
    Page<ExamQue> findExamQue(RowBounds rowBounds, ExamQue examQue);

    /**
     * 根据ID查询考试题目数据
     *
     * @param qid 考试题目ID
     * @return ExamQue
     */
    ExamQue findExamQueById(Integer qid);

    /**
     * 保存考试题目数据
     *
     * @param examQue 考试题目
     * @return int 返回受影响的行数
     */
    int saveExamQue(ExamQue examQue);

    /**
     * 更新考试题目数据
     *
     * @param examQue 考试题目
     * @return int 返回受影响的行数
     */
    int updateExamQue(ExamQue examQue);

    /**
     * 删除考试题目数据
     *
     * @param qid 考试题目ID
     * @return int 返回受影响的行数
     */
    int delExamQue(Integer qid);

    /**
     * 计算考试科目的卷面分
     *
     * @param examId 考试ID
     * @return Integer
     */
    Integer calcMaxScore(Integer examId);

    /**
     * 计算考试科目的得分
     *
     * @param examId 考试ID
     * @return Integer
     */
    Double calcScore(Integer examId);
}
