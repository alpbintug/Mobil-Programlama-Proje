package com.example.a17011066_alp_bintug_uzun_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private  DBHelper db;
    private int STORAGE_PERMISSION = 1;
    Activity main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        main = MainActivity.this;
        if(ContextCompat.checkSelfPermission(main, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestStoragePermission();
        }
    }
    public void buttonClothes(View view){
        Intent open = new Intent(getApplicationContext(),ActivityClothes.class);
        open.putExtra("SOURCE",0);
        startActivity(open);
    }
    public void buttonDrawers(View view){
        Intent open = new Intent(getApplicationContext(),ActivityDrawers.class);
        startActivity(open);
    }
    public void buttonCombines(View view){
        Intent open = new Intent(getApplicationContext(),ActivityCombines.class);
        startActivity(open);
    }
    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(main,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(main)
                    .setTitle("We require your permission")
                    .setMessage("We need to access your storage to pick photos for your clothing.")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(main, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },STORAGE_PERMISSION);
                        }
                    }).setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },STORAGE_PERMISSION);
        }
    }
}