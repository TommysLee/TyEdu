package com.ty.logic.rs.service.impl;

import com.ty.api.model.rs.RsKnowledge;
import com.ty.api.rs.service.RsKnowledgeService;
import com.ty.cm.exception.CustomException;
import com.ty.logic.rs.dao.RsKnowledgeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ty.cm.constant.Messages.EXISTS_CHILD_NODE;

/**
 * 知识点业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/04/21
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RsKnowledgeServiceImpl implements RsKnowledgeService {

    @Autowired
    private RsKnowledgeDao rsKnowledgeDao;

    /**
     * 该方法用于获取符合条件的总记录数
     *
     * @param rsKnowledge 知识点
     * @return int
     * @throws Exception
     */
    @Override
    public int getCount(RsKnowledge rsKnowledge) throws Exception {
        return rsKnowledgeDao.findRsKnowledgeCount(rsKnowledge);
    }

    /**
     * 根据条件查询所有知识点数据
     *
     * @param rsKnowledge 知识点
     * @return List<RsKnowledge>
     * @throws Exception
     */
    @Override
    public List<RsKnowledge> getAll(RsKnowledge rsKnowledge) throws Exception {
        if (null == rsKnowledge) {
            rsKnowledge = new RsKnowledge();
        }
        return rsKnowledgeDao.findRsKnowledge(rsKnowledge);
    }

    /**
     * 保存知识点数据
     *
     * @param rsKnowledge 知识点
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(RsKnowledge rsKnowledge) throws Exception {
        int n = 0;
        if (null != rsKnowledge) {
            n = rsKnowledgeDao.saveRsKnowledge(rsKnowledge);
        }
        return n;
    }

    /**
     * 根据条件查询单条知识点数据
     *
     * @param rsKnowledge 知识点
     * @return RsKnowledge
     * @throws Exception
     */
    @Override
    public RsKnowledge getOne(RsKnowledge rsKnowledge) throws Exception {
        if (rsKnowledge != null) {
            List<RsKnowledge> rsKnowledgeList = rsKnowledgeDao.findRsKnowledge(rsKnowledge);
            if (!rsKnowledgeList.isEmpty()) {
                return rsKnowledgeList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询知识点数据
     *
     * @param id ID
     * @return RsKnowledge
     * @throws Exception
     */
    @Override
    public RsKnowledge getById(Integer id) throws Exception {
        RsKnowledge rsKnowledge = null;
        if (null != id) {
            rsKnowledge = rsKnowledgeDao.findRsKnowledgeById(id);
        }
        return rsKnowledge;
    }

    /**
     * 更新知识点数据
     *
     * @param rsKnowledge 知识点
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(RsKnowledge rsKnowledge) throws Exception {
        int n = 0;
        if (null != rsKnowledge) {
            n = rsKnowledgeDao.updateRsKnowledge(rsKnowledge);
        }
        return n;
    }

    /**
     * 根据ID删除知识点数据
     *
     * @param id ID
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int delete(Integer id) throws Exception {
        int n = 0;
        if (null != id) {
            // 检查是否存在子节点
            int count = this.getCount(new RsKnowledge().setParentId(id));
            if (count > 0) {
                throw new CustomException(EXISTS_CHILD_NODE);
            }

            // 删除
            n = rsKnowledgeDao.delRsKnowledge(id);
        }
        return n;
    }
}