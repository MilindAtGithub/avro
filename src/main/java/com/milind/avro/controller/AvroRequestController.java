package com.milind.avro.controller;

import com.milind.avro.service.ConfirmationService;
import com.milind.avro.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Request controller to handle the request
 */
@RestController
public class AvroRequestController {

    @Autowired
    ConfirmationService confirmationService;


    @RequestMapping(method = RequestMethod.POST, value = "/confirm",
            consumes = "application/octet-stream", produces = "application/octet-stream")
    public byte[] validate(HttpServletRequest request) throws Exception {
        return confirmationService.confirmOrder(readBytes(request), true);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmWithZip",
            consumes = "application/octet-stream", produces = "application/octet-stream")
    public byte[] validateWithZip(HttpServletRequest request) throws Exception {
        return ZipUtil.compress(
                confirmationService.confirmOrder(
                        ZipUtil.decompress(readBytes(request)), false
                )
        );
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmWithJSON",
            consumes = "application/octet-stream", produces = "application/octet-stream")
    public byte[] validateWithJSON(HttpServletRequest request) throws Exception {
        return confirmationService.confirmOrder(
                        readBytes(request), false);
    }

    public static final byte[] readBytes(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int contentLen = request.getContentLength();
        InputStream is = request.getInputStream();
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
                throw e;
            }
        }
        return new byte[]{};
    }
}
