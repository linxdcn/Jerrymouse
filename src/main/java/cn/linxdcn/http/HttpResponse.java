package cn.linxdcn.http;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linxiaodong on 4/29/17.
 */
public class HttpResponse implements Response{

    private static Logger log = Logger.getLogger(HttpResponse.class);

    private List<String> headers = new ArrayList<String>();

    private byte[] body;

    private ContentType contentType;

    private Status status;

    @Override
    public List<String> getHeaders() {
        return headers;
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String header : headers) {
            sb.append(header + "\r\n");
        }
        sb.append("\r\n");
        if (body != null) {
            sb.append(new String(body));
        }

        sb.append("\r\n");
        return sb.toString();
    }
}
