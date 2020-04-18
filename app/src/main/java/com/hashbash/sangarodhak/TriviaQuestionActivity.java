package com.hashbash.sangarodhak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hashbash.sangarodhak.Modals.TriviaQuestionDataModal;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TriviaQuestionActivity extends AppCompatActivity {

    private int questionsLength;
    private int currentQuestionIndex = 0, correctAnswers = 0;
    private ArrayList<TriviaQuestionDataModal> questionsList;
    private TriviaQuestionDataModal currentQuestion;

    private TextView questionTextView, option1TextView, option2TextView, option3TextView, option4TextView, questionCounterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question);

        questionTextView = findViewById(R.id.question);
        option1TextView = findViewById(R.id.option1);
        option2TextView = findViewById(R.id.option2);
        option3TextView = findViewById(R.id.option3);
        option4TextView = findViewById(R.id.option4);
        questionCounterTextView = findViewById(R.id.question_number_counter);

        currentQuestionIndex = 0;

        option1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuestion.getOption1().equals(currentQuestion.getAnswer())){
                    correctAnswers++;
                }
                currentQuestionIndex++;
                updateQuestion();
            }
        });

        option2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuestion.getOption2().equals(currentQuestion.getAnswer())){
                    correctAnswers++;
                }
                currentQuestionIndex++;
                updateQuestion();
            }
        });

        option3TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuestion.getOption3().equals(currentQuestion.getAnswer())){
                    correctAnswers++;
                }
                currentQuestionIndex++;
                updateQuestion();
            }
        });

        option4TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuestion.getOption4().equals(currentQuestion.getAnswer())){
                    correctAnswers++;
                }
                currentQuestionIndex++;
                updateQuestion();
            }
        });

        questionsList = new ArrayList<>();
        try {
            JSONArray questions = new JSONArray(getIntent().getStringExtra("questions"));
            int len = questions.length();
            for(int i = 0; i < len; i++){
                questionsList.add(new TriviaQuestionDataModal(questions.getJSONObject(i)));
            }
            questionsLength = len;
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            finish();
        }



        updateQuestion();
    }

    private void updateQuestion(){
        if(currentQuestionIndex >= questionsLength){
            SharedPreferences preferences = getSharedPreferences(getString(R.string.pref_trivia_data), MODE_PRIVATE);
            preferences.edit().putString(getString(R.string.pref_trivia_previous_score), correctAnswers + "/" + questionsLength).apply();
            String best = preferences.getString(getString(R.string.pref_trivia_highest_score), "0/10");
            int bestCorrect = Integer.parseInt(best.substring(0, best.indexOf('/')));
            int bestTotal = Integer.parseInt(best.substring(best.indexOf('/')+1));
            if(correctAnswers*(60/questionsLength) >= bestCorrect*(60/bestTotal)){
                preferences.edit().putString(getString(R.string.pref_trivia_highest_score), correctAnswers + "/" + questionsLength).apply();
            }
            Intent intent = new Intent(getApplicationContext(), TriviaResultActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            currentQuestion = questionsList.get(currentQuestionIndex);
            questionTextView.setText(currentQuestion.getQuestion());
            option1TextView.setText(currentQuestion.getOption1());
            option2TextView.setText(currentQuestion.getOption2());
            option3TextView.setText(currentQuestion.getOption3());
            option4TextView.setText(currentQuestion.getOption4());
            questionCounterTextView.setText((currentQuestionIndex + 1) + "/" + questionsLength);
        }
    }

}
