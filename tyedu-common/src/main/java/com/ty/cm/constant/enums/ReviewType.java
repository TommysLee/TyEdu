package com.ty.cm.constant.enums;

/**
 * 批阅状态
 *
 * @Author Tommy
 * @Date 2026/5/26
 */
public enum ReviewType {

    UNREVIEWED(0),
    REVIEWED(1);

    private final int val;

    ReviewType(int val) {
        this.val = val;
    }

    public int val() {
        return this.val;
    }

    public boolean eq(Integer v) {
        return null != v && v.equals(this.val);
    }
}
