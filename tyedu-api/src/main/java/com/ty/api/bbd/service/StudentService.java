package com.ty.api.bbd.service;

import com.ty.api.model.bbd.Student;

/**
 * 学生业务逻辑接口
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
public interface StudentService {

    /**
     * 查询单条学生数据
     *
     * @return Student
     * @throws Exception
     */
    Student getOne() throws Exception;

    /**
     * 保存或更新学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     * @throws Exception
     */
    int saveOrUpdate(Student student) throws Exception;
}
