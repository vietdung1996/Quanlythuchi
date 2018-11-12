package com.vietdung.quanlythuchi.view.activity.SignIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vietdung.quanlythuchi.R;
import com.vietdung.quanlythuchi.presenter.signIn.PresenterLogicSignIn;
import com.vietdung.quanlythuchi.view.activity.Login.MainActivity;

public class SignInActivity extends AppCompatActivity implements ViewSignIn {
    EditText etEmail, etPassword;
    Button btnSignIn;
    PresenterLogicSignIn logicSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initView();
        addEvents();
    }

    private void addEvents() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etEmail.getText().toString().trim();
                String pass=etPassword.getText().toString().trim();
                logicSignIn.SignIn(user,pass,getApplication());
                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(i);
                //Log.d("signin", "onClick: sign in success");
            }
        });
    }

    private void initView() {
        etEmail = findViewById(R.id.et_Signin_Email);
        etPassword = findViewById(R.id.et_Signin_Password);
        btnSignIn = findViewById(R.id.btn_SignIn_Activity);
        logicSignIn = new PresenterLogicSignIn(this);
    }

    @Override
    public void SigninSuccess() {
        Toast.makeText(this,"Sign in success",Toast.LENGTH_SHORT).show();
    }
}
