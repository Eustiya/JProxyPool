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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 19:36
 * @description：
 * @version: $
 */
//垃圾
public class Garbage implements ProxyPool{
    
    private Set<Proxy> proxies = new HashSet<>();
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Garbage)) return false;
        Garbage garbage = (Garbage) o;
        return Objects.equals(proxies, garbage.proxies);
    }
    
    public Set<Proxy> getProxies() {
        return proxies;
    }
    
    @Override
    public String
    toString() {
        final StringBuilder sb = new StringBuilder("Garbage{");
        sb.append("proxies=").append(proxies);
        sb.append('}');
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(proxies);
    }
    
    public void addProxy(Proxy proxy){
        this.proxies.add(proxy);
    }
    
    public void delProxy(Proxy proxy){
        this.proxies.remove(proxy);
    }
    
    public boolean containsProxy(Proxy proxy){
        return this.proxies.contains(proxy);
    }
    
    @Override
    public boolean hasProxy() {
        return proxies.size()>=1;
    }
    
    @Override
    public Proxy getProxy() {
        if(!hasProxy()){
          return null;
        }
        Proxy result = null;
        int score$ = -100000;
        for (Proxy proxy : proxies) {
            int score =
                    proxy.getAnonymityDegree().getScore()+proxy.getProxyPriority().getScore()+proxy.getProxySpeed().getScore();
            if(score$<score){
                if(ProxyUsed.containsProxy(proxy)){
                  continue;
                }
                result = proxy;
            }
        }
        if(result==null){
          return null;
        }
        ProxyUsed.putProxy(result);
        return result;
    }
    
    
}
