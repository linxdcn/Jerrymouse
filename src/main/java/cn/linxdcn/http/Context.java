package cn.linxdcn.http;

import java.nio.channels.SelectionKey;

/**
 * Created by linxiaodong on 5/5/17.
 */
public abstract class Context {
    protected Request request;
    protected Response response;

    public abstract void setContext(String requestHeader);

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }
}
