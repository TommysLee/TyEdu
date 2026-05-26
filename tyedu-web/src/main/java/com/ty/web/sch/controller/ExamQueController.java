package com.ty.web.sch.controller;

import com.ty.api.model.sch.ExamQue;
import com.ty.api.sch.service.ExamQueService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试题目Controller
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@RestController
@RequestMapping("/sch/exam/que")
public class ExamQueController extends BaseController {

    @Autowired
    private ExamQueService examQueService;

    /**
     * 查询考试题目列表
     */
    @RequestMapping("/{examId}/list")
    public AjaxResult list(ExamQue examQue) throws Exception {
        return AjaxResult.success(examQueService.getAll(examQue));
    }

    /**
     * 增加考试题目
     */
    @PostMapping("/{examId}/save")
    public AjaxResult save(ExamQue examQue) throws Exception {
        int n = examQueService.save(examQue);
        return AjaxResult.success(n);
    }

    /**
     * 查询考试题目明细
     */
    @GetMapping("/{examId}/single/{qid}")
    public AjaxResult single(@PathVariable Integer examId, @PathVariable Integer qid) throws Exception {
        return AjaxResult.success(examQueService.getOne(new ExamQue().setExamId(examId).setQid(qid)));
    }

    /**
     * 修改考试题目
     */
    @PostMapping("/{examId}/update")
    public AjaxResult update(ExamQue examQue) throws Exception {
        int n = examQueService.update(examQue);
        return AjaxResult.success(n);
    }

    /**
     * 删除考试题目
     */
    @GetMapping("/{examId}/del/{qid}")
    public AjaxResult del(@PathVariable Integer qid) throws Exception {
        int n = examQueService.delete(qid);
        return AjaxResult.success(n);
    }
}
