package com.ty.web.sch.controller;

import com.google.common.collect.Lists;
import com.ty.api.model.sch.WrongQueBank;
import com.ty.api.model.sch.WrongQueBankRefKnowledge;
import com.ty.api.sch.service.WrongQueBankService;
import com.ty.cm.constant.Ty;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * 错题集题目Controller
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@RestController
@RequestMapping("/sch/wrong/que")
public class WrongQueBankController extends BaseController {

    @Autowired
    private WrongQueBankService wrongQueBankService;

    /**
     * 分页查询错题集题目列表
     */
    @RequestMapping("/{stage}/{subject}/list")
    public AjaxResult list(WrongQueBank wrongQueBank, @RequestParam(required = false) LinkedHashSet<Integer> ktagsId, @RequestParam(defaultValue = Ty.DEFAULT_PAGE) String page, @RequestParam(defaultValue = Ty.DEFAULT_PAGESIZE) String pageSize) throws Exception {
        if (CollectionUtils.isNotEmpty(ktagsId)) {
            List<WrongQueBankRefKnowledge> ktags = Lists.newArrayListWithCapacity(ktagsId.size());
            ktagsId.forEach(kid -> ktags.add(new WrongQueBankRefKnowledge().setKid(kid)));
            wrongQueBank.setKtags(ktags);
        }
        return AjaxResult.success(wrongQueBankService.query(wrongQueBank, page, pageSize));
    }

    /**
     * 删除错题集题目
     */
    @GetMapping("/del/{qid}")
    public AjaxResult del(@PathVariable Integer qid) throws Exception {
        int n = wrongQueBankService.delete(qid);
        return AjaxResult.success(n);
    }
}
