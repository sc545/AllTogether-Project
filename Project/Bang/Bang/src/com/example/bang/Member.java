package com.example.bang;
import com.example.bang.thread.MemberSocketThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.TelephonyManager;
public class Member extends Activity {
    Button btnCre,btnesc,btnEsc;
    TextView tvPhnum;
    EditText etId, etPasswd;
    Handler handler;
    public static Member member;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member);
        
        btnCre = (Button)findViewById(R.id.btnMemberCre);
        btnEsc = (Button)findViewById(R.id.btnMemberEsc);
        etId = (EditText)findViewById(R.id.etMemberId);
        etPasswd = (EditText)findViewById(R.id.etMemberPasswd);
        tvPhnum = (TextView)findViewById(R.id.tvMemberPhnum);
        
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phnum = tm.getLine1Number();// 사용자 전화번호 가져오기 ex)+821099868404
        String num = "0"+phnum.substring(3, phnum.length());//국가번호떼고 3번째 인덱스부터 가져와서 앞에다가 0붙이기
        if(phnum!=null && phnum.equals("")){
        	phnum = phnum.substring(phnum.length()-10,phnum.length());
        	phnum = "0"+phnum;
        }
        tvPhnum.setText(num);
        

        
        member = this;
        handler = new Handler();
        
        
        btnCre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               IdFiltering ift = new IdFiltering();
               if (ift.getSpaceCheck(etId.getText().toString())
						|| etId.getText().toString().equals("")
						|| ift.getSpaceCheck(etPasswd.getText().toString())
						|| etPasswd.getText().toString().equals(""))  // 공백 체크 조건문
               { 
                  Toast.makeText(Member.this, "공백이 있어요",Toast.LENGTH_SHORT).show();
               }
               else{
            	  MemberSocketThread mst = new MemberSocketThread(etId.getText().toString(), etPasswd.getText().toString(), tvPhnum.getText().toString(), Member.this);
            	  mst.start();
               }
            }
         });
      
        btnEsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	finish();
            }
        });
      
        
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
    		Toast.makeText(Member.this, "들어올땐 자유지만 나갈땐 아니란다...", Toast.LENGTH_SHORT).show();
  	  }
  	  return false;
  	 }
}
    

//전화번호가져오는 소스
//TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//
//phnum = systemService.getLine1Number();
//
//if(phnum!=null && phnum.equals("")){
//
//phnum = phnum.substring(phnum.length()-10,phnum.length());
//
//phnum = "0"+phnum;
//
//} 

