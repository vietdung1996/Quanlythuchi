package com.vietdung.quanlythuchi.presenter.logIn;

import com.vietdung.quanlythuchi.database.DBUser;

public class PresenterLogicLogin implements PresenterLogin{
    DBUser dbUser;

    public PresenterLogicLogin(DBUser dbUser) {
        this.dbUser = dbUser;
    }

    @Override
    public boolean CheckLogin(String user, String pass) {
        return dbUser.CheckLogin(user,pass);
    }
}
