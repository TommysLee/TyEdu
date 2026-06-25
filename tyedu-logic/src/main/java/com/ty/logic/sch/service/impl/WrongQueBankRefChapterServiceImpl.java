package com.ty.logic.sch.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ty.api.model.sch.WrongQueBankRefChapter;
import com.ty.api.sch.service.WrongQueBankRefChapterService;
import com.ty.cm.utils.DataUtil;
import com.ty.logic.sch.dao.WrongQueBankRefChapterDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 错题集题目章节标签业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class WrongQueBankRefChapterServiceImpl implements WrongQueBankRefChapterService {

    @Autowired
    private WrongQueBankRefChapterDao wrongQueBankRefChapterDao;

    /**
     * 根据题目ID查询所有章节标签数据
     *
     * @param qids 题目ID集合
     * @return List<WrongQueBankRefChapter>
     * @throws Exception
     */
    @Override
    public List<WrongQueBankRefChapter> getAll(Set<Integer> qids) throws Exception {
        List<WrongQueBankRefChapter> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(qids)) {
            list = wrongQueBankRefChapterDao.findWrongQueBankRefChapter(qids);
        }
        return list;
    }

    /**
     * 根据题目ID查询所有章节标签数据，以JSON格式返回
     *
     * @param qid 题目ID
     * @return String - JSON Format
     * @throws Exception
     */
    @Override
    public String getSimpleAllForJson(Integer qid) throws Exception {
        String jsonData = "[]";
        List<WrongQueBankRefChapter> list = Lists.newArrayList();
        if (null != qid) {
            list = this.getAll(Sets.newHashSet(qid));
        }
        if (CollectionUtils.isNotEmpty(list)) {
            jsonData = DataUtil.toJSON(list.stream().map(WrongQueBankRefChapter::getChptId).collect(Collectors.toList()));
        }
        return jsonData;
    }

    /**
     * 批量保存考试题目章节标签数据
     *
     * @param list 题目章节标签数据列表
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Override
    public int saveBatch(List<WrongQueBankRefChapter> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            Integer qid = list.get(0).getQid();

            // 先删除原章节标签数据
            this.delete(qid);

            // 再新增
            n = wrongQueBankRefChapterDao.saveMultiWrongQueBankRefChapter(list);
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
            n = wrongQueBankRefChapterDao.delWrongQueBankRefChapter(qid);
        }
        return n;
    }
}
