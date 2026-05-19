package com.ty.web.thymeleaf;

import com.ty.web.spring.SpringContextHolder;
import com.ty.logic.spring.config.properties.TyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Thymeleaf自定义表达式：获取站点基本信息
 *
 * @Author Tommy
 * @Date 2025/9/5
 */
@Component
@Slf4j
public class SiteExpressionObjectFactory implements IExpressionObjectFactory {

    public static final String SITE_EXPRESSION_OBJECT_NAME = "site";

    protected static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(
            new LinkedHashSet<>(List.of(SITE_EXPRESSION_OBJECT_NAME)
    ));

    /** 默认站点名称的Message Key **/
    public static final String TITLE_KEY = "websiteName";

    /** 默认系统名称的Message Key **/
    public static final String NAME_KEY = "productName";

    /** 默认系统LOGO **/
    public static final String LOGO = "logo-ty";

    @Autowired
    private TyProperties tyProperties;

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if (SITE_EXPRESSION_OBJECT_NAME.equals(expressionObjectName)) {
            return this;
        }
        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return false;
    }

    /**
     * 获取站点名称
     *
     * @return String
     */
    public String title() {
        return SpringContextHolder.getMessage(TITLE_KEY);
    }

    /**
     * 获取系统名称
     *
     * @return String
     */
    public String name() {
        return SpringContextHolder.getMessage(NAME_KEY);
    }

    /**
     * 获取站点LOGO
     *
     * @return String
     */
    public String logo() {
        return LOGO;
    }
}
