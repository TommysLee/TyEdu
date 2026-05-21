package com.ty.web.rs.controller;

import com.google.common.collect.Lists;
import com.ty.api.model.rs.RsQueBank;
import com.ty.api.model.rs.RsQueRefChapter;
import com.ty.api.model.rs.RsQueRefKnowledge;
import com.ty.api.rs.service.RsQueBankService;
import com.ty.api.rs.service.RsQueRefChapterService;
import com.ty.api.rs.service.RsQueRefKnowledgeService;
import com.ty.cm.constant.Ty;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashSet;
import java.util.List;

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

    @Autowired
    private RsQueRefKnowledgeService queRefKnowledgeService;

    @Autowired
    private RsQueRefChapterService queRefChapterService;

    private final String editView = "rs/que-edit";

    /**
     * 新增题目View视图
     */
    @GetMapping("/{stage}/{subject}/edit/view")
    public ModelAndView addView(@PathVariable String stage, RsQueBank queBank) throws Exception {
        return buildView(stage, queBank, editView);
    }

    /**
     * 修改题目View视图
     */
    @GetMapping("/{stage}/{subject}/edit/{qid}/view")
    public ModelAndView editView(@PathVariable String stage, RsQueBank queBank) throws Exception {
        return buildView(stage, queBank, editView);
    }

    /**
     * 题目打标View视图
     */
    @GetMapping("/{stage}/{subject}/marked/{qid}/view")
    public ModelAndView markedView(@PathVariable String stage, RsQueBank queBank) throws Exception {
        ModelAndView mview = buildView(stage, queBank, "rs/que-marked");
        mview.addObject("ktags", queRefKnowledgeService.getSimpleAllForJson(queBank.getQid()));
        mview.addObject("ctags", queRefChapterService.getSimpleAllForJson(queBank.getQid()));
        return mview;
    }

    /**
     * 分页查询题库列表
     */
    @RequestMapping("/{stage}/{subject}/list")
    public AjaxResult list(RsQueBank queBank, @RequestParam(required = false) LinkedHashSet<Integer> ctagsId, @RequestParam(required = false) LinkedHashSet<Integer> ktagsId, @RequestParam(defaultValue = Ty.DEFAULT_PAGE) String page, @RequestParam(defaultValue = Ty.DEFAULT_PAGESIZE) String pageSize) throws Exception {
        if (CollectionUtils.isNotEmpty(ctagsId)) {
            List<RsQueRefChapter> ctags = Lists.newArrayListWithCapacity(ctagsId.size());
            ctagsId.forEach(cid -> ctags.add(new RsQueRefChapter().setChptId(cid)));
            queBank.setCtags(ctags);
        }

        if (CollectionUtils.isNotEmpty(ktagsId)) {
            List<RsQueRefKnowledge> ktags = Lists.newArrayListWithCapacity(ktagsId.size());
            ktagsId.forEach(kid -> ktags.add(new RsQueRefKnowledge().setKid(kid)));
            queBank.setKtags(ktags);
        }
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
    @GetMapping("/single/{qid}")
    public AjaxResult single(@PathVariable Integer qid) throws Exception {
        return AjaxResult.success(queBankService.getById(qid));
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
    @GetMapping("/del/{qid}")
    public AjaxResult del(@PathVariable Integer qid) throws Exception {
        int n = queBankService.delete(qid);
        return AjaxResult.success(n);
    }

    /*
     * 构建 ModelAndView
     */
    ModelAndView buildView(String stage, RsQueBank queBank, String viewName) {
        stage = StringUtils.upperCase(stage);
        queBank.setSubject(StringUtils.upperCase(queBank.getSubject()));
        ModelAndView mview = new ModelAndView(viewName);
        mview.addObject("que", queBank);
        mview.addObject("stage", stage);
        return mview;
    }
}
