package com.tdy.sharding.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tdy.sharding.po.Order;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    void insertBatchList(List<Order> orderList);
}
