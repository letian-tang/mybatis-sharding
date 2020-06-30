package com.tdy.sharding;

import com.tdy.sharding.ShardingApplication;
import com.tdy.sharding.po.Order;
import com.tdy.sharding.service.OrderService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ShardingApplication.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    /**
     * 单表
     */
    @Test
    public void saveOrder() {
        orderService.insert(Order.builder().name("11111").build());
    }

    /**
     * 事务方式 @Transactional
     */
    @Test
    public void saveOrder2() {
        orderService.insertBatch(Lists.newArrayList(Order.builder().name("22222").build(),
                Order.builder().name("33333").build()));
    }

    /**
     * 事务 transactionTemplate方式
     */
    @Test
    public void saveOrder3() {
        orderService.insertBatchTemplate(Lists.newArrayList(Order.builder().name("44444").build(),
                Order.builder().name("55555").build()));
    }

    /**
     * 查询
     */
    @Test
    public void selectOrder() {
        orderService.select();
    }
}
