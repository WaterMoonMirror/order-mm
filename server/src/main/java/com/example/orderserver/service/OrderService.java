package com.example.orderserver.service;

import com.example.orderserver.dtd.OrderDTO;

public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);
}
