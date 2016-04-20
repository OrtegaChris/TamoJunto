package com.renatoandrade.tamojunto.DBManagement;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by arfhanahmad on 4/17/16.
 */
public class EventController {

    private SQLiteDatabase db;
    private DBCreator eventTB;

    public EventController(Context context) {
        eventTB = new DBCreator(context);
    }

    public String insert(String name, String date, String description, String location, String time) {
        ContentValues values;
        long result;
        db = eventTB.getWritableDatabase();
        values = new ContentValues();
        values.put(DBCreator.NAME, name);
        values.put(DBCreator.DATE, date);
        values.put(DBCreator.DESCRIPTION, description);
        values.put(DBCreator.LOCATION, location);
        values.put(DBCreator.TIME, time);
        result = db.insert(DBCreator.TB_Event, null, values);
        db.close();

        if (result == -1)
            return "An error occurred";
        else
            return "Event created";
    }

    public Cursor listAll() {
        Cursor cursor;
        String[] fields = {DBCreator.ID, DBCreator.NAME};
        db = eventTB.getReadableDatabase();
        cursor = db.query(DBCreator.TB_Event, fields, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor returnEventById(int id) {
        Cursor cursor;
        String[] fields = {DBCreator.ID, DBCreator.NAME, DBCreator.DATE, DBCreator.DESCRIPTION,
                DBCreator.LOCATION, DBCreator.TIME};
        String where = DBCreator.ID + "=" + id;
        db = eventTB.getReadableDatabase();
        cursor = db.query(DBCreator.TB_Event, fields, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String update(int id, String name, String date, String description, String location, String time) {
        ContentValues values;
        String where;
        long result;

        db = eventTB.getWritableDatabase();
        where = DBCreator.ID + "=" + id;

        values = new ContentValues();
        values.put(DBCreator.NAME, name);
        values.put(DBCreator.DATE, date);
        values.put(DBCreator.DESCRIPTION, description);
        values.put(DBCreator.LOCATION, location);
        values.put(DBCreator.TIME, time);

        result = db.update(DBCreator.TB_Event, values, where, null);
        db.close();

        if (result == -1)
            return "An error occurred";
        else
            return "Event updated";
    }

    public String delete(int id){
        long result;

        String where = DBCreator.ID + "=" + id;

        db = eventTB.getWritableDatabase();
        result = db.delete(DBCreator.TB_Event, where, null);
        db.close();

        if (result == -1)
            return "An error occurred";
        else
            return "Event deleted";
    }

}
