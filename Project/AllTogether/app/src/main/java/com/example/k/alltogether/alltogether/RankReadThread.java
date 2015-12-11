package com.example.k.alltogether.alltogether;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by K on 2015-11-17.
 */
public class RankReadThread extends Thread {
    String name[];
    int score[];
    Handler handler;
    Context co;
    TextView tvRank[];
    RankReadThread(Context co, TextView tvRank[]){
        handler = new Handler();
        this.co = co;
        this.tvRank = tvRank;
        name = new String[10];
        score = new int[10];
    }
    @Override
    public void run() {
        try {
            Socket s = new Socket(MainActivity.IP, MainActivity.PORT);
            InputStream is = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            OutputStream os = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject("read");

            name = (String[]) ois.readObject();
            score = (int[]) ois.readObject();

            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<10; i++){
                    tvRank[i].setText(name[i]+", "+score[i]);
                }
            }
        });
    }
}
