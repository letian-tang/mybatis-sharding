package com.tdy.sharding.common;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 */
public abstract class SellerIdHolder {

    private static final ThreadLocal<Long> threadLocalSellerId = new ThreadLocal<>();

    public static void setSellerId(Long sellerId) {
        threadLocalSellerId.set(sellerId);
    }

    public static Long getSellerId() {
        return threadLocalSellerId.get();
    }

    public static void clear() {
        threadLocalSellerId.remove();
    }

}
