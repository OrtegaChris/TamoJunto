package com.renatoandrade.tamojunto.DBManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Renato on 4/3/2016.
 */
public class BusinessController {

    private SQLiteDatabase db;
    private DBCreator businessesDB;

    public BusinessController(Context context) {
        businessesDB = new DBCreator(context);
    }

    public String insert(String name, String category, String description, String location, String phone) {
        ContentValues values;
        long result;
        db = businessesDB.getWritableDatabase();
        values = new ContentValues();
        values.put(DBCreator.NAME, name);
        values.put(DBCreator.CATEGORY, category);
        values.put(DBCreator.DESCRIPTION, description);
        values.put(DBCreator.LOCATION, location);
        values.put(DBCreator.PHONE, phone);
        result = db.insert(DBCreator.TB_BUSINESSES, null, values);
        db.close();

        if (result == -1)
            return "An error occurred";
        else
            return "Business created";
    }

    public Cursor listAll() {
        Cursor cursor;
        String[] fields = {DBCreator.ID, DBCreator.NAME};
        db = businessesDB.getReadableDatabase();
        cursor = db.query(DBCreator.TB_BUSINESSES, fields, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor returnBusinessById(int id) {
        Cursor cursor;
        String[] fields = {DBCreator.ID, DBCreator.NAME, DBCreator.CATEGORY, DBCreator.DESCRIPTION,
                DBCreator.LOCATION, DBCreator.PHONE};
        String where = DBCreator.ID + "=" + id;
        db = businessesDB.getReadableDatabase();
        cursor = db.query(DBCreator.TB_BUSINESSES, fields, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String update(int id, String name, String category, String description, String location, String phone) {
        ContentValues values;
        String where;
        long result;

        db = businessesDB.getWritableDatabase();
        where = DBCreator.ID + "=" + id;

        values = new ContentValues();
        values.put(DBCreator.NAME, name);
        values.put(DBCreator.CATEGORY, category);
        values.put(DBCreator.DESCRIPTION, description);
        values.put(DBCreator.LOCATION, location);
        values.put(DBCreator.PHONE, phone);

        result = db.update(DBCreator.TB_BUSINESSES, values, where, null);
        db.close();

        if (result == -1)
            return "An error occurred";
        else
            return "Business updated";
    }

    public String delete(int id){
        long result;

        String where = DBCreator.ID + "=" + id;

        db = businessesDB.getWritableDatabase();
        result = db.delete(DBCreator.TB_BUSINESSES, where, null);
        db.close();

        if (result == -1)
            return "An error occurred";
        else
            return "Business deleted";
    }

    public Cursor search(String name, String category, String description, String location, String phone) {
        Cursor cursor;
        String[] fields = {DBCreator.ID, DBCreator.NAME};
        db = businessesDB.getReadableDatabase();

        String where = "";
        int conditions = 0;
        if (name != null) {
            conditions++;
            where += DBCreator.NAME + "= '" + name + "'";
        }
        if (category != null) {
            conditions++;
            if (conditions > 1)
                where = " AND ";
            where += DBCreator.CATEGORY + "= '" + category+ "'";
        }
        if (description != null) {
            conditions++;
            if (conditions > 1)
                where = " AND ";
            where += DBCreator.DESCRIPTION + "= '" + description+ "'";
        }

        if (location != null) {
            conditions++;
            if (conditions > 1)
                where = " AND ";
            where += DBCreator.LOCATION + "= '" + location+ "'";
        }

        if (phone != null) {
            conditions++;
            if (conditions > 1)
                where = " AND ";
            where += DBCreator.PHONE + "= '" + phone+ "'";
        }

        if (where.isEmpty())
            cursor = db.query(DBCreator.TB_BUSINESSES, fields, null, null, null, null, null, null);
        else
            cursor = db.query(DBCreator.TB_BUSINESSES, fields, where, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}
