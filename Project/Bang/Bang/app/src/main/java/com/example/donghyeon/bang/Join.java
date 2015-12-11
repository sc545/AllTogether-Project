package com.example.donghyeon.bang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.content.Intent;



public class Join extends Activity
{
    Button btncre,btnesc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.join);

        btncre = (Button)findViewById(R.id.btncre);
        btnesc = (Button)findViewById(R.id.btnesc);


        btncre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Join.this, MainActivity.class);
                startActivity(intent);

            }
        });



        btnesc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}