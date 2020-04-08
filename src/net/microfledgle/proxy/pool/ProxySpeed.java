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

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 18:22
 * @description：
 * @version: $
 */
public enum ProxySpeed {
    NIUBILITY(1000.0,"100mbps"),
    FASTEST(500.0,"30mbps"),
    FAST(100.0,"10mbps"),
    NORMAL(50.0,"3mbps"),
    SLOW(20.0,"2mbps"),
    SLOWEST(10.0,"1mbps"),
    FAKE_DEAD(5,"0.5mbps"),
    DEAD(0.1,"0.1mbps以下");
    
    ProxySpeed(double v, String description) {
        this.i = i;
        this.description = description;
    }
    
    private int i;
    private String description;
    
    public int getScore(){
        return this.i;
    }
    
    public String getDescription(){
        return this.description;
    }
}
