package com.example.k.alltogether.alltogether;

import android.os.Handler;

/**
 * Created by K on 2015-11-14.
 */
public class ComboMissThread extends Thread {
    Handler handler; // 핸들러 사용 이유 : 메인스레드를 제외하고는 View 를 건드릴 수 없기 때문에 핸들러를 통해서 화면 갱신 => invalidate()
    GameStageActivity gameStageActivity;
    public ComboMissThread(GameStageActivity gameStageActivity){
        handler = new Handler();
        this.gameStageActivity = gameStageActivity;
    }
    public void run(){
        gameStageActivity.m_bComboMissState = true;
        try {
            Thread.sleep(100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        int c=0;
        while(c<2) {
            int x = (int) ((Math.random() * gameStageActivity.m_rectBubbleArea.width() + gameStageActivity.m_rectBubbleArea.left));
            int y = (int) ((Math.random() * gameStageActivity.m_rectBubbleArea.height() + gameStageActivity.m_rectBubbleArea.top));
            Bubble missBubble = new Bubble(x, y, gameStageActivity.m_nBubbleRadius, gameStageActivity);
            if (gameStageActivity.m_rectBubbleArea.left < (missBubble.x - missBubble.bubbleR) && gameStageActivity.m_rectBubbleArea.top < (missBubble.y - missBubble.bubbleR) && gameStageActivity.m_rectBubbleArea.right > (missBubble.x + missBubble.bubbleR) && gameStageActivity.m_rectBubbleArea.bottom > (missBubble.y + missBubble.bubbleR)) {
                gameStageActivity.m_arraylistBubble.add(missBubble);
                c++;
            }
        }
        c=0;
        handler.post(new Runnable() {
            @Override
            public void run() {
                gameStageActivity.gameView.invalidate();
            }
        });
        gameStageActivity.m_bComboMissState = false;
    }
}
