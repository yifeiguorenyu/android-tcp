package com.yifei.myapplication.tcp.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
   private Scanner mscanner;

   public TcpClient(){
       mscanner = new Scanner(System.in);
       mscanner.useDelimiter("\n");
   }


    public void start(){
        try {
            Socket socket = new Socket("192.168.3.45",9090);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            //输出服务端发送的数据
            new Thread(){
                @Override
                public void run() {
                    try {
                        String line = null;
                        while ((line = br.readLine())!=null){
                                System.out.println(line);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }.start();

            while (true){
                String msg = mscanner.next();
                bw.write(msg);
                bw.newLine();
                bw.flush();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
       new TcpClient().start();
    }
}
