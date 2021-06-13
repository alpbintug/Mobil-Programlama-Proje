package com.example.a17011066_alp_bintug_uzun_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityEvents extends AppCompatActivity {
    int alpha = 250;
    int alphaEmpty = 200;
    int lastTo255 = 0;
    int colorChange = 17;
    LinearLayout layout;
    View viewToAdd;
    int[] rgb = {255,160,0};
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getSupportActionBar().hide();
        db = new DBHelper(this);
        layout = (LinearLayout)findViewById(R.id.eventsLinearLayout);
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.event_item, null);
        ArrayList<Event> events = db.GetEvents();
        PlaceEvents(events);
        addEmptyCard();

    }
    private void PlaceEvents(ArrayList<Event> events) {
        for (Event event : events) {

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.event_item, null);

            //region ARKA PLAN RENKLERINI AYARLAMA
            int bgc = Color.argb(alpha, rgb[0], rgb[1], rgb[2]);
            calculateColors();
            viewToAdd.findViewById(R.id.cardViewEvent).setBackgroundColor(bgc);
            //endregion
            EditText eventName = (EditText)viewToAdd.findViewById(R.id.editTextEventName);
            EditText eventType = (EditText)viewToAdd.findViewById(R.id.editTextEventType);
            EditText eventDate = (EditText)viewToAdd.findViewById(R.id.editTextEventDate);
            EditText eventLocation = (EditText)viewToAdd.findViewById(R.id.editTextEventLocation);
            EditText eventCombine = (EditText)viewToAdd.findViewById(R.id.editTextEventCombine);
            TextView eventID = (TextView)viewToAdd.findViewById(R.id.textViewEventID);
            Button buttonDelete = (Button)viewToAdd.findViewById(R.id.buttonDeleteEvent);
            Button buttonSave = (Button)viewToAdd.findViewById(R.id.buttonSaveEvent);
            Button buttonChangeCombine = (Button)viewToAdd.findViewById(R.id.buttonEventCombines);

            buttonDelete.setBackgroundColor(Color.rgb(100,0,0));
            buttonDelete.setText("X");


            eventName.setEnabled(false);
            eventDate.setEnabled(false);
            eventLocation.setEnabled(false);
            eventType.setEnabled(false);

            buttonChangeCombine.setEnabled(true);
            buttonDelete.setEnabled(true);

            Combine combine = db.GetEventCombine(event.getID());

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            eventName.setText(event.getName());
            eventID.setText(String.valueOf(event.getID()));
            eventType.setText(event.getEventType());
            if(combine !=null)
                eventCombine.setText(combine.getCombineName());
            eventDate.setText(df.format(event.getDate()));
            eventLocation.setText(event.getLocation());
            buttonSave.setText("EDIT");

            layout.addView(viewToAdd);

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        db = new DBHelper(this);
        layout = (LinearLayout)findViewById(R.id.eventsLinearLayout);
        layout.removeAllViews();
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.event_item, null);
        ArrayList<Event> events = db.GetEvents();
        PlaceEvents(events);
        addEmptyCard();
    }
    private void addEmptyCard(){
        calculateColors();
        viewToAdd = LayoutInflater.from(this).inflate(R.layout.event_item,null);
        int bgc = Color.argb(alphaEmpty,rgb[0],rgb[1],rgb[2]);
        ((Button)viewToAdd.findViewById(R.id.buttonSaveEvent)).setText("SAVE");
        viewToAdd.findViewById(R.id.cardViewEvent).setBackgroundColor(bgc);
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

    public void buttonEventCombine(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView textID = (TextView) parentView.findViewById(R.id.textViewEventID);
        EditText textName = (EditText) parentView.findViewById(R.id.editTextEventName);
        Intent open = new Intent(getApplicationContext(),ActivityCombines.class);
        open.putExtra("TAG",textName.getText().toString());
        open.putExtra("EVENT_ID",Integer.parseInt(textID.getText().toString()));
        startActivity(open);
    }
    public void buttonDeleteEvent(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView eventID = (TextView)parentView.findViewById(R.id.textViewEventID);

        int ID = Integer.parseInt(eventID.getText().toString());
        if(db.DeleteEvent(ID)){
            parentView.removeAllViews();
        }

    }
    public void buttonSaveEvent(View view){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Button buttonSave = (Button)view;
        ViewGroup parentView = (ViewGroup)view.getParent();
        EditText eventName = (EditText)parentView.findViewById(R.id.editTextEventName);
        EditText eventType = (EditText)parentView.findViewById(R.id.editTextEventType);
        EditText eventDate = (EditText)parentView.findViewById(R.id.editTextEventDate);
        EditText eventLocation = (EditText)parentView.findViewById(R.id.editTextEventLocation);
        EditText eventCombine = (EditText)parentView.findViewById(R.id.editTextEventCombine);
        TextView eventID = (TextView)parentView.findViewById(R.id.textViewEventID);
        Button buttonDelete = (Button)parentView.findViewById(R.id.buttonDeleteEvent);
        Button buttonChangeCombine = (Button)parentView.findViewById(R.id.buttonEventCombines);

        if(buttonSave.getText().toString()=="SAVE"){
            try {
                String name = eventName.getText().toString();
                String type = eventType.getText().toString();
                Date date = df.parse(eventDate.getText().toString());
                String location = eventLocation.getText().toString();
                if(name.length()==0){
                    Toast.makeText(this,"Please enter the name of the event.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(type.length()==0){
                    Toast.makeText(this,"Please enter the type of the event.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(location.length()==0){
                    Toast.makeText(this,"Please enter the location of the event.",Toast.LENGTH_LONG).show();
                    return;
                }
                Event event = new Event(type,location,date,name,-1);
                if(eventID.getText().toString().length()==0) {
                    if (!db.AddEvent(event)) {
                        Toast.makeText(this, "Cannot add the event.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    eventID.setText(String.valueOf(db.lastEventID()));
                    int bgc = Color.argb(alpha, rgb[0], rgb[1], rgb[2]);
                    calculateColors();
                    parentView.setBackgroundColor(bgc);
                    addEmptyCard();
                }
                else{
                    event.setID(Integer.parseInt(eventID.getText().toString()));
                    if(!db.UpdateEvent(event)){
                        Toast.makeText(this, "Cannot update the event.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            } catch (ParseException e) {
                Toast.makeText(this,"Please enter the date in dd.MM.yyyy format.",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }



            eventName.setEnabled(false);
            eventDate.setEnabled(false);
            eventLocation.setEnabled(false);
            eventType.setEnabled(false);

            buttonChangeCombine.setEnabled(true);
            buttonDelete.setEnabled(true);
            buttonDelete.setBackgroundColor(Color.rgb(100,0,0));
            buttonDelete.setText("X");

            buttonSave.setText("EDIT");
        }
        else{

            eventName.setEnabled(true);
            eventDate.setEnabled(true);
            eventType.setEnabled(true);
            eventLocation.setEnabled(true);

            buttonSave.setText("SAVE");
        }
    }
}