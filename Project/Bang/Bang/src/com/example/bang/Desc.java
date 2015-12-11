package com.example.bang;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ViewFlipper;

public class Desc extends Activity {
    Button btnPrev, btnNext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.desc);

        final ViewFlipper vFlip1;

        btnPrev = (Button)findViewById(R.id.btnDescPrev);
        btnNext = (Button)findViewById(R.id.btnDescNext);
        vFlip1 = (ViewFlipper)findViewById(R.id.viewFlipper1);
        
        btnPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vFlip1.showPrevious();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vFlip1.showNext();
            }
        });



    }
}