package com.example.k.alltogether.alltogether;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.k.alltogether.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameStageActivity extends Activity {
    GameStageActivity gameStageActivity;
    GameStageActivity.GameView gameView;


    Music m_musicGameSound, m_musicBubbleSound, m_musicBombBubbleSound, m_musicFeverSound, m_musicGameOverSound, m_musicButtonSound;

    boolean m_bGameState; // 게임 진행 상태를 담을 변수
    boolean m_bThreadState; // 스레드 상태를 담을 변수
    boolean m_bFeverState; // 피버 상태를 담을 변수
    boolean m_bComboState; // 게임 중 콤보상태를 담을 변수 선언
    boolean m_bComboMissState;
    boolean m_bBombBubbleState; // 폭탄 버블 생성 여부를 담을 변수 선언
    boolean m_bAnimationState[]; // 애니메이션 상태를 담을 변수 선언
    boolean m_bAnimationTempState;
    boolean m_bComboDraw; // 콤보 표시 상태를 담을 변수 선언
    boolean m_bMusicBgmState;
    boolean m_bMusicEffectState;

    int m_nScreenWidth, m_nScreenHeight; // 스마트폰 화면 사이즈를 담을 변수
    int m_nBubbleRadius; // 버블 반지름
    int m_nScore; // 게임 중 몇 점수를 담을 변수 선언
    int m_nCombo; // 게임 중 몇 콤보 수를 담을 변수 선언
    int m_nFeverGauge; // 피버 게이지 변수
    int m_nBubbleMaxSize; // 버블 최대 개수
    int m_iAnimationCount[]; // [0] bubble_in [1] bubble_out [2] bomb_bubble_in [3] bomb_bubble_out

    /*
        버블 관련 Bitmap 변수 선언
     */
    Bitmap m_btmpImgBubble, m_btmpImgBombBubble, m_btmpImgFeverBubble, m_btmpComboMiss;
    Bitmap m_btmpBubbleAnimation[][], m_btmpFeverBubbleAnimation[][], m_btmpBombBubbleInAnimation[], m_btmpBombBubbleOutAnimation[];
    Drawable m_drawableFeverGauge[], m_drawableLifeGauge[];


    ArrayList<Bubble> m_arraylistBubble; // 게임 속에서 생성되는 버블 객체를 가지고 있을 ArrayList
    ArrayList<Bubble> m_arraylistFeverBubble; // 게임 속에서 생성되는 버블 객체를 가지고 있을 ArrayList
    Bubble m_NewBubble, m_OldBubble; // 추가, 삭제 될 버블을 가지고 있을 변수

    Rect m_rectBubbleArea; // 버블 생성 영역

    Vibrator vibrator; // 진동 클래스

    Button btnPause; // 일지정지 버튼
    TextView tvScore, tvCombo; // 점수, 콤보 TextView
    ImageView ivFerverGauge, ivLifeGauge;

//    Timer m_timer;
//    long m_lCurrentTime, m_lStartTime;
//    int m_nGameTime;

    com.example.k.alltogether.alltogether.Timer m_timer;
    int m_nSeconds;
    TextView tvTimer;


    /*
        다이얼로그 변수
     */
    GameOverDialog m_dlgGameOverDialog;
    GamePauseDialog m_dlgGamePauseDialog;
    RankWriteDialog m_dlgRankWriteDialog;

    ThreadPoolExecutor m_ThreadPoolExecutor;

    LinearLayout m_layouyStageBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MainActivity.m_musicBubbleSound.start();

        /*
            멤버 변수 초기화
         */
        gameStageActivity=this;
        m_musicGameSound = new Music(getApplicationContext(), Music.MusicType.GAME_SOUND);
        m_musicGameSound.prepare();
        m_musicFeverSound = new Music(getApplicationContext(), Music.MusicType.FEVER_SOUND);
        m_musicFeverSound.prepare();
        m_musicGameOverSound = new Music(getApplicationContext(), Music.MusicType.GAME_OVER_SOUND);
        m_musicGameOverSound.prepare();
        m_musicButtonSound = new Music(getApplicationContext(), Music.MusicType.BUTTON_SOUND);
        m_musicButtonSound.prepare();

        m_bGameState =true; // GameStageActivity 가 생성 될 때 게임 진행 상태를 true 로 변경
        m_bThreadState = true;
        m_bAnimationState = new boolean[4];

        m_nScreenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels; // 스마트폰 화면 사이즈 가져오는 함수
        m_nScreenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        m_nBubbleRadius = m_nScreenWidth /10;
        m_nBubbleMaxSize = 40;
        m_iAnimationCount = new int[4];

        m_arraylistBubble = new ArrayList<Bubble>(); // m_arraylistBubble 객체 생성
        m_arraylistFeverBubble = new ArrayList<Bubble>();

        m_rectBubbleArea = new Rect();

        int startX = (int) (m_nScreenWidth *0.01);
        int startY = (int) (m_nScreenHeight *0.125);
        int endX = (int) (m_nScreenWidth *0.85);
        int endY = (int) (m_nScreenHeight *0.9775);

        m_rectBubbleArea.set(startX, startY, endX, endY);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        m_dlgGameOverDialog = new GameOverDialog(this, gameStageActivity);
        m_dlgGamePauseDialog = new GamePauseDialog(this, gameStageActivity);
        m_dlgRankWriteDialog = new RankWriteDialog(this, gameStageActivity);

        /*
            첫 번째 뷰
         */
        setContentView(R.layout.game_stage0);

        m_layouyStageBackground = (LinearLayout) findViewById(R.id.stageBackground);

        /*
            두 번째 뷰
         */
        gameView = new GameView(this);
        addContentView(gameView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        /*
            세 번째 뷰
         */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.game_stage1, null);
        layout.setFocusable(true);
        addContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        pbLife = (ProgressBar) findViewById(R.id.pbLife);

        btnPause = (Button) findViewById(R.id.btnPause);

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvCombo = (TextView) findViewById(R.id.tvCombo);

        ivFerverGauge = (ImageView) findViewById(R.id.ivFeverGauge);
        ivLifeGauge = (ImageView) findViewById(R.id.ivLifeGauge);

        tvTimer = (TextView) findViewById(R.id.tvTime);
//        startTimer();

        m_musicBubbleSound = new Music(gameStageActivity.getApplicationContext(), Music.MusicType.BUBBLE_SOUND);
        m_musicBubbleSound.prepare();
        m_musicBombBubbleSound = new Music(gameStageActivity.getApplicationContext(), Music.MusicType.BOMB_BUBBLE_SOUND);
        m_musicBombBubbleSound.prepare();

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_bMusicEffectState) m_musicButtonSound.spStart();
                showGamePauseDialog();
            }
        });

        m_ThreadPoolExecutor = new ThreadPoolExecutor(6, 6, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        m_ThreadPoolExecutor.execute(new BubbleThread(gameStageActivity));
        m_ThreadPoolExecutor.execute(new com.example.k.alltogether.alltogether.Timer(gameStageActivity));

        if(m_bMusicBgmState) m_musicGameSound.start();
    }

