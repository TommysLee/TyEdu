package com.ty.logic.rs.dao;

import com.ty.api.model.rs.RsQueRefKnowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 题目知识点标签数据访问层
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@Mapper
public interface RsQueRefKnowledgeDao {

    /**
     * 根据题目ID查询所有题目知识点标签数据
     *
     * @param qid 题目ID
     * @return List<RsQueRefKnowledge>
     */
    List<RsQueRefKnowledge> findRsQueRefKnowledge(Integer qid);

    /**
     * 查询题目的完整知识点标签数据
     *
     * @param qids 题目ID集合
     * @return List<RsQueRefKnowledge>
     */
    List<RsQueRefKnowledge> findFullRsQueRefKnowledge(Set<Integer> qids);

    /**
     * 批量保存题目知识点标签数据
     *
     * @param list 题目知识点标签集合
     * @return int 返回受影响的行数
     */
    int saveMultiRsQueRefKnowledge(List<RsQueRefKnowledge> list);

    /**
     * 根据题目ID删除题目知识点标签数据
     *
     * @param qid 题目ID
     * @return int 返回受影响的行数
     */
    int delRsQueRefKnowledge(Integer qid);
}
