package com.hashbash.sangarodhak.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hashbash.sangarodhak.R;
import com.hashbash.sangarodhak.TriviaActivity;

public class FragmentFunZone extends Fragment {

    private Button triviaButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fun_zone, container, false);

        triviaButton = view.findViewById(R.id.trivia_button);


        triviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TriviaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
