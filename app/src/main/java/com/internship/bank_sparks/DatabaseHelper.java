package com.internship.bank_sparks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String TABLE_NAME = "user_table";
    private String TABLE_NAME1 = "transfers_table";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (PHONENUMBER INTEGER PRIMARY KEY ,NAME TEXT,BALANCE DECIMAL,EMAIL VARCHAR,ACCOUNT_NO VARCHAR,IFSC_CODE VARCHAR)");
        db.execSQL("create table " + TABLE_NAME1 + " (TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FROMNAME TEXT,TONAME TEXT,AMOUNT DECIMAL,STATUS TEXT)");
        db.execSQL("insert into user_table values(9105845218,'Riyanshi Verma',15567.00,'riyanshi@gmail.com','XXXXXXXXXXXX8584','MBNK0005233')");
        db.execSQL("insert into user_table values(9792052185,'Arav Verma',18582.67,'aravvv@gmail.com','XXXXXXXXXXXX8579','MBNK0005253')");
        db.execSQL("insert into user_table values(9295218583,'Rudraksh Verma ',17459.56,'rudraksh@gmail.com','XXXXXXXXXXXX8583','MBNK0005343')");
        db.execSQL("insert into user_table values(9218591305,'Reyansh Verma',15340.01,'reyansh@gmail.com','XXXXXXXXXXXX8591','MBNK0005673')");
        db.execSQL("insert into user_table values(9585980521,'Alok Verma',26003.48,'alokv@gmail.com','XXXXXXXXXXXX1875','MBNK0005933')");
        db.execSQL("insert into user_table values(9185908052,'Santosh Verma',94415.16,'santoshV@gmail.com','XXXXXXXXXXXX3772','MBNK0009833')");
        db.execSQL("insert into user_table values(9185758052,'Bhupendra Verma',59236.00,'bhupendra@gmail.com','XXXXXXXXXXXX5256','MBNK0005298')");
        db.execSQL("insert into user_table values(9318737052,'Rashmi Verma',85737.22,'rashmi@gmail.com','XXXXXXXXXXXX2734','MBNK0005211')");
        db.execSQL("insert into user_table values(9218570565,'Bhavana Verma',43198.46,'bhavana@gmail.com','XXXXXXXXXXXX5761','MBNK0006733')");
        db.execSQL("insert into user_table values(9885805217,'Arpita Verma',42173.90,'arpita@gmail.com','XXXXXXXXXXXX4741','MBNK0003233')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public Cursor readalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table", null);
        return cursor;
    }

    public Cursor readparticulardata(String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table where phonenumber = " + phonenumber, null);
        return cursor;
    }

    public Cursor readselectuserdata(String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table except select * from user_table where phonenumber = " + phonenumber, null);
        return cursor;
    }

    public void updateAmount(String phonenumber, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update user_table set balance = " + amount + " where phonenumber = " + phonenumber);
    }

    public Cursor readtransferdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from transfers_table", null);
        return cursor;
    }

    public boolean insertTransferData(String date, String from_name, String to_name, String amount, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", date);
        contentValues.put("FROMNAME", from_name);
        contentValues.put("TONAME", to_name);
        contentValues.put("AMOUNT", amount);
        contentValues.put("STATUS", status);
        Long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}