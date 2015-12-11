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
			System.out.println("소켓 활성화!!");
			Socket s = ss.accept();
			
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();//데이터 받음
			ObjectInputStream ois = new ObjectInputStream(is);//데이터 받음
			
			String flag=(String) ois.readObject();
			
			Connection connect=null;
			Statement st=null;
			ResultSet rs=null;
			try{
				Class.forName("com.mysql.jdbc.Driver");
			}catch(ClassNotFoundException ce){
				System.out.println(ce);
				System.out.println("클래스 로드 불가");
			}
			
			if(flag.equals("Login")){// 로그인 처리가 들어올경우
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				id = (String)ois.readObject(); //아이디 받음;
				System.out.println("아이디 : "+id+" 받음");
				passwd = (String)ois.readObject(); //비번받음.
				System.out.println("비밀번호 : "+passwd+" 받음");
				try{
					connect=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Bang","BangRoot","12345");
					st =connect.createStatement();//sql.Statement 를 import 해야됨 java.been.Statement 가 아니라...
					rs= st.executeQuery("select * from user where id='" + id + "'");//user테이블에서 받은 id와 일치하는 레코드반환 
					while(rs.next()) {
						rId = rs.getString("id");
						rPasswd = rs.getString("passwd");
						rConnect = rs.getString("connect");
					}
					if(rId.equals(id) && rPasswd.equals(passwd) && rConnect.equals("F")){
						String sql = "UPDATE user SET connect='T' where id='" + rId + "'";//아이디,비번 모두 일치하면 접속중으로 바꿈.
						st.executeUpdate(sql);
						oos.writeObject("ok");
					}else{
						if(rConnect.equals("T")) oos.writeObject("Connecting"); //만약 접속중인 아이디라면 접속중이라고 알림.
						else if(rId.equals(id)) oos.writeObject("no"); //아이디는 존재하지만 비번이 맞지 않을경우.
						else oos.writeObject("Notmatch"); //아이디가 존재하지 않을경우.
							
					}
					System.out.println("아이디 : "+rId+" 출력");
					System.out.println("비밀번호 : "+rPasswd+" 출력");
				}catch(SQLException se){
					System.out.println(se);
				}finally{
					try{
						if(rs!=null)rs.close();
						if(st!=null)st.close();
						if(connect!=null)connect.close();
					}catch(SQLException se){}
				}
				
				
				System.out.println("Login연결끝");
				s.close();
				++i;
			}
			else if(flag.equals("Member")){// 회원가입 처리가 들어올경우
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				id = (String)ois.readObject(); //아이디 받음;
				System.out.println("아이디 : "+id+" 받음");
				passwd = (String)ois.readObject(); //비번받음.
				System.out.println("비밀번호 : "+passwd+" 받음");
				phnum = (String)ois.readObject(); //폰번호받음.
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
						st.executeUpdate(sql);  //아이디,비번,전화번호 DB에 추가
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
				System.out.println("Member연결끝");
				s.close();
				++i;
			}
			else if(flag.equals("Find")){// 회원가입 처리가 들어올경우
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				phnum = (String)ois.readObject(); //폰번호받음.
				System.out.println("전화번호 : "+phnum+" 받음");
				
				try{
					connect=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Bang","BangRoot","12345");
					st =connect.createStatement();//sql.Statement 를 import 해야됨 java.been.Statement 가 아니라...
					rs= st.executeQuery("select * from user where phone_number='"
							+ phnum + "'");
					while(rs.next()) {
						result=1;
						phnum = rs.getString("phone_number");
						rId = rs.getString("id");
						rPasswd = rs.getString("passwd");
					}
					System.out.println("폰번호 : "+phnum+" 출력");
					System.out.println("아이디 : "+rId+" 출력");
					System.out.println("비밀번호 : "+rPasswd+" 출력");
					
				}catch(SQLException se){
					System.out.println(se);
				}finally{
					try{
						
						if(rs!=null)rs.close();
						if(st!=null)st.close();
						if(connect!=null)connect.close();
					}catch(SQLException se){}
				}
				oos.writeObject(rId);// 찾은 아이디 보내줌
				oos.writeObject(rPasswd);// 찾은 비번 보내줌
				
				if(result==1){
					oos.writeObject("ok");
				}else{
					oos.writeObject("no");
				}
				System.out.println("Find연결끝");
				s.close();
				++i;
			}
			else if(flag.equals("finish")){
				rId = "empty";rPasswd = "empty";rConnect = "empty";
				id = "empty";passwd = "empty";phnum = "empty";
				id = (String) ois.readObject(); // 종료할 아이디를 받음
				try{
					connect=DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Bang","BangRoot","12345");
					st =connect.createStatement();//sql.Statement 를 import 해야됨 java.been.Statement 가 아니라...
					String sql = "UPDATE user SET connect='F' where id='" + id + "'";
					st.executeUpdate(sql);
					System.out.println("종료할 아이디 : "+id+" 출력!!");
					
				}catch(SQLException se){
					System.out.println(se);
				}finally{
					try{
						
						if(rs!=null)rs.close();
						if(st!=null)st.close();
						if(connect!=null)connect.close();
					}catch(SQLException se){}
				}
				System.out.println("finish연결끝");
				s.close();
				++i;
			
			}
			
	
		}
		ss.close();
	}
}


		
