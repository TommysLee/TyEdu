package com.ty.logic.content.service.impl;

import com.github.pagehelper.Page;
import com.ty.api.content.service.ContentEntryService;
import com.ty.api.model.content.ContentEntry;
import com.ty.logic.content.dao.ContentEntryDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ty.cm.constant.Ty.DATA;
import static com.ty.cm.constant.Ty.PAGES;
import static com.ty.cm.constant.Ty.TOTAL;

/**
 * 知识库条目业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/10/23
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ContentEntryServiceImpl implements ContentEntryService {

    @Autowired
    private ContentEntryDao contentEntryDao;

    /**
     * 根据条件查询所有知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @return List<ContentEntry>
     * @throws Exception
     */
    @Override
    public List<ContentEntry> getAll(ContentEntry contentEntry) throws Exception {
        if (null == contentEntry) {
            contentEntry = new ContentEntry();
        }
        return contentEntryDao.findContentEntry(contentEntry);
    }

    /**
     * 根据条件分页查询知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return Map<String, Object> 返回满足条件的数据集合与记录数
     * @throws Exception
     */
    @Override
    public Map<String, Object> query(ContentEntry contentEntry, String pageNum, String pageSize) throws Exception {
        Page<ContentEntry> page = (Page<ContentEntry>) this.queryData(contentEntry, pageNum, pageSize);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(TOTAL, page.getTotal());
        resultMap.put(PAGES, page.getPages());
        resultMap.put(DATA, page);
        return resultMap;
    }

    /**
     * 根据条件分页查询知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return List<ContentEntry> 返回满足条件的数据集合
     * @throws Exception
     */
    @Override
    public List<ContentEntry> queryData(ContentEntry contentEntry, String pageNum, String pageSize) throws Exception {
        Page<ContentEntry> page = new Page<>();
        if (StringUtils.isNumeric(pageNum) && StringUtils.isNumeric(pageSize)) {
            page = contentEntryDao.findContentEntry(new RowBounds(Integer.parseInt(pageNum), Integer.parseInt(pageSize)), contentEntry);
        }
        return page;
    }

    /**
     * 保存知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int save(ContentEntry contentEntry) throws Exception {
        int n = 0;
        if (null != contentEntry) {
            n = contentEntryDao.saveContentEntry(contentEntry);
        }
        return n;
    }

    /**
     * 根据条件查询单条知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @return ContentEntry
     * @throws Exception
     */
    @Override
    public ContentEntry getOne(ContentEntry contentEntry) throws Exception {
        if (contentEntry != null) {
            List<ContentEntry> contentEntryList = contentEntryDao.findContentEntry(contentEntry);
            if (!contentEntryList.isEmpty()) {
                return contentEntryList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据ID查询知识库条目数据
     *
     * @param id ID
     * @return ContentEntry
     * @throws Exception
     */
    @Override
    public ContentEntry getById(Integer id) throws Exception {
        ContentEntry contentEntry = null;
        if (null != id) {
            contentEntry = contentEntryDao.findContentEntryById(id);
        }
        return contentEntry;
    }

    /**
     * 更新知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Transactional
    @Override
    public int update(ContentEntry contentEntry) throws Exception {
        int n = 0;
        if (null != contentEntry) {
            n = contentEntryDao.updateContentEntry(contentEntry);
        }
        return n;
    }

    /**
     * 根据ID删除知识库条目数据
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
            n = contentEntryDao.delContentEntry(id);
        }
        return n;
    }
}
