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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hashbash.sangarodhak.Adapters.FunZoneRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.FunZoneModal;
import com.hashbash.sangarodhak.R;
import com.hashbash.sangarodhak.TriviaActivity;

import java.util.ArrayList;

public class FragmentFunZone extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<FunZoneModal> allFunItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fun_zone, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        allFunItems.add(new FunZoneModal("Mini Games",
                "MiniGameActivity",
                new String[]{"Tic Tac Toe"},
                new String[]{"TicTacToe"}));
        allFunItems.add(new FunZoneModal("Satisfying",
                "MiniGameActivity",
                new String[]{"Square Divider", "Cube Divider", "Pattern Drawing", "Circle Pattern Drawing"},
                new String[]{"BoxDivider", "CubeDivider", "Pattern", "CirclePattern"}));
        allFunItems.add(new FunZoneModal("Trivia Quiz",
                "TriviaActivity",
                new String[]{"MCQs"},
                new String[]{""}));


        recyclerView.setAdapter(new FunZoneRecyclerAdapter(getContext(), allFunItems));

        return view;
    }
}
