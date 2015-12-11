package com.example.bang;

import android.content.Context;
import android.media.MediaPlayer;

public class Music{
	MediaPlayer mp = null;
	
	Music(Context co){
		mp = MediaPlayer.create(co, R.raw.billy);
		//mp.setLooping(true);
	}
	
	void start(){
		mp.start();
	}
	
	void pause(){
		mp.pause();
	}
	
	void stop(){
		mp.stop();
	}
	
	boolean isPlaying(){
		return mp.isPlaying();
	}
	
}
