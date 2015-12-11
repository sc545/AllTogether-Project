import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.bang.RoomInfo;
class ServerSide {
	public static void main(String args[]) throws Exception {
		String rId;
		String rPasswd;
		String rConnect;
		String id;
		String passwd;
		String phnum;
		RoomInfo r;
		int port = Integer.parseInt("1113");
		int times = Integer.parseInt("100");
		ServerSocket ss = new ServerSocket(port);
		int i = 1;
		while( i <= times) {
			int result=0;
			System.out.println("���� Ȱ��ȭ!!");
			Socket s = ss.accept();
			
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();//������ ����
			ObjectInputStream ois = new ObjectInputStream(is);//������ ����
			
			String flag=(String) ois.readObject();
			
			Connection connect=null;
			Statement st=null;
			ResultSet rs=null;
			try{
				Class.forName("com.mysql.jdbc.Driver");
			}catch(ClassNotFoundException ce){
				System.out.println(ce);
				System.out.println("Ŭ���� �ε� �Ұ�");
			}
			
			if(flag.equals("Login")){// �α��� ó���� ���ð��
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				id = (String)ois.readObject(); //���̵� ����;
				System.out.println("���̵� : "+id+" ����");
				passwd = (String)ois.readObject(); //�������.
				System.out.println("��й�ȣ : "+passwd+" ����");
				try{
					connect=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Bang","BangRoot","12345");
					st =connect.createStatement();//sql.Statement �� import �ؾߵ� java.been.Statement �� �ƴ϶�...
					rs= st.executeQuery("select * from user where id='" + id + "'");//user���̺��� ���� id�� ��ġ�ϴ� ���ڵ��ȯ 
					while(rs.next()) {
						rId = rs.getString("id");
						rPasswd = rs.getString("passwd");
						rConnect = rs.getString("connect");
					}
					if(rId.equals(id) && rPasswd.equals(passwd) && rConnect.equals("F")){
						String sql = "UPDATE user SET connect='T' where id='" + rId + "'";//���̵�,��� ��� ��ġ�ϸ� ���������� �ٲ�.
						st.executeUpdate(sql);
						oos.writeObject("ok");
					}else{
						if(rConnect.equals("T")) oos.writeObject("Connecting"); //���� �������� ���̵��� �������̶�� �˸�.
						else if(rId.equals(id)) oos.writeObject("no"); //���̵�� ���������� ����� ���� �������.
						else oos.writeObject("Notmatch"); //���̵� �������� �������.
							
					}
					System.out.println("���̵� : "+rId+" ���");
					System.out.println("��й�ȣ : "+rPasswd+" ���");
				}catch(SQLException se){
					System.out.println(se);
				}finally{
					try{
						if(rs!=null)rs.close();
						if(st!=null)st.close();
						if(connect!=null)connect.close();
					}catch(SQLException se){}
				}
				
				
				System.out.println("Login���᳡");
				s.close();
				++i;
			}
			else if(flag.equals("Member")){// ȸ������ ó���� ���ð��
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				id = (String)ois.readObject(); //���̵� ����;
				System.out.println("���̵� : "+id+" ����");
				passwd = (String)ois.readObject(); //�������.
				System.out.println("��й�ȣ : "+passwd+" ����");
				phnum = (String)ois.readObject(); //����ȣ����.
				try{
					connect=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Bang","BangRoot","12345");
					st = connect.createStatement();
					rs= st.executeQuery("select * from user where id='"
							+ id + "' OR phone_number='"+phnum+"'");
					while(rs.next()) {
							result = 1;
					}
				}catch(SQLException se){
					System.out.println(se);
				}finally{
					if(result==0){
						String sql = "insert into user (id,passwd,phone_number,connect) values ('"+id+"','"+passwd+"','"+phnum+"','T')";
						st.executeUpdate(sql);  //���̵�,���,��ȭ��ȣ DB�� �߰�
						oos.writeObject("ok");
					}else{
						oos.writeObject("no");
					}
					try{
						if(rs!=null)rs.close();
						if(st!=null)st.close();
						if(connect!=null)connect.close();
					}catch(SQLException se){}
				}
				System.out.println("Member���᳡");
				s.close();
				++i;
			}
			else if(flag.equals("Find")){// ȸ������ ó���� ���ð��
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				phnum = (String)ois.readObject(); //����ȣ����.
				System.out.println("��ȭ��ȣ : "+phnum+" ����");
				
				try{
					connect=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Bang","BangRoot","12345");
					st =connect.createStatement();//sql.Statement �� import �ؾߵ� java.been.Statement �� �ƴ϶�...
					rs= st.executeQuery("select * from user where phone_number='"
							+ phnum + "'");
					while(rs.next()) {
						result=1;
						phnum = rs.getString("phone_number");
						rId = rs.getString("id");
						rPasswd = rs.getString("passwd");
					}
					System.out.println("����ȣ : "+phnum+" ���");
					System.out.println("���̵� : "+rId+" ���");
					System.out.println("��й�ȣ : "+rPasswd+" ���");
					
				}catch(SQLException se){
					System.out.println(se);
				}finally{
					try{
						
						if(rs!=null)rs.close();
						if(st!=null)st.close();
						if(connect!=null)connect.close();
					}catch(SQLException se){}
				}
				oos.writeObject(rId);// ã�� ���̵� ������
				oos.writeObject(rPasswd);// ã�� ��� ������
				
				if(result==1){
					oos.writeObject("ok");
				}else{
					oos.writeObject("no");
				}
				System.out.println("Find���᳡");
				s.close();
				++i;
			}
			else if(flag.equals("finish")){
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				id = (String) ois.readObject(); // ������ ���̵� ����
				try{
					connect=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Bang","BangRoot","12345");
					st =connect.createStatement();//sql.Statement �� import �ؾߵ� java.been.Statement �� �ƴ϶�...
					String sql = "UPDATE user SET connect='F' where id='" + id + "'";
					st.executeUpdate(sql);
					System.out.println("������ ���̵� : "+id+" ���!!");
					
				}catch(SQLException se){
					System.out.println(se);
				}finally{
					try{
						
						if(rs!=null)rs.close();
						if(st!=null)st.close();
						if(connect!=null)connect.close();
					}catch(SQLException se){}
				}
				System.out.println("finish���᳡");
				s.close();
				++i;
			
			}
			
	
		}
		ss.close();
	}
}


		
