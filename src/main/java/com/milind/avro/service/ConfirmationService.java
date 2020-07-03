package com.milind.avro.service;

import com.milind.avro.order.request.LineItemRecord;
import com.milind.avro.order.request.Order;
import com.milind.avro.order.response.ConfirmedLineItemRecord;
import com.milind.avro.order.response.OrderConfirmation;
import com.milind.avro.transform.AvroSerializerDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

@Component
public class ConfirmationService {


    /**
     * This confirm the order
     * @return
     */
    public byte[] confirmOrder(byte[] bytes, boolean binaryEncoding) throws IOException {

        AvroSerializerDeserializer avroSerializer =
                new AvroSerializerDeserializer();
        Order order = binaryEncoding? new AvroSerializerDeserializer()
                .deSerializeBinary(bytes, Order.class) :
                new AvroSerializerDeserializer()
                        .deSerialize(bytes, Order.class, Order.getClassSchema());
        Random random = new Random();
        int val = random.nextInt(3);
        OrderConfirmation.Builder orderConfirmBldr = OrderConfirmation.newBuilder()
                .setOrderId(order.getOrderId());
        if (val <= 1) {
            ConfirmedLineItemRecord l1 = ConfirmedLineItemRecord.newBuilder()
                    .setConfirmQuantity(2)
                    .setSku(order.getLineItems().get(0).getSku())
                    .build();
            ConfirmedLineItemRecord l2 = ConfirmedLineItemRecord.newBuilder()
                    .setConfirmQuantity(5)
                    .setSku(order.getLineItems().get(2).getSku())
                    .build();
            orderConfirmBldr.setConfirmedLineItems(Arrays.asList(l1,l2));
        } else {
            ConfirmedLineItemRecord l1 = ConfirmedLineItemRecord.newBuilder()
                    .setConfirmQuantity(1)
                    .setSku(order.getLineItems().get(1).getSku())
                    .build();
            orderConfirmBldr.setConfirmedLineItems(Arrays.asList(l1));
        }
        return binaryEncoding?avroSerializer.serializeBinary(orderConfirmBldr.build()):
                avroSerializer.serialize(orderConfirmBldr.build());
    }
}
