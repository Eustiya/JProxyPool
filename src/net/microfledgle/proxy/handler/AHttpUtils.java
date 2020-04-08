package net.microfledgle.proxy.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
/**
 * @author ：Arisa
 * @date ：Created in 2020/3/23 16:26
 * @description：
 * @version: $
 */
public class AHttpUtils{
    
    
    private static RequestConfig reqConfig = null;
    
    
    
 
    
    public static String doGet(String url,HttpHost host) {
                reqConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000) // 设置连接超时时间
                .setSocketTimeout(10000) // 设置读取超时时间
                .setExpectContinueEnabled(false).setProxy(host)
                .setCircularRedirectsAllowed(true)
                
                .build();
        DefaultHttpClient client = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
//            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c1");
            httpGet.setHeader("User-Agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:74.0) Gecko/20100101 Firefox/74.0");
            // 设置配置请求参数
            httpGet.setConfig(reqConfig);
//            client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
            
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity,"UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != client) {
                client.close();
            }
        }
        return result;
    }
    
    
}
