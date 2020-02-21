package com.example.createupdatewithdelete_data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper {
    Context context;
    MyDataBase myDataBase;

    public long insert(ModelClass modelClass) {
        SQLiteDatabase sqLiteDatabase = myDataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDataBase.NAME, modelClass.getName());
        contentValues.put(myDataBase.EMAIL, modelClass.getEmail());
        contentValues.put(myDataBase.ADDRESS, modelClass.getAddress());
        contentValues.put(myDataBase.PASSWORD, modelClass.getPassword());
        long id = sqLiteDatabase.insert(myDataBase.TABLE_NAME, null, contentValues);
        return id;
    }

    public boolean update(String id, String name, String email, String address, String password) {
        SQLiteDatabase sqLiteDatabase = myDataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDataBase.UID, id);
        contentValues.put(myDataBase.NAME, name);
        contentValues.put(myDataBase.EMAIL, email);
        contentValues.put(myDataBase.ADDRESS, address);
        contentValues.put(myDataBase.PASSWORD, password);
        sqLiteDatabase.update(myDataBase.TABLE_NAME, contentValues, myDataBase.UID + " = ?", new String[]{id});
        return true;
    }

    public int delete(String id) {
        SQLiteDatabase sqLiteDatabase = myDataBase.getWritableDatabase();
        int value = sqLiteDatabase.delete(myDataBase.TABLE_NAME, myDataBase.UID + " = ?", new String[]{id});
        return value;
    }

    public Cursor showAll() {
        SQLiteDatabase sqLiteDatabase = myDataBase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyDataBase.TABLE_NAME, null);
        return cursor;
    }

    public MyDataBaseHelper(Context context) {
        this.context = context;
        myDataBase = new MyDataBase(context);
    }


    class MyDataBase extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "kibriaDta.db";
        private static final String TABLE_NAME = "TABLE_NAME";
        private static final String UID = "_id";
        private static final String NAME = "NAME";
        private static final String EMAIL = "EMAIL";
        private static final String ADDRESS = "ADDRESS";
        private static final String PASSWORD = "PASSWORD";
        private static final int DATABASE_VERSION = 1;
        Context context;
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " VARCHAR(77) , "
                + EMAIL + " VARCHAR(77) , "
                + ADDRESS + " VARCHAR(77) , "
                + PASSWORD + " VARCHAR(77));";

        private static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;

        public MyDataBase(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context, "table created", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, "not create" + e, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
                Toast.makeText(context, "table upgreated", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, "table update pb" + e, Toast.LENGTH_LONG).show();
            }
        }
    }
}
