package com.example.k.alltogether.alltogether;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import com.example.k.alltogether.R;

/**
 * Created by K on 2015-10-15.
 */

public class GamePauseDialog extends Dialog {
    GameStageActivity gameStageActivity;
    ImageButton btnAgain, btnNewGame, btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(MainActivity.SIZE == 0)
            setContentView(R.layout.game_pause_dialog);
        else
            setContentView(R.layout.game_pause_dialog_tab);
        setCanceledOnTouchOutside(false);

        btnAgain = (ImageButton) findViewById(R.id.btnGamePauseAgain);
        btnNewGame = (ImageButton) findViewById(R.id.btnGamePauseNewGame);
        btnMain = (ImageButton) findViewById(R.id.btnGamePauseMain);

        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gameStageActivity.startTimer();
                dismiss();
                if(gameStageActivity.m_bMusicBgmState) gameStageActivity.m_musicGameSound.start();
                gameStageActivity.m_bGameState =true;
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStageActivity.resetState();
                dismiss();
                if(gameStageActivity.m_bMusicBgmState) gameStageActivity.m_musicGameSound.start();
                gameStageActivity.m_bGameState =true;
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStageActivity.m_bThreadState =false;
                Intent i = new Intent(gameStageActivity.getApplicationContext(), MainActivity.class);
                i.putExtra("MusicBgmState", gameStageActivity.m_bMusicBgmState);
                i.putExtra("MusicEffectState", gameStageActivity.m_bMusicEffectState);
                gameStageActivity.startActivity(i);
                gameStageActivity.finish();
                dismiss();
            }
        });
    }

    public GamePauseDialog(Context context, GameStageActivity gameStageActivity) {
        super(context);
        this.gameStageActivity = gameStageActivity;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
//            gameStageActivity.startTimer();
            dismiss();
            gameStageActivity.m_bGameState =true;
        }
        return false;
    }
}
