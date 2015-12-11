package com.example.k.alltogether.alltogether;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.k.alltogether.R;

/**
 * Created by K on 2015-11-18.
 */
public class SettingDialog extends Dialog {
    MainActivity mainActivity;
    ImageButton btnExit;
    ToggleButton tbBgm, tbEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(MainActivity.SIZE == 0)
            setContentView(R.layout.setting_dialog);
        else
            setContentView(R.layout.setting_dialog_tab);

        btnExit = (ImageButton) findViewById(R.id.btnExit);
        tbBgm = (ToggleButton) findViewById(R.id.tbBgm);
        tbEffect = (ToggleButton) findViewById(R.id.tbEffect);

        if(mainActivity.m_bMusicBgmState) tbBgm.setBackgroundResource(R.drawable.on);
        else tbBgm.setBackgroundResource(R.drawable.off);
        if(mainActivity.m_bMusicBgmState) tbEffect.setBackgroundResource(R.drawable.on);
        else tbEffect.setBackgroundResource(R.drawable.off);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tbBgm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mainActivity.m_bMusicEffectState) mainActivity.m_musicButtonSound.spStart();
                if (isChecked) {
                    tbBgm.setBackgroundResource(R.drawable.on);
                    mainActivity.musicBgmStart();
                } else {
                    tbBgm.setBackgroundResource(R.drawable.off);
                    mainActivity.musicBgmStop();
                }
            }
        });

        tbEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mainActivity.m_bMusicEffectState) mainActivity.m_musicButtonSound.spStart();
                if (isChecked) {
                    tbEffect.setBackgroundResource(R.drawable.on);
                    mainActivity.musicEffectStart();
                } else {
                    tbEffect.setBackgroundResource(R.drawable.off);
                    mainActivity.musicEffectStop();
                }
            }
        });
    }

    SettingDialog(Context context, MainActivity mainActivity){
        super(context);
        this.mainActivity = mainActivity;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
            dismiss();
        }
        return false;
    }
}
