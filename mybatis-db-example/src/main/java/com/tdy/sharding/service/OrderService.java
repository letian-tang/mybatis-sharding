package com.tdy.sharding.service;


import com.tdy.sharding.po.Order;

import java.util.List;

public interface OrderService {

    void insert(Order order);

    void insertBatch(List<Order> orders);

    void insertBatchTemplate(List<Order> orders);

    List<Order> select();

    void insertBatchList(List<Order> orders);
}
