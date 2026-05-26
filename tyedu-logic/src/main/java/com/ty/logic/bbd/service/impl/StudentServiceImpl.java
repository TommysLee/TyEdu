package com.ty.logic.bbd.service.impl;

import com.github.pagehelper.Page;
import com.ty.api.bbd.service.StudentService;
import com.ty.api.model.bbd.Student;
import com.ty.cm.utils.DateUtils;
import com.ty.logic.bbd.dao.StudentDao;
import lombok.extern.slf4j.Slf4j;
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
     * 保存或更新学生数据
     *
     * @param student 学生
     * @return int 返回受影响的行数
     * @throws Exception
     */
    @Override
    public int saveOrUpdate(Student student) throws Exception {
        int n = 0;
        if (null != student) {
            student.precheck().setCreateTime(DateUtils.nowText());
            student.setUpdateTime(student.getCreateTime());

            // 若系统已有学生数据，则获取学生ID，并更新数据
            Student existStu = this.getOne();
            if (null != existStu) {
                student.setSid(existStu.getSid());
                n = studentDao.updateStudent(student);
            } else {
                n = studentDao.saveStudent(student);
            }
        }
        return n;
    }
}
