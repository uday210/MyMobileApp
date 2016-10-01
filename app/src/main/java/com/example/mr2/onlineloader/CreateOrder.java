package com.example.mr2.onlineloader;

/**
 * Created by MR2 on 6/7/2016.
 */
public class CreateOrder {

    public final  float price;
    public final int qty;
    public final String product_id;

    public CreateOrder(float price,int qty,String product_id){
        this.price = price;
        this.qty = qty;
        this.product_id = product_id;

    }
}
