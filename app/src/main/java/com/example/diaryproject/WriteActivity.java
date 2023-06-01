package com.example.diaryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private Spinner weatherSpinner;
    private Spinner moodSpinner;
    private DatePicker datePicker;

    private TextView tvYear;
    private TextView tvMonth;
    private TextView tvDay;

    private Button saveButton;
    private EditText diaryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        requestQueue = Volley.newRequestQueue(this);

        tvYear = findViewById(R.id.tvYear);
        tvMonth = findViewById(R.id.tvMonth);
        tvDay = findViewById(R.id.tvDay);

        weatherSpinner = findViewById(R.id.weatherSpinner);
        moodSpinner = findViewById(R.id.moodSpinner);
        datePicker = findViewById(R.id.datePicker2);
        datePicker.setVisibility(View.GONE);

        Button datePickerButton = findViewById(R.id.dateButton);
        saveButton = findViewById(R.id.saveButton);
        diaryEditText = findViewById(R.id.diaryEditText);

        Button exitButton2 = findViewById(R.id.exitButton2);
        exitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePicker.getVisibility() == View.VISIBLE) {
                    datePicker.setVisibility(View.GONE);
                } else {
                    datePicker.setVisibility(View.VISIBLE);
                }
            }
        });



        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvYear.setText(String.format("%02d", year));
                        tvMonth.setText(String.format("%02d", monthOfYear + 1));
                        tvDay.setText(String.format("%02d", dayOfMonth));
                    }
                });

        String[] weatherOptions = {"비", "맑음", "흐림"};
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weatherOptions);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSpinner.setAdapter(weatherAdapter);

        String[] moodOptions = {"좋음", "보통", "나쁨"};
        ArrayAdapter<String> moodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, moodOptions);
        moodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(moodAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer();
            }
        });
    }

    private void sendDataToServer() {
        String date = tvYear.getText().toString() + "-" + tvMonth.getText().toString() + "-" + tvDay.getText().toString();
        String weather = weatherSpinner.getSelectedItem().toString();
        String mood = moodSpinner.getSelectedItem().toString();
        String diary = diaryEditText.getText().toString();

        String url = "http://192.168.0.4/Singup3.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "일기 쓰기 완료", Toast.LENGTH_SHORT).show();
                        tvYear.setText("");
                        tvMonth.setText("");
                        tvDay.setText("");
                        diaryEditText.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "데이터 전송 실패", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", date);
                params.put("weather", weather);
                params.put("mood", mood);
                params.put("diary", diary);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
