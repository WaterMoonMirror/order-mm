package com.example.order.Controller;

import com.example.order.client.ProductClient;
import com.example.order.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {
    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        String msg = productClient.msg();
        log.info("response={}",msg);
        return msg;
    }

    @GetMapping("/getProduct")
    public String getProduct(){
        List<ProductInfo> productList = productClient.getProductList(Arrays.asList("164103465734242707"));
        log.info("response={}",productList);
        return "ok";
    }

}