//    public void startTimer(){
//        final Handler handler = new Handler();
//        final TextView tvTimer = (TextView) findViewById(R.id.tvTime);
//        if(m_timer == null){
//            m_lStartTime = System.currentTimeMillis() - (m_lCurrentTime - m_lStartTime);
//            m_timer = new Timer();
//            m_timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    m_lCurrentTime = System.currentTimeMillis();
//                    long millis = m_lCurrentTime - m_lStartTime;
//                    int seconds = (int) (millis / 1000);
//                    int minutes = seconds / 60;
//                    seconds = seconds % 60;
//
//                    millis=(millis/10)%100;
//                    final String strText = String.format("%02d : %02d", minutes, seconds);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvTimer.setText(strText);
//                        }
//                    });
//
//                }
//            }, 100, 200);
//
//        }
//    }

//    public void stopTimer(){
//        if(m_timer != null){
//            m_timer.cancel();
//            m_timer.purge();
//            m_timer = null;
//        }
//    }

//    public void resetTimer(){
//        final Handler handler = new Handler();
//        final TextView tvTimer = (TextView) findViewById(R.id.tvTime);
//        m_lCurrentTime = m_lStartTime = 0L;
//
//        stopTimer();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                tvTimer.setText("00 : 00");
//            }
//        });
//
//    }

    /*
        새로하기를 할 경우 게임상태 초기화 함수
     */
    public void resetState(){
//        resetTimer();
//        startTimer();
        m_nSeconds = 0;
        tvTimer.setText("00 : 00");
        m_arraylistBubble.clear();
        m_arraylistFeverBubble.clear();
        m_musicGameOverSound.stop();
        m_nScore =0;
        m_nCombo =0;
        m_nFeverGauge = 0;
        m_bFeverState = false;
        m_bComboState = false;
        m_bBombBubbleState = false;
        m_musicFeverSound.prepare();
        m_musicGameOverSound.prepare();
        m_musicGameSound.prepare();
        m_musicGameSound.start();
    }

    /*
        스마트폰에서 디코딩시 아웃오브메모리 줄이기위해서 비율 줄이는 메소드
     */
