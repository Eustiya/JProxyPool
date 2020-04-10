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
import net.microfledgle.proxy.pool.focus.PoolManager;
import net.microfledgle.proxy.timer.ThreadPoolHandler;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/9 23:00
 * @description：
 * @version: $
 */
public class EmailtryProxy implements Website,Runnable {
    
    private static Logger logger = Logger.getLogger(EmailtryProxy.class);
    
    private static ThreadPoolHandler threadPoolHandler = new ThreadPoolHandler();
    
    
    
    private static void inits(){
        for(int i=1,size=50;i<size;i++){
            String url = "http://www.emailtry.com/index/"+i;
            EmailtryConcurrentHandler.addURL(url);
        }
        EmailtryConcurrentHandler.inits();
    }
    
    public static void start(){
        inits();
        
        for(int i =0;i<50;i++) {
            threadPoolHandler.executor(new EmailtryProxy());
            try {
                Thread.sleep(6000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    //^((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}$
    // ip正则表达式
    //^[1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$)
    // 端口正则表达式

    public static HashMap<String,Integer> getProxies(String url){
        HashMap<String,Integer> proxies = new HashMap<>();
        try {
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            Element elementsByClass = document.getElementById("proxy-table1");
            Elements tbody = elementsByClass.select("tbody").select("tr");
            for (Element element : tbody) {
                Elements td = element.getElementsByTag("td");
                //TODO 不够安全
                Element elementsByIndexEquals = td.get(0);
                String text1 = elementsByIndexEquals.text();
                String[] split = text1.split(":");
                proxies.put(split[0],Integer.parseInt(split[1]));
            }
            System.out.println("Emailtry "+proxies);
        }catch (Exception e){
            e.printStackTrace();
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
        while (EmailtryConcurrentHandler.hasNext()) {
            HashMap<String, Integer> proxies =
                    getProxies(EmailtryConcurrentHandler.getURL());
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
                getProxies(EmailtryConcurrentHandler.getURL());
        this.addProxies(proxies);
    }
    
}
