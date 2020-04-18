package com.hashbash.sangarodhak.Modals;

import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class TriviaQuestionDataModal {

    private String question, category, difficulty;
    private String answer;
    private ArrayList<String> options;

    public TriviaQuestionDataModal(JSONObject question) throws JSONException {
        options = new ArrayList<>();
        this.question = parseString(question.getString("question"));
        category = question.getString("category");
        difficulty = question.getString("difficulty");
        difficulty = difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1);
        answer = parseString(question.getString("correct_answer"));
        JSONArray incorrectAnswers = question.getJSONArray("incorrect_answers");
        for(int i = 0; i < 3; i++){
            options.add(parseString(incorrectAnswers.getString(i)));
        }
        Random random = new Random();
        int answerPosition = random.nextInt(4);
        options.add(answerPosition, answer);
    }

    private String parseString(String str){
        str = str.replaceAll("&quot;", "\"")
            .replaceAll("&lrm;", "")
            .replaceAll("&rsquo;", "\'")
            .replaceAll("&#039;", "\'")
            .replaceAll("&amp;", "&");
        return str;
    }

    public String getQuestion() {
        return question;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getAnswer() {
        return answer;
    }

    public String getOption1(){
        return options.get(0);
    }

    public String getOption2(){
        return options.get(1);
    }

    public String getOption3(){
        return options.get(2);
    }

    public String getOption4(){
        return options.get(3);
    }

    public ArrayList<String> getOptions() {
        return options;
    }

}
