package com.example.donghyeon.bang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class DialogCreRoom extends Dialog implements View.OnClickListener {
    Button btnYes,btnNo;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.cre_room);
        setCanceledOnTouchOutside(false);//

        btnYes = (Button)findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = (Button)findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);

    }

    public DialogCreRoom(Context context){
        super(context);
    }

    public void onClick(View view){
        if(view == btnYes){
            Intent intent = new Intent(RoomList.roomList.getApplicationContext(), MainStage.class);
            RoomList.roomList.startActivity(intent);
            dismiss();
        }else if(view == btnNo)
            dismiss();
    }
}
