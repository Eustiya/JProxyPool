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

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 19:34
 * @description：
 * @version: $
 */
public class PoolManager {
    
    private static Acme acme = new Acme();
    private static Commonly commonly = new Commonly();
    private static Excellent excellent = new Excellent();
    private static Garbage garbage = new Garbage();
    private static Poor poor = new Poor();
    
    public static void inits(){
    
    }
    
    public static Acme getAcme() {
        return acme;
    }
    
    public static void setAcme(Acme acme) {
        PoolManager.acme = acme;
    }
    
    public static Commonly getCommonly() {
        return commonly;
    }
    
    public static void setCommonly(Commonly commonly) {
        PoolManager.commonly = commonly;
    }
    
    public static Excellent getExcellent() {
        return excellent;
    }
    
    public static void setExcellent(Excellent excellent) {
        PoolManager.excellent = excellent;
    }
    
    public static Garbage getGarbage() {
        return garbage;
    }
    
    public static void setGarbage(Garbage garbage) {
        PoolManager.garbage = garbage;
    }
    
    public static Poor getPoor() {
        return poor;
    }
    
    public static void setPoor(Poor poor) {
        PoolManager.poor = poor;
    }
    
    public static void looks(){
        System.out.println("Acme: "+acme.toString()
                           +"Commonly: "+commonly.toString()
                            +"Excellent: "+excellent.toString()
                            +"Garbage:"+excellent.toString()
                            +"Poor:"+poor.toString());
    }
    
    private static ProxyPool getProxiesPool(Proxy proxy){
         int score =
                 proxy.getAnonymityDegree().getScore()+proxy.getProxyPriority().getScore()+proxy.getProxySpeed().getScore();
         if (score>8000){
            return getPool(Focus.ACME);
         }else if (score>6000){
            return getPool(Focus.EXCELLENT);
         }else if (score>4000){
             return getPool(Focus.COMMONLY);
         }else if(score>2000){
             return getPool(Focus.Poor);
         }else{
             return getPool(Focus.GARBAGE);
         }
    }
    
    private static ProxyPool getPool(Focus focus){
        switch (focus){
            case ACME:
                return acme;
            case COMMONLY:
                return commonly;
            case Poor:
                return poor;
            case GARBAGE:
                return garbage;
            case EXCELLENT:
                return excellent;
            default:
                return poor;
        }
    }
    
    public static void Proxy(){
        
    }
    
    public static void addProxy(Proxy proxy){
        ProxyPool proxiesPool = getProxiesPool(proxy);
        proxiesPool.addProxy(proxy);
    }
    
    public static void delProxy(Proxy proxy){
        ProxyPool proxiesPool = getProxiesPool(proxy);
        proxiesPool.delProxy(proxy);
    }
    
    public static boolean contains(Proxy proxy){
        ProxyPool proxiesPool = getProxiesPool(proxy);
        return proxiesPool.containsProxy(proxy);
    }
}
