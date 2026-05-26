package com.ty.web.sch.controller;

import com.ty.api.model.sch.ExamQueRefKnowledge;
import com.ty.api.sch.service.ExamQueRefKnowledgeService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 考试题目知识点标签Controller
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@RestController
@RequestMapping("/sch/que-knowledge")
public class ExamQueRefKnowledgeController extends BaseController {

    @Autowired
    private ExamQueRefKnowledgeService examQueRefKnowledgeService;

    /**
     * 查询考试题目知识点标签列表
     */
    @RequestMapping("/list/{qid}")
    public AjaxResult list(@PathVariable Integer qid) throws Exception {
        return AjaxResult.success(examQueRefKnowledgeService.getAll(qid));
    }

    /**
     * 增加考试题目知识点标签
     */
    @PostMapping("/save/{qid}")
    public AjaxResult save(@PathVariable Integer qid, @RequestParam List<ExamQueRefKnowledge> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(k -> {
                k.setQid(qid);
            });
            n = examQueRefKnowledgeService.saveBatch(list);
        } else {
            examQueRefKnowledgeService.delete(qid);
        }
        return AjaxResult.success(n);
    }
}
