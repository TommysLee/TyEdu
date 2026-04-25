package com.ty.logic.rs.dao;

import com.ty.api.model.rs.RsBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 教材数据访问层
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Mapper
public interface RsBookDao {

    /**
     * 根据条件查询教材记录数
     *
     * @param rsBook 教材
     * @return int
     */
    int findRsBookCount(RsBook rsBook);

    /**
     * 根据条件查询所有教材数据
     *
     * @param rsBook 教材
     * @return List<RsBook>
     */
    List<RsBook> findRsBook(RsBook rsBook);

    /**
     * 根据ID查询教材数据
     *
     * @param bId 教材ID
     * @return RsBook
     */
    RsBook findRsBookById(Integer bId);

    /**
     * 保存教材数据
     *
     * @param rsBook 教材
     * @return int 返回受影响的行数
     */
    int saveRsBook(RsBook rsBook);

    /**
     * 更新教材数据
     *
     * @param rsBook 教材
     * @return int 返回受影响的行数
     */
    int updateRsBook(RsBook rsBook);

    /**
     * 删除教材数据
     *
     * @param bId 教材ID
     * @return int 返回受影响的行数
     */
    int delRsBook(Integer bId);
}