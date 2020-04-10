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
import net.microfledgle.proxy.io.SaveProxies;
import net.microfledgle.proxy.pool.PoolHandler;
import net.microfledgle.proxy.pool.PoolUtils;
import net.microfledgle.proxy.pool.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/10 17:52
 * @description：
 * @version: $
 */
public class MCBBSExample {
    
    private static int times = 0;
    
    public static ThreadPoolExecutor executors_ = new ThreadPoolExecutor(50, 1000, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(50));
    
    public static void main(String[] args) {
        Main.reload();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executors_.execute(() -> {
                Proxy proxy = PoolUtils.getProxy();
                System.out.println(proxy);
                try {
                    Document document = Jsoup.connect("https://www.mcbbs.net/thread-995800-1-1.html")
                            .proxy(proxy.getAddress(), Integer.parseInt(proxy.getPort())).timeout(10000)
                            .get();
                    times++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("连接成功 "+times);
        
            });
        }
        
        
    }
}
