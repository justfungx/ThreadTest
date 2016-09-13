package tw.org.iii.threadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private UIHandler handler;
    private Timer timer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.tv);
        handler =  new UIHandler();
        timer = new Timer();
;    }

    public  void text1(View v){
        Thread1 t1 = new Thread1();
        t1.start();
    }
    public  void Text2(View v){
    MyTask mt1 = new MyTask();
        timer.schedule(mt1, 200, 200);
    }
    private class Thread1 extends Thread {
        //還未結束執行續  ，還是會在背景後繼續跑程式
        @Override
        public void run() {
            for (int i=0; i<10; i++){
                Log.d("brad", "i=" + i);
                //tv.setText("i=" + i);
               // handler.sendEmptyMessage(i);

                Message mesg = new Message();
                Bundle data = new Bundle();
                data.putInt("i", i);
                mesg.setData(data);
                handler.sendMessage(mesg);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private  class MyTask extends TimerTask{
        private  int i;
        @Override
        public void run() {
            Log.d("brad", "i = " + i++);
            Message mesg = new Message();
            Bundle data = new Bundle();
            data.putInt("i", i);
            mesg.setData(data);
            handler.sendMessage(mesg);
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int i = data.getInt("i");
            tv.setText("i = " + i);

        }
    }

}