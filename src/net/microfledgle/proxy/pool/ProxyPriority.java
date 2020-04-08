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
 * @date ：Created in 2020/4/7 17:54
 * @description：
 * @version: $
 */
public enum ProxyPriority {
    NIUBILITY(9999,"牛批"),
    FASTEST(3000,"极速"),
    FAST(2000,"快"),
    NORMAL(1000,"一般"),
    SLOW(500,"慢"),
    SLOWEST(100,"非常慢"),
    FAKE_DEAD(50,"假死"),
    DEAD(-1000,"连接超时");
    
    
    ProxyPriority(int i, String description) {
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
