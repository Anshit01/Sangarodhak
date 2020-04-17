package com.hashbash.sangarodhak.Fragments;

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
import com.hashbash.sangarodhak.Adapters.NoticeRecyclerAdapter;
import com.hashbash.sangarodhak.Modals.NoticeDataModal;
import com.hashbash.sangarodhak.R;

import java.util.ArrayList;

public class FragmentNotice extends Fragment {

    private RecyclerView recyclerView;
    private DataSnapshot Data;
    private ProgressBar loading;

    private ArrayList<NoticeDataModal> allNotice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        loading = view.findViewById(R.id.loading);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

        return view;
    }

    private void getData() {
        FirebaseDatabase.getInstance().getReference("notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allNotice = new ArrayList<>();
                for (int i = (int) dataSnapshot.getChildrenCount() - 1; i >= 0; i--)
                    allNotice.add(new NoticeDataModal(dataSnapshot.child(""+i).child("image").toString(), dataSnapshot.child(""+i).child("from").toString(), dataSnapshot.child(""+i).child("text").toString() ));

                recyclerView.setAdapter(new NoticeRecyclerAdapter(getContext(), allNotice));
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}
