package com.ty.web.sch.controller;

import com.ty.api.model.sch.ExamQueRefChapter;
import com.ty.api.sch.service.ExamQueRefChapterService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 考试题目章节标签Controller
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@RestController
@RequestMapping("/sch/que-chapter")
public class ExamQueRefChapterController extends BaseController {

    @Autowired
    private ExamQueRefChapterService examQueRefChapterService;

    /**
     * 查询考试题目章节标签列表
     */
    @RequestMapping("/list/{qid}")
    public AjaxResult list(@PathVariable Integer qid) throws Exception {
        return AjaxResult.success(examQueRefChapterService.getAll(qid));
    }

    /**
     * 增加考试题目章节标签
     */
    @PostMapping("/save/{qid}")
    public AjaxResult save(@PathVariable Integer qid, @RequestBody List<ExamQueRefChapter> list) throws Exception {
        int n = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(c -> {
                c.setQid(qid);
            });
            n = examQueRefChapterService.saveBatch(list);
        } else {
            examQueRefChapterService.delete(qid);
        }
        return AjaxResult.success(n);
    }
}