//    private Bitmap decodeFile(int minImageSize, InputStream is){
//        Bitmap b = null;
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(is, null, options);
//        int scale = 1;
//        if(options.outHeight>minImageSize || options.outWidth>minImageSize){
//            scale = (int)Math.pow(  2,  (int)Math.round( Math.log( minImageSize / (double)Math.max( options.outHeight, options.outWidth ) ) / Math.log( 0.5 ) ) );
//        }
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        b = BitmapFactory.decodeStream(is, null, o2);
//
//        return b;
//    }

    /*
        GameStageActivity 에 화면(View)을 담당해 줄 GameView 클래스
    */
    class GameView extends View {
        Paint paint; // 페인트 변수 선언
        Paint txtPaint;

        public GameView(Context context) {
            super(context);

//          여기서 이미지를 디코딩을 미리 시켜줘야 어플에 부담이 안간다.

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 4;


            int imgBubbleInOut[][] = {{R.drawable.bubble_in0, R.drawable.bubble_in1, R.drawable.bubble_in2, R.drawable.bubble_in3}, {R.drawable.bubble_out0, R.drawable.bubble_out1, R.drawable.bubble_out2, R.drawable.bubble_out3}};
            int imgFeverBubbleInOut[][] = {{R.drawable.fever_bubble_in0, R.drawable.fever_bubble_in1, R.drawable.fever_bubble_in2, R.drawable.fever_bubble_in3}, {R.drawable.fever_bubble_out0, R.drawable.fever_bubble_out1, R.drawable.fever_bubble_out2, R.drawable.fever_bubble_out3}};
            int imgBombBubbleIn[] = {R.drawable.bomb_bubble_in0, R.drawable.bomb_bubble_in1, R.drawable.bomb_bubble_in2, R.drawable.bomb_bubble_in3};
            int imgBombBubbleOut[] = {R.drawable.bomb_bubble_out0, R.drawable.bomb_bubble_out1, R.drawable.bomb_bubble_out2, R.drawable.bomb_bubble_out3, R.drawable.bomb_bubble_out4};
            int imgFeverGauge[] = {R.drawable.fever_gauge0, R.drawable.fever_gauge1, R.drawable.fever_gauge2, R.drawable.fever_gauge3, R.drawable.fever_gauge4, R.drawable.fever_gauge5, R.drawable.fever_gauge6, R.drawable.fever_gauge7, R.drawable.fever_gauge8, R.drawable.fever_gauge9,
                    R.drawable.fever_gauge10, R.drawable.fever_gauge11, R.drawable.fever_gauge12, R.drawable.fever_gauge13, R.drawable.fever_gauge14, R.drawable.fever_gauge15, R.drawable.fever_gauge16, R.drawable.fever_gauge17, R.drawable.fever_gauge18, R.drawable.fever_gauge19,
                    R.drawable.fever_gauge20, R.drawable.fever_gauge21, R.drawable.fever_gauge22, R.drawable.fever_gauge23, R.drawable.fever_gauge24, R.drawable.fever_gauge25, R.drawable.fever_gauge26, R.drawable.fever_gauge27, R.drawable.fever_gauge28, R.drawable.fever_gauge29, R.drawable.fever_gauge30};
            int imgLifeGauge[] = {R.drawable.life_gauge0, R.drawable.life_gauge1, R.drawable.life_gauge2, R.drawable.life_gauge3, R.drawable.life_gauge4,
                    R.drawable.life_gauge5, R.drawable.life_gauge6, R.drawable.life_gauge7, R.drawable.life_gauge8, R.drawable.life_gauge9,
                    R.drawable.life_gauge10, R.drawable.life_gauge11, R.drawable.life_gauge12, R.drawable.life_gauge13, R.drawable.life_gauge14,
                    R.drawable.life_gauge15, R.drawable.life_gauge16, R.drawable.life_gauge17, R.drawable.life_gauge18, R.drawable.life_gauge19, R.drawable.life_gauge20};
            /*
                버블, 피버버블, 폭탄버블 이미지 가져오고 크기 재조정
             */
            Intent intent = getIntent();
            m_bMusicBgmState = intent.getBooleanExtra("MusicBgmState", true);
            m_bMusicEffectState = intent.getBooleanExtra("MusicEffectState", true);
            Bitmap imgBubble = intent.getParcelableExtra("imgBubble");
            Bitmap imgBombBubble = intent.getParcelableExtra("imgBombBubble");
            Bitmap imgFeverBubble = intent.getParcelableExtra("imgFeverBubble");

            if(imgBubble != null) m_btmpImgBubble = imgBubble;
            else m_btmpImgBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble); // 버블 이미지 등록
            if(imgBombBubble != null) m_btmpImgBombBubble = imgBombBubble;
            else m_btmpImgBombBubble = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_bubble); // 폭탄 버블 이미지 등록
            if(imgFeverBubble != null) m_btmpImgFeverBubble = imgFeverBubble;
            else m_btmpImgFeverBubble = BitmapFactory.decodeResource(getResources(), R.drawable.fever_bubble); // 피버 버블 이미지 등록

            m_btmpComboMiss = BitmapFactory.decodeResource(getResources(), R.drawable.combo_miss);
            m_btmpComboMiss = Bitmap.createScaledBitmap(m_btmpComboMiss, m_nScreenWidth, m_nScreenHeight, false); // 버블 반지름에 맞게 버블 이미지 조정

            m_btmpImgBubble = Bitmap.createScaledBitmap(m_btmpImgBubble, m_nBubbleRadius * 2, m_nBubbleRadius * 2, false); // 버블 반지름에 맞게 버블 이미지 조정
            m_btmpImgBombBubble = Bitmap.createScaledBitmap(m_btmpImgBombBubble, m_nBubbleRadius * 2, m_nBubbleRadius * 2, false); // 버블 반지름에 맞게 버블 이미지 조정
            m_btmpImgFeverBubble = Bitmap.createScaledBitmap(m_btmpImgFeverBubble, m_nBubbleRadius * 2, m_nBubbleRadius * 2, false); // 버블 반지름에 맞게 버블 이미지 조정


            /*
                버블 애니메이션 이미지 가져오고 크기 재조정
             */
            m_btmpBubbleAnimation = new Bitmap[2][4];
            m_btmpFeverBubbleAnimation = new Bitmap[2][4];
            m_btmpBombBubbleInAnimation = new Bitmap[4];
            m_btmpBombBubbleOutAnimation = new Bitmap[5];
            for(int i=0; i<2; i++){
                for(int j=0; j<4; j++) {
                    m_btmpBubbleAnimation[i][j] = BitmapFactory.decodeResource(getResources(), imgBubbleInOut[i][j]);
                    m_btmpBubbleAnimation[i][j] = m_btmpBubbleAnimation[i][j].createScaledBitmap(m_btmpBubbleAnimation[i][j], m_nBubbleRadius * 2, m_nBubbleRadius * 2, false);
                }
            }
            for(int i=0; i<2; i++){
                for(int j=0; j<4; j++) {
                    m_btmpFeverBubbleAnimation[i][j] = BitmapFactory.decodeResource(getResources(), imgFeverBubbleInOut[i][j]);
                    m_btmpFeverBubbleAnimation[i][j] = m_btmpFeverBubbleAnimation[i][j].createScaledBitmap(m_btmpFeverBubbleAnimation[i][j], m_nBubbleRadius * 2, m_nBubbleRadius * 2, false);
                }
            }
            for(int i=0; i<4; i++){
                m_btmpBombBubbleInAnimation[i] = BitmapFactory.decodeResource(getResources(), imgBombBubbleIn[i]);
                m_btmpBombBubbleInAnimation[i] = m_btmpBombBubbleInAnimation[i].createScaledBitmap(m_btmpBombBubbleInAnimation[i], m_nBubbleRadius * 2, m_nBubbleRadius * 2, false);
            }
            for(int i=0; i<5; i++){
                m_btmpBombBubbleOutAnimation[i] = BitmapFactory.decodeResource(getResources(), imgBombBubbleOut[i]);
                m_btmpBombBubbleOutAnimation[i] = m_btmpBombBubbleOutAnimation[i].createScaledBitmap(m_btmpBombBubbleOutAnimation[i], m_nBubbleRadius * 2 * 3, m_nBubbleRadius * 2 * 3, false);
            }

            m_drawableFeverGauge = new Drawable[31];
            m_drawableLifeGauge = new Drawable[21];
            for(int i=0; i<31; i++){
                m_drawableFeverGauge[i] = getResources().getDrawable(imgFeverGauge[i]);
            }
            for(int i=0; i<21; i++){
                m_drawableLifeGauge[i] = getResources().getDrawable(imgLifeGauge[i]);
            }

            paint = new Paint();
            txtPaint = new Paint();
            txtPaint.setTextSize(50);

        }

        public GameView(Context context, AttributeSet attrs){
            super(context, attrs);
        }

