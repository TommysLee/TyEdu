package com.ty.logic.sch.dao;

import com.github.pagehelper.Page;
import com.ty.api.model.sch.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 考试数据访问层
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Mapper
public interface ExamDao {

    /**
     * 根据条件查询考试记录数
     *
     * @param exam 考试
     * @return int
     */
    int findExamCount(Exam exam);

    /**
     * 根据条件查询所有考试数据
     *
     * @param exam 考试
     * @return List<Exam>
     */
    List<Exam> findExam(Exam exam);

    /**
     * 根据条件分页查询考试数据
     *
     * @param rowBounds 分页参数
     * @param exam 考试
     * @return Page<Exam>
     */
    Page<Exam> findExam(RowBounds rowBounds, Exam exam);

    /**
     * 根据ID查询考试数据
     *
     * @param examId 考试ID
     * @return Exam
     */
    Exam findExamById(Integer examId);

    /**
     * 保存考试数据
     *
     * @param exam 考试
     * @return int 返回受影响的行数
     */
    int saveExam(Exam exam);

    /**
     * 更新考试数据
     *
     * @param exam 考试
     * @return int 返回受影响的行数
     */
    int updateExam(Exam exam);

    /**
     * 删除考试数据
     *
     * @param examId 考试ID
     * @return int 返回受影响的行数
     */
    int delExam(Integer examId);
}
