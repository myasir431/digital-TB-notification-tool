package com.testing.yasir.userinterface;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yasir on 3/29/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VESION = 1;
    private static final String DATABASE_NAME = "DTNT.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PASSOWRD = "password";
    SQLiteDatabase db;
    private static  final String TABLE_CREATE = "create table users (id integer primary key not null,"+
            "name text not null, location text not null, password text not null);";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VESION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertUser(connectDB c){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "Select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLUMN_ID,count);
        values.put(COLUMN_NAME,c.getName());
        values.put(COLUMN_LOCATION,c.getLocation());
        values.put(COLUMN_PASSOWRD,c.getPassword());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public String searchpass(String user)
    {
        db = this.getReadableDatabase();
        String query = "Select name,password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String u, p;
        p = "Not Found";
        if(cursor.moveToFirst()){
        do {
            u = cursor.getString(0); //on 0 name (in query)
            if (u.equals(user)) {
                p = cursor.getString(1); // 1 means password from query.
                break;
            }
        }
        while (cursor.moveToNext());
        }
        return p;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
