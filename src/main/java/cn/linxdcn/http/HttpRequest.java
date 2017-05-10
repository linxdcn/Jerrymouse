package cn.linxdcn.http;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by linxiaodong on 4/29/17.
 */
public class HttpRequest implements Request{

    private static Logger log = Logger.getLogger(HttpRequest.class);

    private Map<String, Object> attributes = new HashMap<String, Object>();

    private Map<String, Object> headers = new HashMap<String, Object>();

    private Method method;

    private String uri;

    private String protocol;

    public HttpRequest(String httpHeader) {
        init(httpHeader);
    }

    private void init(String httpHeader) {
        String[] headers = httpHeader.split("\r\n");

        if (log.isDebugEnabled()) {
            for (String s : headers)
                log.debug(s);
        }

        initMethod(headers[0]);

        initURI(headers[0]);

        initProtocol(headers[0]);

        initRequestHeaders(headers);
    }

    private void initMethod(String str) {
        method = Method.valueOf(str.substring(0, str.indexOf(" ")));
    }

    private void initURI(String str) {
        uri = str.substring(str.indexOf(" ") + 1, str.indexOf(" ", str.indexOf(" ") + 1));

        if(method == Method.GET) {
            if(uri.contains("?")) {
                String attr = uri.substring(uri.indexOf("?") + 1, uri.length());
                uri = uri.substring(0, uri.indexOf("?"));
                initAttribute(attr);
            }
        }
    }

    private void initAttribute(String attr) {
        String[] attrs = attr.split("&");
        for (String string : attrs) {
            String key = string.substring(0, string.indexOf("="));
            String value = string.substring(string.indexOf("=") + 1);
            attributes.put(key, value);
        }
    }

    private void initProtocol(String str) {
        protocol = str.substring(str.lastIndexOf(" ") + 1, str.length());
    }

    private void initRequestHeaders(String[] strs) {
        for(int i = 1; i < strs.length; i++) {
            String key = strs[i].substring(0, strs[i].indexOf(":"));
            String value = strs[i].substring(strs[i].indexOf(":") + 1);
            headers.put(key, value);
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    @Override
    public Object getHeader(String key) {
        return headers.get(key);
    }
}
