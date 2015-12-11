package com.example.bang;
import com.example.bang.*;
import com.example.bang.thread.RoomListCreThread;

import android.R.integer;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class DialogRoomListCre extends Dialog implements View.OnClickListener  {
    Button btnYes,btnNo;
    RadioButton rbNum4, rbNum5, rbNum6;
    int roomNum = 0;
    static EditText etName,etPasswd;
    public static DialogRoomListCre dialogRoomListCre;
    Context co;
    protected void onCreate(Bundle savedInstanceState){
    	dialogRoomListCre=this;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_room_list_cre);
        setCanceledOnTouchOutside(false);//
        btnYes = (Button)findViewById(R.id.btnDialRoomYes);
        btnYes.setOnClickListener(this);
        btnNo = (Button)findViewById(R.id.btnDialRoomNo);
        btnNo.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.etDialRoomName);
        etPasswd = (EditText) findViewById(R.id.etDialRoomPasswd);
        rbNum4 = (RadioButton) findViewById(R.id.rbDialNum4);
        rbNum4.setOnClickListener(this);
        rbNum5 = (RadioButton) findViewById(R.id.rbDialNum5);
        rbNum5.setOnClickListener(this);
        rbNum6 = (RadioButton) findViewById(R.id.rbDialNum6);
        rbNum6.setOnClickListener(this);

    }

    public DialogRoomListCre(Context context){
    	super(context);
    	this.co=context;
    }

    public void onClick(View view){
        if(view == rbNum4) roomNum=4;
        if(view == rbNum5) roomNum=5;
        if(view == rbNum6) roomNum=6;
    	if(view == btnYes){
        	RoomListCreThread rlct = new RoomListCreThread(etName.getText().toString(), etPasswd.getText().toString(), roomNum, co);
        	rlct.start();
        	dismiss();
        }else if(view == btnNo)
            dismiss();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
    	}
  	  return false;
  	}
}
