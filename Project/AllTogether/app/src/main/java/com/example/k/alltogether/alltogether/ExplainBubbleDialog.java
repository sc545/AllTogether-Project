package com.example.k.alltogether.alltogether;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.k.alltogether.R;

/**
 * Created by K on 2015-11-25.
 */
public class ExplainBubbleDialog extends Dialog{
    ImageButton btnExit, btnLeft;
    HowToDialog howToDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.popup_bubble_layout);

        btnExit = (ImageButton) findViewById(R.id.btnExit);
        btnLeft = (ImageButton) findViewById(R.id.btnLeft);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

    }

    ExplainBubbleDialog(Context context){
        super(context);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
            dismiss();
        }
        return false;
    }
}
