package com.example.diaryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WriteActivity extends AppCompatActivity {

    private Spinner weatherSpinner;
    private Spinner moodSpinner;
    private DatePicker datePicker;

    Button datePickerButton;
    TextView tvYear;
    TextView tvMonth;
    TextView tvDay;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        weatherSpinner = findViewById(R.id.weatherSpinner);
        moodSpinner = findViewById(R.id.moodSpinner);
        datePicker = findViewById(R.id.datePicker2);
        datePicker.setVisibility(View.GONE); //초기에 달력 숨기기

        Button datePickerButton = findViewById(R.id.dateButton);
        TextView tvYear = findViewById(R.id.tvYear);
        TextView tvMonth = findViewById(R.id.tvMonth);
        TextView tvDay = findViewById(R.id.tvDay);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePicker.getVisibility() == View.VISIBLE){
                    datePicker.setVisibility(View.GONE);
                } else{
                    datePicker.setVisibility(View.VISIBLE);
                }
            }
        });

        //달력 날짜 밑에 나오도록
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvYear.setText(String.format("%02d", year));
                        tvMonth.setText(String.format("%02d", monthOfYear + 1));
                        tvDay.setText(String.format("%02d", dayOfMonth));
                    }
                });

        //스피너 항목 설정
        String[] weatherOptions= {"비", "맑음", "흐림"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weatherOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSpinner.setAdapter(adapter);

//        //스피너 선택 이벤트 처리
//        weatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedWeather = weatherSpinner.getSelectedItem().toString();
//                Toast.makeText(getApplicationContext(), selectedWeather, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // 아무 항목도 선택되지 않았을 때의 처리
//            }
//        });


        //기분 스피너 항목설정
        String[] moodOptions={"좋음","보통","나쁨"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,moodOptions);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter1);


    }
}