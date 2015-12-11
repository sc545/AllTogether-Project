package com.example.bang;

import com.example.bang.thread.FindSocketThread;

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

public class Find extends Activity{
	Button btnSearch, btnEsc;
	TextView tvPhnum, tvId, tvPasswd;
	Handler handler;
	public static Find find;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.find);
		
		btnSearch = (Button) findViewById(R.id.btnFindSearch);
		btnEsc = (Button) findViewById(R.id.btnFindEsc);
		tvPhnum = (TextView) findViewById(R.id.tvFindPhnum);
		tvId = (TextView) findViewById(R.id.tvFindId);
		tvPasswd = (TextView) findViewById(R.id.tvFindPasswd);
		
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phnum = tm.getLine1Number();// 사용자 전화번호 가져오기 ex)+821099868404
        final String num = "0"+phnum.substring(3, phnum.length());//국가번호떼고 3번째 인덱스부터 가져와서 앞에다가 0붙이기
        if(phnum!=null && phnum.equals("")){
        	phnum = phnum.substring(phnum.length()-10,phnum.length());
        	phnum = "0"+phnum;
        }
        tvPhnum.setText(num);
		
		find = this;
		
		handler = new Handler();
		
		btnSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FindSocketThread fst = new FindSocketThread(num, tvId, tvPasswd, Find.this);
				fst.start();
			
				
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
    		Toast.makeText(Find.this, "들어올땐 자유지만 나갈땐 아니란다...", Toast.LENGTH_SHORT).show();
  	  }
  	  return false;
  	 }
	

}
