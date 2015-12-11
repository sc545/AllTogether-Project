package com.example.k.alltogether.alltogether;

import android.os.Handler;

import com.example.k.alltogether.R;

/**
 * Created by K on 2015-11-13.
 */
public class FeverThread extends Thread {
    Handler handler; // 핸들러 사용 이유 : 메인스레드를 제외하고는 View 를 건드릴 수 없기 때문에 핸들러를 통해서 화면 갱신 => invalidate()
    GameStageActivity gameStageActivity;
    Music musicFever;
    public FeverThread(GameStageActivity gameStageActivity){
        handler = new Handler();
        this.gameStageActivity = gameStageActivity;
        musicFever = gameStageActivity.m_musicFeverSound;
    }
    public void run(){
        gameStageActivity.m_bFeverState = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                gameStageActivity.m_layouyStageBackground.setBackgroundResource(R.drawable.fever_background);
            }
        });
        if(gameStageActivity.m_bMusicEffectState) musicFever.start();
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        musicFever.pause();
        if(gameStageActivity != null) gameStageActivity.m_nFeverGauge = 0;
        handler.post(new Runnable() {
            @Override
            public void run() {
                gameStageActivity.m_layouyStageBackground.setBackgroundResource(R.drawable.stage_background);
            }
        });
        if(gameStageActivity != null) gameStageActivity.m_bFeverState = false;
        if(gameStageActivity != null) if(gameStageActivity.m_bMusicBgmState) gameStageActivity.m_musicGameSound.start();
    }
}
