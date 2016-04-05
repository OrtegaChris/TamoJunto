package com.renatoandrade.tamojunto.DBManagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Renato on 4/3/2016.
 */
public class DBCreator extends SQLiteOpenHelper {

    public static final String DB_NAME = "tamojunto.db";
    public static final String TB_BUSINESSES = "Business";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String LOCATION = "location";
    public static final String PHONE = "phone";
    public static final int VERSION = 1;

    public DBCreator(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TB_BUSINESSES + "(" +
                ID + " integer primary key autoincrement, " +
                NAME + " text, " +
                CATEGORY + " text, " +
                DESCRIPTION + " text, " +
                LOCATION + " text, " +
                PHONE + " text" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TB_BUSINESSES);
        onCreate(db);
    }
}
