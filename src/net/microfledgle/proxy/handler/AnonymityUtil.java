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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import net.microfledgle.proxy.pool.AnonymityDegree;
import net.microfledgle.proxy.pool.ProxyPriority;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/8 16:47
 * @description：
 * @version: $
 */
public class AnonymityUtil {
    
    private static String Website_URL = "http://httpbin.org/ip";
    
    public static AnonymityDegree getAnonymityDegree(net.microfledgle.proxy.pool.Proxy proxy){
        int anonymity = getAnonymity(proxy);
        if(anonymity == 1){
           return AnonymityDegree.TRANSPARENT_PROXY;
        }else if(anonymity == 0){
            return AnonymityDegree.ELITE_PROXY;
        }else{
            proxy.setProxyPriority(ProxyPriority.DEAD);
           return AnonymityDegree.TRANSPARENT_PROXY;
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
    
    public static int getAnonymity(net.microfledgle.proxy.pool.Proxy proxy){
        try {
            Document document =
                    Jsoup.connect("http://emailtry.com/proxychecker").proxy(proxy.getAddress(),
                            Integer.parseInt(proxy.getPort())).get();
            Elements br = document.getElementsByClass("col-md-12");
            List<TextNode> textNodes = br.textNodes();
            String remoteIP = getIP();
        
            for (int i = 0; i < textNodes.size(); i++) {
                String text = textNodes.get(i).text();
                if(text.indexOf("REMOTE_ADDR")!=-1){
                    remoteIP = text.replace(" REMOTE_ADDR= ", "");
                }
            }
            if(getIP().equals(remoteIP)){
                return 1;
            }
        
        }catch (Exception e){
            return -1;
        }
        return 0;
    }
    
    public static int getAnonymity(String ip,int port,String scheme){
        try {
            Document document =
                    Jsoup.connect("http://emailtry.com/proxychecker").proxy(ip,port).get();
            Elements br = document.getElementsByClass("col-md-12");
            List<TextNode> textNodes = br.textNodes();
            String remoteIP = getIP();
        
            for (int i = 0; i < textNodes.size(); i++) {
                String text = textNodes.get(i).text();
                if(text.indexOf("REMOTE_ADDR")!=-1){
                    remoteIP = text.replace(" REMOTE_ADDR= ", "");
                }
            }
            if(getIP().equals(remoteIP)){
                return 1;
            }
        
        }catch (Exception e){
            return -1;
        }
        return 0;
    }
    
    
    
    
   public static String outsideIP;
    
    static{
        outsideIP = getOutsideIP();
    }
    
    public static String getIP(){
        return outsideIP;
    }
    
    public static String getOutsideIP(){
        String ip = null;
        try {
            Document document = Jsoup.connect("https://www.ip.cn/").get();
            Elements select = document.getElementsByClass("well").select("p");
            Elements element = select.get(0).select("code");
            ip = element.text();
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return ip;
    }
    
    public static int getAnonymity(String ip,int port){
        try {
            Document document =
                    Jsoup.connect("http://emailtry.com/proxychecker").proxy(ip,port).get();
            Elements br = document.getElementsByClass("col-md-12");
            List<TextNode> textNodes = br.textNodes();
            String remoteIP = getIP();
            
            for (int i = 0; i < textNodes.size(); i++) {
                String text = textNodes.get(i).text();
                if(text.indexOf("REMOTE_ADDR")!=-1){
                    remoteIP = text.replace(" REMOTE_ADDR= ", "");
                }
            }
            if(getIP().equals(remoteIP)){
                return 1;
            }
            
        }catch (Exception e){
            return -1;
        }
        return 0;
    }
    
    
    @Deprecated
    private static void doGetAnonymity(){
        Connection connect =
                Jsoup.connect("https://www.baidu.com").proxy(new Proxy(java.net.Proxy.Type.HTTP,
                        new InetSocketAddress("95.71.48.246", 8080)));
        try {
            Document document = connect.get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //"64.225.50.0"
    //8080
    public static void main(String[] args) {
//        getOutsideIP();
        System.out.println(getAnonymity("95.71.48.246", 8180));
    }
}
