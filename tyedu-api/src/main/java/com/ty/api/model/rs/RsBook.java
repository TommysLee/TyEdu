package com.ty.api.model.rs;

import com.ty.api.model.BaseBO;
import lombok.Data;

import java.io.Serial;

/**
 * 教材实体类
 *
 * @Author TyCode
 * @Date 2026/04/25
 */
@Data
public class RsBook extends BaseBO {

    @Serial
    private static final long serialVersionUID = 560763799897600000L;

    /** 教材ID (主键) **/
    private Integer bId;

    /** 教材名称 **/
    private String bName;

    /** 学段 **/
    private String stage;

    /** 学科 **/
    private String subject;
}
