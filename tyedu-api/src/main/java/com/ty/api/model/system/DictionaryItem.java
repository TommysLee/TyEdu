package com.ty.api.model.system;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典项实体类
 *
 * @Author TyCode
 * @Date 2026/4/21
 */
@Data
public class DictionaryItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -4478429848749571523L;

    /** Item值 **/
    private Object value;

    /** Item文本 **/
    private String title;
}
