package com.ty.web.system.controller;

import com.google.common.collect.Lists;
import com.ty.api.model.system.DictionaryItem;
import com.ty.cm.model.AjaxResult;
import com.ty.web.spring.config.properties.TyProperties;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据字典Controller
 *
 * @Author Tommy
 * @Date 2026/4/22
 */
@RestController
@RequestMapping("/dict")
public class DictionaryController {

    @Autowired
    private TyProperties tyProperties;

    /**
     * 获取字典列表【学段】
     */
    @GetMapping("/stage")
    public AjaxResult stageList() throws Exception {
        return AjaxResult.success(tyProperties.getDictStageList());
    }

    /**
     * 获取字典列表【难度】
     */
    @GetMapping("/difficulty")
    public AjaxResult difficultyList() throws Exception {
        return AjaxResult.success(tyProperties.getDictDifficultyList());
    }

    /**
     * 获取字典列表【学科】
     */
    @GetMapping("/subject/{stage}")
    public AjaxResult subjectList(@PathVariable String stage) throws Exception {
        stage = stage.toUpperCase();
        return AjaxResult.success(tyProperties.getDictSubjectMap().getOrDefault(stage, Lists.newArrayList()));
    }

    /**
     * 获取字典列表【教材版本】
     */
    @GetMapping("/edition/{stage}/{subject}")
    public AjaxResult editionList(@PathVariable String stage, @PathVariable String subject) throws Exception {
        stage = stage.toUpperCase();
        subject = subject.toUpperCase();

        List<DictionaryItem> editionList = Lists.newArrayList();
        Map<String, List<DictionaryItem>> subjectMap = tyProperties.getDictEditionMap().getOrDefault(stage, null);
        if (MapUtils.isNotEmpty(subjectMap)) {
            editionList = subjectMap.getOrDefault(subject, editionList);
        }
        return AjaxResult.success(editionList);
    }

    /**
     * 获取字典列表【题型】
     */
    @GetMapping("/qtype/{stage}/{subject}")
    public AjaxResult qtypeList(@PathVariable String stage, @PathVariable String subject) throws Exception {
        stage = stage.toUpperCase();
        subject = subject.toUpperCase();

        List<DictionaryItem> qtypeList = Lists.newArrayList();
        Map<String, List<DictionaryItem>> qtypeMap = tyProperties.getDictQTypeMap().getOrDefault(stage, null);
        if (MapUtils.isNotEmpty(qtypeMap)) {
            qtypeList = qtypeMap.getOrDefault(subject, qtypeList);
        }
        return AjaxResult.success(qtypeList);
    }
}
