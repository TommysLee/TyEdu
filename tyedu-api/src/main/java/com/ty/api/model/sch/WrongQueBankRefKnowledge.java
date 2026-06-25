package com.ty.api.model.sch;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 错题集题目知识点标签实体类
 *
 * @Author TyCode
 * @Date 2026/06/25
 */
@Data
public class WrongQueBankRefKnowledge implements Serializable {

    @Serial
    private static final long serialVersionUID = 582728572759404544L;

    /** 题目ID (主键) **/
    private Integer qid;

    /** 知识点ID (主键) **/
    private Integer kid;

    /** 知识点名称 **/
    private String kname;
}
