package com.hashbash.sangarodhak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hashbash.sangarodhak.Modals.TriviaQuestionDataModal;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TriviaResultActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private TextView bestScoreTextView;
    private Button finishButton;



    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_result);

        scoreTextView = findViewById(R.id.score);
        bestScoreTextView = findViewById(R.id.best_score);
        finishButton = findViewById(R.id.finish_button);

        sharedPreferences = getSharedPreferences(getString(R.string.pref_trivia_data), MODE_PRIVATE);





        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String score = sharedPreferences.getString(getString(R.string.pref_trivia_previous_score), "0/0");
        String bestScore = sharedPreferences.getString(getString(R.string.pref_trivia_highest_score), "0/0");
        if(bestScore.equals("0/0") || bestScore.equals(score)){
            bestScoreTextView.setText("This is your best score!");
        }
        else{
            bestScoreTextView.setText("Best score:  "+bestScore);
        }
        scoreTextView.setText(score);
    }
}
