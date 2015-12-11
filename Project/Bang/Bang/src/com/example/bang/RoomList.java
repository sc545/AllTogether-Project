package com.example.bang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class RoomList extends Activity{
    Button btnCre, btnBack, btnRoom1, btnSet;
    DialogRoomListCre dialogRoomListCre;
    Setting setting;
    Music m = Main.m;
    Handler handler = new Handler();
    public static RoomList roomList;

    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.room_list);
        roomList = this;


        btnRoom1 = (Button)findViewById(R.id.btnRoomList1);
        btnCre = (Button)findViewById(R.id.btnRoomListCre);
        btnBack = (Button)findViewById(R.id.btnRoomListBack);
        btnSet = (Button)findViewById(R.id.btnRoomListSet);
        dialogRoomListCre = new DialogRoomListCre(this);
        setting = new Setting(this);
        btnSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setting.activNum = 2;
                setting.show();

            }
        });

        btnCre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	dialogRoomListCre.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });


        btnRoom1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainStage4.class);

                startActivity(intent);
            }
        });
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
    		Toast.makeText(RoomList.this, "들어올땐 자유지만 나갈땐 아니란다...", Toast.LENGTH_SHORT).show();
    	}
  	  return false;
  	}


}