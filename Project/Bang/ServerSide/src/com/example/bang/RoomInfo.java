package com.example.bang;

import java.io.Serializable;

public class RoomInfo implements Serializable{
	
	/**
	 * Hi, Hello New World !! 
	 */
	private static final long serialVersionUID = 1L;
	public String roomName;
	public String roomPasswd;
	public int roomNum;
	public RoomInfo(String roomName, String roomPasswd, int roomNum){
		this.roomName = roomName;
		this.roomPasswd = roomPasswd;
		this.roomNum = roomNum;
	}



}
