package com.ty.logic.content.service.impl;

import com.ty.api.content.service.ContentCategoryService;
import com.ty.api.model.content.ContentCategory;
import com.ty.cm.exception.CustomException;
import com.ty.cm.utils.NumberUtil;
import com.ty.logic.content.dao.ContentCategoryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ty.cm.constant.Messages.RELATED_DATA_DELETE;

/**
 * 知识库分类业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/10/23
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryDao contentCategoryDao;

    /**
     * 获取最大排序号
     *
     * @return Integer
     * @throws Exception
     */
    @Override
    public Integer maxSeq() throws Exception {
        return null;
    }

    /**
     * 根据条件查询所有知识库分类数据
     *
     * @param contentCategory 知识库分类
     * @return List<ContentCategory>
     * @throws Exception
     */
    @Override
    public List<ContentCategory> getAll(ContentCategory contentCategory) throws Exception {
        if (null == contentCategory) {
            contentCategory = new ContentCategory();
        }
        return contentCategoryDao.findContentCategory(contentCategory);
    }

    /**
     * 保存知识库分类数据
     *
     * @param contentCategory 知识库分类
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(ContentCategory contentCategory) throws Exception {
        int n = 0;
        if (null != contentCategory) {
            Integer maxSeq = NumberUtil.defaultIfNull(contentCategoryDao.maxSeq(), 0);
            contentCategory.setSeq(maxSeq + 1);
            n = contentCategoryDao.saveContentCategory(contentCategory);
        }
        return n;
    }

    /**
     * 根据条件查询单条知识库分类数据
     *
     * @param contentCategory 知识库分类
     * @return ContentCategory
     * @throws Exception
     */
    @Override
    public ContentCategory getOne(ContentCategory contentCategory) throws Exception {
        if (contentCategory != null) {
            List<ContentCategory> contentCategoryList = contentCategoryDao.findContentCategory(contentCategory);
            if (!contentCategoryList.isEmpty()) {
                return contentCategoryList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询知识库分类数据
     *
     * @param id ID
     * @return ContentCategory
     * @throws Exception
     */
    @Override
    public ContentCategory getById(Integer id) throws Exception {
        ContentCategory contentCategory = null;
        if (null != id) {
            contentCategory = contentCategoryDao.findContentCategoryById(id);
        }
        return contentCategory;
    }

    /**
     * 更新知识库分类数据
     *
     * @param contentCategory 知识库分类
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(ContentCategory contentCategory) throws Exception {
        int n = 0;
        if (null != contentCategory) {
            n = contentCategoryDao.updateContentCategory(contentCategory);
        }
        return n;
    }

    /**
     * 交换两者的排序号
     *
     * @param ctgId1 知识库分类ID_1
     * @param ctgId2 知识库分类ID_2
     * @return boolean
     * @throws Exception
     */
    @Transactional
    @Override
    public boolean swapSeq(Integer ctgId1, Integer ctgId2) throws Exception {
        ContentCategory ctg1 = this.getById(ctgId1);
        ContentCategory ctg2 = this.getById(ctgId2);
        if (null == ctg1 || null == ctg2) {
            return false;
        }

        // 交换排序号
        Integer seq1 = ctg1.getSeq();
        Integer seq2 = ctg2.getSeq();
        ctg1.setSeq(seq2);
        ctg2.setSeq(seq1);

        // 更新
        this.update(ctg1);
        this.update(ctg2);
        return true;
    }

    /**
     * 根据ID删除知识库分类数据
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
            try {
                n = contentCategoryDao.delContentCategory(id);
            } catch (UncategorizedSQLException e) {
                throw new CustomException(RELATED_DATA_DELETE);
            }
        }
        return n;
    }
}
