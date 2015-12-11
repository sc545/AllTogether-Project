package com.example.donghyeon.bang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class Setting extends Dialog implements View.OnClickListener {
    int activNum;
    Button btnok, btnLogOut, btnQuit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.setting);
        setCanceledOnTouchOutside(false);

        btnok = (Button) findViewById(R.id.btnok);
        btnok.setOnClickListener(this);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(this);
        switch (activNum) {
            case 3:
                btnLogOut.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public Setting(Context context) {
        super(context);
    }

    public void onClick(View view) {
        if (view == btnok) {
            switch (activNum){
                case 1:
                    dismiss();
                    break;
                case 2:
                    dismiss();
                    break;
                case 3:
                    dismiss();
                    break;
            }
        } else if (view == btnQuit)
            dismiss();
        else if (view == btnLogOut) {
            Intent intent = new Intent(MainActivity.mainActivity.getApplicationContext(), Login.class);
            MainActivity.mainActivity.startActivity(intent);
            dismiss();

        }

    }
}

