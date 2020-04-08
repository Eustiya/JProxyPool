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

import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 17:57
 * @description：
 * @version: $
 */
public enum AnonymityDegree {
    TRANSPARENT_PROXY(0,"透明代理"),
    ANONYMOUS_PROXY(1000,"匿名代理"),
    DISTORTING_PROXY(2000,"混淆代理"),
    ELITE_PROXY(3000,"高匿代理"),
    UNCLEAR_PROXY(500,"不明代理");
    
    AnonymityDegree(int i, String description) {
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
