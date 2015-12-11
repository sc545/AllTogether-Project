package com.example.donghyeon.bang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainStage extends Activity {
    Button  btnSet;

    Setting setting;
    public static MainStage mainStage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_stage);
        mainStage = this;



        btnSet = (Button)findViewById(R.id.btnSet);

        setting = new Setting(this);

        btnSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setting.activNum = 3;
                setting.show();

            }
        });


    }

}