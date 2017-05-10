package cn.linxdcn.http;

import java.nio.channels.SelectionKey;
import java.util.List;
import java.util.Map;

/**
 * Created by linxiaodong on 5/5/17.
 */
public interface Response {

    public ContentType getContentType();

    public Status getStatus();

    public List<String> getHeaders();

    public byte[] getBody();

    public void setContentType(ContentType contentType);

    public void setStatus(Status status);

    public void setBody(byte[] body);

    public void setHeaders(List<String> headers);

    public String toString();
}
