package com.example.bang.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.bang.Login;
import com.example.bang.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindSocketThread extends Thread{
	
	String phnum;
	TextView tvId, tvPasswd;
	Handler handler;
	Context co;
	static String server;
	
	public FindSocketThread(String phnum, TextView tvId, TextView tvPasswd, Context co) {
		this.phnum = phnum;
		this.tvId = tvId;
		this.tvPasswd = tvPasswd;
		this.co = co;
		handler = new Handler();
	}

	public void run() {
		
		try {
			Socket c;
			if(Login.wifi.isConnected()) {      // wifi가 연결되었을 때
				c = new Socket(LoginSocketThread.WIFI_IP, LoginSocketThread.WIFI_PORT);
			} else {                            // wifi가 연결되지 않았을 때
				c = new Socket(LoginSocketThread.LTE_IP, LoginSocketThread.LTE_PORT);
			}
			
			InputStream is = c.getInputStream();// 데이터 받음
			ObjectInputStream ois = new ObjectInputStream(is);// 데이터 받음
			
			OutputStream os = c.getOutputStream(); // 데이터 보냄
			ObjectOutputStream oos = new ObjectOutputStream(os);// 데이터 보냄
			
			oos.writeObject("Find");// 서버로 아이디찾기 처리라고알림
			
			oos.writeObject(phnum);// 서버로 전화번호 전송
			final String id = (String) ois.readObject();// 찾은 아이디 받음
			final String passwd = (String) ois.readObject();// 찾은 비번 받음
			
			final String result = (String) ois.readObject();
			c.close();
	

			handler.post(new Runnable() {
				public void run() {
					if (result.equals("ok")) {
						tvId.setText(id);
						tvPasswd.setText(passwd);
					} else {
						Toast.makeText(co, "존재하지않는 폰번호 입니다", Toast.LENGTH_SHORT).show();
					}
				}
			});

		}
		catch (Exception e) {
		}
		
		

	}

}


