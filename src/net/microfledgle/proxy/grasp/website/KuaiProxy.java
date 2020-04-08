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

package net.microfledgle.proxy.grasp.website;

import net.microfledgle.proxy.grasp.Website;
import net.microfledgle.proxy.io.FileHandler;
import net.microfledgle.proxy.io.SaveProxies;
import net.microfledgle.proxy.pool.PoolUtils;
import net.microfledgle.proxy.pool.Proxy;
import net.microfledgle.proxy.pool.ProxyType;
import net.microfledgle.proxy.pool.focus.PoolManager;
import net.microfledgle.proxy.timer.ThreadPoolHandler;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/8 19:21
 * @description：
 * @version: $
 */
public class KuaiProxy implements Website,Runnable {
    
    private static Logger logger = Logger.getLogger(KuaiProxy.class);
    
    
    //"https://www.kuaidaili.com/free/inha/1/"
    private static final String URL = "https://www.kuaidaili.com/free/inha/";
    
    private static void inits(){
       for(int i=1,size=50;i<size;i++){
           String url = URL+i+ "/";
           KuaiConcurrentHandler.addURL(url);
       }
       KuaiConcurrentHandler.inits();
    }
    
    public static void start(){
        inits();
        for(int i =0;i<50;i++) {
            ThreadPoolHandler.executor(new KuaiProxy());
            try {
                Thread.sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    
    public static HashMap<String,Integer> getProxies(String url){
        HashMap<String,Integer> proxies = new HashMap<>();
        try {
            Connection connect = Jsoup.connect(url);
            Document document = connect.proxy(new java.net.Proxy(java.net.Proxy.Type.HTTP,
                    new InetSocketAddress("64.225.50.0", 8080))).get();
            Elements elementsByClass = document.getElementsByClass("table-striped");
            Elements tbody = elementsByClass.select("tbody").select("tr");
            for (Element element : tbody) {
                Elements td = element.getElementsByTag("td");
                
                String ip = null;
                String port = null;
                for (Element element1 : td) {
                    String ip$ = element1.getElementsByAttributeValue("data-title", "IP").text();
                    if(!"".equals(ip$)){
                       ip = ip$;
                    }
                    String port$ = element1.getElementsByAttributeValue("data-title", "PORT").text();
                    if(!"".equals(port$)){
                        port = port$;
                    }
                }
                if(ip=="" || port=="" || ip == null || port == null){
                   continue;
                }
                System.out.println(ip+"|"+port);
                proxies.put(ip,Integer.parseInt(port));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return proxies;
    }
    
    public static void main(String[] args) throws InterruptedException {
        SaveProxies.start();
        start();
//        Thread.sleep(60 *1000);
        PoolManager.looks();
    }
    
    protected void addProxies(HashMap<String, Integer> proxies){
        Set<Map.Entry<String, Integer>> entries = proxies.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            String key = next.getKey();
            Integer value = next.getValue();
            Proxy proxy = new Proxy(key, String.valueOf(value), ProxyType.HTTP);
            PoolUtils.addProxy(proxy);
        }
    }
    
    protected static void addProxies$(HashMap<String, Integer> proxies){
        Set<Map.Entry<String, Integer>> entries = proxies.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            String key = next.getKey();
            Integer value = next.getValue();
            Proxy proxy = new Proxy(key, String.valueOf(value), ProxyType.HTTP);
            PoolUtils.addProxy(proxy);
        }
    }
    
    public static void test(){
        while (KuaiConcurrentHandler.hasNext()) {
            HashMap<String, Integer> proxies =
                    getProxies(KuaiConcurrentHandler.getURL());
            addProxies$(proxies);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void run() {
            HashMap<String, Integer> proxies =
                getProxies(KuaiConcurrentHandler.getURL());
        this.addProxies(proxies);
    }
}
