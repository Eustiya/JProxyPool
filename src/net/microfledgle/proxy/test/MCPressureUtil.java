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

package net.microfledgle.proxy.test;

import net.microfledgle.proxy.Main;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/11 1:42
 * @description：
 * @version: $
 */
public class MCPressureUtil {
    
    public static ThreadPoolExecutor executors_ = new ThreadPoolExecutor(500, 500, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(5));
    
    public static void main(String[] args) {
        go();
        inits();
        
    }
    
    public static void inits(){
        Main.main(null);
    }
    
    public static void go(){
        executors_.execute(() -> {
            while (true) {
                for (int i = 0; i < 100; i++) {
                    executors_.execute(new MCPressuretest());
                }
                try {
                    Thread.sleep(1000000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
