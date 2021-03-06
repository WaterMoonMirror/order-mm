package com.example.orderserver.service.iml;

import com.example.orderserver.Repository.OrderDetailRepository;
import com.example.orderserver.Repository.OrderMasterRepository;
import com.example.orderserver.dataobject.OrderDetail;
import com.example.orderserver.dataobject.OrderMaster;
import com.example.orderserver.dtd.OrderDTO;
import com.example.orderserver.enums.OrderStatusEnum;
import com.example.orderserver.enums.PayStatusEnum;
import com.example.orderserver.service.OrderService;
import com.example.orderserver.utils.KeyUtil;
import com.example.product.client.ProductClient;
import com.example.product.common.DecreaseStockInput;
import com.example.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        List<ProductInfoOutput> productList = productClient.listForOrder(productIdList);


//          计算总价
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);

        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()){

            for (ProductInfoOutput productInfo: productList){
                if (productInfo.getProductId().equals(orderDetail.getProductId())){
                    orderAmount=productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetail.setOrderId(orderId);
                    orderDetail.setCreateTime(new Date());
                    orderDetail.setUpdateTime(new Date());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }


//         * 4. 扣库存（调用商品服务）
        List<DecreaseStockInput> cartDTDList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
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
