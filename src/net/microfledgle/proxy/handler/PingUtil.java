/*
 * ©2021 August-soft Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.microfledgle.proxy.handler;

import net.microfledgle.proxy.pool.Proxy;
import net.microfledgle.proxy.pool.ProxyPriority;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 22:25
 * @description：
 * @version: $
 */
public class PingUtil {
    
    public static Proxy setDelay(Proxy proxy){
        String address = proxy.getAddress();
        int pingDelay = PingUtil.getPingDelay(address);
        proxy.setProxyPriority(getProxyPriority$(pingDelay));
        return proxy;
    }
    
    public static ProxyPriority getProxyPriority(Proxy proxy){
       return getProxyPriority$(PingUtil.getPingDelay(proxy.getAddress()));
    }
    
    private static ProxyPriority getProxyPriority$(int pingDelay){
        if(pingDelay==-1){
            return ProxyPriority.DEAD;
        }
        if(pingDelay<15){
            return ProxyPriority.NIUBILITY;
        }else if(pingDelay<25){
            return ProxyPriority.FASTEST;
        }else if(pingDelay<50){
            return ProxyPriority.FAST;
        }else if(pingDelay<100){
            return ProxyPriority.NORMAL;
        }else if(pingDelay<300){
            return ProxyPriority.SLOW;
        }else if(pingDelay<1200){
            return ProxyPriority.SLOWEST;
        }
        return ProxyPriority.FAKE_DEAD;
    }
    
    public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }
    
    public static int getPingDelay(String ipAddress){
        String line = null;
        int ping = -1;
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    pro.getInputStream()));
            while ((line = buf.readLine()) != null) {
                int ms = line.indexOf("ms");
                if (ms!=-1) {
                    String[] s = line.split(" ");
                    for (String s1 : s) {
                        if(s1.indexOf("ms")!=-1){
                            String s2 = s1.split("=")[1];
                            String ms1 = s2.replace("ms", "");
                            return Integer.parseInt(ms1);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return ping;
    }
    
    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        try {   // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }
    
    /**
     * 测试IP 端口 是否通
     * @param host
     * @param port
     * @return
     */
    private static boolean isHostPortReachable(String host,Integer port,Integer timeOut){
        Boolean isConnect = false;
        Socket connect = new Socket();
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
            connect.connect(inetSocketAddress, timeOut);
            isConnect = connect.isConnected();
        } catch (IOException e) {
        
        }finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (IOException e) {
                }
            }
        }
        return isConnect;
    }

    public static void main(String[] args) throws Exception {
        long l = System.nanoTime();
        String ipAddress = "www.baidu.com";
        int pingDelay = getPingDelay(ipAddress);
        System.out.println(pingDelay);
        long l2 = System.nanoTime();
        System.out.println("花费"+(l2-l)+"ns");
    }
    
}
