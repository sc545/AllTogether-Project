package com.example.k.alltogether.alltogether;

import android.os.Handler;
import android.widget.Toast;

/**
 * Created by K on 2015-11-25.
 */
public class Timer extends Thread {
    Handler handler;
    GameStageActivity gameStageActivity;
    Timer(GameStageActivity gameStageActivity){
        handler = new Handler();
        this.gameStageActivity = gameStageActivity;
    }
    @Override
    public void run() {
        while(true) {
            if(gameStageActivity.m_bGameState) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int seconds = ++gameStageActivity.m_nSeconds;
                int minutes = seconds / 60;
                seconds = seconds % 60;

                final String strText = String.format("%02d : %02d", minutes, seconds);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameStageActivity.tvTimer.setText(strText);
//                        Toast.makeText(gameStageActivity.getApplicationContext(), ""+gameStageActivity.m_nSeconds, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
