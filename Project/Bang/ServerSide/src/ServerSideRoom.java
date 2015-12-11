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
			System.out.println("�� ���� ���� Ȱ��ȭ!!");
			Socket s = ss.accept();
			
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();//������ ����
			ObjectInputStream ois = new ObjectInputStream(is);//������ ����
			
			String flag=(String) ois.readObject();
			
			if(flag.equals("RoomCre")){// ����� ó���� ���ð��
				System.out.println("�����");
				RoomInfo ri = (RoomInfo) ois.readObject();// �� ���� ��ü ����
				System.out.println("��  ���� : "+ri.roomName);
				System.out.println("��й�ȣ : "+ri.roomPasswd);
				System.out.println("��    �� : "+ri.roomNum);
			
				if(true){
					oos.writeObject("ok");
				}else
					oos.writeObject("no");
				
				System.out.println("RoomCre���᳡");
				
				s.close();
				++i;
			}
			
			
	
		}
		ss.close();
	}
}
