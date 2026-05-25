package com.ty.logic.spring.config.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.api.model.system.DictionaryItem;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * 项目属性配置类
 *
 * @Author Tommy
 * @Date 2022/2/21
 */
@ConfigurationProperties(prefix = "ty")
@Data
public class TyProperties {

    /** 本地化语言的Cookie标识 **/
    private String lang;

    /** 语言列表 **/
    private List<Map<String, String>> langList;

    /** 视图映射 **/
    private Map<String, String> viewMapping = Maps.newHashMap();

    /** 字典【学段】 **/
    private List<DictionaryItem> dictStageList = Lists.newArrayList();

    /** 字典【年级】 **/
    private Map<String, List<DictionaryItem>> dictGradeMap = Maps.newLinkedHashMap();

    /** 字典【难度】 **/
    private List<DictionaryItem> dictDifficultyList = Lists.newArrayList();

    /** 字典【考试类型】 **/
    private List<DictionaryItem> dictExamTypeList = Lists.newArrayList();

    /** 字典【学科】 **/
    private Map<String, List<DictionaryItem>> dictSubjectMap = Maps.newLinkedHashMap();

    /** 字典【教材版本】 **/
    private Map<String, Map<String, List<DictionaryItem>>> dictEditionMap = Maps.newLinkedHashMap();

    /** 字典【题型】 **/
    private Map<String, Map<String, List<DictionaryItem>>> dictQTypeMap = Maps.newLinkedHashMap();
}
