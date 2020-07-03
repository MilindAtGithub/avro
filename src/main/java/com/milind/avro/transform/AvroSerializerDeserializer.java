package com.milind.avro.transform;

import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerializerDeserializer {


    /**
     * This will serialize in JSON Format
     * @param request
     * @return
     */
    public byte[] serialize(SpecificRecordBase request) throws
            IOException{

        DatumWriter<SpecificRecordBase> writer = new SpecificDatumWriter(
                request.getClass());
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        jsonEncoder = EncoderFactory.get().jsonEncoder(
                request.getSchema(), stream);
        writer.write(request, jsonEncoder);
        jsonEncoder.flush();
        data = stream.toByteArray();
        return data;
    }

    /**
     * This will deserialize in json format
     * @param data
     * @param t
     * @param schema
     * @param <T>
     * @return
     */
    public <T> T deSerialize(byte[] data, Class<T> t, Schema schema) {
        DatumReader<SpecificRecordBase> reader
                = new SpecificDatumReader(t);
        Decoder decoder = null;
        try {
            decoder = DecoderFactory.get().jsonDecoder(
                    schema, new String(data));
            return  (T) reader.read(null, decoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * This will encode in binary format
     * @param request
     * @return
     * @throws IOException
     */
    public byte[] serializeBinary(SpecificRecordBase request)
            throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DatumWriter<SpecificRecordBase> outputDatumWriter =
                new SpecificDatumWriter<>(request.getSchema());
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(baos,
                null);
        outputDatumWriter.write(request,encoder);
        encoder.flush();
        return baos.toByteArray();
    }

    /**
     * This will deserialize in binary
     * @param data
     * @param t
     * @param <T>
     * @return
     */
    public <T> T  deSerializeBinary(byte[] data, Class<T> t)
            throws IOException {
        DatumReader<SpecificRecordBase> reader
                = new SpecificDatumReader(t);
        BinaryDecoder decoder = new DecoderFactory().binaryDecoder(data,
                null);
        return (T) reader.read(null,decoder);
    }
}
