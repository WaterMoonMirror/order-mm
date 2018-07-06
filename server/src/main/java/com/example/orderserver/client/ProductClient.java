package com.example.orderserver.client;

import com.example.orderserver.dataobject.ProductInfo;
import com.example.orderserver.dtd.CartDTD;
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

    @PostMapping("/product/decreaseStock")
    String decreaseStock(List<CartDTD> cartDTDList);


}
