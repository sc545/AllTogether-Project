package com.example.k.alltogether.alltogether;

import android.graphics.Bitmap;

/**
 * Created by K on 2015-11-13.
 */
public class Bubble {
    int x;
    int y;
    int bubbleR, bombBubbleR;
    boolean isBombBubble;
    Bitmap m_btmpImgBubble, m_btmpImgFeverBubble, m_btmpImgBombBubble;
    Bitmap m_btmpBubbleAnimation[][], m_btmpFeverBubbleAnimation[][], m_btmpBombBubbleInAnimation[], m_btmpBombBubbleOutAnimation[];

    public Bubble(int x, int y, int r, GameStageActivity gameStageActivity){ // 일반 버블 생성자
        this.x = x;
        this.y = y;
        this.bubbleR = r;
        m_btmpImgBubble = gameStageActivity.m_btmpImgBubble;
        m_btmpImgFeverBubble = gameStageActivity.m_btmpImgFeverBubble;
        m_btmpImgBombBubble = gameStageActivity.m_btmpImgBombBubble;
        m_btmpBubbleAnimation = gameStageActivity.m_btmpBubbleAnimation;
        m_btmpFeverBubbleAnimation = gameStageActivity.m_btmpFeverBubbleAnimation;
        m_btmpBombBubbleInAnimation = gameStageActivity.m_btmpBombBubbleInAnimation;
        m_btmpBombBubbleOutAnimation = gameStageActivity.m_btmpBombBubbleOutAnimation;
    }

    public Bubble(int x, int y, int r, GameStageActivity gameStageActivity, boolean isBombBubble){ // 폭탄 버블 생성자
        this.x = x;
        this.y = y;
        this.bubbleR = r;
        bombBubbleR = r*3;
        this.isBombBubble = isBombBubble;
        m_btmpImgBubble = gameStageActivity.m_btmpImgBubble;
        m_btmpImgFeverBubble = gameStageActivity.m_btmpImgFeverBubble;
        m_btmpImgBombBubble = gameStageActivity.m_btmpImgBombBubble;
        m_btmpBubbleAnimation = gameStageActivity.m_btmpBubbleAnimation;
        m_btmpFeverBubbleAnimation = gameStageActivity.m_btmpFeverBubbleAnimation;
        m_btmpBombBubbleInAnimation = gameStageActivity.m_btmpBombBubbleInAnimation;
        m_btmpBombBubbleOutAnimation = gameStageActivity.m_btmpBombBubbleOutAnimation;
    }

    /*
        화면 터치시 터치한 좌표가 버블 안인지 밖인지 판별해주는 함수
    */
    public boolean contains(int x, int y){
        return Math.pow(this.x-x, 2)+Math.pow(this.y-y, 2) <= Math.pow(bubbleR, 2);
    }

    /*
        폭탄 버블 터치시 주변 버블 위치를 판별해주는 함수
    */
    public boolean bombContains(int x, int y){
        return Math.pow(this.x-x, 2)+Math.pow(this.y-y, 2) <= Math.pow(bombBubbleR, 2);
    }
}
