package com.example.bang.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bang.Login;
import com.example.bang.Main;

public class LoginSocketThread extends Thread {
	public static final String WIFI_IP = "192.168.219.100";// 집 내부 192.168.219.100 /외부 182.214.230.135 동방 192.168.51.141 경배 192.168.51.143
	public static final String LTE_IP = "182.214.230.135";
	public static final int WIFI_PORT = 1113; // 와이파이->3G, 3G->와이파이, 와이파이->다른와이파이 경우의 수를 생각해줘야됨.
	public static final int LTE_PORT = 4661;
	String id, passwd;
	EditText et;
	Handler handler;
	Context co;
	static String server;
	
	public LoginSocketThread(String id, String passwd, Context co) {
		this.id = id;
		this.passwd = passwd;
		this.co = co;
		handler = new Handler();
	}

	public void run() {
		
		try {
			Socket c;
			if(Login.wifi.isConnected()) {      // wifi가 연결되었을 때
				c = new Socket(WIFI_IP, WIFI_PORT);
			} else {                            // wifi가 연결되지 않았을 때
				c = new Socket(LTE_IP, LTE_PORT);
			}
			
			InputStream is = c.getInputStream();// 데이터 받음
			ObjectInputStream ois = new ObjectInputStream(is);// 데이터 받음
			
			OutputStream os = c.getOutputStream(); // 데이터 보냄
			ObjectOutputStream oos = new ObjectOutputStream(os);// 데이터 보냄
			
			oos.writeObject("Login");// 서버로 로그인처리라고알림
			
			oos.writeObject(id);// 서버로 아이디 전송
			oos.writeObject(passwd);// 서버로 비밀번호 전송
			
			final String result = (String) ois.readObject();

			c.close();

			handler.post(new Runnable() {
				public void run() {
					if (result.equals("ok")) {
						Login.loginId=id; // 로그인할 아이디를 넘겨준다
						Intent intent = new Intent(co, Main.class);co.startActivity(intent);
					} else if(result.equals("Connecting")){
						Toast.makeText(co, "현재 접속 중인 아이디 입니다", Toast.LENGTH_SHORT).show();
					} else if(result.equals("Notmatch")){
						Toast.makeText(co, "존재하지 않는 아이디 입니다", Toast.LENGTH_SHORT).show();
					} else
						Toast.makeText(co, "아이디 또는 암호가 틀렸습니다", Toast.LENGTH_SHORT).show();
				}
			});

		}
		catch (Exception e) {
		}
		
		

	}

	

}

/*

네트워크 작업을 하기 직전에 인터넷 연결이 가능한 상태를 확인하면 불필요한 작업을 줄일 수 있다


인터넷 연결이 가능한지는 다음과 같은 코드로 알아낼 수 있다


  ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

 NwtworkInfo activeNetwork = cm.getActivityNetworkInfo();

 boolean isConnected = false;


 if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

   isConnected = true;

 }
 


연결 상태를 얻어오기 위해서는 AndroidManifest..xml에 다음과 같은 권한을 사용하겠다고 선언해야 한다 


  <uses-permission android:name="android.permisson.ACCESS_NETWORK_STATE"/>
 

통신 상태의 변화는 브로드캐스트로 알려지게 되므로 브로드캐스트 리시버를 준비해두면 통신 상태의 변화에 대해 모니터링할 수 있다 (CONNECTIVITY_CHANGE)


////////////////////////////////////////////////////////////////////////////////////////////////////


getActiveNetworkInfor() 매개변수로 어떤 네트워크 디바이스 상태를 알고 싶은지

함께 보내줘야지만 값을 얻어 옵니다.

 

3g, 4g는 ConnectivityManager.TYPE_MOBILE을 설정해야합니다.

 

getActiveNetworkInfor(ConnectivityManager.TYPE_MOBILE);

 

만약 와이파이라면

 

getActiveNetworkInfor(ConnectivityManager.TYPE_WIFI);

 

라고 해줘야지만 상태 정보를 가져옵니다.


////////////////////////////////////////////////////////////////////////////////////////////////////

Android의 ConnectivitiyManager를 사용하시면 됩니다. 



ConnectivityManager con = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

NetworkInfo wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);




if(wifi.isConnected()) {

// wifi가 연결되었을 때

} else {

// wifi가 연결되지 않았을 때

}

*/