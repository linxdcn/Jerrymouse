package cn.linxdcn.http;

/**
 * Created by linxiaodong on 5/5/17.
 */
public interface Handler {
    public void init(Context context);

    public void service(Context context);

    public void doGet(Context context);

    public void doPost(Context context);

    public void destory(Context context);
}
