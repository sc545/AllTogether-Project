package com.example.bang;

import com.example.bang.thread.FinishThread;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class Main extends Activity {
    Button btnSet, btnStart, btnDesc, btnCredit, btnExit,btn ;
    Setting setting;
    
	private int keyCode;
    public static Main main;
    public static Music m ;
    
    
    protected void onCreate(Bundle savedInstanceState) {
    	m = new Music(Main.this);
		m.start();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        main = this;

        btnSet = (Button)findViewById(R.id.btnMainSet);
        btnStart = (Button)findViewById(R.id.btnMainStart);
        btnDesc = (Button)findViewById(R.id.btnMainDesc);
        btnCredit = (Button)findViewById(R.id.btnMainCredit);
        btnExit = (Button)findViewById(R.id.btnMainExit);
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
            	m.stop();
            	FinishThread ft = new FinishThread(); 
				ft.start();
            	finish();
            	Login.login.finish();
            }
        });


    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
    		Toast.makeText(Main.this, "들어올땐 자유지만 나갈땐 아니란다...", Toast.LENGTH_SHORT).show();
  	  }
  	  return false;
  	 }

}
//
//public boolean onKeyDown(int keyCode, KeyEvent event) {
//	  if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
//	   dialog.show();
//	  }
//	  return false;
//	 }