package com.yifei.myapplication.tcp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTask extends Thread implements MsgPool.MsgComingListener {

    private Socket socket;
    private InputStream mIs;
    private OutputStream mOs;

    public ClientTask(Socket socket){
        this.socket =socket;
        try {
            mIs = socket.getInputStream();
            mOs = socket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(mIs));
        try {
            String line = null;
            while ((line=br.readLine())!=null){
                System.out.println("read = "+line);
                //转发消息至其他Socket
                MsgPool.getsInstance().sendMsg(socket.getPort()+"==="+line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMsgComing(String msg) {
        try {
            mOs.write(msg.getBytes());
            mOs.write("\n".getBytes());
            mOs.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
