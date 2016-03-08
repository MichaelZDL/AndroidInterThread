package com.michael.interthreadtest;

import android.app.Activity;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

   private MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.button);
        Button btn2 = (Button)findViewById(R.id.button2);
        myThread=new MyThread();
        myThread.start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 1;
                myThread.myHandler.sendMessage(message);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=2;
                myThread.myHandler.sendMessage(message);
            }
        });
    }

    class MyThread extends Thread{
        public Handler myHandler;

        @Override
        public void run (){
            //建立消息循环的步骤
            Looper.prepare();//1、初始化Looper
            myHandler = new Handler(){//2、绑定handler到CustomThread实例的Looper对象
                @Override
                public void handleMessage (Message msg) {//3、定义处理消息的方法
                    switch(msg.what) {
                        case 1:
                            Toast.makeText(getApplicationContext(), "MyThread is here", Toast.LENGTH_SHORT).show();break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "MyThread is here2", Toast.LENGTH_SHORT).show();break;
                        default:
                    }
                }
            };
            Looper.loop();//4、启动消息循环
            for (;;);
        }
    }
}
