package com.ty.web.content.controller;

import com.ty.api.content.service.ContentCategoryService;
import com.ty.api.model.content.ContentCategory;
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
 * 知识库分类Controller
 *
 * @Author TyCode
 * @Date 2026/10/24
 */
@RestController
@RequestMapping("/content/ctg")
public class ContentCategoryController extends BaseController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询知识库分类列表
     */
    @RequestMapping("/list")
    public AjaxResult list(@RequestParam(defaultValue = "1") Integer enabled) throws Exception {
        return AjaxResult.success(contentCategoryService.getAll(new ContentCategory().setEnabled(enabled)));
    }

    /**
     * 增加知识库分类
     */
    @PostMapping("/save")
    public AjaxResult save(ContentCategory contentCategory) throws Exception {
        int n = contentCategoryService.save(contentCategory);
        return AjaxResult.success(n);
    }

    /**
     * 查询知识库分类明细
     */
    @GetMapping("/single/{ctgId}")
    public AjaxResult single(@PathVariable Integer ctgId) throws Exception {
        return AjaxResult.success(contentCategoryService.getById(ctgId));
    }

    /**
     * 修改知识库分类
     */
    @PostMapping("/update")
    public AjaxResult update(ContentCategory contentCategory) throws Exception {
        int n = contentCategoryService.update(contentCategory);
        return AjaxResult.success(n);
    }

    /**
     * 交换排序号
     */
    @GetMapping("/swap/seq/{ctgId1}/{ctgId2}")
    public AjaxResult swapSeq(@PathVariable Integer ctgId1, @PathVariable Integer ctgId2) throws Exception {
        return AjaxResult.success(contentCategoryService.swapSeq(ctgId1, ctgId2));
    }

    /**
     * 删除知识库分类
     */
    @GetMapping("/del/{ctgId}")
    public AjaxResult del(@PathVariable Integer ctgId) throws Exception {
        int n = contentCategoryService.delete(ctgId);
        return AjaxResult.success(n);
    }
}
