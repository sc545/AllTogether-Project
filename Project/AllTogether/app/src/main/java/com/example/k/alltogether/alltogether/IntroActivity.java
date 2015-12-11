package com.example.k.alltogether.alltogether;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.k.alltogether.R;

/**
 * Created by K on 2015-11-25.
 */
public class IntroActivity extends Activity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        handler = new Handler();
        handler.postDelayed(irun, 4000); // 약 4초 동안 인트로 화면

    }

    Runnable irun = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(i);
            finish();

            // fade in 으로 시작하여 fade out 으로 인트로 화면이 꺼지게 애니메이션 추가
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    // 인트로 화면 중간에 뒤로가기 버튼을 눌러서 꺼졌을 시 4초 후 메인 페이지가 뜨는 것을 방지
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(irun);
    }
}
