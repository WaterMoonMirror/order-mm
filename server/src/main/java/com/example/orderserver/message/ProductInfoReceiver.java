package com.example.orderserver.message;

import com.example.orderserver.utils.JsonUtil;
import com.example.product.common.ProductInfoOutput;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Auther: tkn
 * @Date: 2018/8/4 10:24
 * @Description: 商品接受消息类
 */
@Component
@Slf4j
public class ProductInfoReceiver {


    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        // messge --->  productInfoOutput
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>)JsonUtil.fromJson(message,
                new TypeReference<List<ProductInfoOutput>>() {});
      log.info("从队列中【{}】接受到消息:{}","productInfo",productInfoOutputList);

    }
}
