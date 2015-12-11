package com.example.bang;

import com.example.bang.thread.FinishThread;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;


public class Setting extends Dialog implements View.OnClickListener {
	int activNum;
    Button btnOk, btnLogOut, btnQuit;
    RadioButton rbtnBgmOn, rbtnBgmOff, rbtnEffOn, rbtnEffOff;
    Music m = Main.m;
    FinishThread ft = new FinishThread();
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.setting);
        setCanceledOnTouchOutside(false);
        
        btnOk = (Button) findViewById(R.id.btnSetOk);
        btnOk.setOnClickListener(this);
        btnLogOut = (Button) findViewById(R.id.btnSetLogOut);
        btnLogOut.setOnClickListener(this);
        btnQuit = (Button) findViewById(R.id.btnSetQuit);
        btnQuit.setOnClickListener(this);
        rbtnBgmOn=(RadioButton) findViewById(R.id.rbtnSetBgmOn);
        rbtnBgmOn.setOnClickListener(this);
        rbtnBgmOff=(RadioButton) findViewById(R.id.rbtnSetBgmOff);
        rbtnBgmOff.setOnClickListener(this);
        rbtnEffOn=(RadioButton) findViewById(R.id.rbtnSetEffOn);
        rbtnEffOn.setOnClickListener(this);
        rbtnEffOff=(RadioButton) findViewById(R.id.rbtnSetEffOff);
        rbtnEffOff.setOnClickListener(this);
        
        	switch (activNum) {
        	case 5:case 4:case 3:
        			btnLogOut.setVisibility(View.GONE);
                break;
        	}
        
    }
    protected void onStart(){ // 다이얼로그 켜질때마다 음악이 실행중인지를 비교해서 on off 버튼 체크
    	if(m.isPlaying())
    		rbtnBgmOn.setChecked(true);
    	else
    		rbtnBgmOff.setChecked(true);
    }
   

    public Setting(Context context) {
    	super(context);
    	
    }

    public void onClick(View view) {
        if (view == btnOk) {
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
        }
        else if (view == btnQuit){
        	m.stop();
        	ft.start(); // 어플종료시 로그인중인 아이디 상태값 F 로 변경
			switch(activNum){ // 셋팅창이 켜진 위치에 따라서 액티비티를 종료해줌
			case 5:case 4:case 3: 
				switch(activNum){
				case 5:MainStage6.mainStage.finish(); break;
				case 4:MainStage5.mainStage.finish(); break;
				case 3:MainStage4.mainStage.finish();
				}
			case 2: RoomList.roomList.finish();
			case 1: Main.main.finish();
			case 0: Login.login.finish();
			}
            dismiss();
        }            
        else if (view == btnLogOut) {
        	m.stop(); 
        	ft.start(); // 로그아웃시 로그인중인 아이디 상태값 F 로 변경
			switch(activNum){ // 셋팅창이 켜진 위치에 따라서 액티비티를 종료해줌
			case 5:case 4:case 3: 
				switch(activNum){
				case 5:MainStage6.mainStage.finish(); break;
				case 4:MainStage5.mainStage.finish(); break;
				case 3:MainStage4.mainStage.finish();
				}
			case 2: RoomList.roomList.finish();
			case 1: Main.main.finish();
			}
            dismiss();
        }
        else if(view == rbtnEffOn){
        
        }else if(view == rbtnEffOff){
        
        }else if(view == rbtnBgmOn){
        	m.start();
        }
        else if(view == rbtnBgmOff){
        	m.pause();
        }

    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
    	}
  	  return false;
  	}

		
}

