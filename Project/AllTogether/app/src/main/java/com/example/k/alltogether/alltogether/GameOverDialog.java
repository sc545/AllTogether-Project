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
public class GameOverDialog extends Dialog {
    GameStageActivity gameStageActivity;
    ImageButton btnReplay, btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(MainActivity.SIZE == 0)
            setContentView(R.layout.game_over_dialog);
        else
            setContentView(R.layout.game_over_dialog_tab);
        setCanceledOnTouchOutside(false);

        btnReplay = (ImageButton) findViewById(R.id.btnGameOverRePlay);
        btnMain = (ImageButton) findViewById(R.id.btnGameOverMain);

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStageActivity.resetState();
                dismiss();
                gameStageActivity.m_bGameState = true;
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStageActivity.m_musicGameOverSound.stop();
                gameStageActivity.m_bThreadState =false;
                Intent i = new Intent(gameStageActivity.getApplicationContext(), MainActivity.class);
                i.putExtra("MusicBgmState", gameStageActivity.m_bMusicBgmState);
                i.putExtra("MusicEffectState", gameStageActivity.m_bMusicEffectState);
                gameStageActivity.startActivity(i);
                gameStageActivity.finish();
                gameStageActivity.m_musicGameOverSound.stop();
                dismiss();
            }
        });


    }

    public GameOverDialog(Context context, GameStageActivity gameStageActivity) {
        super(context);
        this.gameStageActivity = gameStageActivity;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시

        }
        return false;
    }

}
