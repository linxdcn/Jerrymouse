package cn.linxdcn.http;

/**
 * Created by linxiaodong on 5/5/17.
 */
public class AbstractHandler implements Handler{

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        this.service(context);
    }

    @Override
    public void service(Context context) {
        Method method = context.getRequest().getMethod();
        if(method == Method.GET) {
            this.doGet(context);
        } else if (method == Method.POST) {
            this.doPost(context);
        }
        // sendResponse(context);
    }

    @Override
    public void doGet(Context context) {

    }

    @Override
    public void doPost(Context context) {

    }

    @Override
    public void destory(Context context) {
        context = null;
    }
}
