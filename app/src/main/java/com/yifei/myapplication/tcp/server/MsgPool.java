package com.yifei.myapplication.tcp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class MsgPool  {
    private LinkedBlockingDeque<String> mQueue = new LinkedBlockingDeque<>();
    private static MsgPool sInstance = new MsgPool();
    private List<MsgComingListener> mListener =new ArrayList<>();

    private MsgPool(){}
    public void start(){
        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        String msg = mQueue.take();
                        notifyMsgComing(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static MsgPool getsInstance(){
        return  sInstance;
    }
    public void addMsgComingListener(MsgComingListener listener){
        mListener.add(listener);
    }

    public void sendMsg (String msg){
        try {
            mQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void notifyMsgComing(String msg) {
        for(MsgComingListener listener:mListener){
            listener.onMsgComing(msg);
        }
    }

    public interface  MsgComingListener{
        void onMsgComing(String msg);
    }
}
