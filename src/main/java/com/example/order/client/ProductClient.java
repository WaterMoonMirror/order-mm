package com.example.order.client;

import com.example.order.dataobject.ProductInfo;
import org.hibernate.validator.constraints.EAN;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PRODUCT")
public interface ProductClient {

    @GetMapping("/msg")
     String msg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> getProductList(@RequestBody List<String> productIdList);


}
