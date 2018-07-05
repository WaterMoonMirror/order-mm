package com.example.order.Repository;

import com.example.order.OrderApplicationTests;
import com.example.order.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class OrderDetailRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Test
    public void saveTest(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setOrderId("1");
        orderDetail.setDetailId("1234");
        orderDetail.setProductIcon("http://");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductId("157875196366160022");
        orderDetail.setProductPrice(new BigDecimal(12.00));
        orderDetail.setProductQuantity(2);
        orderDetail.setCreateTime(new Date());
        orderDetail.setUpdateTime(new Date());
        OrderDetail orderDetail1 = orderDetailRepository.save(orderDetail);
        Assert.assertTrue(orderDetail1!=null);


    }
}