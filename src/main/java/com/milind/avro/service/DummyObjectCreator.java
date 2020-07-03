package com.milind.avro.service;


import com.milind.avro.order.request.LineItemRecord;
import com.milind.avro.order.request.Order;

import java.util.Arrays;

/**
 *  This will create the dummy Order
 */
public class DummyObjectCreator {

    /**
     * This will create the dummy order object
     * @return
     */
    public static Order createOrder(){
        //LineItemRecord

        LineItemRecord l1 = LineItemRecord.newBuilder()
                .setSku("sk10001")
                .setName("Shirt")
                .setDescription(null)
                .setCategory("apparel-clothing")
                .setOther("blue color shirt")
                .setUnitPrice(90.9f)
                .setSalePrice(99.99f)
                .setQuantity(2)
                .setTotalPrice(198.99f)
                .build();

        LineItemRecord l2 = LineItemRecord.newBuilder()
                .setSku("sk20001")
                .setName("mobile")
                .setDescription("iphonex")
                .setCategory("electronics-mobile")
                .setOther(null)
                .setUnitPrice(590.9f)
                .setSalePrice(599.99f)
                .setQuantity(1)
                .setTotalPrice(599.99f)
                .build();

        LineItemRecord l3 = LineItemRecord.newBuilder()
                .setSku("sk99001")
                .setName("salt")
                .setCategory("foodItem-daily")
                .setUnitPrice(1.9f)
                .setSalePrice(1.99f)
                .setQuantity(10)
                .setTotalPrice(10.99f)
                .setDescription(null)
                .setOther(null)
                .build();

        Order order = Order.newBuilder()
                .setCurrency("US")
                .setTotalAmount(800.10F)
                .setOrderId("O-2989")
                .setEmailAddress("mail@gmail.com")
                .setCartUrl(null)
                .setLineItems(Arrays.asList(l1,l2,l3))
                .build();
        return order;
    }
}
