package com.ty.logic.sch.dao;

import com.ty.api.model.sch.WrongQueBankRefKnowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 错题集题目知识点标签数据访问层
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Mapper
public interface WrongQueBankRefKnowledgeDao {

    /**
     * 根据条件查询所有错题集题目知识点标签数据
     *
     * @param qids 题目ID集合
     * @return List<WrongQueBankRefKnowledge>
     */
    List<WrongQueBankRefKnowledge> findWrongQueBankRefKnowledge(Set<Integer> qids);

    /**
     * 批量保存错题集题目知识点标签数据
     *
     * @param list 错题集题目知识点标签集合
     * @return int 返回受影响的行数
     */
    int saveMultiWrongQueBankRefKnowledge(List<WrongQueBankRefKnowledge> list);

    /**
     * 根据题目ID删除错题集题目知识点标签数据
     *
     * @param qid 题目ID
     * @return int 返回受影响的行数
     */
    int delWrongQueBankRefKnowledge(Integer qid);
}
