package com.ty.api.system.service;

import com.ty.api.model.system.DictionaryItem;

import java.util.List;
import java.util.Map;

/**
 * 数据字典业务逻辑接口
 *
 * @Author Tommy
 * @Date 2026/5/18
 */
public interface DictionaryService {

    /**
     * 获取字典列表【学段】
     *
     * @return List<DictionaryItem>
     */
    List<DictionaryItem> stageList();

    /**
     * 获取字典列表【难度】
     *
     * @return List<DictionaryItem>
     */
    List<DictionaryItem> difficultyList();

    /**
     * 获取字典列表【学科】
     *
     * @param stage 学段
     * @return List<DictionaryItem>
     */
    List<DictionaryItem> subjectList(String stage);

    /**
     * 获取字典列表【教材版本】
     *
     * @param stage   学段
     * @param subject 科目
     * @return List<DictionaryItem>
     */
    List<DictionaryItem> editionList(String stage, String subject);

    /**
     * 获取字典列表【教材版本】的Map形式
     *
     * @param stage   学段
     * @param subject 科目
     * @return List<DictionaryItem>
     */
    Map<String, DictionaryItem> editionMap(String stage, String subject);

    /**
     * 获取字典列表【题型】
     *
     * @param stage   学段
     * @param subject 科目
     * @return List<DictionaryItem>
     */
    List<DictionaryItem> qtypeList(String stage, String subject);
}
