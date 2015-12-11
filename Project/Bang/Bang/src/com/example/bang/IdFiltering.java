package com.example.bang;


public class IdFiltering extends Member {

	   boolean getSpaceCheck(String s) {
	      char sp = ' ';
	      for (int i = 0; i < s.length(); i++) {
	         if (sp == (s.charAt(i))) {
	            return true; // 스페이가 있으면 true 반환
	         }
	      }

	      return false; // 없으면 false 반환
	   }
	}
