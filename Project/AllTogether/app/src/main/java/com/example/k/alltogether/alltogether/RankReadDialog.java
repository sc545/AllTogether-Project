package com.example.k.alltogether.alltogether;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.k.alltogether.R;

/**
 * Created by 5-3COM- on 2015-11-18.
 */
public class RankReadDialog extends Dialog {
    Context co;
    ImageButton btnExit;
    TextView tvRank[];
    int resId[] = {R.id.tvRank1, R.id.tvRank2, R.id.tvRank3, R.id.tvRank4, R.id.tvRank5, R.id.tvRank6, R.id.tvRank7, R.id.tvRank8, R.id.tvRank9, R.id.tvRank10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(MainActivity.SIZE == 0)
            setContentView(R.layout.rank_read_dialog);
        else
            setContentView(R.layout.rank_read_dialog_tab);

        btnExit = (ImageButton) findViewById(R.id.btnExit);
        tvRank = new TextView[10];
        for(int i=0; i<10; i++){
            tvRank[i] = (TextView) findViewById(resId[i]);
        }


        RankReadThread rankReadThread = new RankReadThread(co, tvRank);
        rankReadThread.start();


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    RankReadDialog(Context context){
        super(context);
        this.co = context;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
            dismiss();
        }
        return false;
    }
}
