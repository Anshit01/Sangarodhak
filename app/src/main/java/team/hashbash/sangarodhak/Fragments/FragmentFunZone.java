package team.hashbash.sangarodhak.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import team.hashbash.sangarodhak.Adapters.FunZoneRecyclerAdapter;
import team.hashbash.sangarodhak.Modals.FunZoneModal;
import team.hashbash.sangarodhak.R;

public class FragmentFunZone extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<FunZoneModal> allFunItems = new ArrayList<>();
    private Map<String, String> allFunItemsImageLinks = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fun_zone, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference imageDatabase = FirebaseDatabase.getInstance().getReference("funzone");

        imageDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    allFunItemsImageLinks.put((String) dataSnapshot.child("" + i).child("item_name").getValue(), (String) dataSnapshot.child("" + i).child("item_image_link").getValue());
                }
                showFunItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getLinks();
        showFunItems();

        return view;
    }

    private void showFunItems() {
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
                "TriviaActivity",
                new String[]{"" + allFunItemsImageLinks.get("Trivia")},
                new String[]{"MCQs"},
                new String[]{"Trivia"}));

        saveLinks();

        recyclerView.setAdapter(new FunZoneRecyclerAdapter(getContext(), allFunItems));
    }

    private void saveLinks() {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.pref_user_data), Context.MODE_PRIVATE);

        String temp = gson.toJson(allFunItemsImageLinks);

        sharedPreferences.edit().putString("imageLinks", temp).apply();

    }

    void getLinks() {

        allFunItemsImageLinks = new HashMap<>();

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.pref_user_data), Context.MODE_PRIVATE);

        String temp = sharedPreferences.getString("imageLinks", "[]");

        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();

        allFunItemsImageLinks = gson.fromJson(temp, type);

        showFunItems();
    }

}
