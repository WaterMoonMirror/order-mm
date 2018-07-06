package com.example.orderserver.Controller;

import com.example.orderserver.client.ProductClient;
import com.example.orderserver.dataobject.ProductInfo;
import com.example.orderserver.dtd.CartDTD;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {
    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        String msg = productClient.msg();
        log.info("response={}", msg);
        return msg;
    }

    @GetMapping("/getProduct")
    public String getProduct() {
        List<ProductInfo> productList = productClient.getProductList(Arrays.asList("164103465734242707"));
        log.info("response={}", productList);
        return "ok";
    }

    @GetMapping("/decreaseStock")
    public String decreaseStock() {
        String msg = productClient.decreaseStock(Arrays.asList(new CartDTD("164103465734242707", 3)));
        return msg;

    }

}
