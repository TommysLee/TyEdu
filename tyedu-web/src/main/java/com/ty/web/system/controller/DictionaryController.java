package com.ty.web.system.controller;

import com.ty.api.system.service.DictionaryService;
import com.ty.cm.model.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private DictionaryService dictionaryService;

    /**
     * 获取字典列表【学段】
     */
    @GetMapping("/stage")
    public AjaxResult stageList() throws Exception {
        return AjaxResult.success(dictionaryService.stageList());
    }

    /**
     * 获取字典列表【年级】
     */
    @GetMapping("/grade/{stage}")
    public AjaxResult gradeList(@PathVariable String stage) throws Exception {
        return AjaxResult.success(dictionaryService.gradeList(stage));
    }

    /**
     * 获取字典列表【难度】
     */
    @GetMapping("/difficulty")
    public AjaxResult difficultyList() throws Exception {
        return AjaxResult.success(dictionaryService.difficultyList());
    }

    /**
     * 获取字典列表【考试类型】
     */
    @GetMapping("/exam_type")
    public AjaxResult examTypeList() throws Exception {
        return AjaxResult.success(dictionaryService.examTypeList());
    }

    /**
     * 获取字典列表【学科】
     */
    @GetMapping("/subject/{stage}")
    public AjaxResult subjectList(@PathVariable String stage) throws Exception {
        return AjaxResult.success(dictionaryService.subjectList(stage));
    }

    /**
     * 获取字典列表【教材版本】
     */
    @GetMapping("/edition/{stage}/{subject}")
    public AjaxResult editionList(@PathVariable String stage, @PathVariable String subject) throws Exception {
        return AjaxResult.success(dictionaryService.editionList(stage, subject));
    }

    /**
     * 获取字典列表【题型】
     */
    @GetMapping("/qtype/{stage}/{subject}")
    public AjaxResult qtypeList(@PathVariable String stage, @PathVariable String subject) throws Exception {
        return AjaxResult.success(dictionaryService.qtypeList(stage, subject));
    }
}
