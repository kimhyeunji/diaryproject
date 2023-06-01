package com.example.diaryproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DiaryActivity extends AppCompatActivity {

    private TextView dateTextView;
    private TextView weatherTextView;
    private TextView moodTextView;
    private TextView diaryContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        // Get the date passed from the previous activity
        String date = getIntent().getStringExtra("date");

        // Initialize the views
        dateTextView = findViewById(R.id.dateTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        moodTextView = findViewById(R.id.moodTextView);
        diaryContentTextView = findViewById(R.id.diaryContentTextView);

        // Set the date on the TextView
        dateTextView.setText(date);

        // Create a RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Define the URL for the JSON data
        String url = "http://192.168.0.4/dbgetjson.php";

        // Create the full URL with the date parameter
        String fullUrl = url + "?date=" + date;

        // JSON 데이터를 가져오기 위한 JsonArrayRequest 생성
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, fullUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("API Response", response.toString());

                            // Check if the response is empty
                            if (response.length() == 0) {
                                Toast.makeText(DiaryActivity.this, "해당 날짜에 대한 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                                diaryContentTextView.setText(""); // 데이터가 없을 경우 TextView 내용 초기화
                                diaryContentTextView.setVisibility(View.GONE); // 데이터가 없을 경우 TextView 숨기기
                            } else {
                                // Clear the existing content before appending new data
                                diaryContentTextView.setText("");

                                for (int i = 0; i < response.length(); i++) {
                                    // Retrieve each object from the array
                                    JSONObject data = response.getJSONObject(i);

                                    // Extract the values from the object
                                    String weather = data.getString("weather");
                                    String mood = data.getString("mood");
                                    String diary = "";

                                    // Check if the object has the "diary" key
                                    if (data.has("diary")) {
                                        diary = data.getString("diary");
                                    }

                                    // Use the extracted information for further processing
                                    // For example, you can append the data to a TextView or display it in a list
                                    weatherTextView.append(weather + "\n");
                                    moodTextView.append(mood + "\n");
                                    diaryContentTextView.append(diary + "\n");
                                    diaryContentTextView.setVisibility(View.VISIBLE); // 데이터가 있을 경우 TextView 표시
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

// Set a custom retry policy for the request
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // Timeout duration in milliseconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Maximum number of retries
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Backoff multiplier
        ));

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);

        // Set a click listener on the close button to finish the activity
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
