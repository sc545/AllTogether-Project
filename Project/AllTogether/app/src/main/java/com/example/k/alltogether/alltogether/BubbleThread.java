package com.example.k.alltogether.alltogether;

import android.graphics.Rect;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by K on 2015-11-13.
 */
public class BubbleThread extends Thread {
    Handler handler; // 핸들러 사용 이유 : 메인스레드를 제외하고는 View 를 건드릴 수 없기 때문에 핸들러를 통해서 화면 갱신 => invalidate()
    GameStageActivity gameStageActivity;
    GameStageActivity.GameView gameView;
    ArrayList<Bubble> arrayListBubble, arrayListFeverBubble;
    Rect rectBubbleArea;
    RankWriteDialog rankWriteDialog;
    int score;

    public BubbleThread(GameStageActivity gameStageActivity){
        handler = new Handler();
        this.gameStageActivity = gameStageActivity;
        gameView = gameStageActivity.gameView;
        arrayListBubble = gameStageActivity.m_arraylistBubble;
        arrayListFeverBubble = gameStageActivity.m_arraylistFeverBubble;
        rectBubbleArea = gameStageActivity.m_rectBubbleArea;
        rankWriteDialog = gameStageActivity.m_dlgRankWriteDialog;
    }

    public void run(){
        while(gameStageActivity.m_bThreadState) {
            if(gameStageActivity.m_bGameState) {
                score = gameStageActivity.m_nScore/10000;
                if(gameStageActivity.m_bFeverState){
                    createBubble(arrayListFeverBubble);
                }else if(gameStageActivity.m_bBombBubbleState) {
                    int random = (int) (Math.random()*10);
                    if(random == 9) {
                        createBombBubble();
                    }else{
                        createBubble(arrayListBubble);
                    }
                }else{
                    createBubble(arrayListBubble);
                }


            }
        }
    }
    void createBubble(ArrayList<Bubble> array){
        ArrayList<Bubble> arrayList = array;
        try {
            int x = (int) ((Math.random() * rectBubbleArea.width() + rectBubbleArea.left));
            int y = (int) ((Math.random() * rectBubbleArea.height() + rectBubbleArea.top));
            int r = (int) (Math.random()*2);
            Bubble bubble = new Bubble(x, y, gameStageActivity.m_nBubbleRadius, gameStageActivity);
//            switch (r){
//                case 0:
//                    bubble = new Bubble(x, y, gameStageActivity.m_nBubbleRadius*1, gameStageActivity);
//                    break;
//                case 1:
//                    bubble = new Bubble(x, y, gameStageActivity.m_nBubbleRadius*2, gameStageActivity);
//                    break;
//            }
            gameStageActivity.m_NewBubble = bubble;
            if (rectBubbleArea.left < (bubble.x - bubble.bubbleR) && rectBubbleArea.top < (bubble.y - bubble.bubbleR) && rectBubbleArea.right > (bubble.x + bubble.bubbleR) && rectBubbleArea.bottom > (bubble.y + bubble.bubbleR)) {
                if (!gameStageActivity.m_bFeverState) {
                    if(300 - gameStageActivity.m_nSeconds*MainActivity.GAMEDIFFICULTY - score - gameStageActivity.m_nCombo/10> 50) {
                        Thread.sleep(300 - gameStageActivity.m_nSeconds*MainActivity.GAMEDIFFICULTY - score - gameStageActivity.m_nCombo/10); // 스레드가 0.5초 동안 잠든다 => 0.5초 간격으로 버블 생성
                        aniNewBubble();
                        arrayList.add(bubble);
                    }else{
                        Thread.sleep(25); // 스레드가 0.5초 동안 잠든다 => 0.5초 간격으로 버블 생성
                        aniNewBubble();
                        arrayList.add(bubble);
                    }
                }else{
                    Thread.sleep(25);
                    aniNewBubble();
                    arrayList.add(bubble);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                gameView.invalidate(); // 핸들러를 통해서 화면 갱신
                if (arrayListBubble.size() >= gameStageActivity.m_nBubbleMaxSize) { // 게임 실패시
                    gameStageActivity.m_bGameState = false;
                    if(gameStageActivity.m_bMusicEffectState) rankWriteDialog.musicStart();
                    gameStageActivity.m_musicGameSound.stop();
//                    gameStageActivity.stopTimer();
                    rankWriteDialog.show();
                }

                if(arrayListFeverBubble.size() >= 50)
                    arrayListFeverBubble.clear();
                gameStageActivity.m_bBombBubbleState = gameStageActivity.m_nCombo >= 10 && arrayListBubble.size() >= 10;

            }
        });
    }

    void createBombBubble(){
        try{
            int x = (int) ((Math.random() * rectBubbleArea.width() + rectBubbleArea.left));
            int y = (int) ((Math.random() * rectBubbleArea.height() + rectBubbleArea.top));
            Bubble bubble = new Bubble(x, y, gameStageActivity.m_nBubbleRadius, gameStageActivity, true);
            gameStageActivity.m_NewBubble = bubble;
            if (rectBubbleArea.left < (bubble.x - bubble.bubbleR) && rectBubbleArea.top < (bubble.y - bubble.bubbleR) && rectBubbleArea.right > (bubble.x + bubble.bubbleR) && rectBubbleArea.bottom > (bubble.y + bubble.bubbleR)) {
                if(300 - gameStageActivity.m_nSeconds*MainActivity.GAMEDIFFICULTY - score - gameStageActivity.m_nCombo/10> 50) {
                    Thread.sleep(300 - gameStageActivity.m_nSeconds*MainActivity.GAMEDIFFICULTY - score - gameStageActivity.m_nCombo/10); // 스레드가 0.5초 동안 잠든다 => 0.5초 간격으로 버블 생성
                    aniNewBombBubble();
                    arrayListBubble.add(bubble);
                }else{
                    Thread.sleep(25); // 스레드가 0.5초 동안 잠든다 => 0.5초 간격으로 버블 생성
                    aniNewBombBubble();
                    arrayListBubble.add(bubble);
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                gameView.invalidate(); // 핸들러를 통해서 화면 갱신
                if (arrayListBubble.size() >= gameStageActivity.m_nBubbleMaxSize) { // 게임 실패시
                    gameStageActivity.m_bGameState = false;
                    if(gameStageActivity.m_bMusicEffectState) rankWriteDialog.musicStart();
                    gameStageActivity.m_musicGameSound.stop();
//                    gameStageActivity.stopTimer();
                    rankWriteDialog.show();
                }
                gameStageActivity.m_bBombBubbleState = gameStageActivity.m_nCombo >= 10 && arrayListBubble.size() >= 10;
            }
        });
    }
    void aniNewBubble(){
        gameStageActivity.m_bAnimationState[0] = true;
        for (int i = 0; i < 4; i++) {
            gameStageActivity.m_iAnimationCount[0] = i;
            try {
                Thread.sleep(25);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    gameView.invalidate();
                }
            });

        }
        gameStageActivity.m_bAnimationState[0] = false;
    }
    void aniNewBombBubble(){
        gameStageActivity.m_bAnimationState[2] = true;
        for (int i = 0; i < 4; i++) {
            gameStageActivity.m_iAnimationCount[2] = i;
            try {
                Thread.sleep(25);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    gameView.invalidate();
                }
            });

        }
        gameStageActivity.m_bAnimationState[2] = false;
    }

}
