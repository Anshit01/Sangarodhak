//package com.hashbash.sangarodhak.Adapters;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//import com.google.firebase.database.DataSnapshot;
//
//import java.util.ArrayList;
//
//
//public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.AnnouncementViewHolder> {
//    Context context;
//    DataSnapshot dataSnapshot;
//    private int arraySize;
//
//    public NoticeRecyclerAdapter(Context context, DataSnapshot dataSnapshot) {
//        this.context = context;
//        this.dataSnapshot = dataSnapshot;
//        arraySize = (int) dataSnapshot.getChildrenCount();
//    }
//
//    @NonNull
//    @Override
//    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.announcement_unit, parent, false);
//        return new AnnouncementViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final AnnouncementViewHolder holder, final int position) {
//        Glide.with(context).load(dataSnapshot.child("" + (arraySize - position - 1)).child("image").getValue()).addListener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                String normalText = (String) dataSnapshot.child("" + (arraySize - position - 1)).child("text").getValue();
//                ArrayList<Integer> asterisks = new ArrayList<>();
//                ArrayList<Integer> underscores = new ArrayList<>();
//                for (int i = 0; i < normalText.length(); i++) {
//                    if (normalText.charAt(i) == '*')
//                        asterisks.add(i);
//                }
//                SpannableStringBuilder convertText = new SpannableStringBuilder(normalText);
//                for (int i = 0; i < asterisks.size(); i += 2) {
//                    convertText.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), asterisks.get(i) - i, asterisks.get(i + 1) - i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    convertText.delete(asterisks.get(i) - i, asterisks.get(i) - i + 1);
//                    convertText.delete(asterisks.get(i + 1) - i - 1, asterisks.get(i + 1) - i);
//                }
//                for (int i = 0; i < convertText.length(); i++) {
//                    if (convertText.charAt(i) == '_')
//                        underscores.add(i);
//                }
//                for (int i = 0; i < underscores.size(); i += 2) {
//                    convertText.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), underscores.get(i) - i, underscores.get(i + 1) - i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    convertText.delete(underscores.get(i) - i, underscores.get(i) - i + 1);
//                    convertText.delete(underscores.get(i + 1) - i - 1, underscores.get(i + 1) - i);
//                }
//                holder.announceText.setText(convertText);
//                holder.announceTeamName.setText((String) dataSnapshot.child("" + (arraySize - position - 1)).child("from").getValue());
//                return false;
//            }
//        }).into(holder.announceImage);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arraySize;
//    }
//
//    class AnnouncementViewHolder extends RecyclerView.ViewHolder {
//        ImageView announceImage;
//        TextView announceText, announceTeamName;
//
//        public AnnouncementViewHolder(@NonNull View itemView) {
//            super(itemView);
//            announceImage = itemView.findViewById(R.id.announceimage);
//            announceText = itemView.findViewById(R.id.announcetext);
//            announceTeamName = itemView.findViewById((R.id.teamName));
//        }
//    }
//}
