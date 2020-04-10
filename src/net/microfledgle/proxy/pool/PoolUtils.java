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

package net.microfledgle.proxy.pool;

import net.microfledgle.proxy.handler.AnonymityUtil;
import net.microfledgle.proxy.pool.focus.*;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 19:33
 * @description：
 * @version: $
 */
public class PoolUtils {
    
    public static void gc(){
    
    }
    
    public static void addProxy(Proxy proxy){
        PoolManager.addProxy(proxy);
    }
    
    public static void delProxy(Proxy proxy){
        PoolManager.delProxy(proxy);
    }
    
    public static boolean contains(Proxy proxy){
        //TODO 存在问题 更新不同步
        return PoolManager.contains(proxy);
    }
    
    
    
    public static Proxy get(ProxyType proxyType, ProxyPool proxyPool){
        return null;
    }
    
    public static Proxy getProxy(){
        Proxy proxy_ = getProxy_();
       
        boolean go = false;
        int i = 0;
        for(int anonymity = -1;anonymity==-1;anonymity = AnonymityUtil.getAnonymity(proxy_)){
            i++;
            if(i>=50){
               new Exception("没有可用 代理");
            }
           proxy_ = getProxy_();
        }
        return proxy_;
    }
    
    public static Proxy getProxy_(){
        Acme acme = PoolManager.getAcme();
        Commonly commonly = PoolManager.getCommonly();
        Excellent excellent = PoolManager.getExcellent();
        Poor poor = PoolManager.getPoor();
        Proxy proxy = acme.getProxy();
        if(proxy != null){
            return proxy;
        }
        proxy = excellent.getProxy();
        if(proxy != null){
            return proxy;
        }
        proxy = commonly.getProxy();
        if(proxy != null){
            return proxy;
        }
        proxy = poor.getProxy();
        if(proxy != null){
            return proxy;
        }
         return null;
    }
    
    
    public static Proxy get(ProxyType proxyType){
        return null;
    }
    
    public static Proxy get(ProxyPool proxyPool){
        return null;
    }
    
}
