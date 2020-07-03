package com.milind.avro.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {

    /**
     * Compressing the bytes
     * @param bytes
     * @return
     */
    public static byte[] compress(byte [] bytes) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)){
            gzipOutputStream.write(bytes);
        };
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * De-Compress the bytes
     * @param bytes
     * @return
     * @throws IOException
     */
    public static  byte[] decompress(byte [] bytes) throws IOException{
        byte[] readBuffer = new byte[1024];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try(GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)){
            int len;
            while((len = gzipInputStream.read(readBuffer)) != -1){
                byteArrayOutputStream.write(readBuffer,0,len);
            }
        };
        return  byteArrayOutputStream.toByteArray();
    }
}
