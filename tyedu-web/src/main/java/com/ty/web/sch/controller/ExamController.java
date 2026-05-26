package com.ty.web.sch.controller;

import com.ty.api.model.sch.Exam;
import com.ty.api.sch.service.ExamService;
import com.ty.cm.constant.Ty;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试Controller
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@RestController
@RequestMapping("/sch/exam")
public class ExamController extends BaseController {

    @Autowired
    private ExamService examService;

    /**
     * 分页查询考试列表
     */
    @RequestMapping("/{stage}/list")
    public AjaxResult list(Exam exam, @RequestParam(defaultValue = Ty.DEFAULT_PAGE) String page, @RequestParam(defaultValue = Ty.DEFAULT_PAGESIZE) String pageSize) throws Exception {
        return AjaxResult.success(examService.query(exam, page, pageSize));
    }

    /**
     * 增加考试
     */
    @PostMapping("/save")
    public AjaxResult save(Exam exam) throws Exception {
        int n = examService.save(exam.clean());
        return AjaxResult.success(n);
    }

    /**
     * 查询考试明细
     */
    @GetMapping("/single/{examId}")
    public AjaxResult single(@PathVariable Integer examId) throws Exception {
        return AjaxResult.success(examService.getById(examId));
    }

    /**
     * 修改考试
     */
    @PostMapping("/update")
    public AjaxResult update(Exam exam) throws Exception {
        int n = examService.update(exam.clean());
        return AjaxResult.success(n);
    }

    /**
     * 删除考试
     */
    @GetMapping("/del/{examId}")
    public AjaxResult del(@PathVariable Integer examId) throws Exception {
        int n = examService.delete(examId);
        return AjaxResult.success(n);
    }

    /**
     * 更新考试的发布状态
     */
    @GetMapping("/{examId}/ustatus/publish/{status}")
    public AjaxResult updatePublishStatus(@PathVariable Integer examId, @PathVariable Integer status) throws Exception {
        int n = examService.updatePublishStatus(examId, status);
        return AjaxResult.success(n);
    }

    /**
     * 更新考试的批阅状态
     */
    @GetMapping("/{examId}/ustatus/review/{status}")
    public AjaxResult updateReviewStatus(@PathVariable Integer examId, @PathVariable Integer status) throws Exception {
        int n = examService.updateReviewStatus(examId, status);
        return AjaxResult.success(n);
    }
}
