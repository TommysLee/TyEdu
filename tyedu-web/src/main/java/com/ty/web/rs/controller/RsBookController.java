package com.ty.web.rs.controller;

import com.ty.api.model.rs.RsBook;
import com.ty.api.rs.service.RsBookService;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教材Controller
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@RestController
@RequestMapping("/rs/book")
public class RsBookController extends BaseController {

    @Autowired
    private RsBookService bookService;

    /**
     * 查询教材列表
     */
    @RequestMapping("/list")
    public AjaxResult list(RsBook book) throws Exception {
        return AjaxResult.success(bookService.getAll(book));
    }

    /**
     * 增加教材
     */
    @PostMapping("/save")
    public AjaxResult save(RsBook book) throws Exception {
        int n = bookService.save(book);
        return AjaxResult.success(n);
    }

    /**
     * 查询教材明细
     */
    @GetMapping("/single/{bId}")
    public AjaxResult single(@PathVariable Integer bId) throws Exception {
        return AjaxResult.success(bookService.getById(bId));
    }

    /**
     * 修改教材
     */
    @PostMapping("/update")
    public AjaxResult update(RsBook book) throws Exception {
        int n = bookService.update(book);
        return AjaxResult.success(n);
    }

    /**
     * 删除教材
     */
    @GetMapping("/del/{bId}")
    public AjaxResult del(@PathVariable Integer bId) throws Exception {
        int n = bookService.delete(bId);
        return AjaxResult.success(n);
    }
}
