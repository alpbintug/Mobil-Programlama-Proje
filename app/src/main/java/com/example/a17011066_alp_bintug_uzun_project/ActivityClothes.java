package com.example.a17011066_alp_bintug_uzun_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityClothes extends AppCompatActivity {

    int alpha = 250;
    int alphaEmpty = 200;
    public static final int PICK_IMAGE = 1;
    DateFormat dateFormat;
    LinearLayout layout;
    View viewToAdd;
    int[] rgb = {255,110,0};
    int SOURCE;
    int MAIN_MENU = 0;
    int DRAWERS = 1;
    int COMBINES = 2;
    int SOURCE_ID;
    int CLOTH_TYPE;
    int COMBINE_ID;
    ArrayAdapter<CharSequence> adapter;
    int lastTo255 = 0;
    int colorChange = 17;
    private DBHelper db;
    ImageView imageCloth;
    Uri imageURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        Intent intent = getIntent();

        SOURCE = intent.getIntExtra("SOURCE",-1);
        SOURCE_ID = intent.getIntExtra("SOURCE_ID",-1);
        CLOTH_TYPE = intent.getIntExtra("CLOTH_TYPE",-1);
        COMBINE_ID = intent.getIntExtra("COMBINE_ID",-1);
        String tag = intent.getStringExtra("TAG");
        if(tag!=null)
            getSupportActionBar().setTitle(tag);
        else
            getSupportActionBar().hide();

        db = new DBHelper(this);
        layout = (LinearLayout)findViewById(R.id.clothesLinearList);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        viewToAdd = LayoutInflater.from(this).inflate(R.layout.cloth_item,null);

        adapter = ArrayAdapter.createFromResource(this,R.array.clothTypes, android.R.layout.simple_spinner_item);
        Spinner dropdown = (Spinner)viewToAdd.findViewById(R.id.spinnerClothType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        if(SOURCE == MAIN_MENU){
            ArrayList<Cloth> clothes = db.GetClothes(-1,false);
            PlaceClothes(clothes,false);
            addEmptyCard();
        }
        else {
            ArrayList<Cloth> drawerClothes = db.GetClothes(SOURCE_ID,CLOTH_TYPE<0);
            ArrayList<Cloth> clothesWODrawer = db.GetClothesWODrawer();
            clothesWODrawer.removeIf(e -> e.getClothType()!=CLOTH_TYPE || CLOTH_TYPE<0);
            PlaceClothes(drawerClothes,true);
            if(SOURCE==DRAWERS)
                PlaceClothes(clothesWODrawer,false);
        }
    }

    private void PlaceClothes(ArrayList<Cloth> clothes, boolean isInDrawer){
        for(Cloth cloth: clothes){

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.cloth_item,null);

            //region ARKA PLAN RENKLERINI AYARLAMA
            int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
            calculateColors();
            //endregion
            EditText textPattern = (EditText)viewToAdd.findViewById(R.id.editTextPattern);
            EditText textDate = (EditText)viewToAdd.findViewById(R.id.editTextDate);
            EditText textPrice = (EditText)viewToAdd.findViewById(R.id.editTexPrice);
            EditText textRedValue = (EditText)viewToAdd.findViewById(R.id.editTextColor);
            Button buttonEdit = (Button)viewToAdd.findViewById(R.id.buttonSave);
            imageCloth = (ImageView)viewToAdd.findViewById(R.id.imageViewCloth);
            Spinner dropdown = (Spinner)viewToAdd.findViewById(R.id.spinnerClothType);
            Button buttonDelete = (Button)viewToAdd.findViewById(R.id.buttonDelete);
            TextView textID = (TextView)viewToAdd.findViewById(R.id.textClothID);

            textPattern.setText(cloth.getPattern());
            textDate.setText(dateFormat.format(cloth.getDatePurchased()));
            textPrice.setText(String.valueOf(cloth.getPrice()));
            textRedValue.setText(cloth.getColor());
            textID.setText(String.valueOf(cloth.getID()));
            buttonEdit.setText("EDIT");


            if(SOURCE==MAIN_MENU){
                buttonDelete.setBackgroundColor(Color.rgb(100,0,0));
                buttonDelete.setText("X");
            }
            else
                {
                if(SOURCE == DRAWERS && isInDrawer){
                    buttonDelete.setBackgroundColor(Color.rgb(100,0,0));
                    buttonDelete.setText("X");
                }
                else if(CLOTH_TYPE<0){
                    buttonDelete.setBackgroundColor(Color.rgb(100,0,0));
                    buttonDelete.setText("X");
                }
                else{
                    buttonDelete.setBackgroundColor(Color.rgb(0,100,0));
                    buttonDelete.setText("+");
                }
            }

            imageCloth.setImageDrawable(Drawable.createFromPath(cloth.getPhotoPath()));
            imageCloth.setTag(cloth.getPhotoPath());

            textPattern.setEnabled(false);
            textDate.setEnabled(false);
            textPrice.setEnabled(false);
            textRedValue.setEnabled(false);
            imageCloth.setEnabled(false);
            dropdown.setEnabled(false);
            buttonDelete.setVisibility(View.VISIBLE);

            adapter = ArrayAdapter.createFromResource(this,R.array.clothTypes, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);
            dropdown.setSelection(cloth.getClothType());
            viewToAdd.findViewById(R.id.cardCloth).setBackgroundColor(bgc);

            layout.addView(viewToAdd);
        }
    }

    private void addEmptyCard(){
        calculateColors();
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.cloth_item,null);
        int bgc = Color.argb(alphaEmpty,rgb[0],rgb[1],rgb[2]);
        ((Button)viewToAdd.findViewById(R.id.buttonSave)).setText("SAVE");
        viewToAdd.findViewById(R.id.cardCloth).setBackgroundColor(bgc);
        imageCloth = (ImageView)viewToAdd.findViewById(R.id.imageViewCloth);
        imageCloth.setTag("");

        adapter = ArrayAdapter.createFromResource(this,R.array.clothTypes, android.R.layout.simple_spinner_item);
        Spinner dropdown = (Spinner)viewToAdd.findViewById(R.id.spinnerClothType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown.setAdapter(adapter);
        layout.addView(viewToAdd);
    }
    private void calculateColors(){

        if(rgb[(lastTo255+1)%3]<255){
            rgb[(lastTo255+1)%3]+=colorChange;
            if(rgb[(lastTo255+1)%3]>255)
                rgb[(lastTo255+1)%3]=255;
        }
        else if(rgb[lastTo255]>0){
            rgb[lastTo255]-=colorChange;
            if(rgb[lastTo255]<0)
                rgb[lastTo255]=0;
        }
        else{
            lastTo255=(lastTo255+1)%3;
        }
    }

    public void buttonSave(View view){
        Button buttonEdit = (Button)view;
        ViewGroup parentView = (ViewGroup)view.getParent();

        EditText textPattern = (EditText)parentView.findViewById(R.id.editTextPattern);
        EditText textDate = (EditText)parentView.findViewById(R.id.editTextDate);
        EditText textPrice = (EditText)parentView.findViewById(R.id.editTexPrice);
        EditText textColor = (EditText)parentView.findViewById(R.id.editTextColor);
        TextView textID = (TextView)parentView.findViewById(R.id.textClothID);
        ImageView imageCloth = (ImageView)parentView.findViewById(R.id.imageViewCloth);
        Spinner dropdown = (Spinner)viewToAdd.findViewById(R.id.spinnerClothType);
        Button buttonDelete = (Button)parentView.findViewById(R.id.buttonDelete);


        if(buttonEdit.getText().toString().equals("SAVE")){
            if(textPattern.getText().length()==0){
                Toast.makeText(this,"Please  enter the pattern",Toast.LENGTH_LONG).show();
                return;
            }
            else if(imageCloth.getTag().toString().length()==0){
                Toast.makeText(this,"Please select an image",Toast.LENGTH_LONG).show();
                return;
            }
            else if(textPrice.getText().length()==0){
                Toast.makeText(this,"Please enter the price",Toast.LENGTH_LONG).show();
                return;
            }
            else if(textDate.getText().length()==0){
                Toast.makeText(this,"Please enter the date purchased",Toast.LENGTH_LONG).show();
                return;
            }
            else if(textColor.getText().length()==0){
                Toast.makeText(this,"Please enter the colors",Toast.LENGTH_LONG).show();
                return;
            }

            String color = textColor.getText().toString();
            String pattern = textPattern.getText().toString();
            Float price = Float.parseFloat(textPrice.getText().toString());
            Date datePurchased;
            try {
                datePurchased = dateFormat.parse(textDate.getText().toString());
            } catch (ParseException e) {
                Toast.makeText(this,"Please enter the date in DD.MM.YYYY format",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }
            String photoPath = imageCloth.getTag().toString();
            int type = dropdown.getSelectedItemPosition();

            textPattern.setEnabled(false);
            textDate.setEnabled(false);
            textColor.setEnabled(false);
            textPrice.setEnabled(false);
            dropdown.setEnabled(false);
            imageCloth.setEnabled(false);
            buttonDelete.setVisibility(View.VISIBLE);

            if(textID.getText().toString().length()>0){

                int ID = Integer.parseInt(textID.getText().toString());
                Cloth cloth = new Cloth(type,color,pattern,price,datePurchased,photoPath,ID);
                if(!db.UpdateCloth(cloth)){
                    Toast.makeText(this,"Cannot update the clothing information, please make sure all areas are filled.",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else {
                Cloth cloth = new Cloth(type,color,pattern,price,datePurchased,photoPath,-1);
                if (!db.AddCloth(cloth)) {
                    Toast.makeText(this, "Cannot add the clothing, please make sure all areas are filled.", Toast.LENGTH_LONG).show();
                    return;
                }
                textID.setText(String.valueOf(db.lastClothID()));
                calculateColors();
                int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
                parentView.setBackgroundColor(bgc);
                addEmptyCard();
            }
            buttonEdit.setText("EDIT");
        }
        else{
            textPattern.setEnabled(true);
            textDate.setEnabled(true);
            textColor.setEnabled(true);
            textPrice.setEnabled(true);
            dropdown.setEnabled(true);
            imageCloth.setEnabled(true);
            buttonEdit.setText("SAVE");
        }
    }
    public void SelectImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("IMAGE_VIEW_ID",view.getId());
        intent.setAction(Intent.ACTION_PICK);
        imageCloth = (ImageView)view;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    public void ButtonDeleteCloth(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        Button button = (Button)view;
        TextView textID = (TextView)parentView.findViewById(R.id.textClothID);

        int ID = Integer.parseInt(textID.getText().toString());
        if(button.getText().toString().equals("X"))
        {
            if(SOURCE==MAIN_MENU){
                if(db.DeleteCloth(ID)){
                    parentView.removeAllViews();
                }
            }
            else{
                if(CLOTH_TYPE>=0) {
                    if (db.RemoveClothFromDrawer(SOURCE_ID, ID)) {
                        button.setText("+");
                        button.setBackgroundColor(Color.rgb(0, 100, 0));
                    }
                }
                else{
                    if(db.RemoveClothFromCombine(COMBINE_ID,ID)){
                        button.setText("+");
                        button.setBackgroundColor(Color.rgb(0, 100, 0));
                    }
                }
            }
        }
        else{
            if(SOURCE==COMBINES)
                db.AddClothToCombine(COMBINE_ID,ID);
            else
                db.AddClothToDrawer(SOURCE_ID,ID);
            button.setText("X");
            button.setBackgroundColor(Color.rgb(100,0,0));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                imageURI = data.getData();

                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),imageURI);
                imageCloth.setImageBitmap(bmp);
                //soruGorseli.setTag(imageURI.toString());

                String path = getPath(this,imageURI);
                String name = getName(imageURI);
                Log.d("name",name);
                Log.d("path",path);

                /*
                try {
                    saveToLocal(path,name);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }*/

                imageCloth.setTag(path);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private String getName(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try {
                if(cursor != null & cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(result==null){
            result=uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut!=-1){
                result=result.substring(cut + 1);
            }
        }
        return result;
    }
    private String getPath(Activity context, Uri uri){
        String[] proj = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri,proj,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            int colindex = cursor.getColumnIndexOrThrow(proj[0]);
            String val = cursor.getString(colindex);
            return val;
        }
        else
            return uri.getPath();
    }
}