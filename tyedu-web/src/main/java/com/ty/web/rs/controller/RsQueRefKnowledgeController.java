package com.ty.web.rs.controller;

import com.google.common.collect.Lists;
import com.ty.api.model.rs.RsQueRefKnowledge;
import com.ty.api.rs.service.RsQueRefKnowledgeService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 题目知识点标签Controller
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@RestController
@RequestMapping("/rs/que-knowledge")
public class RsQueRefKnowledgeController extends BaseController {

    @Autowired
    private RsQueRefKnowledgeService queRefKnowledgeService;

    /**
     * 查询题目的知识点标签列表
     */
    @RequestMapping("/list/{qid}")
    public AjaxResult list(@PathVariable Integer qid) throws Exception {
        return AjaxResult.success(queRefKnowledgeService.getAll(qid));
    }

    /**
     * 增加题目的知识点标签
     */
    @PostMapping("/save/{qid}")
    public AjaxResult save(@PathVariable Integer qid, @RequestParam LinkedHashSet<Integer> ids) throws Exception {
        List<RsQueRefKnowledge> list = Lists.newArrayList();
        for (Integer kid : ids) {
            list.add(new RsQueRefKnowledge().setQid(qid).setKid(kid));
        }

        int n = 0;
        if (list.isEmpty()) {
            n = queRefKnowledgeService.delete(qid);
        } else {
            n = queRefKnowledgeService.saveBatch(list);;
        }
        return AjaxResult.success(n);
    }
}
