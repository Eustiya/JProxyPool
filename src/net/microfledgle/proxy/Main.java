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

package net.microfledgle.proxy;

import net.microfledgle.proxy.grasp.GraspProxy;
import net.microfledgle.proxy.httpserver.HttpServerStarter;
import net.microfledgle.proxy.io.ReadProxies;
import net.microfledgle.proxy.io.SaveProxies;
import net.microfledgle.proxy.io.ZipBackup;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 17:48
 * @description：
 * @version: $
 */
public class Main {
    
    public static void getProxies(){
        GraspProxy.main(null);
    }
    
    public static void reload(){
        ReadProxies.readAll();
        ZipBackup.main(null);
        SaveProxies.saveAll();
    }
    
    public static void main(String[] args){
        reload();
        getProxies();
        SaveProxies.start();
        HttpServerStarter.main(null);
    }
}
