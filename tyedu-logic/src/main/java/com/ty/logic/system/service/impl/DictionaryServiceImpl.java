package com.ty.logic.system.service.impl;

import com.google.common.collect.Lists;
import com.ty.api.model.system.DictionaryItem;
import com.ty.api.system.service.DictionaryService;
import com.ty.logic.spring.config.properties.TyProperties;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据字典业务逻辑实现类
 *
 * @Author Tommy
 * @Date 2026/5/18
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private TyProperties tyProperties;

    /**
     * 获取字典列表【学段】
     *
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> stageList() {
        return tyProperties.getDictStageList();
    }

    /**
     * 获取字典列表【年级】
     *
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> gradeList() {
        List<DictionaryItem> list = Lists.newArrayList();
        tyProperties.getDictGradeMap().values().forEach(list::addAll);
        return list;
    }

    /**
     * 获取字典列表【年级】
     *
     * @param stage 学段
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> gradeList(String stage) {
        stage = stage.toUpperCase();
        return tyProperties.getDictGradeMap().getOrDefault(stage, Lists.newArrayList());
    }

    /**
     * 获取字典列表【难度】
     *
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> difficultyList() {
        return tyProperties.getDictDifficultyList();
    }

    /**
     * 获取字典列表【考试类型】
     *
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> examTypeList() {
        return tyProperties.getDictExamTypeList();
    }

    /**
     * 获取字典列表【学科】
     *
     * @param stage 学段
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> subjectList(String stage) {
        stage = stage.toUpperCase();
        return tyProperties.getDictSubjectMap().getOrDefault(stage, Lists.newArrayList());
    }

    /**
     * 获取字典列表【教材版本】
     *
     * @param stage   学段
     * @param subject 科目
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> editionList(String stage, String subject) {
        stage = stage.toUpperCase();
        subject = subject.toUpperCase();

        List<DictionaryItem> editionList = Lists.newArrayList();
        Map<String, List<DictionaryItem>> subjectMap = tyProperties.getDictEditionMap().getOrDefault(stage, null);
        if (MapUtils.isNotEmpty(subjectMap)) {
            editionList = subjectMap.getOrDefault(subject, editionList);
        }
        return editionList;
    }

    /**
     * 获取字典列表【教材版本】的Map形式
     *
     * @param stage   学段
     * @param subject 科目
     * @return List<DictionaryItem>
     */
    @Override
    public Map<String, DictionaryItem> editionMap(String stage, String subject) {
        List<DictionaryItem> editionList = this.editionList(stage, subject);
        return editionList.stream().collect(Collectors.toMap(DictionaryItem::getTitle, Function.identity()));
    }

    /**
     * 获取字典列表【题型】
     *
     * @param stage   学段
     * @param subject 科目
     * @return List<DictionaryItem>
     */
    @Override
    public List<DictionaryItem> qtypeList(String stage, String subject) {
        stage = stage.toUpperCase();
        subject = subject.toUpperCase();

        List<DictionaryItem> qtypeList = Lists.newArrayList();
        Map<String, List<DictionaryItem>> qtypeMap = tyProperties.getDictQTypeMap().getOrDefault(stage, null);
        if (MapUtils.isNotEmpty(qtypeMap)) {
            qtypeList = qtypeMap.getOrDefault(subject, qtypeList);
        }
        return qtypeList;
    }
}
