package com.example.k.alltogether.alltogether;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.k.alltogether.R;

/**
 * Created by K on 2015-11-14.
 */
public class Music {
    enum MusicType {MAIN_SOUND, GAME_SOUND, BUBBLE_SOUND, BOMB_BUBBLE_SOUND, FEVER_SOUND, BUTTON_SOUND, GAME_OVER_SOUND};
    MediaPlayer mp = null;
    SoundPool sp = null;
    Context co;
    MusicType musicType;
    int soundId;
    Music(Context co, MusicType musicType){
        this.co = co;
        this.musicType = musicType;
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }
    void spStart(){
        sp.play(soundId, 1, 1, 0, 0, 1);
    }
    void spStop(){
        sp.stop(soundId);
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
    void reset(){
        mp.reset();
    }
    void release(){
        mp.release();
    }
    void prepare(){
        switch (musicType){
            case MAIN_SOUND:
                mp = MediaPlayer.create(co, R.raw.main_sound); mp.setLooping(true); break;
            case GAME_SOUND:
                mp = MediaPlayer.create(co, R.raw.game_sound); mp.setLooping(true); break;
            case BUBBLE_SOUND:
                soundId = sp.load(co, R.raw.bubble_sound1, 1); break;
            case BOMB_BUBBLE_SOUND:
                soundId = sp.load(co, R.raw.bomb_bubble_sound, 1); break;
            case FEVER_SOUND:
                mp = MediaPlayer.create(co, R.raw.ferver_sound); mp.setLooping(true); break;
            case BUTTON_SOUND:
                soundId = sp.load(co, R.raw.button_sound, 1); break;
            case GAME_OVER_SOUND:
                mp = MediaPlayer.create(co, R.raw.game_over_sound); mp.setLooping(true); mp.setVolume(100, 100);break;
        }
    }
    boolean isPlaying(){
        return mp.isPlaying();
    }
}
