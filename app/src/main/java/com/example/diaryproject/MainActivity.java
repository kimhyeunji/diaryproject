package com.example.diaryproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button writeButton;
    private ConstraintLayout diaryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = findViewById(R.id.datePicker);
        datePicker.setVisibility(View.GONE); // 초기에 숨김

        Button dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDatePickerVisibility();
            }
        });

        writeButton = findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWriteActivity();
            }
        });

        diaryLayout = findViewById(R.id.diaryLayout);
        diaryLayout.setVisibility(View.GONE); // 초기에 숨김

        // 달력에서 날짜 선택 시 DiaryActivity를 열도록 설정
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        openDiaryActivity(date);
                    }
                });

        Button exitButton = findViewById(R.id.exitButton3);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toggleDatePickerVisibility() {
        if (datePicker.getVisibility() == View.VISIBLE) {
            datePicker.setVisibility(View.GONE);
            diaryLayout.setVisibility(View.VISIBLE);
        } else {
            datePicker.setVisibility(View.VISIBLE);
            diaryLayout.setVisibility(View.GONE);
        }
    }

    private void openWriteActivity() {
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }

    private void openDiaryActivity(String date) {
        Intent intent = new Intent(this, DiaryActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }
}
