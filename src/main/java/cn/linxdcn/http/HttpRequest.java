package cn.linxdcn.http;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxiaodong on 4/29/17.
 */
public class HttpRequest {

    private static Logger log = Logger.getLogger(HttpRequest.class);

    List<String> headers = new ArrayList<String>();

    Method method;

    String uri;

    String version;

    public HttpRequest(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String str = reader.readLine();
        parseRequestLine(str);

        while (!str.equals("")) {
            str = reader.readLine();
            parseRequestHeader(str);
        }
    }

    private void parseRequestLine(String str) {
        log.info(str);
        String[] split = str.split("\\s+");
        try {
            method = Method.valueOf(split[0]);
        } catch (Exception e) {
            method = Method.UNRECOGNIZED;
        }
        uri = split[1];
        version = split[2];
    }

    private void parseRequestHeader(String str) {
        log.info(str);
        headers.add(str);
    }
}
