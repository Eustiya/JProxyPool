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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/9 23:00
 * @description：
 * @version: $
 */
public class XSConcurrentHandler {
    
    
    
    private static Set<String> urls = new HashSet<>();
    private static Iterator<String> iterator;
    
    public static void main(String[] args) {
    
    }
    
    public static void inits(){
        iterator = urls.iterator();
    }
    
    public static void addURL(String url){
        urls.add(url);
        
    }
    
    public static boolean hasNext(){
        return iterator.hasNext();
    }
    
    public static boolean checkURL(String url){
        return urls.contains(url);
    }
    
    public static String getURL(){
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    
}
