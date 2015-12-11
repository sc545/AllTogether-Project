package com.example.bang.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.example.bang.Login;
import com.example.bang.Main;
import com.example.bang.Member;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

public class MemberSocketThread extends Thread{
	static String id;
	static String passwd;
	static String phnum;
	EditText et;
	Handler handler;
	Context co;
//클라이언트에서 아이디와 비번을 담고있어야함. 서버에서는 받기만
	
	public MemberSocketThread(String id, String passwd, String phnum , Context co){
		this.id = id;
		this.passwd = passwd;
		this.phnum = phnum;
		this.co = co;
		handler = new Handler();
	}

	public void run(){
		
		try{
			Socket c;
			if(Login.wifi.isConnected()) {      // wifi가 연결되었을 때
				c = new Socket(LoginSocketThread.WIFI_IP, LoginSocketThread.WIFI_PORT);
			} else {                            // wifi가 연결되지 않았을 때
				c = new Socket(LoginSocketThread.LTE_IP, LoginSocketThread.LTE_PORT);
			}
			InputStream is = c.getInputStream();//데이터 받음
			ObjectInputStream ois = new ObjectInputStream(is);//데이터 받음
			
			OutputStream os = c.getOutputStream(); //데이터 보냄
			ObjectOutputStream oos = new ObjectOutputStream(os);//데이터 보냄
			
			oos.writeObject("Member");// 서버로 회원가입처리라고알림
			
			oos.writeObject(id);// 서버로 아이디 전송
			oos.writeObject(passwd);// 서버로 패스워드 전송
			oos.writeObject(phnum);// 서버로 전화번호 전송
		
			final String result = (String) ois.readObject();

			c.close();

			handler.post(new Runnable() {
				public void run() {
					if (result.equals("ok")){
						Login.loginId=id; // 로그인할 아이디를 넘겨준다
						Intent intent = new Intent(co, Main.class);co.startActivity(intent);
						Member.member.finish();					
					}
					else{
						Toast.makeText(co, "아이디 또는 전화번호가 이미 존재 합니다", Toast.LENGTH_SHORT).show();
					}
				}
			});
				
		}catch(Exception e){}
	}
	
}





