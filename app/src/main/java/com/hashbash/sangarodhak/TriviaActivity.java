package com.hashbash.sangarodhak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hashbash.sangarodhak.Modals.TriviaQuestionDataModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    private Button startButton;
    private Spinner questionNumberSpinner;
    private Spinner categorySpinner;
    private Spinner difficultySpinner;
    private ProgressBar progressBar;

    public ArrayList<TriviaQuestionDataModal> questionsList;

    public String result = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        questionsList = new ArrayList<>();

        progressBar = findViewById(R.id.loading);
        startButton = findViewById(R.id.startButton);
        questionNumberSpinner = findViewById(R.id.question_number_spinner);
        categorySpinner = findViewById(R.id.category_spinner);
        difficultySpinner = findViewById(R.id.difficulty_spinner);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionNumber = questionNumberSpinner.getSelectedItem().toString();
                int categoryID = categorySpinner.getSelectedItemPosition();
                String difficulty = difficultySpinner.getSelectedItem().toString().toLowerCase();
                Log.d("log", questionNumber + " " + categoryID + " " + difficulty);
                progressBar.setVisibility(View.VISIBLE);
                fetch_data(questionNumber, categoryID, difficulty);
            }
        });

    }

    private void fetch_data(final String questionNumber, int categoryID, String difficulty) {
        String url = "https://opentdb.com/api.php?amount="+questionNumber+"&";
        if(categoryID != 0){
            url += "category="+(categoryID+8) + "&";
        }
        url += "difficulty="+difficulty+"&type=multiple";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("response_code") != 0){
                        Toast.makeText(getApplicationContext(), "Not enough questions found for selected options. Try some other set of options!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        JSONArray questions = response.getJSONArray("results");
                        Log.d("log", "done");
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(TriviaActivity.this, TriviaQuestionActivity.class);
                        intent.putExtra("questions", questions.toString());
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("log", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Network issue. Try again later!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log", error.getMessage());
                Toast.makeText(getApplicationContext(), "Network issue. Try again later!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

}