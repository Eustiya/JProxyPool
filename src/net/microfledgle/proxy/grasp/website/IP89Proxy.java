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
import net.microfledgle.proxy.io.SaveProxies;
import net.microfledgle.proxy.pool.PoolUtils;
import net.microfledgle.proxy.pool.Proxy;
import net.microfledgle.proxy.pool.ProxyType;
import net.microfledgle.proxy.timer.ThreadPoolHandler;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/10 2:01
 * @description：
 * @version: $
 */
public class IP89Proxy implements Website,Runnable{
    private static Logger logger = Logger.getLogger(IP89Proxy.class);
    
    private static ThreadPoolHandler threadPoolHandler = new ThreadPoolHandler();
    
    
    
    private static final int runningTimes = 50;
    
    public static void start() throws InterruptedException {
        inits();
        Thread.sleep(2000);
        for(int i =0;i<runningTimes;i++) {
            threadPoolHandler.executor(new IP89Proxy());
            try {
                Thread.sleep(6000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public static void inits(){
        try {
            String s = "http://www.89ip.cn/tqdl.html?num=9999&address=&kill_address=&port=&kill_port=&isp=";
            Connection cont = Jsoup.connect(s);
            Document document1 = cont.get();
            String text = document1.text();
            String[] s1 = text.split(" ");
            for (int i = 1; i <s1.length; i++) {
               
                String s2 = s1[i];
                if(s2.indexOf(":")!=-1 && s2.indexOf("http")==-1){
                    System.out.println("IP89 "+s2);
                    IP89ConcurrentHandler.addURL(s2);
                }
            }
            IP89ConcurrentHandler.inits();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public static HashMap<String,Integer> getProxies(List<String> addresses){
        HashMap<String,Integer> proxies = new HashMap<>();
        for (String address : addresses) {
            String[] split = address.split(":");
            proxies.put(split[0],Integer.parseInt(split[1]));
        }
        
        return proxies;
    }
    
    public static void  main(String[] args) {
        try {
            start();
        }catch (Exception e){
            e.printStackTrace();
        }
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
        while (IP89ConcurrentHandler.hasNext()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(IP89ConcurrentHandler.getURL());
            }
            HashMap<String, Integer> proxies =
                    getProxies(list);
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
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(IP89ConcurrentHandler.getURL());
        }
        HashMap<String, Integer> proxies =
                getProxies(list);
        this.addProxies(proxies);
    }
}
