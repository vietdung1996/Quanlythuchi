package com.vietdung.quanlythuchi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vietdung.quanlythuchi.model.User;

public class DBUser extends SQLiteOpenHelper {
    private Context context;

    private static final String DBUser = "DataUser";
    private static final String DBUser_Name = "UserName";
    private static final String DBUser_Password = "Password";



    public DBUser(Context context) {
        super(context,DBUser,null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbNote = "CREATE TABLE " + DBUser + " (" + DBUser_Name + " TEXT, " +DBUser_Password+" TEXT)";
        sqLiteDatabase.execSQL(tbNote);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DBUser);
        onCreate(sqLiteDatabase);

    }

    public void addUser(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUser_Name,user.getUserName());
        values.put(DBUser_Password,user.getPassWord());
        sqLiteDatabase.insert(DBUser,null,values);
        Log.d("addUser", "addUser: ");
        sqLiteDatabase.close();
    }

    public boolean CheckLogin(String user,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from "+DBUser+" Where "+DBUser_Name+" = '"+ user +"'And " + DBUser_Password+" ='"+pass+"'";
        Cursor cursor =db.rawQuery(query, null);
        cursor.moveToFirst();
        String username=" ";
        while (!cursor.isAfterLast()){
            username=cursor.getString(cursor.getColumnIndex(DBUser_Name));
            cursor.moveToNext();
        }
        Log.d("username", "CheckLogin: "+username);
        if(username.equals(" ")){
            return false;
        }
        return true;
    }
}
