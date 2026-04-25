package com.ty.web.rs.controller;

import com.ty.api.model.rs.RsQueBank;
import com.ty.api.rs.service.RsQueBankService;
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
 * 题库Controller
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@RestController
@RequestMapping("/rs/que")
public class RsQueBankController extends BaseController {

    @Autowired
    private RsQueBankService queBankService;

    /**
     * 分页查询题库列表
     */
    @RequestMapping("/list")
    public AjaxResult list(RsQueBank queBank, @RequestParam(defaultValue = Ty.DEFAULT_PAGE) String page, @RequestParam(defaultValue = Ty.DEFAULT_PAGESIZE) String pageSize) throws Exception {
        return AjaxResult.success(queBankService.query(queBank, page, pageSize));
    }

    /**
     * 增加题库
     */
    @PostMapping("/save")
    public AjaxResult save(RsQueBank queBank) throws Exception {
        int n = queBankService.save(queBank);
        return AjaxResult.success(n);
    }

    /**
     * 查询题库明细
     */
    @GetMapping("/single/{qId}")
    public AjaxResult single(@PathVariable Integer qId) throws Exception {
        return AjaxResult.success(queBankService.getById(qId));
    }

    /**
     * 修改题库
     */
    @PostMapping("/update")
    public AjaxResult update(RsQueBank queBank) throws Exception {
        int n = queBankService.update(queBank);
        return AjaxResult.success(n);
    }

    /**
     * 删除题库
     */
    @GetMapping("/del/{qId}")
    public AjaxResult del(@PathVariable Integer qId) throws Exception {
        int n = queBankService.delete(qId);
        return AjaxResult.success(n);
    }
}