//        class AnimationTempThread extends Thread{
//            Handler handler; // 핸들러 사용 이유 : 메인스레드를 제외하고는 View 를 건드릴 수 없기 때문에 핸들러를 통해서 화면 갱신 => invalidate()
//            public AnimationTempThread() {
//                handler = new Handler();
//            }
//            @Override
//            public void run() {
//                            for (int i = 0; i < 4; i++) {
//                                m_iAnimationCount[1]= i;
//                                try {
//                                    Thread.sleep(80);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        invalidate();
//                                    }
//                                });
//                            }
//            }
//        }

        @Override
        protected void onDraw(Canvas canvas) {
//            m_nGameTime = (int) ((m_lCurrentTime - m_lStartTime)/1000);
//            Toast.makeText(getApplicationContext(), ""+m_nGameTime, Toast.LENGTH_SHORT).show();
            String strScore = ""+ m_nScore;
            String strCombo = ""+ m_nCombo;
            tvScore.setText(strScore);
            tvCombo.setText(strCombo);

            if(m_bFeverState) paint.setColor(Color.RED);
            else paint.setColor(Color.BLACK);
            /*
                버블 생성 영역 그리기
             */
            paint.setStrokeWidth(5);
            canvas.drawLine(m_rectBubbleArea.left, m_rectBubbleArea.top, m_rectBubbleArea.right, m_rectBubbleArea.top, paint);
            canvas.drawLine(m_rectBubbleArea.left, m_rectBubbleArea.top, m_rectBubbleArea.left, m_rectBubbleArea.bottom, paint);
            canvas.drawLine(m_rectBubbleArea.right, m_rectBubbleArea.top, m_rectBubbleArea.right, m_rectBubbleArea.bottom, paint);
            canvas.drawLine(m_rectBubbleArea.left, m_rectBubbleArea.bottom, m_rectBubbleArea.right, m_rectBubbleArea.bottom, paint);

            int currentSize = m_arraylistBubble.size();

            /*
                체력, 피버 게이지 갱신
             */
            if(currentSize == 1)
                ivLifeGauge.setImageDrawable(m_drawableLifeGauge[1]);
            else {
                int i = currentSize/2;
                if(i<21) {
                    ivLifeGauge.setImageDrawable(m_drawableLifeGauge[i]);
                }else{
                    ivLifeGauge.setImageDrawable(m_drawableLifeGauge[20]);
                }
            }

            drawFeverGauge();

            /*
                현재 m_arraylistBubble 에 존재하는 버블 그리기
             */
            if(m_bGameState){
                if (m_bFeverState) {
                    int currentFeverSize = m_arraylistFeverBubble.size();
                    for(int i=0; i<currentFeverSize; i++) {
                        Bubble bubble = m_arraylistFeverBubble.get(i);
                        canvas.drawBitmap(bubble.m_btmpImgFeverBubble, bubble.x - bubble.bubbleR, bubble.y - bubble.bubbleR, paint);
                    }
                }else {
                    for (int i = 0; i < currentSize; i++) {
                        Bubble bubble = m_arraylistBubble.get(i);
                        if (bubble.isBombBubble)
                            canvas.drawBitmap(bubble.m_btmpImgBombBubble, bubble.x - bubble.bubbleR, bubble.y - bubble.bubbleR, paint);
                        else
                            canvas.drawBitmap(bubble.m_btmpImgBubble, bubble.x - bubble.bubbleR, bubble.y - bubble.bubbleR, paint);
                    }
                }
            }
            /*
                m_bAnimationState[0] - 새로운 버블이 생성될 경우 애니메이션
                m_bAnimationState[1] - 버블이 터질 경우 애니메이션
                m_bAnimationState[2] - 새로운 폭탄 버블이 생성될 경우 애니메이션
                m_bAnimationState[3] - 폭탄 버블이 터질 경우 애니메이션
             */
            if(m_bAnimationState[0]){
                if(!m_bFeverState)
                    canvas.drawBitmap(m_NewBubble.m_btmpBubbleAnimation[0][m_iAnimationCount[0]], m_NewBubble.x - m_NewBubble.bubbleR, m_NewBubble.y - m_NewBubble.bubbleR, paint);
                else
                    canvas.drawBitmap(m_NewBubble.m_btmpFeverBubbleAnimation[0][m_iAnimationCount[0]], m_NewBubble.x - m_NewBubble.bubbleR, m_NewBubble.y - m_NewBubble.bubbleR, paint);
            }
            if(m_bAnimationState[1]) {
                if(!m_bFeverState)
                    canvas.drawBitmap(m_OldBubble.m_btmpBubbleAnimation[1][m_iAnimationCount[1]], m_OldBubble.x - m_OldBubble.bubbleR, m_OldBubble.y - m_OldBubble.bubbleR, paint);
                else
                    canvas.drawBitmap(m_OldBubble.m_btmpFeverBubbleAnimation[1][m_iAnimationCount[1]], m_OldBubble.x - m_OldBubble.bubbleR, m_OldBubble.y - m_OldBubble.bubbleR, paint);
            }
            if(m_bAnimationState[2]) {
                canvas.drawBitmap(m_NewBubble.m_btmpBombBubbleInAnimation[m_iAnimationCount[2]], m_NewBubble.x - m_NewBubble.bubbleR, m_NewBubble.y - m_NewBubble.bubbleR, paint);
            }
            if(m_bAnimationState[3]){
                canvas.drawBitmap(m_OldBubble.m_btmpBombBubbleOutAnimation[m_iAnimationCount[3]], m_OldBubble.x - m_OldBubble.bombBubbleR, m_OldBubble.y - m_OldBubble.bombBubbleR, paint);
            }
            if(m_bAnimationTempState){
                canvas.drawBitmap(m_btmpBubbleAnimation[1][m_iAnimationCount[1]], m_OldBubble.x - m_OldBubble.bubbleR, m_OldBubble.y - m_OldBubble.bubbleR, paint);
            }
            /*
                현재 콤보가 진행중 일 경우 콤보 표시
             */
            if(m_bComboDraw){
                String tmp = m_nCombo +"COMOBO!!";
                txtPaint.setColor(Color.RED);
                txtPaint.setTextSize(50);
                canvas.drawText(tmp, m_OldBubble.x + m_OldBubble.bubbleR /2, m_OldBubble.y - m_OldBubble.bubbleR /2, txtPaint);
            }

            if(m_bComboMissState){
                canvas.drawBitmap(m_btmpComboMiss, 0, 0, paint);
            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int px = (int) event.getX(); // 터치이벤트가 발생 했을때 x좌표값
            int py = (int) event.getY(); // 터치이벤트가 발생 했을때 y좌표값

            if (m_bFeverState) {
                for (int i = m_arraylistFeverBubble.size() - 1; 0 <= i; i--) { // 현재 존재하는 버블 객체 수 만큼 반복
                    Bubble bubble = m_arraylistFeverBubble.get(i);

                    if (bubble.contains(px, py)) {
                        m_OldBubble = bubble;
                        m_arraylistFeverBubble.remove(bubble);
                        m_nScore += 5 + (5 * m_nCombo++);
                        m_bComboState = true;
                        m_bComboDraw = true;

                        if(bubble.isBombBubble) {
                            m_bAnimationState[3] = true;
                            m_ThreadPoolExecutor.execute(new AnimationThread(gameStageActivity));
                            vibrator.vibrate(500);
                            m_bAnimationTempState = true;
                            for (int j = m_arraylistFeverBubble.size() - 1; 0 <= j; j--) {
                                Bubble tmpBubble = m_arraylistFeverBubble.get(j);
                                if (bubble.bombContains(tmpBubble.x, tmpBubble.y)) {
                                    m_arraylistFeverBubble.remove(j);
                                    m_nScore += 5 + (5 * m_nCombo++);
                                }
                                m_bAnimationTempState = false;
                            }
                        }else{
                            m_bAnimationState[1] = true;
                            m_ThreadPoolExecutor.execute(new AnimationThread(gameStageActivity));
                        }
                        break;
                    }
                }
                invalidate();
            } else {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    for (int i = m_arraylistBubble.size() - 1; 0 <= i; i--) { // 현재 존재하는 버블 객체수 만큼 반복
                        Bubble bubble = m_arraylistBubble.get(i);

                        if (bubble.contains(px, py)) {
                            m_OldBubble = bubble;
                            m_arraylistBubble.remove(bubble);
                            m_nScore += 10 + (1 * m_nCombo++);
                            m_nFeverGauge += 300;
                            m_bComboState = true;
                            m_bComboDraw = true;
                            if(bubble.isBombBubble) {
                                m_bAnimationState[3] = true;
                                m_ThreadPoolExecutor.execute(new AnimationThread(gameStageActivity));
                                vibrator.vibrate(500);
                                m_bAnimationTempState = true;
                                for (int j = m_arraylistBubble.size() - 1; 0 <= j; j--) {
                                    Bubble tmpBubble = m_arraylistBubble.get(j);
                                    if (bubble.bombContains(tmpBubble.x, tmpBubble.y)) {
                                        m_arraylistBubble.remove(j);
                                        m_nFeverGauge += 300;
                                        m_nScore += 10 + (1 * m_nCombo++);
                                    }
                                    m_bAnimationTempState = false;
                                }
                            }else{
                                m_bAnimationState[1] = true;
                                m_ThreadPoolExecutor.execute(new AnimationThread(gameStageActivity));
                            }

                            if(m_nFeverGauge >= 31000){
                                if(m_bMusicBgmState) m_musicGameSound.pause();
                                Toast.makeText(getApplicationContext(), "Fever Time!!", Toast.LENGTH_SHORT).show();
                                m_ThreadPoolExecutor.execute(new FeverThread(gameStageActivity));
                            }

                            break;
                        } else {
                            m_bComboState = false;
                        }
                    }
                    if (!m_bComboState){
                        m_nCombo = 0;
                        if(m_nFeverGauge < 0)
                            m_nFeverGauge = 0;
                        else
                            m_nFeverGauge -= 100;
                        m_ThreadPoolExecutor.execute(new ComboMissThread(gameStageActivity));
                    }
                    invalidate();
                }
            }


            return true;
        }
    }

    public void drawFeverGauge(){
        if(m_nFeverGauge >=0 && m_nFeverGauge < 1000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[0]);
        if(m_nFeverGauge >=1000 && m_nFeverGauge < 2000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[1]);
        if(m_nFeverGauge >=2000 && m_nFeverGauge < 3000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[2]);
        if(m_nFeverGauge >=3000 && m_nFeverGauge < 4000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[3]);
        if(m_nFeverGauge >=4000 && m_nFeverGauge < 5000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[4]);
        if(m_nFeverGauge >=5000 && m_nFeverGauge < 6000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[4]);
        if(m_nFeverGauge >=6000 && m_nFeverGauge < 7000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[5]);
        if(m_nFeverGauge >=7000 && m_nFeverGauge < 8000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[6]);
        if(m_nFeverGauge >=8000 && m_nFeverGauge < 9000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[7]);
        if(m_nFeverGauge >=9000 && m_nFeverGauge < 10000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[8]);
        if(m_nFeverGauge >=10000 && m_nFeverGauge < 11000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[9]);
        if(m_nFeverGauge >=11000 && m_nFeverGauge < 12000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[10]);
        if(m_nFeverGauge >=12000 && m_nFeverGauge < 13000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[11]);
        if(m_nFeverGauge >=13000 && m_nFeverGauge < 14000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[12]);
        if(m_nFeverGauge >=14000 && m_nFeverGauge < 15000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[13]);
        if(m_nFeverGauge >=15000 && m_nFeverGauge < 16000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[14]);
        if(m_nFeverGauge >=16000 && m_nFeverGauge < 17000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[15]);
        if(m_nFeverGauge >=17000 && m_nFeverGauge < 18000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[16]);
        if(m_nFeverGauge >=18000 && m_nFeverGauge < 19000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[17]);
        if(m_nFeverGauge >=19000 && m_nFeverGauge < 20000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[18]);
        if(m_nFeverGauge >=20000 && m_nFeverGauge < 21000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[19]);
        if(m_nFeverGauge >=21000 && m_nFeverGauge < 22000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[20]);
        if(m_nFeverGauge >=22000 && m_nFeverGauge < 23000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[21]);
        if(m_nFeverGauge >=23000 && m_nFeverGauge < 24000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[22]);
        if(m_nFeverGauge >=24000 && m_nFeverGauge < 25000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[23]);
        if(m_nFeverGauge >=25000 && m_nFeverGauge < 26000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[24]);
        if(m_nFeverGauge >=26000 && m_nFeverGauge < 27000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[25]);
        if(m_nFeverGauge >=27000 && m_nFeverGauge < 28000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[26]);
        if(m_nFeverGauge >=28000 && m_nFeverGauge < 29000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[27]);
        if(m_nFeverGauge >=29000 && m_nFeverGauge < 30000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[28]);
        if(m_nFeverGauge >=30000 && m_nFeverGauge < 31000 ) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[29]);
        if(m_nFeverGauge >=31000) ivFerverGauge.setImageDrawable(m_drawableFeverGauge[30]);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
            showGamePauseDialog();
        }
        return false;
    }

    public void showGamePauseDialog(){
        m_musicGameSound.pause();
//        stopTimer();
        m_bGameState =false;
        m_dlgGamePauseDialog.show();
    }

    @Override
    protected void onDestroy() {
        gameStageActivity = null;
        gameView = null;
        m_musicGameSound = m_musicBubbleSound = m_musicBombBubbleSound = m_musicFeverSound = m_musicGameOverSound = m_musicButtonSound = null;

        m_btmpImgBubble = m_btmpImgBombBubble = m_btmpImgFeverBubble = m_btmpComboMiss = null;
        m_btmpBubbleAnimation = null;
        m_btmpFeverBubbleAnimation = null;
        m_btmpBombBubbleInAnimation = m_btmpBombBubbleOutAnimation = null;
        m_drawableFeverGauge = m_drawableLifeGauge = null;

        m_arraylistBubble = null;
        m_arraylistFeverBubble = null;
        m_NewBubble = m_OldBubble = null;

        m_rectBubbleArea = null;

        vibrator = null;

        btnPause = null;
        tvScore = tvCombo = null;
        ivFerverGauge = ivLifeGauge = null;

        m_timer = null;

        m_dlgGameOverDialog = null;
        m_dlgGamePauseDialog = null;
        m_dlgRankWriteDialog = null;

        m_ThreadPoolExecutor = null;

        System.gc();
        super.onDestroy();
    }
}

