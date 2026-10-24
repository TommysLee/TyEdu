package com.ty.web.content.controller;

import com.ty.api.content.service.ContentEntryService;
import com.ty.api.model.content.ContentEntry;
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
 * 知识库条目Controller
 *
 * @Author TyCode
 * @Date 2026/10/24
 */
@RestController
@RequestMapping("/content/entry")
public class ContentEntryController extends BaseController {

    @Autowired
    private ContentEntryService contentEntryService;

    /**
     * 分页查询知识库条目列表
     */
    @RequestMapping("/list")
    public AjaxResult list(ContentEntry contentEntry, @RequestParam(defaultValue = Ty.DEFAULT_PAGE) String page, @RequestParam(defaultValue = Ty.DEFAULT_PAGESIZE) String pageSize) throws Exception {
        return AjaxResult.success(contentEntryService.query(contentEntry, page, pageSize));
    }

    /**
     * 增加知识库条目
     */
    @PostMapping("/save")
    public AjaxResult save(ContentEntry contentEntry) throws Exception {
        int n = contentEntryService.save(contentEntry);
        return AjaxResult.success(n);
    }

    /**
     * 查询知识库条目明细
     */
    @GetMapping("/single/{eid}")
    public AjaxResult single(@PathVariable Integer eid) throws Exception {
        return AjaxResult.success(contentEntryService.getById(eid));
    }

    /**
     * 修改知识库条目
     */
    @PostMapping("/update")
    public AjaxResult update(ContentEntry contentEntry) throws Exception {
        int n = contentEntryService.update(contentEntry);
        return AjaxResult.success(n);
    }

    /**
     * 删除知识库条目
     */
    @GetMapping("/del/{eid}")
    public AjaxResult del(@PathVariable Integer eid) throws Exception {
        int n = contentEntryService.delete(eid);
        return AjaxResult.success(n);
    }
}
