package com.ty.logic.rs.dao;

import com.ty.api.model.rs.RsKnowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 知识点数据访问层
 *
 * @Author TyCode
 * @Date 2026/04/21
 */
@Mapper
public interface RsKnowledgeDao {

    /**
     * 根据条件查询知识点记录数
     *
     * @param rsKnowledge 知识点
     * @return int
     */
    int findRsKnowledgeCount(RsKnowledge rsKnowledge);

    /**
     * 根据条件查询所有知识点数据
     *
     * @param rsKnowledge 知识点
     * @return List<RsKnowledge>
     */
    List<RsKnowledge> findRsKnowledge(RsKnowledge rsKnowledge);

    /**
     * 根据ID查询知识点数据
     *
     * @param kid 知识点ID
     * @return RsKnowledge
     */
    RsKnowledge findRsKnowledgeById(Integer kid);

    /**
     * 保存知识点数据
     *
     * @param rsKnowledge 知识点
     * @return int 返回受影响的行数
     */
    int saveRsKnowledge(RsKnowledge rsKnowledge);

    /**
     * 更新知识点数据
     *
     * @param rsKnowledge 知识点
     * @return int 返回受影响的行数
     */
    int updateRsKnowledge(RsKnowledge rsKnowledge);

    /**
     * 删除知识点数据
     *
     * @param kid 知识点ID
     * @return int 返回受影响的行数
     */
    int delRsKnowledge(Integer kid);
}
