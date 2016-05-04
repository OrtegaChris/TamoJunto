package com.renatoandrade.tamojunto.DBManagement;

import android.content.ContentValues;
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
    public static final int VERSION = 3;
    public static final String TB_Event="Event";
    public static final String DATE = "date";
    public static final String TIME = "time";

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

        sql = "CREATE TABLE " + TB_Event + "(" +
                ID + " integer primary key autoincrement, " +
                NAME + " text, " +
                DATE + " text, " +
                DESCRIPTION + " text, " +
                LOCATION + " text, " +
                TIME + " text" +
                ")";
        db.execSQL(sql);

        mockValues(db);
    }

    private void mockValues(SQLiteDatabase db) {
        newBusiness(db, "Casa Restaurant","Restaurant","Open from 6pm to 11pm","72 Bedford St, New York","(212) 366-9410");
        newBusiness(db, "Villa Brazil","Restaurant","Open from 11am to 9pm","43-16 34th Ave, Long Island City","(347) 670-4202");
        newBusiness(db, "US Brazil Deli & Grocery Inc","Grocery Store","Working hours not available","41 02 34th Avenue, 34th Ave","(718) 482-0219");
        newBusiness(db, "Rio Market Inc","Grocery Store","Open from 8am to 9pm","32-15 36th Ave, Astoria","(718) 706-7272");
        newBusiness(db, "Cafe Patoro","Bar or Pub","Cafe Patoro bar"," 223 Front St, New York","(917) 262-0031");
        newBusiness(db, "Miss Favela","Bar or Pub","Open from 12pm to 12am","57 S 5th St, Brooklyn","(718) 230-4040");
        newBusiness(db, "Beco","Shopping","Open from 7am to 12am","45 Richardson St, Brooklyn","(718) 599-1645");
        newBusiness(db, "Texas de Brazil","Shopping","Closed","1011 3rd Ave, New York,","(212) 537-0060");

        newEvent(db, "College Festa","10/10/2016","Best Brazilian Party","The Parlour","10:00");
        newEvent(db, "Samba Parade","11/11/2016","Best Samba Parade","6th Avenue","11:00");

    }

    private void newEvent(SQLiteDatabase db, String name, String date, String description, String location, String time) {
        ContentValues values;

        values = new ContentValues();
        values.put(DBCreator.NAME, name);
        values.put(DBCreator.DATE, date);
        values.put(DBCreator.DESCRIPTION, description);
        values.put(DBCreator.LOCATION, location);
        values.put(DBCreator.TIME, time);
        db.insert(DBCreator.TB_Event, null, values);
    }

    private void newBusiness(SQLiteDatabase db, String name, String category, String description, String location, String phone) {
        ContentValues values;

        values = new ContentValues();
        values.put(DBCreator.NAME, name);
        values.put(DBCreator.CATEGORY, category);
        values.put(DBCreator.DESCRIPTION, description);
        values.put(DBCreator.LOCATION, location);
        values.put(DBCreator.PHONE, phone);
        db.insert(DBCreator.TB_BUSINESSES, null, values);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_BUSINESSES);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Event);
        onCreate(db);
    }
}
