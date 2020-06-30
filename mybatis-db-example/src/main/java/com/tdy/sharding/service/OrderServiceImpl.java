package com.tdy.sharding.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tdy.sharding.po.Order;
import com.tdy.sharding.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 单表支持
     * 
     * @param order
     */
    @Override
    public void insert(Order order) {
        orderMapper.insert(order);
    }

    /**
     * @Transactional 注解事务支持
     * @param orders
     */
    @Override
    @Transactional
    public void insertBatch(List<Order> orders) {
        for (Order order : orders) {
            orderMapper.insert(order);
        }
    }

    /**
     * 事务模板 transactionTemplate 支持
     * 
     * @param orders
     */
    @Override
    public void insertBatchTemplate(List<Order> orders) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (Order order : orders) {
                    orderMapper.insert(order);
                }
            }
        });
    }

    /**
     * 查询支持
     * 
     * @return
     */
    @Override
    public List<Order> select() {
        return orderMapper.selectList(new LambdaQueryWrapper<Order>());
    }

    @Override
    @Transactional
    public void insertBatchList(List<Order> orders) {
        orderMapper.insertBatchList(orders);
    }
}
