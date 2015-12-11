package com.example.donghyeon.bang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;


public class Login extends Activity
{
    Button btnJoin,btnMember,btnEsc;
    // String passwd = (String)findViewById(R.id.passwd);
    public static Login login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        btnJoin = (Button)findViewById(R.id.btnJoin);
        btnMember = (Button)findViewById(R.id.btnMember);
        btnEsc = (Button)findViewById(R.id.btnEsc);
        login = this;


        btnJoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnMember.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Join.class);
                startActivity(intent);
            }
        });

        btnEsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast tMsg = Toast.makeText(Login.this,"ㅇㅇㅇ", Toast.LENGTH_SHORT);
                tMsg.show();
                //finish();
            }
        });
    }
}