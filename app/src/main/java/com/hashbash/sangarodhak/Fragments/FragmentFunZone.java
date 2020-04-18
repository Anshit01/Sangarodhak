package com.hashbash.sangarodhak.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class FragmentFunZone extends Fragment {

    private Button triviaButton;


    private RecyclerView recyclerView;
    private ArrayList<FunZoneModal> allFunItems = new ArrayList<>();

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

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        try {
//            DexFile df = new DexFile(getContext().getPackageCodePath());
//            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements();) {
//                String s = iter.nextElement();
//                Log.d("FunZone", s);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        allFunItems.add(new FunZoneModal("Mini Games",
                "MiniGameActivity",
                new String[]{"Tic Tac Toe"},
                new String[]{"TicTacToe"}));
        allFunItems.add(new FunZoneModal("Satisfying",
                "MiniGameActivity",
                new String[]{"Box Divider", "Cube Divider"},
                new String[]{"BoxDivider", "CubeDivider"}));


        recyclerView.setAdapter(new FunZoneRecyclerAdapter(getContext(), allFunItems));

        return view;
    }
}
