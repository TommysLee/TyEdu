package com.ty.logic.content.dao;

import com.github.pagehelper.Page;
import com.ty.api.model.content.ContentEntry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 知识库条目数据访问层
 *
 * @Author TyCode
 * @Date 2026/10/23
 */
@Mapper
public interface ContentEntryDao {

    /**
     * 根据条件查询知识库条目记录数
     *
     * @param contentEntry 知识库条目
     * @return int
     */
    int findContentEntryCount(ContentEntry contentEntry);

    /**
     * 根据条件查询所有知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @return List<ContentEntry>
     */
    List<ContentEntry> findContentEntry(ContentEntry contentEntry);

    /**
     * 根据条件分页查询知识库条目数据
     *
     * @param rowBounds 分页参数
     * @param contentEntry 知识库条目
     * @return Page<ContentEntry>
     */
    Page<ContentEntry> findContentEntry(RowBounds rowBounds, ContentEntry contentEntry);

    /**
     * 根据ID查询知识库条目数据
     *
     * @param eid 知识库条目ID
     * @return ContentEntry
     */
    ContentEntry findContentEntryById(Integer eid);

    /**
     * 保存知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @return int 返回受影响的行数
     */
    int saveContentEntry(ContentEntry contentEntry);

    /**
     * 更新知识库条目数据
     *
     * @param contentEntry 知识库条目
     * @return int 返回受影响的行数
     */
    int updateContentEntry(ContentEntry contentEntry);

    /**
     * 删除知识库条目数据
     *
     * @param eid 知识库条目ID
     * @return int 返回受影响的行数
     */
    int delContentEntry(Integer eid);
}
