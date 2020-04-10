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

import io.netty.handler.proxy.ProxyHandler;
import net.microfledgle.proxy.pool.PoolUtils;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.event.session.*;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/11 1:15
 * @description：
 * @version: $
 */
public class MCPressuretest implements Runnable {
    
    private static String player = "player_";
    
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    
    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                int i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            return "null";
        }
    }
    
    @Override
    public void run() {
       String player$ = player+getRandomString(5);
    
        net.microfledgle.proxy.pool.Proxy proxy_ = PoolUtils.getProxy();
        System.out.println(proxy_);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy_.getAddress(),
                Integer.parseInt(proxy_.getPort())));
        MinecraftProtocol minecraftProtocol = new MinecraftProtocol(player$);
        Client client = new Client("coslight.cc", 39518, minecraftProtocol,
                new TcpSessionFactory(proxy));
        client.getSession().setWriteTimeout(5000);
        client.getSession().setReadTimeout(3000);
        
        client.getSession().addListener(new SessionListener() {
            public void packetReceived(PacketReceivedEvent event) {
                if (event.getPacket() instanceof org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket) {
                    try {
                        Thread.sleep(100000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                } else if (event.getPacket() instanceof ServerChatPacket) {
                
                }
            }
            
            public void disconnected(DisconnectedEvent arg0) {
            
            }
            
            
            public void connected(ConnectedEvent arg0) {
            }
            
            public void disconnecting(DisconnectingEvent arg0) {
            }
            
            
            public void packetSent(PacketSentEvent arg0) {
            }
        });
        client.getSession().connect();
    }
}
    

