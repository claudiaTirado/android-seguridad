package com.example.proyecto_security;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BaseDeDatosHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME = "USUARIO";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String UUID = "uuid";
    static final String DB_NAME = "CLAUDIATIRADO.DB";
    static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT , "+ EMAIL + " TEXT , "+ UUID + " TEXT);";


    public BaseDeDatosHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(String nombre, String email, String uuid, Context context){
        ContentValues contentValue = new ContentValues();
        contentValue.put(NAME,nombre);
        contentValue.put(EMAIL, email);
        contentValue.put(UUID, uuid);
        SQLiteOpenHelper dbHelper = new BaseDeDatosHelper(context);
        db = dbHelper.getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValue);
    }
    public int update(String nombre, String email, String uuid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,nombre);
        contentValues.put(EMAIL, email);
        contentValues.put(UUID, uuid);
        int i = db.update(TABLE_NAME, contentValues, ID + " = " + uuid, null);
        return i;
    }
    public void delete(long _id) {
        db.delete(TABLE_NAME, ID + "=" + _id, null);
    }


}
