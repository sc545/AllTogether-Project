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
import android.widget.ViewFlipper;

import com.example.k.alltogether.R;

/**
 * Created by 5-3COM- on 2015-11-18.
 */
public class HowToDialog extends Dialog {
    ImageButton btnExit, btnLeft, btnRight;
    ViewFlipper vfHowTo;
    ExplainBubbleDialog explainBubbleDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(MainActivity.SIZE == 0) {
            setContentView(R.layout.how_to_dialog);

            btnExit = (ImageButton) findViewById(R.id.btnExit);
            btnLeft = (ImageButton) findViewById(R.id.btnLeft);
            btnRight = (ImageButton) findViewById(R.id.btnRight);
            vfHowTo = (ViewFlipper) findViewById(R.id.vfHowTo);

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnLeft.setVisibility(View.INVISIBLE);
                    btnRight.setVisibility(View.VISIBLE);
                    vfHowTo.showPrevious();
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnRight.setVisibility(View.INVISIBLE);
                    btnLeft.setVisibility(View.VISIBLE);
                    vfHowTo.showNext();
                }
            });
        }
        else {
            setContentView(R.layout.bubble_pang_main);

            btnExit = (ImageButton) findViewById(R.id.btnExit);
            btnRight = (ImageButton) findViewById(R.id.btnRight);

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    explainBubbleDialog.show();
                }
            });
        }




    }

    HowToDialog(Context context){
        super(context);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
            dismiss();
        }
        return false;
    }
}
