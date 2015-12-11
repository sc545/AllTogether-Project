package com.example.k.alltogether.alltogether;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.k.alltogether.R;

import java.util.regex.Pattern;


/**
 * Created by K on 2015-10-15.
 */
public class RankWriteDialog extends Dialog {
    GameStageActivity gameStageActivity;
    ImageButton btnCancel, btnRegister;
    EditText etName;
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(MainActivity.SIZE == 0)
            setContentView(R.layout.rank_write_dialog);
        else
            setContentView(R.layout.rank_write_dialog_tab);

        setCanceledOnTouchOutside(false);

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnRegister = (ImageButton) findViewById(R.id.btnRegister);
        etName = (EditText) findViewById(R.id.etName);
        tvScore = (TextView) findViewById(R.id.tvScore);

        InputFilter filterAlphaNum = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                if (!ps.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };
        InputFilter filterArray = new InputFilter.LengthFilter(6);
        etName.setFilters(new InputFilter[]{filterAlphaNum, filterArray});

        tvScore.setText(""+gameStageActivity.m_nScore);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                gameStageActivity.m_dlgGameOverDialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etName.getText().toString().equals("")) {
                    RankWriteThread rankWriteThread = new RankWriteThread(gameStageActivity.getApplicationContext(), etName.getText().toString(), gameStageActivity.m_nScore);
                    rankWriteThread.start();
                    etName.setText("");
                    dismiss();
                    gameStageActivity.m_dlgGameOverDialog.show();
                }else{
                    Toast.makeText(gameStageActivity.getApplicationContext(), "이니셜을 입력 해주세요!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void musicStart(){
        gameStageActivity.m_musicGameOverSound.start();
    }

    public RankWriteDialog(Context context, GameStageActivity gameStageActivity) {
        super(context);
        this.gameStageActivity = gameStageActivity;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시

        }
        return false;
    }

}
