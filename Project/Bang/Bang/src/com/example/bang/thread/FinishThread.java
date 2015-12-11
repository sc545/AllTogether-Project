package com.example.bang.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.bang.Login;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class FinishThread extends Thread{
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
			
			oos.writeObject("finish");// 서버로 종료 처리라고알림
			oos.writeObject(Login.loginId); // 현재 로그인중인 아이디를 넘김 
			c.close();
		}
		catch (Exception e) {}
		

	}

}



