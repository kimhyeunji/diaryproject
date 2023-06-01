package com.example.diaryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button writeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = findViewById(R.id.datePicker);
        datePicker.setVisibility(View.GONE); //초기에 Date
        Button dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePicker.getVisibility() == View.VISIBLE){
                    datePicker.setVisibility(View.GONE);
                } else{
                    datePicker.setVisibility(View.VISIBLE);
                }
            }
        });

        writeButton = findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWriteActivity();
            }
        });
    }
    private void openWriteActivity(){
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }

}