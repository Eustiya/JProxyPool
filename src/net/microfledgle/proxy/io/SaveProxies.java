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

package net.microfledgle.proxy.io;

import net.microfledgle.proxy.pool.Proxy;
import net.microfledgle.proxy.pool.focus.*;
import net.microfledgle.proxy.timer.ThreadPoolHandler;

import java.util.Iterator;
import java.util.Set;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/8 20:59
 * @description：
 * @version: $
 */
public class SaveProxies implements Runnable{
    
    
    
    public static void saveAll(){
        Acme acme = PoolManager.getAcme();
        Commonly commonly = PoolManager.getCommonly();
        Excellent excellent = PoolManager.getExcellent();
        Garbage garbage = PoolManager.getGarbage();
        Poor poor = PoolManager.getPoor();
        saveOne(acme);
        saveOne(commonly);
        saveOne(excellent);
        saveOne(garbage);
        saveOne(poor);
    }
    
    
    public static <T extends ProxyPool> void saveOne(T proxyPool){
        Set<Proxy> proxies = proxyPool.getProxies();
        Iterator<Proxy> iterator = proxies.iterator();
        String simpleName = proxyPool.getClass().getSimpleName();
        FileHandler.FileClean(simpleName);
        while(iterator.hasNext()){
            Proxy next = iterator.next();
            String serialize = Serialize.serialize(next);
            FileHandler.FileWrite(simpleName,serialize);
            
        }
    }
    
    public static void start(){
        SaveProxies saveProxies = new SaveProxies();
        ThreadPoolHandler.executor_(saveProxies);
    }
    
    
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            saveAll();
            System.out.println("保存所有数据成功！");
        }
        
    }
}
