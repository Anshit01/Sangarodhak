package com.hashbash.sangarodhak.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hashbash.sangarodhak.Adapters.FunZoneRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.FunZoneModal;
import com.hashbash.sangarodhak.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentFunZone extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<FunZoneModal> allFunItems = new ArrayList<>();
    private Map<String, String> allFunItemsImageLinks = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fun_zone, container, false);

        DatabaseReference imageDatabse = FirebaseDatabase.getInstance().getReference("funzone");

        imageDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    allFunItemsImageLinks.put( (String) dataSnapshot.child("" + i).child("item_name").getValue(), (String) dataSnapshot.child("" + i).child("item_image_link").getValue());
                }
                showFunItems();
                Log.d("FunZone", allFunItemsImageLinks.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getLinks();

        return view;
    }

    private void showFunItems(){
        allFunItems = new ArrayList<>();

        allFunItems.add(new FunZoneModal("Mini Games",
                "MiniGameActivity",
                new String[]{"" + allFunItemsImageLinks.get("TicTacToe")},
                new String[]{"Tic Tac Toe"},
                new String[]{"TicTacToe"}));
        allFunItems.add(new FunZoneModal("Satisfying",
                "MiniGameActivity",
                new String[]{"" + allFunItemsImageLinks.get("BoxDivider"), "" + allFunItemsImageLinks.get("CubeDivider"), "" + allFunItemsImageLinks.get("Pattern"), "" + allFunItemsImageLinks.get("CirclePattern")},
                new String[]{"Square Divider", "Cube Divider", "Pattern Drawing", "Circle Pattern Drawing"},
                new String[]{"BoxDivider", "CubeDivider", "Pattern", "CirclePattern"}));
        allFunItems.add(new FunZoneModal("Trivia Quiz",
                "TriviaQuestionActivity",
                new String[]{"" + allFunItemsImageLinks.get("Trivia")},
                new String[]{"MCQs"},
                new String[]{"Trivia"}));

        saveLinks();


        recyclerView.setAdapter(new FunZoneRecyclerAdapter(getContext(), allFunItems));

    }

    void saveLinks(){

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.pref_user_data), Context.MODE_PRIVATE);

        String temp = gson.toJson(allFunItemsImageLinks);

        sharedPreferences.edit().putString("imageLinks", temp).apply();

    }

    void getLinks(){

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.pref_user_data), Context.MODE_PRIVATE);

        String temp = sharedPreferences.getString("imageLinks", "[]");

        if(!temp.equals("[]")){

            Type type = new TypeToken<HashMap<String, String>>(){}.getType();

            allFunItemsImageLinks = gson.fromJson(temp, type);

            showFunItems();
        }
    }

}
