package com.ty.logic.sch.dao;

import com.ty.api.model.sch.ExamQueRefKnowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 考试题目知识点标签数据访问层
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Mapper
public interface ExamQueRefKnowledgeDao {

    /**
     * 根据题目ID查询所有知识点标签数据
     *
     * @param qids 题目ID集合
     * @return List<ExamQueRefKnowledge>
     */
    List<ExamQueRefKnowledge> findExamQueRefKnowledge(Set<Integer> qids);

    /**
     * 批量保存考试题目知识点标签数据
     *
     * @param list 考试题目知识点标签集合
     * @return int 返回受影响的行数
     */
    int saveMultiExamQueRefKnowledge(List<ExamQueRefKnowledge> list);

    /**
     * 根据题目ID删除考试题目知识点标签数据
     *
     * @param qid 考试题目ID
     * @return int 返回受影响的行数
     */
    int delExamQueRefKnowledge(Integer qid);
}
