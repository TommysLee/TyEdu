package com.ty.logic.bbd.dao;

import com.github.pagehelper.Page;
import com.ty.api.model.bbd.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 学生数据访问层
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Mapper
public interface StudentDao {

    /**
     * 根据条件查询所有学生数据
     *
     * @param student 学生
     * @return List<Student>
     */
    List<Student> findStudent(Student student);

    /**
     * 根据条件分页查询学生数据
     *
     * @param rowBounds 分页参数
     * @param student 学生
     * @return Page<Student>
     */
    Page<Student> findStudent(RowBounds rowBounds, Student student);

    /**
     * 保存学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     */
    int saveStudent(Student student);

    /**
     * 更新学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     */
    int updateStudent(Student student);
}
