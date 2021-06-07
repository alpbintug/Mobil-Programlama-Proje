package com.example.a17011066_alp_bintug_uzun_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private  DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        //this.deleteDatabase(db.getDatabaseName());

        Log.d("Deniyoz",db.DATABASE_NAME);


        db.AddClothToCombine(1,1);


        Log.d("Deniyoz",String.valueOf(db.GetCombineSize(1)));
        Log.d("Deniyoz",db.DATABASE_NAME);
        Log.d("Deniyoz",db.DATABASE_NAME);
    }
}