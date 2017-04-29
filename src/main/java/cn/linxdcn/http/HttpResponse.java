package cn.linxdcn.http;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxiaodong on 4/29/17.
 */
public class HttpResponse {

    private static Logger log = Logger.getLogger(HttpResponse.class);

    public static final String VERSION = "HTTP/1.1";

    List<String> headers = new ArrayList<String>();

    byte[] body;

    public HttpResponse(HttpRequest req) throws IOException {

        switch (req.method) {
            case HEAD:
                fillHeaders(Status._200);
                break;
            case GET:
                try {
                    fillHeaders(Status._200);
                    File file = new File("." + req.uri);
                    if (file.isDirectory()) {
                        headers.add(ContentType.HTML.toString());
                        StringBuilder result = new StringBuilder("<html><head><title>Index of ");
                        result.append(req.uri);
                        result.append("</title></head><body><h1>Index of ");
                        result.append(req.uri);
                        result.append("</h1><hr><pre>");

                        File[] files = file.listFiles();
                        for (File subfile : files) {
                            result.append(" <a href=\"" + subfile.getPath() + "\">" + subfile.getPath() + "</a>\n");
                        }
                        result.append("<hr></pre></body></html>");
                        fillResponse(result.toString());
                    } else if (file.exists()) {
                        setContentType(req.uri, headers);
                        fillResponse(getBytes(file));
                    } else {
                        log.info("File not found:" + req.uri);
                        fillHeaders(Status._404);
                        fillResponse(Status._404.toString());
                    }
                } catch (Exception e) {
                    log.error("Response Error", e);
                    fillHeaders(Status._400);
                    fillResponse(Status._400.toString());
                }

                break;
            case UNRECOGNIZED:
                fillHeaders(Status._400);
                fillResponse(Status._400.toString());
                break;
            default:
                fillHeaders(Status._501);
                fillResponse(Status._501.toString());
        }

    }

    private byte[] getBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }
        in.close();
        return array;
    }

    private void fillHeaders(Status status) {
        headers.add(HttpResponse.VERSION + " " + status.toString());
        headers.add("Connection: close");
        headers.add("Server: jerrymouse");
    }

    private void fillResponse(String response) {
        body = response.getBytes();
    }

    private void fillResponse(byte[] response) {
        body = response;
    }

    public void write(OutputStream os) throws IOException {
        DataOutputStream output = new DataOutputStream(os);
        for (String header : headers) {
            output.writeBytes(header + "\r\n");
        }
        output.writeBytes("\r\n");
        if (body != null) {
            output.write(body);
        }
        output.writeBytes("\r\n");
        output.flush();
    }

    private void setContentType(String uri, List<String> list) {
        try {
            String ext = uri.substring(uri.lastIndexOf(".") + 1);
            list.add(ContentType.valueOf(ext.toUpperCase()).toString());
        } catch (Exception e) {
            log.error("ContentType not found: " + e, e);
        }
    }
}
