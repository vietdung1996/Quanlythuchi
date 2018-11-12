package com.vietdung.quanlythuchi.view.activity.Login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vietdung.quanlythuchi.R;
import com.vietdung.quanlythuchi.database.DBUser;
import com.vietdung.quanlythuchi.presenter.logIn.PresenterLogicLogin;
import com.vietdung.quanlythuchi.presenter.signIn.PresenterLogicSignIn;
import com.vietdung.quanlythuchi.view.activity.SignIn.SignInActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail, etPassword;
    Button btnLogIn, btnSignIn;

    PresenterLogicLogin presenterLogicLogin;
    private CallbackManager callbackManager;
    private LoginButton btnLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        addEvents();

        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(
                    "com.vietdung.quanlythuchi",
                    PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for (Signature signature : info.signatures) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            md.update(signature.toByteArray());
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        }
    }

    private void addEvents() {
        setEventButton();
    }

    private void setEventButton() {
        btnLogIn.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this,"User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this,"Login canceled.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this,"Login fail.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        etEmail = findViewById(R.id.et_Email);
        etPassword = findViewById(R.id.et_Password);
        btnLogIn = findViewById(R.id.btn_Login);
        btnSignIn = findViewById(R.id.btn_SignIn);
        btnLoginFacebook = findViewById(R.id.btn_LoginFaceBook);

        DBUser dbUser = new DBUser(this);
        presenterLogicLogin =  new PresenterLogicLogin(dbUser);

        callbackManager = CallbackManager.Factory.create();


        AppEventsLogger.activateApp(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_SignIn:
                Intent i = new Intent(this, SignInActivity.class);
                startActivity(i);
                break;
            case R.id.btn_Login:
                String user = etEmail.getText().toString().trim();
                String pass= etPassword.getText().toString().trim();
                boolean check = presenterLogicLogin.CheckLogin(user,pass);
                if(check){
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Success fails",Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
