package com.ty.web.bbd.controller;

import com.ty.api.bbd.service.StudentService;
import com.ty.api.model.bbd.Student;
import com.ty.cm.model.AjaxResult;
import com.ty.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生Controller
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@RestController
@RequestMapping("/bbd/stu")
public class StudentController extends BaseController {

    @Autowired
    private StudentService studentService;

    /**
     * 查询学生明细
     */
    @GetMapping("/info")
    public AjaxResult info() throws Exception {
        return AjaxResult.success(studentService.getOne());
    }

    /**
     * 增加学生
     */
    @PostMapping("/save")
    public AjaxResult save(Student student) throws Exception {
        int n = studentService.saveOrUpdate(student);
        return AjaxResult.success(n);
    }
}
