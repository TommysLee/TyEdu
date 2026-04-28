package com.ty.logic.rs.service.impl;

import com.ty.api.model.rs.RsBook;
import com.ty.api.rs.service.RsBookService;
import com.ty.cm.utils.DateUtils;
import com.ty.cm.utils.FuzzyQueryParamUtil;
import com.ty.logic.rs.dao.RsBookDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 教材业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RsBookServiceImpl implements RsBookService {

    @Autowired
    private RsBookDao rsBookDao;

    /**
     * 根据条件查询所有教材数据
     *
     * @param rsBook 教材
     * @return List<RsBook>
     * @throws Exception
     */
    @Override
    public List<RsBook> getAll(RsBook rsBook) throws Exception {
        if (null == rsBook) {
            rsBook = new RsBook();
        }
        rsBook.setBName(FuzzyQueryParamUtil.escape(rsBook.getBName()));
        return rsBookDao.findRsBook(rsBook);
    }

    /**
     * 保存教材数据
     *
     * @param rsBook 教材
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(RsBook rsBook) throws Exception {
        int n = 0;
        if (null != rsBook) {
            rsBook.setCreateTime(DateUtils.nowText());
            rsBook.setUpdateTime(rsBook.getCreateTime());
            n = rsBookDao.saveRsBook(rsBook);
        }
        return n;
    }

    /**
     * 根据条件查询单条教材数据
     *
     * @param rsBook 教材
     * @return RsBook
     * @throws Exception
     */
    @Override
    public RsBook getOne(RsBook rsBook) throws Exception {
        if (rsBook != null) {
            List<RsBook> rsBookList = rsBookDao.findRsBook(rsBook);
            if (!rsBookList.isEmpty()) {
                return rsBookList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询教材数据
     *
     * @param id ID
     * @return RsBook
     * @throws Exception
     */
    @Override
    public RsBook getById(Integer id) throws Exception {
        RsBook rsBook = null;
        if (Objects.nonNull(id)) {
            rsBook = rsBookDao.findRsBookById(id);
        }
        return rsBook;
    }

    /**
     * 更新教材数据
     *
     * @param rsBook 教材
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(RsBook rsBook) throws Exception {
        int n = 0;
        if (null != rsBook) {
            rsBook.setUpdateTime(DateUtils.nowText());
            n = rsBookDao.updateRsBook(rsBook);
        }
        return n;
    }

    /**
     * 根据ID删除教材数据
     *
     * @param id ID
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int delete(Integer id) throws Exception {
        int n = 0;
        if (Objects.nonNull(id)) {
            n = rsBookDao.delRsBook(id);
        }
        return n;
    }
}