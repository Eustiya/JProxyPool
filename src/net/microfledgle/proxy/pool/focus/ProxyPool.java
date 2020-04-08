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

package net.microfledgle.proxy.pool.focus;

import net.microfledgle.proxy.pool.Proxy;

import java.util.Set;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 19:54
 * @description：
 * @version: $
 */
public interface ProxyPool {
    
    public void addProxy(Proxy proxy)  ;
    
    public void delProxy(Proxy proxy)  ;
    
    public Set<Proxy> getProxies();
    
    public boolean containsProxy(Proxy proxy);
    
    public boolean hasProxy();
    
    public Proxy getProxy();
}
