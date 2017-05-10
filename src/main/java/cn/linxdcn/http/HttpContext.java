package cn.linxdcn.http;

import java.nio.channels.SelectionKey;

/**
 * Created by linxiaodong on 5/5/17.
 */
public class HttpContext extends Context {
    @Override
    public void setContext(String requestHeader) {
        this.request = new HttpRequest(requestHeader);
        this.response = new HttpResponse();
    }
}
