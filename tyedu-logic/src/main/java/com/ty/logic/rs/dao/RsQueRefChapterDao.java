package com.ty.logic.rs.dao;

import com.github.pagehelper.Page;
import com.ty.api.model.rs.RsQueRefChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 题目章节标签数据访问层
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@Mapper
public interface RsQueRefChapterDao {

    /**
     * 根据题目ID查询所有题目章节标签数据
     *
     * @param qid 题目ID
     * @return List<RsQueRefChapter>
     */
    List<RsQueRefChapter> findRsQueRefChapter(Integer qid);

    /**
     * 批量保存题目章节标签数据
     *
     * @param list 题目章节标签集合
     * @return int 返回受影响的行数
     */
    int saveMultiRsQueRefChapter(List<RsQueRefChapter> list);

    /**
     * 根据题目ID删除题目章节标签数据
     *
     * @param qid 题目ID
     * @return int 返回受影响的行数
     */
    int delRsQueRefChapter(Integer qid);
}
