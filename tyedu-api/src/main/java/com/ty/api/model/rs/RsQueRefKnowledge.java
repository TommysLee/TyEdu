package com.ty.api.model.rs;

import com.ty.api.model.BaseBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 题目知识点标签实体类
 *
 * @Author TyCode
 * @Date 2026/05/17
 */
@Data
@Accessors(chain = true)
public class RsQueRefKnowledge extends BaseBO {

    @Serial
    private static final long serialVersionUID = 568749652506800128L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 知识点ID (主键) **/
    private Integer kid;
}
