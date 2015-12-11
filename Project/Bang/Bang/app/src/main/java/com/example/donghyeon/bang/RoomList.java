package com.example.donghyeon.bang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class RoomList extends Activity{
    Button btnCreRoom, btnBack, btnRoom1, btnSet;
    DialogCreRoom dialogCreRoom;
    Setting setting;
    public static RoomList roomList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.room_list);
        roomList = this;


        btnRoom1 = (Button)findViewById(R.id.btnRoom1);
        btnCreRoom = (Button)findViewById(R.id.btnCreRoom);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnSet = (Button)findViewById(R.id.btnSet);
        dialogCreRoom = new DialogCreRoom(this);
        setting = new Setting(this);

        btnSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setting.activNum = 2;
                setting.show();

            }
        });

        btnCreRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogCreRoom.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });


        btnRoom1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainStage.class);

                startActivity(intent);
            }
        });
    }


}
