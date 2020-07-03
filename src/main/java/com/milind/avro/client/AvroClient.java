package com.milind.avro.client;


import com.milind.avro.order.response.OrderConfirmation;
import com.milind.avro.service.DummyObjectCreator;
import com.milind.avro.transform.AvroSerializerDeserializer;
import com.milind.avro.util.ZipUtil;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class AvroClient {

    /**
     * This will send the order for confirmation in binary format
     *
     * @throws IOException
     */
    public void sendForConfirmation() throws IOException {

        AvroSerializerDeserializer avroSerializerDeserializer =
                new AvroSerializerDeserializer();
        //Creating the MIME
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("application/octet-stream");
        OkHttpClient client = new OkHttpClient();
        long start = System.currentTimeMillis();
        RequestBody requestBody = RequestBody.create(avroSerializerDeserializer
                        .serializeBinary(DummyObjectCreator.createOrder()),
                MEDIA_TYPE_MARKDOWN);
        System.out.println("Time To Serialize in MilliSec: " +
                (System.currentTimeMillis() - start));
        Request request = new Request.Builder()
                .url("http://localhost:8080/confirm")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        start = System.currentTimeMillis();
        OrderConfirmation val = avroSerializerDeserializer.deSerializeBinary(response.body().bytes(),
                OrderConfirmation.class);
        System.out.println("Time To De-Serialize in MilliSec: " + (System.currentTimeMillis()
                - start));
        System.out.println(val);
    }

    /**
     * TThis will send for confirmation in JSON + ZIP Format
     * @throws IOException
     */
    public void sendForConfirmationWithZip() throws IOException {

        AvroSerializerDeserializer avroSerializerDeserializer =
                new AvroSerializerDeserializer();
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("application/octet-stream");
        OkHttpClient client = new OkHttpClient();
        long start = System.currentTimeMillis();
        //Zipping Request
        RequestBody requestBody = RequestBody.create(
                ZipUtil.compress(avroSerializerDeserializer
                        .serialize(DummyObjectCreator.createOrder())),
                MEDIA_TYPE_MARKDOWN);
        System.out.println("Time To Serialize in MilliSec: " + (System.currentTimeMillis() - start));
        Request request = new Request.Builder()
                .url("http://localhost:8080/confirmWithZip")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        start = System.currentTimeMillis();
        // UnZipping the response
        OrderConfirmation val = avroSerializerDeserializer.deSerialize(
                ZipUtil.decompress(response.body().bytes()),
                OrderConfirmation.class, OrderConfirmation.getClassSchema());
        System.out.println("Time To De-Serialize in MilliSec: " + (System.currentTimeMillis() - start));
        System.out.println(val);
    }

    /**
     * This will send for confirmation in JSON Format
     * @throws IOException
     */
    public void sendForConfirmationWithJSON() throws IOException {

        AvroSerializerDeserializer avroSerializerDeserializer =
                new AvroSerializerDeserializer();
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("application/octet-stream");
        OkHttpClient client = new OkHttpClient();
        long start = System.currentTimeMillis();
        //Zipping Request
        RequestBody requestBody = RequestBody.create(
                avroSerializerDeserializer
                        .serialize(DummyObjectCreator.createOrder()),
                MEDIA_TYPE_MARKDOWN);
        System.out.println("Time To Serialize in MilliSec: " + (System.currentTimeMillis() - start));
        Request request = new Request.Builder()
                .url("http://localhost:8080/confirmWithJSON")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        start = System.currentTimeMillis();
        // UnZipping the response
        OrderConfirmation val = avroSerializerDeserializer.deSerialize(
                response.body().bytes(),
                OrderConfirmation.class, OrderConfirmation.getClassSchema());
        System.out.println("Time To De-Serialize in MilliSec: " + (System.currentTimeMillis() - start));
        System.out.println(val);
    }
}
