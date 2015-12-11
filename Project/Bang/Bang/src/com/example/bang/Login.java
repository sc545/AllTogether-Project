package com.example.bang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.bang.thread.FinishThread;
import com.example.bang.thread.LoginSocketThread;


public class Login extends Activity {
	public static String loginId; // 현재 로그인중인 아이디를 담는 변수
	public static ConnectivityManager con;
	public static NetworkInfo wifi; // 네트워크의 현재 상태를 알려주는 변수
	Button btnJoin, btnFind, btnMember, btnEsc;
	EditText etId;
	EditText etPasswd;
	Handler handler;
	public static Login login;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		// 처음 네트워크 상태 체크
		con = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		btnJoin = (Button) findViewById(R.id.btnLoginJoin);
		btnFind = (Button) findViewById(R.id.btnLoginFind);
		btnMember = (Button) findViewById(R.id.btnLoginMember);
		btnEsc = (Button) findViewById(R.id.btnLoginEsc);
		etId = (EditText) findViewById(R.id.etLoginId);
		etPasswd = (EditText) findViewById(R.id.etLoginPasswd);
		
		login = this;
		handler = new Handler();
		

		btnJoin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				
				IdFiltering ift = new IdFiltering();
				if (ift.getSpaceCheck(etId.getText().toString())
						|| etId.getText().toString().equals("")
						|| ift.getSpaceCheck(etPasswd.getText().toString())
						|| etPasswd.getText().toString().equals(""))  // 공백 체크 조건문
				{ 
					Toast.makeText(Login.this, "공백이 있어요", Toast.LENGTH_SHORT).show();
				}
				else{
					LoginSocketThread lst = new LoginSocketThread(etId.getText().toString(), etPasswd.getText().toString(), Login.this);
					lst.start();
				}
			}
		});
		
		btnFind.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Login.this, Find.class);startActivity(intent);
			}
		});
		
		btnMember.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Login.this, Member.class);startActivity(intent);
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
    		Toast.makeText(Login.this, "들어올땐 자유지만 나갈땐 아니란다...", Toast.LENGTH_SHORT).show();
  	  }
  	  return false;
  	 }
}