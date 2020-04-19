package com.hashbash.sangarodhak;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class TriviaQuestionActivity extends AppCompatActivity {

    Dialog dialogTrivia, dialogTriviaResult;
    ProgressBar progressBar;
    private int questionsLength;
    private int currentQuestionIndex = 0, correctAnswers = 0;
    private ArrayList<TriviaQuestionDataModal> questionsList;
    private TriviaQuestionDataModal currentQuestion;
    private TextView questionTextView, option1TextView, option2TextView, option3TextView, option4TextView, questionCounterTextView;
    private int waitTime = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question);

        progressBar = findViewById(R.id.progress_bar);

        dialogTrivia = new Dialog(this);
        dialogTrivia.setContentView(R.layout.dialog_trivia);
        dialogTrivia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTrivia.setCancelable(false);

        dialogTrivia.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionNumber = ((Spinner) dialogTrivia.findViewById(R.id.question_number_spinner)).getSelectedItem().toString();
                int categoryID = ((Spinner) dialogTrivia.findViewById(R.id.category_spinner)).getSelectedItemPosition();
                String difficulty = ((Spinner) dialogTrivia.findViewById(R.id.difficulty_spinner)).getSelectedItem().toString().toLowerCase();
                progressBar.setVisibility(View.VISIBLE);
                fetchData(questionNumber, categoryID, difficulty);
                dialogTrivia.dismiss();
            }
        });

        dialogTrivia.show();

        dialogTriviaResult = new Dialog(this);
        dialogTriviaResult.setContentView(R.layout.dialog_trivia_result);
        dialogTriviaResult.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTriviaResult.setCancelable(false);

        dialogTriviaResult.findViewById(R.id.trivia_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                if (currentQuestion.getOption1().equals(currentQuestion.getAnswer())) {
                    correctAnswers++;
                    option1TextView.setBackground(getDrawable(R.drawable.trivia_option_background_right));
                } else
                    option1TextView.setBackground(getDrawable(R.drawable.trivia_option_background_wrong));
                currentQuestionIndex++;
                option1TextView.setClickable(false);
                option2TextView.setClickable(false);
                option3TextView.setClickable(false);
                option4TextView.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateQuestion();
                    }
                }, waitTime);
            }
        });

        option2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion.getOption2().equals(currentQuestion.getAnswer())) {
                    correctAnswers++;
                    option2TextView.setBackground(getDrawable(R.drawable.trivia_option_background_right));
                } else
                    option2TextView.setBackground(getDrawable(R.drawable.trivia_option_background_wrong));
                currentQuestionIndex++;
                option1TextView.setClickable(false);
                option2TextView.setClickable(false);
                option3TextView.setClickable(false);
                option4TextView.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateQuestion();
                    }
                }, waitTime);
            }
        });

        option3TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion.getOption3().equals(currentQuestion.getAnswer())) {
                    correctAnswers++;
                    option3TextView.setBackground(getDrawable(R.drawable.trivia_option_background_right));
                } else
                    option3TextView.setBackground(getDrawable(R.drawable.trivia_option_background_wrong));
                currentQuestionIndex++;
                option1TextView.setClickable(false);
                option2TextView.setClickable(false);
                option3TextView.setClickable(false);
                option4TextView.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateQuestion();
                    }
                }, waitTime);
            }
        });

        option4TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion.getOption4().equals(currentQuestion.getAnswer())) {
                    correctAnswers++;
                    option4TextView.setBackground(getDrawable(R.drawable.trivia_option_background_right));
                } else
                    option4TextView.setBackground(getDrawable(R.drawable.trivia_option_background_wrong));
                currentQuestionIndex++;
                option1TextView.setClickable(false);
                option2TextView.setClickable(false);
                option3TextView.setClickable(false);
                option4TextView.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateQuestion();
                    }
                }, waitTime);
            }
        });
    }

    private void fetchData(final String questionNumber, int categoryID, String difficulty) {
        String url = "https://opentdb.com/api.php?amount=" + questionNumber + "&";
        if (categoryID != 0) {
            url += "category=" + (categoryID + 8) + "&";
        }
        url += "difficulty=" + difficulty + "&type=multiple";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("response_code") != 0) {
                        Toast.makeText(getApplicationContext(), "Not enough questions found for selected options. Try some other set of options!", Toast.LENGTH_SHORT).show();
                        dialogTrivia.show();
                    } else {
                        JSONArray questions = response.getJSONArray("results");
                        Log.d("log", "done");
                        progressBar.setVisibility(View.GONE);
                        questionsList = new ArrayList<>();
                        int len = questions.length();
                        for (int i = 0; i < len; i++) {
                            questionsList.add(new TriviaQuestionDataModal(questions.getJSONObject(i)));
                        }
                        questionsLength = len;
                        updateQuestion();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("log", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Network issue. Try again later!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log", error.getMessage());
                Toast.makeText(getApplicationContext(), "Network issue. Try again later!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(request);
    }

    private void updateQuestion() {
        option1TextView.setBackground(getDrawable(R.drawable.trivia_option_background));
        option2TextView.setBackground(getDrawable(R.drawable.trivia_option_background));
        option3TextView.setBackground(getDrawable(R.drawable.trivia_option_background));
        option4TextView.setBackground(getDrawable(R.drawable.trivia_option_background));

        option1TextView.setClickable(true);
        option2TextView.setClickable(true);
        option3TextView.setClickable(true);
        option4TextView.setClickable(true);

        if (currentQuestionIndex >= questionsLength) {
            SharedPreferences preferences = getSharedPreferences(getString(R.string.pref_trivia_data), MODE_PRIVATE);
            String best = preferences.getString(getString(R.string.pref_trivia_highest_score), "0/10");
            int bestCorrect = Integer.parseInt(best.substring(0, best.indexOf('/')));
            int bestTotal = Integer.parseInt(best.substring(best.indexOf('/') + 1));
            String score = "Score: " + correctAnswers + "/" + questionsLength + "\n";
            if (correctAnswers * (60 / questionsLength) >= bestCorrect * (60 / bestTotal)) {
                preferences.edit().putString(getString(R.string.pref_trivia_highest_score), correctAnswers + "/" + questionsLength).apply();
//                ((TextView)dialogTriviaResult.findViewById(R.id.trivia_score)).setText("Score: " + correctAnswers + "/" + questionsLength + "\bBest Score: " );
                score += "Best Score: " + correctAnswers + "/" + questionsLength;
            } else
                score += "Best Score: " + bestCorrect + "/" + bestTotal;

            ((TextView) dialogTriviaResult.findViewById(R.id.trivia_score)).setText(score);

            dialogTriviaResult.show();

        } else {
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
