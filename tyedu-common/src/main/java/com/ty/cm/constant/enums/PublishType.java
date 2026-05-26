package com.ty.cm.constant.enums;

/**
 * 发布状态
 *
 * @Author Tommy
 * @Date 2026/5/26
 */
public enum PublishType {

    UNPUBLISHED(0),
    PUBLISHED(1);

    private final int val;

    PublishType(int val) {
        this.val = val;
    }

    public int val() {
        return this.val;
    }

    public boolean eq(Integer v) {
        return null != v && v.equals(this.val);
    }
}
