package com.ty.logic.rs.service.impl;

import com.google.common.collect.Lists;
import com.ty.api.model.rs.RsQueRefChapter;
import com.ty.api.rs.service.RsQueRefChapterService;
import com.ty.logic.rs.dao.RsQueRefChapterDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 题目章节标签业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RsQueRefChapterServiceImpl implements RsQueRefChapterService {

    @Autowired
    private RsQueRefChapterDao queRefChapterDao;

    /**
     * 根据题目ID查询所有题目章节标签数据
     *
     * @param qid 题目ID
     * @return List<RsQueRefChapter>
     */
    @Override
    public List<RsQueRefChapter> getAll(Integer qid) throws Exception {
        List<RsQueRefChapter> list = Lists.newArrayList();
        if (null != qid) {
            list = queRefChapterDao.findRsQueRefChapter(qid);
        }
        return list;
    }

    /**
     * 批量保存题目章节标签数据
     *
     * @param list 题目章节标签数据列表
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int saveBatch(List<RsQueRefChapter> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            // 先删除原知识点标签
            this.delete(list.get(0).getQid());

            // 再新增
            n = queRefChapterDao.saveMultiRsQueRefChapter(list);
        }
        return n;
    }

    /**
     * 根据题目ID删除题目章节标签数据
     *
     * @param qid 题目ID
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int delete(Integer qid) throws Exception {
        int n = 0;
        if (null != qid) {
            n = queRefChapterDao.delRsQueRefChapter(qid);
        }
        return n;
    }
}
