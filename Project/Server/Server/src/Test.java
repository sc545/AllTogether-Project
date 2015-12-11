import java.io.UnsupportedEncodingException;

import com.mysql.jdbc.log.Log;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		charSet("せせせせ");

	}
	
	public static void charSet(String str_kr) throws UnsupportedEncodingException {
		String charset[] = {"euc-kr", "ksc5601", "iso-8859-1", "8859_1", "ascii", "UTF-8"};
		
		for(int i=0; i<charset.length; i++){
			for(int j=0; i<charset.length; j++){
				if(i==j) continue;
				System.out.println(charset[i]+" :"+new String(str_kr.getBytes(charset[i]), charset[j]));
			}
		}

		
	}

}
