package com.ty.logic.rs.service.impl;

import com.google.common.collect.Lists;
import com.ty.api.model.rs.RsQueBank;
import com.ty.api.model.rs.RsQueRefChapter;
import com.ty.api.rs.service.RsQueBankService;
import com.ty.api.rs.service.RsQueRefChapterService;
import com.ty.cm.utils.DataUtil;
import com.ty.logic.rs.dao.RsQueRefChapterDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    @Lazy
    private RsQueBankService queBankService;

    /**
     * 根据题目ID查询题目的所有章节标签数据
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
     * 根据题目ID查询题目的所有章节标签ID数据，以JSON返回
     *
     * @param qid 题目ID
     * @return String - JSON Format
     * @throws Exception
     */
    @Override
    public String getSimpleAllForJson(Integer qid) throws Exception {
        String jsonData = "[]";
        List<RsQueRefChapter> list = this.getAll(qid);
        if (CollectionUtils.isNotEmpty(list)) {
            jsonData = DataUtil.toJSON(list.stream().map(RsQueRefChapter::getChptId).collect(Collectors.toList()));
        }
        return jsonData;
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
            Integer qid = list.get(0).getQid();

            // 先删除原知识点标签
            this.delete(qid);

            // 再新增
            n = queRefChapterDao.saveMultiRsQueRefChapter(list);

            // 更新题目的章节标记
            queBankService.update(new RsQueBank().setQid(qid).setChptMarked(1));
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

            // 更新题目的章节标记
            queBankService.update(new RsQueBank().setQid(qid).setChptMarked(0));
        }
        return n;
    }
}
