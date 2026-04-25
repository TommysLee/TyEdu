package com.ty.logic.rs.dao;

import com.github.pagehelper.Page;
import com.ty.api.model.rs.RsQueBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 题库数据访问层
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Mapper
public interface RsQueBankDao {

    /**
     * 根据条件查询题库记录数
     *
     * @param rsQueBank 题库
     * @return int
     */
    int findRsQueBankCount(RsQueBank rsQueBank);

    /**
     * 根据条件查询所有题库数据
     *
     * @param rsQueBank 题库
     * @return List<RsQueBank>
     */
    List<RsQueBank> findRsQueBank(RsQueBank rsQueBank);

    /**
     * 根据条件分页查询题库数据
     *
     * @param rowBounds 分页参数
     * @param rsQueBank 题库
     * @return Page<RsQueBank>
     */
    Page<RsQueBank> findRsQueBank(RowBounds rowBounds, RsQueBank rsQueBank);

    /**
     * 根据ID查询题库数据
     *
     * @param qId 题库ID
     * @return RsQueBank
     */
    RsQueBank findRsQueBankById(Integer qId);

    /**
     * 保存题库数据
     *
     * @param rsQueBank 题库
     * @return int 返回受影响的行数
     */
    int saveRsQueBank(RsQueBank rsQueBank);

    /**
     * 更新题库数据
     *
     * @param rsQueBank 题库
     * @return int 返回受影响的行数
     */
    int updateRsQueBank(RsQueBank rsQueBank);

    /**
     * 删除题库数据
     *
     * @param qId 题库ID
     * @return int 返回受影响的行数
     */
    int delRsQueBank(Integer qId);
}
