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

import net.microfledgle.proxy.pool.PoolHandler;
import net.microfledgle.proxy.pool.PoolUtils;
import net.microfledgle.proxy.pool.Proxy;
import net.microfledgle.proxy.pool.focus.*;

import java.util.Iterator;
import java.util.Set;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/10 17:07
 * @description：
 * @version: $
 */
public class ReadProxies {
    
    public static void readAll(){
        Acme acme = PoolManager.getAcme();
        Commonly commonly = PoolManager.getCommonly();
        Excellent excellent = PoolManager.getExcellent();
        Garbage garbage = PoolManager.getGarbage();
        Poor poor = PoolManager.getPoor();
        readOne(acme);
        readOne(commonly);
        readOne(excellent);
        readOne(garbage);
        readOne(poor);
    }
    
    public static <T extends ProxyPool> void readOne(T proxyPool){
        Set<String> strings = FileHandler.fileRead(proxyPool.getClass().getSimpleName());
        if(strings == null){
            return;
        }
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            Proxy proxy = Serialize.unSerialize(next);
            //TODO 缺少校验
            PoolUtils.addProxy(proxy);
        }
        
    }
}
