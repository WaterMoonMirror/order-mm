package com.example.order.dtd;

import lombok.Data;

@Data
public class CartDTD {

    /*商品id*/
    private String productId;

    /*商品数量*/
    private Integer productQuantity;

    public CartDTD(String productId, Integer productQuantity) {

        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDTD() {
    }
}
