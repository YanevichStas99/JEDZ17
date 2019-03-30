package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        /*String userDir = System.getProperty("user.dir");
        File homeDir = new File(userDir);
        System.out.println(homeDir);

        if(homeDir.exists() && homeDir.isDirectory()){
            File[] files=homeDir.listFiles();
            for(File file:files){
                System.out.println(file.getName());
                if (file.getName().equals("test.txt")){
                    file.delete();
                }
            }
        }*/
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir);
        System.out.println(file.getAbsolutePath());
        try {
            downloadFile("https://dl.dropboxusercontent.com/s/21rtgfdmlp6wycx/urls.txt", userDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file);
        System.out.println("---------------------");
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            allFiles(files);
        }
        System.out.println("----------------------");
        File file2 = new File("C:\\Program Files\\Java\\jdk1.8.0_161\\src2");
        System.out.println(file2.getAbsolutePath());
        if (file2.exists() && file2.isDirectory()) {
            File[] files2 = file2.listFiles();
            System.out.println("Java files in jdk\\src: "+checkAllFiles(files2));
        }

    }

    public static void allFiles(File[] files) {
        for (File file1 : files) {
            if (file1.isDirectory()) {
                File[] files2 = file1.listFiles();
                allFiles(files2);
            } else {
                System.out.println(file1.getName());
            }
        }


    }
    public static int checkAllFiles(File[] files){
        int count=0;
        for (File file1 : files) {
            if (file1.isDirectory()) {
                File[] files2 = file1.listFiles();
                count=count+checkAllFiles(files2);
            } else {
                if (file1.getName().contains(".java")){
                    count++;
                }
            }
        }
        return count;
    }

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
}
