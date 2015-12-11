package com.example.bang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainStage5 extends Activity {
    boolean isPageOpen = false;
    int roomId;
    EditText etName = DialogRoomListCre.etName;
    EditText etPasswd = DialogRoomListCre.etPasswd;
    
    // static max10, 0key5,uket5 현재 방갯수는 서버에서관리하기
    // 방제목 비밀번호 인원수 key유무 게임내 접속인원수 && 다왔는가 체크
    
	Button  btnSet,btnOpen;
    Setting setting;
    public static MainStage5 mainStage;

    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_stage5);
        mainStage = this;


        
        btnSet = (Button)findViewById(R.id.btnMainStageSet5);

        setting = new Setting(this);

        btnSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setting.activNum = 4;
                setting.show();

            }
        });


    }
    

}