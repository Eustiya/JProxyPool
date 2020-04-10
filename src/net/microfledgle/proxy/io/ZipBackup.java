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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author ：Arisa
 * @date ：Created in 2020/4/10 17:17
 * @description：
 * @version: $
 */
public class ZipBackup {
    
    /**
     * 缓冲器大小
     */
    private static final int BUFFER = 512;
    
    /**
     * 压缩得到的文件的后缀名
     */
    private static final String SUFFIX = ".zip";
    
    /**
     * 得到源文件路径的所有文件
     *
     * @param dirFile 压缩源文件路径
     */
    public static List<File> getAllFile(File dirFile) {
        List<File> fileList = new ArrayList<>();
        File[] files = dirFile.listFiles();
        for (File file : files) {//文件
            if (file.isFile()) {
                fileList.add(file);
                System.out.println("add file:" + file.getName());
            } else {//目录
                if (file.listFiles().length != 0) {//非空目录
                    fileList.addAll(getAllFile(file));//把递归文件加到fileList中
                } else {//空目录
                    fileList.add(file);
                    System.out.println("add empty dir:" + file.getName());
                }
            }
        }
        return fileList;
    }
    
    /**
     * 获取相对路径
     *
     * @param dirPath 源文件路径
     * @param file    准备压缩的单个文件
     */
    public static String getRelativePath(String dirPath, File file) {
        File dirFile = new File(dirPath);
        String relativePath = file.getName();
        
        while (true) {
            file = file.getParentFile();
            if (file == null) break;
            if (file.equals(dirFile)) {
                break;
            } else {
                relativePath = file.getName() + "/" + relativePath;
            }
        }
        return relativePath;
    }
    
    public static void main(String[] args) {
        compress("D:\\IDEA-Project\\JProxyPool\\resources");
    }
    
    /**
     * 没有指定压缩目标路径进行压缩,用默认的路径进行压缩
     *
     * @param dirPath 压缩源文件路径
     */
    public static void compress(String dirPath) {
        
        int firstIndex = dirPath.indexOf("/");
        int lastIndex = dirPath.lastIndexOf("/");
        //"/backup"
        String zipFileName = dirPath.substring(0, firstIndex + 1) + dirPath.substring(lastIndex + 1);
        Date date = new Date();
        compress(dirPath,
                zipFileName.replace("\\resources","")+"\\backup\\resources_"+(1970+date.getYear())+"-"+(date.getMonth()+1)+"-"+date.getDate());
    }
    
    /**
     * 压缩文件
     *
     * @param dirPath     压缩源文件路径
     * @param zipFileName 压缩目标文件路径
     */
    public static void compress(String dirPath, String zipFileName) {
        zipFileName = zipFileName + SUFFIX;//添加文件的后缀名
        System.out.println(zipFileName);
        File dirFile = new File(dirPath);
        List<File> fileList = getAllFile(dirFile);
        byte[] buffer = new byte[BUFFER];
        ZipEntry zipEntry = null;
        int readLength = 0;
        try {
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                    zipFileName), new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);
            for (File file : fileList) {
                if (file.isFile()) {
                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));  //
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zos.putNextEntry(zipEntry);
                    InputStream is = new BufferedInputStream(new FileInputStream(file));
                    while ((readLength = is.read(buffer, 0, BUFFER)) != -1) {
                        zos.write(buffer, 0, readLength);
                    }
                    is.close();
                    System.out.println("file compress:" + file.getCanonicalPath());
                } else {
                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));
                    zos.putNextEntry(zipEntry);
                    System.out.println("dir compress: " + file.getCanonicalPath() + "/");
                }
            }
            zos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
