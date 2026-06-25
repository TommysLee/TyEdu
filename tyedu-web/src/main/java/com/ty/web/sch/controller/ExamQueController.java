package com.ty.web.sch.controller;

import com.ty.api.model.sch.ExamQue;
import com.ty.api.sch.service.ExamQueRefChapterService;
import com.ty.api.sch.service.ExamQueRefKnowledgeService;
import com.ty.api.sch.service.ExamQueService;
import com.ty.api.sch.service.ExamService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamQueRefKnowledgeService queRefKnowledgeService;

    @Autowired
    private ExamQueRefChapterService queRefChapterService;

    /**
     * 考试题目列表View视图
     */
    @GetMapping("/{examId}/view")
    public ModelAndView listView(@PathVariable Integer examId) throws Exception {
        return this.buildView(examId, "sch/exam-que");
    }

    /**
     * 新增考试题目View视图
     */
    @GetMapping("/{examId}/edit/view")
    public ModelAndView addView(@PathVariable Integer examId) throws Exception {
        return this.buildView(examId, "sch/exam-que-edit");
    }

    /**
     * 修改考试题目View视图
     */
    @GetMapping("/{examId}/edit/{qid}/view")
    public ModelAndView addView(@PathVariable Integer examId, @PathVariable Integer qid) throws Exception {
        ModelAndView mview = this.buildView(examId, "sch/exam-que-edit");
        mview.addObject("qid", qid);
        return mview;
    }

    /**
     * 考试题目打标View视图
     */
    @GetMapping("/{examId}/marked/{qid}/view")
    public ModelAndView markedView(@PathVariable Integer examId, @PathVariable Integer qid) throws Exception {
        ModelAndView mview = this.buildView(examId, "sch/exam-que-marked");
        mview.addObject("qid", qid);
        mview.addObject("ktags", queRefKnowledgeService.getSimpleAllForJson(qid));
        mview.addObject("ctags", queRefChapterService.getSimpleAllForJson(qid));
        return mview;
    }

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

    /**
     * 更新考试题目学生作答
     */
    @PostMapping("/{examId}/upsert/resp/{qid}")
    public AjaxResult upsertResponse(@PathVariable Integer qid, @RequestParam String response) throws Exception {
        int n = examQueService.update(new ExamQue().setQid(qid).setResponse(response));
        return AjaxResult.success(n);
    }

    /**
     * 更新考试题目得分
     */
    @RequestMapping("/{examId}/upsert/socre/{qid}/{score}")
    public AjaxResult upsertScore(@PathVariable Integer qid, @PathVariable Double score) throws Exception {
        int n = examQueService.update(new ExamQue().setQid(qid).setScore(score));
        return AjaxResult.success(n);
    }

    /**
     * 将题目加入到错题集
     */
    @GetMapping("/{examId}/copy/{qid}/{index}")
    public AjaxResult copyToWrong(@PathVariable Integer qid, @PathVariable Integer index) throws Exception {
        int n = examQueService.copyToWrong(qid, index);
        return AjaxResult.success(n);
    }

    /*
     * 构建 ModelAndView
     */
    ModelAndView buildView(Integer examId, String viewName) throws Exception {
        ModelAndView mview = new ModelAndView(viewName);
        mview.addObject("exam", examService.getById(examId));
        return mview;
    }
}
