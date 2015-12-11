package com.example.bang.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.example.bang.DialogRoomListCre;
import com.example.bang.Login;
import com.example.bang.Main;
import com.example.bang.MainStage4;
import com.example.bang.MainStage5;
import com.example.bang.MainStage6;
import com.example.bang.Member;
import com.example.bang.RoomInfo;

public class RoomListCreThread extends Thread {
		final public static int WIFI_ROOM_PORT=2222;
		final public static int LTE_ROOM_PORT=4663;
		RoomInfo ri;
		Handler handler;
		Context co;
		public RoomListCreThread(String roomName, String roomPasswd, int roomNum, Context co){
			ri = new RoomInfo(roomName, roomPasswd, roomNum);
			this.co = co;
			handler = new Handler();
		}

		public void run(){
			
			try{
				Socket c;
				if(Login.wifi.isConnected()) {      // wifi가 연결되었을 때
					c = new Socket(LoginSocketThread.WIFI_IP, WIFI_ROOM_PORT);
				} else {                            // wifi가 연결되지 않았을 때
					c = new Socket(LoginSocketThread.LTE_IP, LTE_ROOM_PORT);
				}
				InputStream is = c.getInputStream();//데이터 받음
				ObjectInputStream ois = new ObjectInputStream(is);//데이터 받음
				
				OutputStream os = c.getOutputStream(); //데이터 보냄
				ObjectOutputStream oos = new ObjectOutputStream(os);//데이터 보냄
				
				oos.writeObject("RoomCre"); // 방생성 처리라고 알림
				oos.writeObject(ri); // 방 정보 객체 보냄
				
				final String result = (String) ois.readObject();

				c.close();

				handler.post(new Runnable() {
					public void run() {
						if (result.equals("ok")){
							Intent intent;
							switch(ri.roomNum){
							case 4: intent = new Intent(co, MainStage4.class);co.startActivity(intent); break;
							case 5: intent = new Intent(co, MainStage5.class);co.startActivity(intent); break;
							case 6: intent = new Intent(co, MainStage6.class);co.startActivity(intent); break;
							}
							DialogRoomListCre.dialogRoomListCre.dismiss();
						}
						else{
							Toast.makeText(co, "방제목이 이미 있습니다", Toast.LENGTH_SHORT).show();
						}
					}
				});
					
			}catch(Exception e){}
	

		}
}
	
