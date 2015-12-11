package com.example.donghyeon.bang;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends Activity {
    Button btnSet, btnStart, btnDesc, btnCredit, btnExit,btn;
    Setting setting;
    public static MainActivity mainActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        btnSet = (Button)findViewById(R.id.btnSet);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnDesc = (Button)findViewById(R.id.btnDesc);
        btnCredit = (Button)findViewById(R.id.btnCredit);
        btnExit = (Button)findViewById(R.id.btnExit);
        setting = new Setting(this);


        btnSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setting.activNum = 1;
                setting.show();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoomList.class);

                startActivity(intent);
            }
        });

        btnDesc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Desc.class);

                startActivity(intent);

            }
        });

        btnCredit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Credit.class);

                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });




    }

}
