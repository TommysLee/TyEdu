package com.ty.logic.bbd.service.impl;

import com.github.pagehelper.Page;
import com.ty.api.bbd.service.StudentService;
import com.ty.api.model.bbd.Student;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.bbd.dao.StudentDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生业务逻辑实现
 *
 * @Author TyCode
 * @Date 2026/05/26
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    /**
     * 查询单条学生数据
     *
     * @return Student
     * @throws Exception
     */
    @Override
    public Student getOne() throws Exception {
        Page<Student> list = studentDao.findStudent(new RowBounds(1, 1), null);
        return !list.isEmpty() ? list.get(0) : null;
    }

    /**
     * 保存学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Override
    public int save(Student student) throws Exception {
        int n = 0;
        if (null != student) {
            student.setStage(StringUtils.upperCase(student.getStage()));
            student.setGrade(StringUtils.upperCase(student.getGrade()));
            student.setCreateTime(DateUtils.nowText());
            n = studentDao.saveStudent(student);
        }
        return n;
    }

    /**
     * 更新学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Override
    public int update(Student student) throws Exception {
        int n = 0;
        if (null != student) {
            student.setStage(StringUtils.upperCase(student.getStage()));
            student.setGrade(StringUtils.upperCase(student.getGrade()));
            student.setUpdateTime(DateUtils.nowText());
            n = studentDao.updateStudent(student);
        }
        return n;
    }
}
