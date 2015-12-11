import java.io.InputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.bang.RoomInfo;


public class ServerSideRoom {
	public static void main(String args[]) throws Exception {
		int port = Integer.parseInt("2222");
		int times = Integer.parseInt("100");
		ServerSocket ss = new ServerSocket(port);
		int i = 1;
		while( i <= times) {
			int result=0;
			System.out.println("방 생성 소켓 활성화!!");
			Socket s = ss.accept();
			
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();//데이터 받음
			ObjectInputStream ois = new ObjectInputStream(is);//데이터 받음
			
			String flag=(String) ois.readObject();
			
			if(flag.equals("RoomCre")){// 방생성 처리가 들어올경우
				System.out.println("방생성");
				RoomInfo ri = (RoomInfo) ois.readObject();// 방 정보 객체 받음
				System.out.println("방  제목 : "+ri.roomName);
				System.out.println("비밀번호 : "+ri.roomPasswd);
				System.out.println("인    원 : "+ri.roomNum);
			
				if(true){
					oos.writeObject("ok");
				}else
					oos.writeObject("no");
				
				System.out.println("RoomCre연결끝");
				
				s.close();
				++i;
			}
			
			
	
		}
		ss.close();
	}
}
