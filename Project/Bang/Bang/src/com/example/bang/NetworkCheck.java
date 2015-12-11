package com.example.bang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkCheck extends BroadcastReceiver{
	String action;
	@Override
	public void onReceive(Context context, Intent intent){
		// TODO Auto-generated method stub
		action = intent.getAction();
	         
		if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
			//네트워크 변화가 생길경우에 처리할 코드 삽입
			Login.wifi = Login.con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			// 네트워크가 변경될 때 마다 네트워크 타입이 와이파이 인지를 체크해서 갱신해준다 
			Toast.makeText(context, "네트워크가 변경 되었습니다...", Toast.LENGTH_SHORT).show();
		}
	}
}
	


