package com.example.a17011066_alp_bintug_uzun_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mobil_project.db";
    public static final int DATABASE_VERSION = 1;
    public final int TROUSERS = 0;
    public final int HAT = 1;
    public final int T_SHIRT = 2;
    public final int COAT = 3;
    public final int TRUNKS = 4;
    public final int DRESS = 5;
    public final int SLIPPERS = 6;
    public final int SHOES = 7;
    public final int SKIRT = 8;
    public final int SWEATER = 9;



    //region TABLE_CLOTHES
    public static final String TABLE_CLOTHES = "Clothes";

    public static final String CLOTH_TYPE = "Cloth_Type";
    public static final String CLOTH_COLOR = "Color";
    public static final String CLOTH_PATTERN = "Pattern";
    public static final String CLOTH_PRICE = "Price";
    public static final String CLOTH_DATE_PURCHASED = "Date_Purchased";
    public static final String CLOTH_ID = "Cloth_ID";
    public static final String CLOTH_PHOTO = "Photo_Path";

    private static final String CREATE_TABLE_CLOTHES = "CREATE TABLE "+TABLE_CLOTHES+"(" +
            CLOTH_COLOR+" TEXT NOT NULL, " +
            CLOTH_PATTERN+" TEXT NOT NULL, " +
            CLOTH_PRICE+" REAL NOT NULL, " +
            CLOTH_DATE_PURCHASED+" TEXT NOT NULL, " +
            CLOTH_TYPE+" INTEGER NOT NULL, " +
            CLOTH_PHOTO + " TEXT NOT NULL, "+
            CLOTH_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE" +
            ")";
    //endregion

    //region TABLE_DRAWER
    public static final String TABLE_DRAWER = "Drawers";

    public static final String DRAWER_ID = "Drawer_ID";
    public static final String DRAWER_CLOTH_TYPE = "Drawer_Cloth_Type";
    public static final String DRAWER_TAG = "Drawer_Tag";

    public static final String CREATE_TABLE_DRAWER = "CREATE TABLE "+TABLE_DRAWER+"(" +
            DRAWER_CLOTH_TYPE+" INTEGER NOT NULL, " +
            DRAWER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
            DRAWER_TAG + " TEXT NOT NULL"+
            ")";
    //endregion

    //region TABLE_DRAWER_CLOTHES
    public static final String TABLE_DRAWER_CLOTHES = "Drawer_Clothes";

    public static final String CREATE_TABLE_DRAWER_CLOTHES = "CREATE TABLE "+TABLE_DRAWER_CLOTHES+"(" +
            CLOTH_ID+" INTEGER NOT NULL, " +
            DRAWER_ID+" INTEGER NOT NULL" +
            ")";
    //endregion

    //region TABLE_COMBINE_CLOTHES
    public static final String TABLE_COMBINE_CLOTHES = "Combine_Clothes";
    public static final String COMBINE_ID = "Combine_ID";
    public static final String CREATE_TABLE_COMBINE_CLOTHES= "CREATE TABLE "+TABLE_COMBINE_CLOTHES+"(" +
            CLOTH_ID+" INTEGER NOT NULL, " +
            COMBINE_ID+" INTEGER NOT NULL" +
            ")";
    //endregion

    //region TABLE_COMBINES
    public static final String TABLE_COMBINES= "Combines";
    public static final String COMBINE_NAME = "Combine_Name";
    public static final String CREATE_TABLE_COMBINES= "CREATE TABLE "+TABLE_COMBINES+"(" +
            COMBINE_NAME+" TEXT NOT NULL, " +
            COMBINE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
            ")";
    //endregion

    //region TABLE_EVENTS

    public static final String TABLE_EVENT = "Events";
    public static final String EVENT_ID = "Event_ID";
    public static final String EVENT_TYPE = "Event_Type";
    public static final String EVENT_DATE = "Event_Date";
    public static final String EVENT_NAME = "Event_Name";
    public static final String EVENT_LOCATION = "Event_Location";

    public static final String CREATE_TABLE_EVENT = "CREATE TABLE "+TABLE_EVENT+"(" +
            COMBINE_ID+ " INTEGER NOT NULL, " +
            EVENT_TYPE + " TEXT NOT NULL, " +
            EVENT_DATE + " TEXT NOT NULL, " +
            EVENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT" +
            " UNIQUE, " +
            EVENT_NAME + " TEXT NOT NULL, "+
            EVENT_LOCATION + " TEXT NOT NULL"+
            ")";

    //endregion
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_EVENT);
    db.execSQL(CREATE_TABLE_COMBINES);
    db.execSQL(CREATE_TABLE_DRAWER_CLOTHES);
    db.execSQL(CREATE_TABLE_DRAWER);
    db.execSQL(CREATE_TABLE_CLOTHES);
    db.execSQL(CREATE_TABLE_COMBINE_CLOTHES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMBINES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRAWER_CLOTHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRAWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMBINE_CLOTHES);

        // Create tables again
        onCreate(db);
    }

    public boolean AddCloth(Cloth cloth){
        Log.d("price:",String.valueOf(cloth.getPrice()));
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        cv.put(CLOTH_COLOR,cloth.getColor());
        cv.put(CLOTH_DATE_PURCHASED,df.format(cloth.getDatePurchased()));
        cv.put(CLOTH_PATTERN,cloth.getPattern());
        cv.put(CLOTH_PHOTO,cloth.getPhotoPath());
        cv.put(CLOTH_PRICE,cloth.getPrice());
        cv.put(CLOTH_TYPE,cloth.getClothType());

        long result = db.insert(TABLE_CLOTHES,null,cv);
        db.close();
        return  result!=-1;
    }

    public ArrayList<Cloth> GetClothes(int ID,boolean fromCombine) throws ParseException {
        ArrayList<Cloth> clothes = new ArrayList<Cloth>();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        String q;
        if(ID>=0&&fromCombine){
            q = "SELECT * FROM " + TABLE_COMBINE_CLOTHES + " WHERE " + COMBINE_ID + " = " + ID;
        }
        else if(ID>=0){

            q = "SELECT * FROM " + TABLE_DRAWER_CLOTHES + " WHERE " + COMBINE_ID + " = " + ID;
        }
        else {
            q = "SELECT * FROM " + TABLE_CLOTHES;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(q,null);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            date = df.parse(cursor.getString(cursor.getColumnIndex(CLOTH_DATE_PURCHASED)));
            clothes.add(new Cloth(
                    cursor.getInt(cursor.getColumnIndex(CLOTH_TYPE)),
                    cursor.getString(cursor.getColumnIndex(CLOTH_COLOR)),
                    cursor.getString(cursor.getColumnIndex(CLOTH_PATTERN)),
                    cursor.getFloat(cursor.getColumnIndex(CLOTH_PRICE)),
                    date,
                    cursor.getString(cursor.getColumnIndex(CLOTH_PHOTO)),
                    cursor.getInt(cursor.getColumnIndex(CLOTH_ID))
            ));
        }
        return clothes;
    }
    public boolean DeleteCloth(int ClothID){
        SQLiteDatabase db = this.getWritableDatabase();
        long result =  db.delete(TABLE_CLOTHES, CLOTH_ID+"=?",new String[]{String.valueOf(ClothID)});
        db.close();
        return result>0;
    }

    public boolean UpdateCloth(Cloth cloth){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        cv.put(CLOTH_TYPE,cloth.getClothType());
        cv.put(CLOTH_PRICE,cloth.getPrice());
        cv.put(CLOTH_PHOTO,cloth.getPhotoPath());
        cv.put(CLOTH_PATTERN,cloth.getPattern());
        cv.put(CLOTH_COLOR,cloth.getColor());
        cv.put(CLOTH_DATE_PURCHASED,df.format(cloth.getDatePurchased()));
        long result = db.update(TABLE_CLOTHES, cv, CLOTH_ID + "=?",new String[]{String.valueOf(cloth.getID())});
        db.close();
        return result > 0;
    }

    public void AddClothToCombine(int combineID, int clothID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COMBINE_ID,combineID);
        cv.put(CLOTH_ID,clothID);
        db.insert(TABLE_COMBINES,null,cv);
        cv.clear();
        db.close();
    }
    public int GetCombineSize(int combineID){
        String q = "SELECT * FROM " + TABLE_COMBINES + " WHERE " + COMBINE_ID + " = " + combineID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q,null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

}