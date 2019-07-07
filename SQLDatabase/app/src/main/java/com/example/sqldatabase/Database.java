package com.example.sqldatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class Database extends SQLiteOpenHelper {

    public Database(Context context, String name, int version, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, version);
    }

    // Phương thức ko trả về , insert , update , delete,...
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    // Phương thức trả về  kết quả truy vấn, select
    // Dùng con chỏ cusor trả về
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();// đọc dữ liệu ra
        return database.rawQuery(sql,null);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
