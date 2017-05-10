package cn.linxdcn.http;

import java.util.Map;
import java.util.Set;

/**
 * Created by linxiaodong on 5/5/17.
 */
public interface Request {

    public Map<String, Object> getAttributes();

    public Method getMethod();

    public String getUri();

    public String getProtocol();

    public Map<String, Object> getHeaders();

    public Set<String> getHeaderNames();

    public Object getHeader(String key);
}
