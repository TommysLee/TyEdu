package com.ty.web.rs.controller;

import com.google.common.collect.Lists;
import com.ty.api.model.rs.RsQueRefChapter;
import com.ty.api.rs.service.RsQueRefChapterService;
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
 * 题目章节标签Controller
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@RestController
@RequestMapping("/rs/que-chapter")
public class RsQueRefChapterController extends BaseController {

    @Autowired
    private RsQueRefChapterService queRefChapterService;

    /**
     * 查询题目的章节标签列表
     */
    @RequestMapping("/list/{qid}")
    public AjaxResult list(@PathVariable Integer qid) throws Exception {
        return AjaxResult.success(queRefChapterService.getAll(qid));
    }

    /**
     * 增加题目的章节标签
     */
    @PostMapping("/save/{qid}")
    public AjaxResult save(@PathVariable Integer qid, @RequestParam LinkedHashSet<Integer> ids) throws Exception {
        List<RsQueRefChapter> list = Lists.newArrayList();
        for (Integer chptId : ids) {
            list.add(new RsQueRefChapter().setQid(qid).setChptId(chptId));
        }

        int n = 0;
        if (list.isEmpty()) {
            n = queRefChapterService.delete(qid);
        } else {
            n = queRefChapterService.saveBatch(list);
        }
        return AjaxResult.success(n);
    }
}
