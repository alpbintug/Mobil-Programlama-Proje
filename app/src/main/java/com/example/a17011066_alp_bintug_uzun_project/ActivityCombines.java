package com.example.a17011066_alp_bintug_uzun_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;

public class ActivityCombines extends AppCompatActivity {
    int alpha = 255;
    int lastTo255 = 0;
    int colorChange = 17;
    LinearLayout layout;
    View viewToAdd;
    boolean GenderMale = true;
    int[] rgb = {255,160,0};
    ArrayList<Integer> clothes;
    ArrayAdapter<CharSequence> adapter;
    private DBHelper db;
    String combine = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combines);
        getSupportActionBar().hide();
        clothes = new ArrayList<Integer>();

        db = new DBHelper(this);
        layout = (LinearLayout)findViewById(R.id.combinesLinearList);
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.combine_item, null);
        ArrayList<Combine> combines = db.GetCombines();
        PlaceCombines(combines);
        addEmptyCard();
    }
    private void PlaceCombines(ArrayList<Combine> combines) {
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
        db = new DBHelper(this);
        layout = (LinearLayout)findViewById(R.id.combinesLinearList);
        layout.removeAllViews();
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.combine_item, null);
        ArrayList<Combine> combines = db.GetCombines();
        PlaceCombines(combines);
        addEmptyCard();
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
        int bgc = Color.argb((int)(alpha*0.22),rgb[0],rgb[1],rgb[2]);
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
        Button buttonEdit = (Button)view;
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewCombineID);
        int ID =Integer.parseInt(textID.getText().toString());
        if(db.DeleteCombine(ID))
            parentView.removeAllViews();
    }
}