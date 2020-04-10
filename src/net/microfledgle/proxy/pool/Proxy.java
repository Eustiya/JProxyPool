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

import net.microfledgle.proxy.handler.AnonymityUtil;
import net.microfledgle.proxy.handler.PingUtil;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/7 17:51
 * @description：
 * @version: $
 */
public class Proxy {
    
    
    private String address;
    private String port;
    
    //代理匿名度
    private AnonymityDegree anonymityDegree;
    //代理延迟
    private ProxyPriority proxyPriority;
    //代理的宽带
    private ProxySpeed proxySpeed;
    //代理的类型
    private ProxyType proxyType;
    
    private String username;
    private String password;
    
    public Proxy(String address, String port, AnonymityDegree anonymityDegree, ProxyPriority proxyPriority, ProxySpeed proxySpeed, ProxyType proxyType, String username, String password) {
        this.address = address;
        this.port = port;
        this.anonymityDegree = anonymityDegree;
        this.proxyPriority = proxyPriority;
        this.proxySpeed = proxySpeed;
        this.proxyType = proxyType;
        this.username = username;
        this.password = password;
    }
    
    
    
    public Proxy(String address, String port,ProxyType proxyType){
        proxySpeed = ProxySpeed.SLOWEST;
        this.address = address;
        this.port = port;
        this.proxyPriority = PingUtil.getProxyPriority(this);
        this.anonymityDegree = AnonymityUtil.getAnonymityDegree(this);
        
    }
    
    public Proxy(String address, String port, ProxyType proxyType, String username, String password){
        proxySpeed = ProxySpeed.SLOWEST;
        this.address = address;
        this.port = port;
        this.username = username;
        this.password = password;
        this.proxyPriority = PingUtil.getProxyPriority(this);
        this.anonymityDegree = AnonymityUtil.getAnonymityDegree(this);
        
    }
    
    
    public String getAddress() {
        return address;
    }
    
    public Proxy setAddress(String address) {
        this.address = address;
        return this;
    }
    
    public String getPort() {
        return port;
    }
    
    public Proxy setPort(String port) {
        this.port = port;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proxy{");
        sb.append("address='").append(address).append('\'');
        sb.append(", port='").append(port).append('\'');
        sb.append(", anonymityDegree=").append(anonymityDegree);
        sb.append(", proxyPriority=").append(proxyPriority);
        sb.append(", proxySpeed=").append(proxySpeed);
        sb.append(", proxyType=").append(proxyType);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proxy)) return false;
        Proxy proxy = (Proxy) o;
        return Objects.equals(getAddress(), proxy.getAddress()) &&
                Objects.equals(getPort(), proxy.getPort()) &&
                getAnonymityDegree() == proxy.getAnonymityDegree() &&
                getProxyPriority() == proxy.getProxyPriority() &&
                getProxySpeed() == proxy.getProxySpeed() &&
                getProxyType() == proxy.getProxyType() &&
                Objects.equals(getUsername(), proxy.getUsername()) &&
                Objects.equals(getPassword(), proxy.getPassword());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getPort(), getAnonymityDegree(), getProxyPriority(), getProxySpeed(), getProxyType(), getUsername(), getPassword());
    }
    
    public AnonymityDegree getAnonymityDegree() {
        return anonymityDegree;
    }
    
    public Proxy setAnonymityDegree(AnonymityDegree anonymityDegree) {
        this.anonymityDegree = anonymityDegree;
        return this;
    }
    
    public ProxyPriority getProxyPriority() {
        return proxyPriority;
    }
    
    public Proxy setProxyPriority(ProxyPriority proxyPriority) {
        this.proxyPriority = proxyPriority;
        return this;
    }
    
    public ProxySpeed getProxySpeed() {
        return proxySpeed;
    }
    
    public Proxy setProxySpeed(ProxySpeed proxySpeed) {
        this.proxySpeed = proxySpeed;
        return this;
    }
    
    public ProxyType getProxyType() {
        return proxyType;
    }
    
    public Proxy setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
        return this;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Proxy setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public String getPassword() {
        return password;
    }
    
    public Proxy setPassword(String password) {
        this.password = password;
        return this;
    }
    
}
