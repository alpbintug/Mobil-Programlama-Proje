package com.example.a17011066_alp_bintug_uzun_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityCombines extends AppCompatActivity {
    int alpha = 250;
    int alphaEmpty = 200;
    int lastTo255 = 0;
    int colorChange = 17;
    LinearLayout layout;
    View viewToAdd;
    boolean GenderMale = true;
    int[] rgb = {255,160,0};
    ArrayList<Integer> clothes;
    int EventID;
    int EventCombineID = -1;
    String EventName = null;
    ArrayAdapter<CharSequence> adapter;
    private DBHelper db;
    String combine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combines);
        clothes = new ArrayList<Integer>();
        Intent intent = getIntent();
        EventName = intent.getStringExtra("TAG");
        EventID = intent.getIntExtra("EVENT_ID",-1);
        layout = (LinearLayout)findViewById(R.id.combinesLinearList);
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.combine_item, null);
        db = new DBHelper(this);
        if(EventID==-1) {
            getSupportActionBar().hide();
            ArrayList<Combine> combines = db.GetCombines();
            PlaceCombines(combines,false);;
            addEmptyCard();
        }
        else {
            getSupportActionBar().setTitle(EventName);
            Combine EventCombine = db.GetEventCombine(EventID);
            ArrayList<Combine> combines = db.GetCombines();
            if(EventCombine!=null) {
                EventCombineID = EventCombine.getID();
                ArrayList<Combine> _EventCombine = new ArrayList<>();
                _EventCombine.add(EventCombine);
                PlaceCombines(_EventCombine, true);
                combines.removeIf(e -> e.getID()==EventCombine.getID());
            }
            PlaceCombines(combines,true);
        }
    }
    private void PlaceCombines(ArrayList<Combine> combines, boolean isEventCombine) {
        for (Combine combine : combines) {

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.combine_item, null);

            //region ARKA PLAN RENKLERINI AYARLAMA
            int bgc = Color.argb(alpha, rgb[0], rgb[1], rgb[2]);
            calculateColors();
            viewToAdd.findViewById(R.id.cardCombine).setBackgroundColor(bgc);
            //endregion
            EditText textName = (EditText) viewToAdd.findViewById(R.id.editTextCombineName);
            Button buttonEdit = (Button) viewToAdd.findViewById(R.id.buttonEditCombine);
            Button buttonChangeClothes = (Button) viewToAdd.findViewById(R.id.buttonChangeCombineClothes);
            ImageButton buttonShare = (ImageButton) viewToAdd.findViewById(R.id.buttonShareCombine);
            Button buttonDelete = (Button) viewToAdd.findViewById(R.id.buttonDeleteCombine);
            TextView textID = (TextView) viewToAdd.findViewById(R.id.textViewCombineID);
            ImageView body = (ImageView) viewToAdd.findViewById(R.id.imageViewBody);
            ImageView upperBody = (ImageView) viewToAdd.findViewById(R.id.imageViewUpperBody);
            ImageView lowerBody = (ImageView) viewToAdd.findViewById(R.id.imageViewLowerBody);
            ImageView head = (ImageView) viewToAdd.findViewById(R.id.imageViewHead);
            ImageView foot = (ImageView) viewToAdd.findViewById(R.id.imageViewFoot);
            ArrayList<Cloth> combineClothes = db.GetClothes(combine.getID(),true);

            Button buttonTorso = (Button) viewToAdd.findViewById(R.id.buttonTorso);
            Button buttonLegs = (Button) viewToAdd.findViewById(R.id.buttonLegs);
            Button buttonHead = (Button) viewToAdd.findViewById(R.id.buttonHead);
            Button buttonFoot = (Button) viewToAdd.findViewById(R.id.buttonFoot);

            for(Cloth cloth : combineClothes){
                addImageToBody(cloth.getClothType(),cloth.getPhotoPath());
            }
            textID.setText(String.valueOf(combine.getID()));
            textName.setText(combine.getCombineName());
            buttonEdit.setText("EDIT");



            if(isEventCombine){
                buttonDelete.setBackgroundColor(Color.rgb(0,100,0));
                buttonDelete.setText("+");
            }
            else{
                buttonDelete.setBackgroundColor(Color.rgb(100,0,0));
                buttonDelete.setText("X");
            }
            if(EventCombineID==combine.getID()){
                buttonDelete.setVisibility(View.INVISIBLE);
                viewToAdd.findViewById(R.id.cardCombine).setBackgroundColor(Color.WHITE);
            }

            textName.setEnabled(false);
            buttonChangeClothes.setEnabled(true);
            buttonDelete.setEnabled(true);
            if(combineClothes.size()>1)
                buttonShare.setEnabled(true);
            buttonFoot.setEnabled(true);
            buttonHead.setEnabled(true);
            buttonLegs.setEnabled(true);
            buttonTorso.setEnabled(true);



            layout.addView(viewToAdd);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        clothes = new ArrayList<Integer>();
        Intent intent = getIntent();
        EventName = intent.getStringExtra("TAG");
        EventID = intent.getIntExtra("EVENT_ID",-1);
        layout = (LinearLayout)findViewById(R.id.combinesLinearList);
        layout.removeAllViews();
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.combine_item, null);
        db = new DBHelper(this);
        if(EventID==-1) {
            getSupportActionBar().hide();
            ArrayList<Combine> combines = db.GetCombines();
            PlaceCombines(combines,false);;
            addEmptyCard();
        }
        else {
            getSupportActionBar().setTitle(EventName);
            Combine EventCombine = db.GetEventCombine(EventID);
            ArrayList<Combine> combines = db.GetCombines();
            if(EventCombine!=null) {
                EventCombineID = EventCombine.getID();
                ArrayList<Combine> _EventCombine = new ArrayList<>();
                _EventCombine.add(EventCombine);
                PlaceCombines(_EventCombine, true);
                combines.removeIf(e -> e.getID()==EventCombine.getID());
            }
            PlaceCombines(combines,true);
        }
    }
    public void GetLowerBodyDrawers(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        EditText textName = (EditText) parentView.findViewById(R.id.editTextCombineName);
        Intent open = new Intent(getApplicationContext(),ActivityDrawers.class);
        int type = 0;
        open.putExtra("TAG",textName.getText().toString());
        open.putExtra("TYPE",type);
        open.putExtra("COMBINE_ID",Integer.parseInt(textID.getText().toString()));
        startActivity(open);
    }
    public void GetUpperBodyDrawers(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        EditText textName = (EditText) parentView.findViewById(R.id.editTextCombineName);
        Intent open = new Intent(getApplicationContext(),ActivityDrawers.class);
        int type = 1;
        open.putExtra("TAG",textName.getText().toString());
        open.putExtra("TYPE",type);
        open.putExtra("COMBINE_ID",Integer.parseInt(textID.getText().toString()));
        startActivity(open);
    }
    public void ButtonClothes(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        Intent open = new Intent(getApplicationContext(),ActivityClothes.class);
        open.putExtra("SOURCE",2);
        open.putExtra("CLOTH_TYPE",-1);
        open.putExtra("COMBINE_ID",Integer.parseInt(textID.getText().toString()));
        open.putExtra("SOURCE_ID",Integer.parseInt(textID.getText().toString()));
        startActivity(open);
    }
    public void shareCombine(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textViewName = (TextView) parentView.findViewById(R.id.textViewCombineName);
        EditText textName = (EditText) parentView.findViewById(R.id.editTextCombineName);
        Button buttonTorso = (Button) parentView.findViewById(R.id.buttonTorso);
        Button buttonEdit = (Button) parentView.findViewById(R.id.buttonEditCombine);
        Button buttonClothes = (Button) parentView.findViewById(R.id.buttonChangeCombineClothes);
        Button buttonDelete = (Button) parentView.findViewById(R.id.buttonDeleteCombine);
        ImageButton buttonShare = (ImageButton) parentView.findViewById(R.id.buttonShareCombine);
        Button buttonLegs = (Button) parentView.findViewById(R.id.buttonLegs);
        Button buttonHead = (Button) parentView.findViewById(R.id.buttonHead);
        Button buttonFoot = (Button) parentView.findViewById(R.id.buttonFoot);

        textName.setVisibility(View.INVISIBLE);
        textViewName.setVisibility(View.INVISIBLE);
        buttonTorso.setVisibility(View.INVISIBLE);
        buttonEdit.setVisibility(View.INVISIBLE);
        buttonClothes.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        buttonShare.setVisibility(View.INVISIBLE);
        buttonLegs.setVisibility(View.INVISIBLE);
        buttonHead.setVisibility(View.INVISIBLE);
        buttonFoot.setVisibility(View.INVISIBLE);
        Drawable bg = parentView.getBackground();
        parentView.setBackgroundColor(Color.rgb(255,255,255));
        parentView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(parentView.getDrawingCache());
        parentView.setDrawingCacheEnabled(false);
        parentView.setBackground(bg);
        textName.setVisibility(View.VISIBLE);
        textViewName.setVisibility(View.VISIBLE);
        buttonTorso.setVisibility(View.VISIBLE);
        buttonEdit.setVisibility(View.VISIBLE);
        buttonClothes.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.VISIBLE);
        buttonShare.setVisibility(View.VISIBLE);
        buttonLegs.setVisibility(View.VISIBLE);
        buttonHead.setVisibility(View.VISIBLE);
        buttonFoot.setVisibility(View.VISIBLE);

        File cachePath = new File(getExternalCacheDir(), "my_images/");
        cachePath.mkdirs();

        //create png file
        File file = new File(cachePath, "Image_123.png");
        FileOutputStream fileOutputStream;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //---Share File---//
        //get file uri
        Uri myImageFileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);

        //create a intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri);
        intent.setType("image/png");
        startActivity(Intent.createChooser(intent, "Share with"));
    }
    public void GetFootDrawers(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        EditText textName = (EditText) parentView.findViewById(R.id.editTextCombineName);
        Intent open = new Intent(getApplicationContext(),ActivityDrawers.class);
        int type = 2;
        open.putExtra("TAG",textName.getText().toString());
        open.putExtra("TYPE",type);
        open.putExtra("COMBINE_ID",Integer.parseInt(textID.getText().toString()));
        startActivity(open);
    }
    public void GetHeadDrawers(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        EditText textName = (EditText) parentView.findViewById(R.id.editTextCombineName);
        Intent open = new Intent(getApplicationContext(),ActivityDrawers.class);
        int type = 3;
        open.putExtra("TAG",textName.getText().toString());
        open.putExtra("TYPE",type);
        open.putExtra("COMBINE_ID",Integer.parseInt(textID.getText().toString()));
        startActivity(open);
    }
    public void ButtonEditCombine(View view){
        Button buttonEdit = (Button)view;
        ViewGroup parentView = (ViewGroup)view.getParent();

        EditText textName = (EditText) parentView.findViewById(R.id.editTextCombineName);
        Button buttonChangeClothes = (Button) parentView.findViewById(R.id.buttonChangeCombineClothes);
        ImageButton buttonShare = (ImageButton) parentView.findViewById(R.id.buttonShareCombine);
        Button buttonDelete = (Button) parentView.findViewById(R.id.buttonDeleteCombine);
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        Button buttonTorso = (Button) parentView.findViewById(R.id.buttonTorso);
        Button buttonLegs = (Button) parentView.findViewById(R.id.buttonLegs);
        Button buttonHead = (Button) parentView.findViewById(R.id.buttonHead);
        Button buttonFoot = (Button) parentView.findViewById(R.id.buttonFoot);

        if(buttonEdit.getText().toString().equals("SAVE")){
            if(textName.getText().toString().length()==0){
                Toast.makeText(this,"Please enter combine name.",Toast.LENGTH_LONG).show();
                return;
            }
            if(textID.getText().toString().length()==0){
                Combine combine = new Combine(textName.getText().toString(),-1);
                if(!db.AddCombine(combine)){
                    Toast.makeText(this,"Cannot add combine.",Toast.LENGTH_LONG).show();
                    return;
                }
                textID.setText(String.valueOf(db.lastCombineID()));
                int bgc = Color.argb(alpha, rgb[0], rgb[1], rgb[2]);
                calculateColors();
                parentView.setBackgroundColor(bgc);
                addEmptyCard();
            }
            else{
                Combine combine = new Combine(textName.getText().toString(),Integer.parseInt(textID.getText().toString()));
                if(!db.UpdateCombine(combine)){
                    Toast.makeText(this,"Cannot update combine.",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            buttonEdit.setText("EDIT");
            buttonFoot.setEnabled(true);
            buttonHead.setEnabled(true);
            buttonLegs.setEnabled(true);
            buttonTorso.setEnabled(true);
            buttonDelete.setEnabled(true);
            buttonChangeClothes.setEnabled(true);
            textName.setEnabled(false);
        }
        else{
            textName.setEnabled(true);
            buttonEdit.setText("SAVE");
        }

    }
    private void addImageToBody(int clothType, String photoPath) {
        ImageView target;
        if(clothType==0)
            target = (ImageView) viewToAdd.findViewById(R.id.imageViewLowerBody);
        else if(clothType==1)
            target = (ImageView) viewToAdd.findViewById(R.id.imageViewUpperBody);
        else if(clothType==2)
            target = (ImageView) viewToAdd.findViewById(R.id.imageViewFoot);
        else
            target = (ImageView) viewToAdd.findViewById(R.id.imageViewHead);
        try {
            target.setImageDrawable(Drawable.createFromPath(photoPath));
            target.setTag(photoPath);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void addEmptyCard(){
        calculateColors();
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.combine_item,null);
        int bgc = Color.argb(alphaEmpty,rgb[0],rgb[1],rgb[2]);
        ((Button)viewToAdd.findViewById(R.id.buttonEditCombine)).setText("SAVE");
        viewToAdd.findViewById(R.id.cardCombine).setBackgroundColor(bgc);
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
    public void changeGender(View view){
        ImageView image = (ImageView) view;
        Drawable drawableBody = getResources().getDrawable(getResources().getIdentifier(GenderMale?"female":"male","drawable",getPackageName()));
        GenderMale = !GenderMale;

        image.setImageDrawable(drawableBody);
    }
    public void deleteCombine(View view){
        Button buttonDelete = (Button)view;
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        int ID =Integer.parseInt(textID.getText().toString());
        if(buttonDelete.getText().toString().equals("X")) {
            if (db.DeleteCombine(ID))
                parentView.removeAllViews();
        }
        else{
            if(db.ChangeEventCombine(EventID,ID)){
                this.finish();
            }
        }
    }
}