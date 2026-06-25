package com.ty.logic.sch.dao;

import com.github.pagehelper.Page;
import com.ty.api.model.sch.WrongQueBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 错题集题目数据访问层
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Mapper
public interface WrongQueBankDao {

    /**
     * 根据条件查询错题集题目记录数
     *
     * @param wrongQueBank 错题集题目
     * @return int
     */
    int findWrongQueBankCount(WrongQueBank wrongQueBank);

    /**
     * 根据条件查询所有错题集题目数据
     *
     * @param wrongQueBank 错题集题目
     * @return List<WrongQueBank>
     */
    List<WrongQueBank> findWrongQueBank(WrongQueBank wrongQueBank);

    /**
     * 根据条件分页查询错题集题目数据
     *
     * @param rowBounds 分页参数
     * @param wrongQueBank 错题集题目
     * @return Page<WrongQueBank>
     */
    Page<WrongQueBank> findWrongQueBank(RowBounds rowBounds, WrongQueBank wrongQueBank);

    /**
     * 根据ID查询错题集题目数据
     *
     * @param qid 错题集题目ID
     * @return WrongQueBank
     */
    WrongQueBank findWrongQueBankById(Integer qid);

    /**
     * 保存错题集题目数据
     *
     * @param wrongQueBank 错题集题目
     * @return int 返回受影响的行数
     */
    int saveWrongQueBank(WrongQueBank wrongQueBank);

    /**
     * 删除错题集题目数据
     *
     * @param qid 错题集题目ID
     * @return int 返回受影响的行数
     */
    int delWrongQueBank(Integer qid);
}
