package com.ty.web.rs.controller;

import com.ty.api.model.rs.RsBook;
import com.ty.api.model.rs.RsBookChapter;
import com.ty.api.rs.service.RsBookChapterService;
import com.ty.api.rs.service.RsBookService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 教材章节Controller
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@RestController
@RequestMapping("/rs/book-chpt")
public class RsBookChapterController extends BaseController {

    @Autowired
    private RsBookChapterService bookChapterService;

    @Autowired
    private RsBookService bookService;

    /**
     * 教材章节View视图
     */
    @GetMapping("/{bid}/view")
    public ModelAndView view(@PathVariable Integer bid) throws Exception {
        RsBook book = bookService.getById(bid);
        ModelAndView mview = new ModelAndView("adm/rs/book-chpt");
        mview.addObject("book", book);
        return mview;
    }

    /**
     * 查询教材章节列表
     */
    @RequestMapping("/{bid}/list")
    public AjaxResult list(RsBookChapter bookChapter) throws Exception {
        return AjaxResult.success(bookChapterService.getAll(bookChapter));
    }

    /**
     * 增加教材章节
     */
    @PostMapping("/{bid}/save")
    public AjaxResult save(RsBookChapter bookChapter) throws Exception {
        int n = bookChapterService.save(bookChapter);
        return AjaxResult.success(n);
    }

    /**
     * 查询教材章节明细
     */
    @GetMapping("/{bid}/single/{chptId}")
    public AjaxResult single(@PathVariable Integer chptId) throws Exception {
        return AjaxResult.success(bookChapterService.getById(chptId));
    }

    /**
     * 修改教材章节
     */
    @PostMapping("/{bid}/update")
    public AjaxResult update(RsBookChapter bookChapter) throws Exception {
        int n = bookChapterService.update(bookChapter);
        return AjaxResult.success(n);
    }

    /**
     * 删除教材章节
     */
    @GetMapping("/{bid}/del/{chptId}")
    public AjaxResult del(@PathVariable Integer chptId) throws Exception {
        int n = bookChapterService.delete(chptId);
        return AjaxResult.success(n);
    }
}
