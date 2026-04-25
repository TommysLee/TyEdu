package com.ty.logic.rs.service.impl;

import com.ty.api.model.rs.RsBookChapter;
import com.ty.api.rs.service.RsBookChapterService;
import com.ty.logic.rs.dao.RsBookChapterDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 教材章节业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RsBookChapterServiceImpl implements RsBookChapterService {

    @Autowired
    private RsBookChapterDao rsBookChapterDao;

    /**
     * 根据条件查询所有教材章节数据
     *
     * @param rsBookChapter 教材章节
     * @return List<RsBookChapter>
     * @throws Exception
     */
    @Override
    public List<RsBookChapter> getAll(RsBookChapter rsBookChapter) throws Exception {
        if (null == rsBookChapter) {
            rsBookChapter = new RsBookChapter();
        }
        return rsBookChapterDao.findRsBookChapter(rsBookChapter);
    }

    /**
     * 保存教材章节数据
     *
     * @param rsBookChapter 教材章节
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(RsBookChapter rsBookChapter) throws Exception {
        int n = 0;
        if (null != rsBookChapter) {
            n = rsBookChapterDao.saveRsBookChapter(rsBookChapter);
        }
        return n;
    }

    /**
     * 根据条件查询单条教材章节数据
     *
     * @param rsBookChapter 教材章节
     * @return RsBookChapter
     * @throws Exception
     */
    @Override
    public RsBookChapter getOne(RsBookChapter rsBookChapter) throws Exception {
        if (rsBookChapter != null) {
            List<RsBookChapter> rsBookChapterList = rsBookChapterDao.findRsBookChapter(rsBookChapter);
            if (!rsBookChapterList.isEmpty()) {
                return rsBookChapterList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询教材章节数据
     *
     * @param id ID
     * @return RsBookChapter
     * @throws Exception
     */
    @Override
    public RsBookChapter getById(Integer id) throws Exception {
        RsBookChapter rsBookChapter = null;
        if (Objects.nonNull(id)) {
            rsBookChapter = rsBookChapterDao.findRsBookChapterById(id);
        }
        return rsBookChapter;
    }

    /**
     * 更新教材章节数据
     *
     * @param rsBookChapter 教材章节
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(RsBookChapter rsBookChapter) throws Exception {
        int n = 0;
        if (null != rsBookChapter) {
            n = rsBookChapterDao.updateRsBookChapter(rsBookChapter);
        }
        return n;
    }

    /**
     * 根据ID删除教材章节数据
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
            n = rsBookChapterDao.delRsBookChapter(id);
        }
        return n;
    }
}