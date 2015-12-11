package com.example.k.alltogether.alltogether;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.k.alltogether.R;

import java.util.regex.Pattern;

/**
 * Created by K on 2015-10-14.
 */

public class MainActivity extends Activity {
    static String IP = "192.168.131.220"; // 192.168.51.141
    static int PORT = 1113;
    static int GAMEDIFFICULTY = 7;
    static int SCREENWIDTH, SCREENHEIGHT; // 스마트폰 화면 사이즈를 담을 변수
    static int SIZE;


    ImageButton btnHowTo, btnSetting, btnStart, btnRank;
    static Music music=null;
    Music m_musicButtonSound;
    HowToDialog howToDialog;
    SettingDialog settingDialog;
    RankReadDialog rankReadDialog;
    SelectModeDialog selectModeDialog;
    MainActivity mainActivity;
    boolean m_bMusicBgmState, m_bMusicEffectState;

    ExplainBubbleDialog explainBubbleDialog;

    private BackPressCloseHandler backPressCloseHandler;

    private PopupWindow pWHowTo, pWExplainBub;

    private View how_to_popup, explain_bubble_popup, rank_buuble_popup;

    ImageView hide;
    int c;
    AlertDialog.Builder dlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SCREENWIDTH = getApplicationContext().getResources().getDisplayMetrics().widthPixels; // 스마트폰 화면 사이즈 가져오는 함수
        SCREENHEIGHT = getApplicationContext().getResources().getDisplayMetrics().heightPixels;

        backPressCloseHandler = new BackPressCloseHandler(this);

        if(SCREENWIDTH <1100 && SCREENHEIGHT <2000)
            SIZE = 0;
        else
            SIZE = 1;

        if(SIZE == 0)
            setContentView(R.layout.main_activity);
        else
            setContentView(R.layout.bubble_pang_main);

        mainActivity = this;

        hide = (ImageView) findViewById(R.id.hide);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "" + IP, Toast.LENGTH_SHORT).show();
                if (++c == 5) {
                    showHide();
                    c = 0;
                }
            }
        });


//        Toast.makeText(getApplicationContext(), ""+SCREENWIDTH+", "+SCREENHEIGHT, Toast.LENGTH_SHORT).show();
        // 1080 / 1920
        Intent intent = getIntent();
        m_bMusicBgmState = intent.getBooleanExtra("MusicBgmState", true);
        m_bMusicEffectState = intent.getBooleanExtra("MusicEffectState", true);
        if(m_bMusicBgmState) {
            if (music == null) {
                music = new Music(MainActivity.this, Music.MusicType.MAIN_SOUND);
                music.prepare();
            }
            if (!music.isPlaying())
                music.start();
        }
        m_musicButtonSound = new Music(getApplicationContext(), Music.MusicType.BUTTON_SOUND);
        m_musicButtonSound.prepare();

        howToDialog = new HowToDialog(this);
        settingDialog = new SettingDialog(this, mainActivity);
        rankReadDialog = new RankReadDialog(this);
        selectModeDialog = new SelectModeDialog(this, mainActivity);

        explainBubbleDialog = new ExplainBubbleDialog(this);

        btnHowTo = (ImageButton) findViewById(R.id.btnHowTo);
        btnSetting = (ImageButton) findViewById(R.id.btnSetting);
        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnRank = (ImageButton) findViewById(R.id.btnRank);

        if(SIZE == 0)
            btnHowTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    howToDialog.show();
                }
            });
        else
            btnHowTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    howToPopup();
                }
            });


        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingDialog.show();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectModeDialog.show();
            }
        });

        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankReadDialog.show();
            }
        });
    }

    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    private  void showHide(){
        dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Setting");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.hide, null);

        dlg.setView(layout);
        final EditText etIP = (EditText) layout.findViewById(R.id.etIP);

        final EditText etGAMEDIFFICULTY = (EditText) layout.findViewById(R.id.etGAMEDIFFICULTY);

        dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = etIP.getText().toString().trim();
                IP = value;
                if(!etGAMEDIFFICULTY.getText().toString().equals("")) {
                    int value2 = Integer.parseInt(etGAMEDIFFICULTY.getText().toString());
                    GAMEDIFFICULTY = value2;
                }else{
                    GAMEDIFFICULTY = 7;
                }
            }
        });
        dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.show();
    }

    private void howToPopup() {
        try {
            //  LayoutInflater 객체화 시킴
            LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

            ImageButton pop_Close, pop_Bubble_Close, arrow_left, arrow_right;
            int mPopWidth, mPopHeight;

            mPopWidth = (int)(0.9* SCREENWIDTH);
            mPopHeight = (int)(0.7* SCREENHEIGHT);

            how_to_popup = inflater.inflate(R.layout.popup_layout, (ViewGroup) findViewById(R.id.popLinear));
            explain_bubble_popup = inflater.inflate(R.layout.popup_bubble_layout, (ViewGroup) findViewById(R.id.popBubbleLinear));

            pWHowTo = new PopupWindow(how_to_popup, mPopWidth, mPopHeight, true);
            pWExplainBub = new PopupWindow(explain_bubble_popup, mPopWidth, mPopHeight, true);
            pWHowTo.showAtLocation(how_to_popup, Gravity.CENTER, 0, 0);

            pop_Close = (ImageButton) how_to_popup.findViewById(R.id.btnExit);
            pop_Bubble_Close = (ImageButton) explain_bubble_popup.findViewById(R.id.btnExit);

            arrow_right = (ImageButton) how_to_popup.findViewById(R.id.btnRight);
            arrow_left = (ImageButton) explain_bubble_popup.findViewById(R.id.btnLeft);

            pop_Close.setOnClickListener(howto_cancel_button_click_listener);
            pop_Bubble_Close.setOnClickListener(howto_cancel_button_click_listener);

            arrow_right.setOnClickListener(arrow_right_click_listener);
            arrow_left.setOnClickListener(arrow_left_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener howto_cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pWHowTo.dismiss();
            pWExplainBub.dismiss();
        }
    };

    private View.OnClickListener arrow_left_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pWHowTo.showAtLocation(how_to_popup, Gravity.CENTER, 0, 0);
            pWExplainBub.dismiss();
        }
    };

    private View.OnClickListener arrow_right_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pWExplainBub.showAtLocation(explain_bubble_popup, Gravity.CENTER, 0, 0);
            pWHowTo.dismiss();
        }
    };

    public void musicBgmStart(){
        music.start();
        m_bMusicBgmState = true;
    }

    public void musicBgmStop(){
        music.pause();
        m_bMusicBgmState = false;
    }

    public void musicEffectStart(){
        m_bMusicEffectState = true;
    }

    public void musicEffectStop(){
        m_bMusicEffectState = false;
    }

    @Override
    protected void onDestroy() {
        System.gc();
        music.pause();
        super.onDestroy();
    }

}
