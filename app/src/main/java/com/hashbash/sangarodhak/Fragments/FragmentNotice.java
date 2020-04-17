package com.hashbash.sangarodhak.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hashbash.sangarodhak.Adapters.NoticeRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.NoticeDataModal;
import com.hashbash.sangarodhak.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FragmentNotice extends Fragment {

    private RecyclerView recyclerView;
    private DataSnapshot Data;
    private ProgressBar loading;

    private ArrayList<NoticeDataModal> allNotice;

    private Gson gson = new Gson();
    private SharedPreferences statsPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        statsPreference = getActivity().getSharedPreferences(getString(R.string.pref_stats_data), MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recycler_view);
        loading = view.findViewById(R.id.loading);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        retrieveData();
        getData();

        return view;
    }

    private void getData() {
        FirebaseDatabase.getInstance().getReference("notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allNotice = new ArrayList<>();
                for (int i = (int) dataSnapshot.getChildrenCount() - 1; i >= 0; i--)
                    allNotice.add(new NoticeDataModal((String) dataSnapshot.child("" + i).child("image").getValue(), (String) dataSnapshot.child("" + i).child("from").getValue(), (String) dataSnapshot.child("" + i).child("text").getValue(), (String) dataSnapshot.child("" + i).child("video").getValue()));
                showNotice();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showNotice() {
        loading.setVisibility(View.GONE);
        recyclerView.setAdapter(new NoticeRecyclerAdapter(getContext(), allNotice));
        saveAllData();
    }

    private void saveAllData() {
        String allData = gson.toJson(allNotice);

        statsPreference.edit().putString(getString(R.string.pref_stats_notice_data), allData).apply();
    }

    private void retrieveData() {

        String allData = statsPreference.getString(getString(R.string.pref_stats_notice_data), "[]");

        if (!allData.equals("[]")) {
            Type type = new TypeToken<ArrayList<NoticeDataModal>>() {
            }.getType();

            allNotice = gson.fromJson(allData, type);

            showNotice();
        }

    }

}
