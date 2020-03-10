package person.zsx.transfering.serviceImpl;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/10 19:09
 * @Description:
 */
public class TxManagerTxUrlServiceImpl  {//implements TxManagerTxUrlService {
    @Value("${tm.manager.url}")
    private String url;

   // @Override
    public String getTxUrl() {
        return url;
    }
}