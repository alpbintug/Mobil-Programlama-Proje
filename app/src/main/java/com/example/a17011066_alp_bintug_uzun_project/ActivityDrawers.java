package com.example.a17011066_alp_bintug_uzun_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityDrawers extends AppCompatActivity {

    int alpha = 255;
    int lastTo255 = 0;
    int colorChange = 17;
    LinearLayout layout;
    View viewToAdd;
    int[] rgb = {255,110,0};
    ArrayAdapter<CharSequence> adapter;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawers);

        layout = (LinearLayout)findViewById(R.id.drawersLinearList);
        db = new DBHelper(this);
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.drawer_item,null);

        adapter = ArrayAdapter.createFromResource(this,R.array.clothTypes, android.R.layout.simple_spinner_item);
        Spinner dropdown = (Spinner)viewToAdd.findViewById(R.id.spinnerDrawerClothType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        ArrayList<Drawer> drawers = db.GetDrawers();

        PlaceDrawers(drawers);

        addEmptyCard();
    }

    private void PlaceDrawers(ArrayList<Drawer> drawers) {
        for (Drawer drawer : drawers) {

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.drawer_item, null);

            //region ARKA PLAN RENKLERINI AYARLAMA
            int bgc = Color.argb(alpha, rgb[0], rgb[1], rgb[2]);
            calculateColors();
            //endregion
            EditText textTag = (EditText) viewToAdd.findViewById(R.id.editTextDrawerTag);
            Button buttonEdit = (Button) viewToAdd.findViewById(R.id.buttonEditDrawer);
            Spinner dropdown = (Spinner) viewToAdd.findViewById(R.id.spinnerDrawerClothType);
            Button buttonAddClothes = (Button) viewToAdd.findViewById(R.id.buttonAddDrawerClothes);
            Button buttonDelete = (Button) viewToAdd.findViewById(R.id.buttonDeleteDrawer);
            TextView textID = (TextView) viewToAdd.findViewById(R.id.textViewDrawerID);

            textID.setText(String.valueOf(drawer.getID()));
            textTag.setText(drawer.getTag());
            buttonEdit.setText("EDIT");


            textTag.setEnabled(false);
            dropdown.setEnabled(false);
            buttonDelete.setVisibility(View.VISIBLE);
            buttonAddClothes.setEnabled(true);
            adapter = ArrayAdapter.createFromResource(this, R.array.clothTypes, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);

            viewToAdd.findViewById(R.id.cardDrawer).setBackgroundColor(bgc);


            layout.addView(viewToAdd);

        }
    }
    private void addEmptyCard(){
        calculateColors();
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.drawer_item,null);
        int bgc = Color.argb((int)(alpha*0.22),rgb[0],rgb[1],rgb[2]);
        ((Button)viewToAdd.findViewById(R.id.buttonEditDrawer)).setText("SAVE");
        viewToAdd.findViewById(R.id.cardDrawer).setBackgroundColor(bgc);

        adapter = ArrayAdapter.createFromResource(this,R.array.clothTypes, android.R.layout.simple_spinner_item);
        Spinner dropdown = (Spinner)viewToAdd.findViewById(R.id.spinnerDrawerClothType);
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

    public void buttonEditDrawer(View view){
        Button buttonEdit = (Button)view;
        ViewGroup parentView = (ViewGroup)view.getParent();

        EditText textTag = (EditText) parentView.findViewById(R.id.editTextDrawerTag);
        Spinner dropdown = (Spinner) parentView.findViewById(R.id.spinnerDrawerClothType);
        Button buttonAddClothes = (Button) parentView.findViewById(R.id.buttonAddDrawerClothes);
        Button buttonDelete = (Button) parentView.findViewById(R.id.buttonDeleteDrawer);
        TextView textID = (TextView) parentView.findViewById(R.id.textViewDrawerID);


        if(buttonEdit.getText().toString().equals("SAVE")){
            if(textTag.getText().toString().length()==0){
                Toast.makeText(this,"Please enter the a tag for your drawer",Toast.LENGTH_LONG).show();
                return;
            }
            int ID = -1;
            String tag = textTag.getText().toString();
            int type = dropdown.getSelectedItemPosition();

            if(textID.getText().toString().length()!=0){
                ID = Integer.parseInt(textID.getText().toString());
                Drawer drawer = new Drawer(type,tag,ID);
                if(!db.UpdateDrawer(drawer)) {
                    Toast.makeText(this,"Cannot update the drawer",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else {
                Drawer drawer = new Drawer(type,tag,ID);
                if(!db.AddDrawer(drawer)){
                    Toast.makeText(this,"Cannot create the drawer",Toast.LENGTH_LONG).show();
                    return;
                }
                calculateColors();
                int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
                parentView.setBackgroundColor(bgc);
                addEmptyCard();
            }

            dropdown.setEnabled(false);
            textTag.setEnabled(false);
            buttonAddClothes.setEnabled(true);
            buttonEdit.setText("EDIT");
        }
        else{
            dropdown.setEnabled(true);
            textTag.setEnabled(true);
            buttonEdit.setText("SAVE");
        }
    }
}