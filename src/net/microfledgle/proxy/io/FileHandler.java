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

package net.microfledgle.proxy.io;

import java.io.*;
import java.util.*;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/8 18:12
 * @description：
 * @version: $
 */
public class FileHandler {
    
    public static String FILE_URL;
    
    static {
        String path = FileHandler.class.getResource("/").getPath();
        String substring = path.replace("target/classes/", "").substring(1);
        FILE_URL = substring + "resources/";
    }
    
    
    public static Set<String> fileRead(String fileName) {
        String s = FILE_URL + fileName + ".txt";
        File file = new File(s);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                
                Set<String> list = new HashSet<>();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    list.add(text);
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    
    public static void createFinle(String path) {
        File writename = new File(path);
        if (!writename.exists()) {
            try {
                writename.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void FileWrite(String fileName, String content) {
        String s = FILE_URL + fileName + ".txt";
        createFinle(s);
        FileWriter writer = null;
        try {
            writer = new FileWriter(s, true);
            
            writer.write(content + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void FileClean(String fileName) {
        String s = FILE_URL + fileName + ".txt";
        createFinle(s);
        FileWriter writer = null;
        try {
            writer = new FileWriter(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
