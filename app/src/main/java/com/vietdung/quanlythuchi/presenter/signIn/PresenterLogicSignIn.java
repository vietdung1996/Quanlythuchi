package com.vietdung.quanlythuchi.presenter.signIn;

import android.content.Context;

import com.vietdung.quanlythuchi.database.DBUser;
import com.vietdung.quanlythuchi.model.User;
import com.vietdung.quanlythuchi.view.activity.SignIn.ViewSignIn;

public class PresenterLogicSignIn implements PresenterSignIn {
    User user;
    DBUser dbUser;
    ViewSignIn viewSignIn;

    public PresenterLogicSignIn(ViewSignIn viewSignIn) {
        this.viewSignIn = viewSignIn;
    }

    @Override
    public void SignIn(String userName, String passWord, Context context) {
        user = new User(userName,passWord);
        dbUser = new DBUser(context);
        dbUser.addUser(user);
        viewSignIn.SigninSuccess();
    }
}
