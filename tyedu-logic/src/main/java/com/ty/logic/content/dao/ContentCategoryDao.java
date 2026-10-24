package com.ty.logic.content.dao;

import com.ty.api.model.content.ContentCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 知识库分类数据访问层
 *
 * @Author TyCode
 * @Date 2026/10/23
 */
@Mapper
public interface ContentCategoryDao {

    /**
     * 获取最大排序号
     *
     * @return Integer
     */
    Integer maxSeq();

    /**
     * 根据条件查询知识库分类记录数
     *
     * @param contentCategory 知识库分类
     * @return int
     */
    int findContentCategoryCount(ContentCategory contentCategory);

    /**
     * 根据条件查询所有知识库分类数据
     *
     * @param contentCategory 知识库分类
     * @return List<ContentCategory>
     */
    List<ContentCategory> findContentCategory(ContentCategory contentCategory);

    /**
     * 根据ID查询知识库分类数据
     *
     * @param ctgId 知识库分类ID
     * @return ContentCategory
     */
    ContentCategory findContentCategoryById(Integer ctgId);

    /**
     * 保存知识库分类数据
     *
     * @param contentCategory 知识库分类
     * @return int 返回受影响的行数
     */
    int saveContentCategory(ContentCategory contentCategory);

    /**
     * 更新知识库分类数据
     *
     * @param contentCategory 知识库分类
     * @return int 返回受影响的行数
     */
    int updateContentCategory(ContentCategory contentCategory);

    /**
     * 删除知识库分类数据
     *
     * @param ctgId 知识库分类ID
     * @return int 返回受影响的行数
     */
    int delContentCategory(Integer ctgId);
}
