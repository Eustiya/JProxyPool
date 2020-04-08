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

import net.microfledgle.proxy.pool.AnonymityDegree;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/8 16:47
 * @description：
 * @version: $
 */
public class AnonymityUtil {
    
    private static String Website_URL = "http://httpbin.org/ip";
    
    public static AnonymityDegree getAnonymityDegree(net.microfledgle.proxy.pool.Proxy proxy){
        if(getAnonymity(proxy)){
           return AnonymityDegree.TRANSPARENT_PROXY;
        }else{
            return AnonymityDegree.ELITE_PROXY;
        }
    }
    
    private static HttpHost getHttpHost(String ip,int port){
        return new HttpHost(ip, port, "http");
    }
    
    private static HttpHost getHttpHost(String ip,int port,String scheme){
        return new HttpHost(ip, port, scheme);
    }
    
    private static HttpHost getHttpHost(String ip,String port){
        return new HttpHost(ip, Integer.parseInt(port), "http");
    }
    
    private static HttpHost getHttpHost(String ip,String port,String scheme){
        return new HttpHost(ip, Integer.parseInt(port), scheme);
    }
    
    private static HttpHost getHttpHost(net.microfledgle.proxy.pool.Proxy proxy){
        return getHttpHost(proxy.getAddress(),proxy.getPort());
    }
    
    public static boolean getAnonymity(net.microfledgle.proxy.pool.Proxy proxy){
        try {
            
            String localIP = InetAddress.getLocalHost().getHostAddress();
            String s = AHttpUtils.doGet("http://httpbin.org/ip",getHttpHost(proxy));
            if(localIP.equals(s)){
                return true;
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean getAnonymity(String ip,int port,String scheme){
        try {
            String localIP = InetAddress.getLocalHost().getHostAddress();
            String s = AHttpUtils.doGet("http://httpbin.org/ip",getHttpHost(ip, port, scheme));
            if(localIP.equals(s)){
                return true;
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean getAnonymity(String ip,int port){
        try {
            String localIP = InetAddress.getLocalHost().getHostAddress();
            String s = AHttpUtils.doGet("http://httpbin.org/ip",getHttpHost(ip, port));
            if(localIP.equals(s)){
                return true;
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    @Deprecated
    private static void doGetAnonymity(){
        Connection connect =
                Jsoup.connect("https://www.baidu.com").proxy(new Proxy(java.net.Proxy.Type.HTTP,
                        new InetSocketAddress("64.225.50.0", 8080)));
        try {
            Document document = connect.get();
            System.out.println(document);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //"64.225.50.0"
    //8080
    public static void main(String[] args) {
        System.out.println(getAnonymity("64.225.50.0", 8080));
    }
}
