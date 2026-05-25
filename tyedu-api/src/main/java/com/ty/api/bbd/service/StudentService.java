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
     * 保存学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     * @throws Exception
     */
    int save(Student student) throws Exception;

    /**
     * 更新学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     * @throws Exception
     */
    int update(Student student) throws Exception;
}
