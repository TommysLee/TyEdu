package com.ty.logic.rs.service.impl;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.ty.api.model.rs.RsQueBank;
import com.ty.api.model.rs.RsQueRefKnowledge;
import com.ty.api.rs.service.RsQueBankService;
import com.ty.api.rs.service.RsQueRefKnowledgeService;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.rs.dao.RsQueBankDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ty.cm.constant.Ty.DATA;
import static com.ty.cm.constant.Ty.PAGES;
import static com.ty.cm.constant.Ty.TOTAL;

/**
 * 题库业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RsQueBankServiceImpl implements RsQueBankService {

    @Autowired
    private RsQueBankDao queBankDao;

    @Autowired
    private RsQueRefKnowledgeService queRefKnowledgeService;

    /**
     * 根据条件查询所有题库数据
     *
     * @param rsQueBank 题库
     * @return List<RsQueBank>
     * @throws Exception
     */
    @Override
    public List<RsQueBank> getAll(RsQueBank rsQueBank) throws Exception {
        if (null == rsQueBank) {
            rsQueBank = new RsQueBank();
        }
        return queBankDao.findRsQueBank(rsQueBank);
    }

    /**
     * 根据条件分页查询题库数据
     *
     * @param rsQueBank 题库
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return Map<String, Object> 返回满足条件的数据集合与记录数
     * @throws Exception
     */
    @Override
    public Map<String, Object> query(RsQueBank rsQueBank, String pageNum, String pageSize) throws Exception {
        Page<RsQueBank> page = (Page<RsQueBank>) this.queryData(rsQueBank, pageNum, pageSize);
        this.getQueRefKnowledges(page);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(TOTAL, page.getTotal());
        resultMap.put(PAGES, page.getPages());
        resultMap.put(DATA, page);
        return resultMap;
    }

    /**
     * 根据条件分页查询题库数据
     *
     * @param rsQueBank 题库
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return List<RsQueBank> 返回满足条件的数据集合
     * @throws Exception
     */
    @Override
    public List<RsQueBank> queryData(RsQueBank rsQueBank, String pageNum, String pageSize) throws Exception {
        Page<RsQueBank> page = new Page<>();
        if (StringUtils.isNumeric(pageNum) && StringUtils.isNumeric(pageSize)) {
            page = queBankDao.findRsQueBank(new RowBounds(Integer.parseInt(pageNum), Integer.parseInt(pageSize)), rsQueBank);
        }
        return page;
    }

    /**
     * 保存题库数据
     *
     * @param rsQueBank 题库
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(RsQueBank rsQueBank) throws Exception {
        int n = 0;
        if (null != rsQueBank) {
            rsQueBank.setCreateTime(DateUtils.nowText());
            rsQueBank.setSubject(StringUtils.upperCase(rsQueBank.getSubject()));
            n = queBankDao.saveRsQueBank(rsQueBank);
        }
        return n;
    }

    /**
     * 根据条件查询单条题库数据
     *
     * @param rsQueBank 题库
     * @return RsQueBank
     * @throws Exception
     */
    @Override
    public RsQueBank getOne(RsQueBank rsQueBank) throws Exception {
        if (rsQueBank != null) {
            List<RsQueBank> rsQueBankList = queBankDao.findRsQueBank(rsQueBank);
            if (!rsQueBankList.isEmpty()) {
                return rsQueBankList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询题库数据
     *
     * @param id ID
     * @return RsQueBank
     * @throws Exception
     */
    @Override
    public RsQueBank getById(Integer id) throws Exception {
        RsQueBank rsQueBank = null;
        if (Objects.nonNull(id)) {
            rsQueBank = queBankDao.findRsQueBankById(id);
        }
        return rsQueBank;
    }

    /**
     * 更新题库数据
     *
     * @param rsQueBank 题库
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(RsQueBank rsQueBank) throws Exception {
        int n = 0;
        if (null != rsQueBank) {
            rsQueBank.setUpdateTime(DateUtils.nowText());
            n = queBankDao.updateRsQueBank(rsQueBank);
        }
        return n;
    }

    /**
     * 根据ID删除题库数据
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
            n = queBankDao.delRsQueBank(id);
        }
        return n;
    }

    /*
     * 获取题目的详细知识点标签数据
     */
    void getQueRefKnowledges(List<RsQueBank> queList) throws Exception {
        if (CollectionUtils.isNotEmpty(queList)) {
            // 抽取题目ID集合
            Set<Integer> qidSet = queList.stream().map(RsQueBank::getQid).collect(Collectors.toSet());

            // 查询知识点标签数据后，按题目ID分组
            List<RsQueRefKnowledge> ktags = queRefKnowledgeService.getFullAll(qidSet);
            Map<Integer,List<RsQueRefKnowledge>> ktagsMap = ktags.stream().collect(Collectors.groupingBy(RsQueRefKnowledge::getQid));

            // 将知识点标签数据，添加到题目对象中
            for (RsQueBank q : queList) {
                q.setKtags(ktagsMap.getOrDefault(q.getQid(), Lists.newArrayListWithCapacity(0)));
            }
        }
    }
}