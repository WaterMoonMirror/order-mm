package com.example.order.service.iml;

import com.example.order.Repository.OrderDetailRepository;
import com.example.order.Repository.OrderMasterRepository;
import com.example.order.client.ProductClient;
import com.example.order.dataobject.OrderDetail;
import com.example.order.dataobject.OrderMaster;
import com.example.order.dataobject.ProductInfo;
import com.example.order.dtd.CartDTD;
import com.example.order.dtd.OrderDTO;
import com.example.order.enums.OrderStatusEnum;
import com.example.order.enums.PayStatusEnum;
import com.example.order.service.OrderService;
import com.example.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: tkn
 * @Date: 2018/7/5 22:53
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private ProductClient productClient;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
       String orderId=KeyUtil.genUniqueKey();
//          查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfo> productList = productClient.getProductList(productIdList);


//          计算总价
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);

        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()){

            for (ProductInfo productInfo: productList){
                if (productInfo.getProductId().equals(orderDetail.getProductId())){
                    orderAmount=productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetail.setOrderId(orderId);
                    orderDetailRepository.save(orderDetail);
                }
            }
        }


//         * 4. 扣库存（调用商品服务）
        List<CartDTD> cartDTDList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTD(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTDList);

//         * 5. 订单入库

        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterRepository.save(orderMaster);
        return orderDTO;

    }
}
