package com.ty.logic.sch.dao;

import com.ty.api.model.sch.ExamQueRefChapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 考试题目章节标签数据访问层
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Mapper
public interface ExamQueRefChapterDao {

    /**
     * 根据题目ID查询所有章节标签数据
     *
     * @param qid 考试题目ID
     * @return List<ExamQueRefChapter>
     */
    List<ExamQueRefChapter> findExamQueRefChapter(Integer qid);

    /**
     * 批量保存考试题目章节标签数据
     *
     * @param list 考试题目章节标签集合
     * @return int 返回受影响的行数
     */
    int saveMultiExamQueRefChapter(List<ExamQueRefChapter> list);

    /**
     * 根据题目ID删除考试题目章节标签数据
     *
     * @param qid 考试题目ID
     * @return int 返回受影响的行数
     */
    int delExamQueRefChapter(Integer qid);
}
