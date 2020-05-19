package person.zsx.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.security.PublicKey;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/24 13:12
 * @Description: zuul网关过滤器，必须继承ZuulFilter
 */

@Component
public class LoggerFilter extends ZuulFilter {
    @Override
    /**
     * 过滤器类型：
     * pre-前置，在进入zuul后路由之前立刻执行
     * route--路由后进行过滤
     * error - 任意一个filter发生异常的时候执行或远程服务调用没有反馈的时候执行（超时），通常用于处理异常；
     * post - 在route或error执行后被调用，一般用于收集服务信息，统计服务性能指标等，也可以对response结果做特殊处理。
     */
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    /** true表示该过滤器生效
     *  false表示停用
     */

    public boolean shouldFilter() {
        return true;
    }

    /**run方法中是过滤器执行的具体逻辑
     *  返回值可以为任意
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("在zuul路由之前打印日志");
        "str".replace("\\$","dd");
        return null;
    }
}
