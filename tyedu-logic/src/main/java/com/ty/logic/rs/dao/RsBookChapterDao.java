package com.ty.logic.rs.dao;

import com.ty.api.model.rs.RsBookChapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 教材章节数据访问层
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Mapper
public interface RsBookChapterDao {

    /**
     * 根据条件查询教材章节记录数
     *
     * @param rsBookChapter 教材章节
     * @return int
     */
    int findRsBookChapterCount(RsBookChapter rsBookChapter);

    /**
     * 根据条件查询所有教材章节数据
     *
     * @param rsBookChapter 教材章节
     * @return List<RsBookChapter>
     */
    List<RsBookChapter> findRsBookChapter(RsBookChapter rsBookChapter);

    /**
     * 根据ID查询教材章节数据
     *
     * @param chptId 教材章节ID
     * @return RsBookChapter
     */
    RsBookChapter findRsBookChapterById(Integer chptId);

    /**
     * 保存教材章节数据
     *
     * @param rsBookChapter 教材章节
     * @return int 返回受影响的行数
     */
    int saveRsBookChapter(RsBookChapter rsBookChapter);

    /**
     * 更新教材章节数据
     *
     * @param rsBookChapter 教材章节
     * @return int 返回受影响的行数
     */
    int updateRsBookChapter(RsBookChapter rsBookChapter);

    /**
     * 删除教材章节数据
     *
     * @param chptId 教材章节ID
     * @return int 返回受影响的行数
     */
    int delRsBookChapter(Integer chptId);
}
