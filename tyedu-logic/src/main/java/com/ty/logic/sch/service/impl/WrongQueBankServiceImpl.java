package com.ty.logic.sch.service.impl;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.ty.api.model.sch.WrongQueBank;
import com.ty.api.model.sch.WrongQueBankRefKnowledge;
import com.ty.api.sch.service.WrongQueBankRefKnowledgeService;
import com.ty.api.sch.service.WrongQueBankService;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.sch.dao.WrongQueBankDao;
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
 * 错题集题目业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class WrongQueBankServiceImpl implements WrongQueBankService {

    @Autowired
    private WrongQueBankDao wrongQueBankDao;

    @Autowired
    private WrongQueBankRefKnowledgeService wrongQueRefKnowledgeService;

    /**
     * 该方法用于获取符合条件的总记录数
     *
     * @param wrongQueBank 错题集题目
     * @return int
     * @throws Exception
     */
    @Override
    public int getCount(WrongQueBank wrongQueBank) throws Exception {
        int count = 0;
        if (null != wrongQueBank) {
            count = wrongQueBankDao.findWrongQueBankCount(wrongQueBank);
        }
        return count;
    }

    /**
     * 根据条件查询所有错题集题目数据
     *
     * @param wrongQueBank 错题集题目
     * @return List<WrongQueBank>
     * @throws Exception
     */
    @Override
    public List<WrongQueBank> getAll(WrongQueBank wrongQueBank) throws Exception {
        if (null == wrongQueBank) {
            wrongQueBank = new WrongQueBank();
        }
        return wrongQueBankDao.findWrongQueBank(wrongQueBank);
    }

    /**
     * 根据条件分页查询错题集题目数据
     *
     * @param wrongQueBank 错题集题目
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return Map<String, Object> 返回满足条件的数据集合与记录数
     * @throws Exception
     */
    @Override
    public Map<String, Object> query(WrongQueBank wrongQueBank, String pageNum, String pageSize) throws Exception {
        Page<WrongQueBank> page = (Page<WrongQueBank>) this.queryData(wrongQueBank, pageNum, pageSize);
        this.getWrongQueRefKnowledges(page);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(TOTAL, page.getTotal());
        resultMap.put(PAGES, page.getPages());
        resultMap.put(DATA, page);
        return resultMap;
    }

    /**
     * 根据条件分页查询错题集题目数据
     *
     * @param wrongQueBank 错题集题目
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return List<WrongQueBank> 返回满足条件的数据集合
     * @throws Exception
     */
    @Override
    public List<WrongQueBank> queryData(WrongQueBank wrongQueBank, String pageNum, String pageSize) throws Exception {
        Page<WrongQueBank> page = new Page<>();
        if (StringUtils.isNumeric(pageNum) && StringUtils.isNumeric(pageSize)) {
            page = wrongQueBankDao.findWrongQueBank(new RowBounds(Integer.parseInt(pageNum), Integer.parseInt(pageSize)), wrongQueBank);
        }
        return page;
    }

    /**
     * 保存错题集题目数据
     *
     * @param wrongQueBank 错题集题目
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(WrongQueBank wrongQueBank) throws Exception {
        int n = 0;
        if (null != wrongQueBank) {
            wrongQueBank.setCreateTime(DateUtils.nowText());
            n = wrongQueBankDao.saveWrongQueBank(wrongQueBank);
        }
        return n;
    }

    /**
     * 根据条件查询单条错题集题目数据
     *
     * @param wrongQueBank 错题集题目
     * @return WrongQueBank
     * @throws Exception
     */
    @Override
    public WrongQueBank getOne(WrongQueBank wrongQueBank) throws Exception {
        if (wrongQueBank != null) {
            List<WrongQueBank> wrongQueBankList = wrongQueBankDao.findWrongQueBank(wrongQueBank);
            if (!wrongQueBankList.isEmpty()) {
                return wrongQueBankList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询错题集题目数据
     *
     * @param id ID
     * @return WrongQueBank
     * @throws Exception
     */
    @Override
    public WrongQueBank getById(Integer id) throws Exception {
        WrongQueBank wrongQueBank = null;
        if (Objects.nonNull(id)) {
            wrongQueBank = wrongQueBankDao.findWrongQueBankById(id);
        }
        return wrongQueBank;
    }

    /**
     * 根据ID删除错题集题目数据
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
            n = wrongQueBankDao.delWrongQueBank(id);
        }
        return n;
    }

    /*
     * 获取题目的详细知识点标签数据
     */
    void getWrongQueRefKnowledges(List<WrongQueBank> list) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            // 抽取题目ID集合
            Set<Integer> qidSet = list.stream().map(WrongQueBank::getQid).collect(Collectors.toSet());

            // 查询知识点标签数据后，按题目ID分组
            List<WrongQueBankRefKnowledge> ktags = wrongQueRefKnowledgeService.getAll(qidSet);
            Map<Integer, List<WrongQueBankRefKnowledge>> ktagsMap = ktags.stream().collect(Collectors.groupingBy(WrongQueBankRefKnowledge::getQid));

            // 将知识点标签数据，添加到题目对象中
            for (WrongQueBank q : list) {
                q.setKtags(ktagsMap.getOrDefault(q.getQid(), Lists.newArrayListWithCapacity(0)));
            }
        }
    }
}
