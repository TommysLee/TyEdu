package com.ty.logic.sch.dao;

import com.ty.api.model.sch.WrongQueBankRefChapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 错题集题目章节标签数据访问层
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Mapper
public interface WrongQueBankRefChapterDao {

    /**
     * 根据题目ID查询所有错题集题目章节标签数据
     *
     * @param qids 错题集题目ID集合
     * @return List<WrongQueBankRefChapter>
     */
    List<WrongQueBankRefChapter> findWrongQueBankRefChapter(Set<Integer> qids);

    /**
     * 批量保存错题集题目章节标签数据
     *
     * @param list 错题集题目章节标签集合
     * @return int 返回受影响的行数
     */
    int saveMultiWrongQueBankRefChapter(List<WrongQueBankRefChapter> list);

    /**
     * 根据题目ID删除错题集题目章节标签数据
     *
     * @param qid 错题集题目ID
     * @return int 返回受影响的行数
     */
    int delWrongQueBankRefChapter(Integer qid);
}
