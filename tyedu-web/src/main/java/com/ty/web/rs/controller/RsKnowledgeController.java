package com.ty.web.rs.controller;

import com.ty.api.model.rs.RsKnowledge;
import com.ty.api.rs.service.RsKnowledgeService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识点Controller
 *
 * @Author TyCode
 * @Date 2026/04/21
 */
@RestController
@RequestMapping("/rs/knowledge")
public class RsKnowledgeController extends BaseController {

    @Autowired
    private RsKnowledgeService knowledgeService;

    /**
     * 查询知识点列表
     */
    @RequestMapping("/list")
    public AjaxResult list(RsKnowledge knowledge) throws Exception {
        return AjaxResult.success(knowledgeService.getAll(knowledge));
    }

    /**
     * 增加知识点
     */
    @PostMapping("/save")
    public AjaxResult save(RsKnowledge knowledge) throws Exception {
        int n = knowledgeService.save(knowledge);
        return AjaxResult.success(n);
    }

    /**
     * 查询知识点明细
     */
    @GetMapping("/single/{kId}")
    public AjaxResult single(@PathVariable Integer kId) throws Exception {
        return AjaxResult.success(knowledgeService.getById(kId));
    }

    /**
     * 修改知识点
     */
    @PostMapping("/update")
    public AjaxResult update(RsKnowledge knowledge) throws Exception {
        int n = knowledgeService.update(knowledge);
        return AjaxResult.success(n);
    }

    /**
     * 删除知识点
     */
    @GetMapping("/del/{kId}")
    public AjaxResult del(@PathVariable Integer kId) throws Exception {
        int n = knowledgeService.delete(kId);
        return AjaxResult.success(n);
    }
}
