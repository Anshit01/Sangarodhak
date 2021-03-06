package team.hashbash.sangarodhak.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import team.hashbash.sangarodhak.Modals.NoticeDataModal;
import team.hashbash.sangarodhak.R;

import java.util.ArrayList;


public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.AnnouncementViewHolder> {
    private Context context;

    private ArrayList<NoticeDataModal> allNotice;

    public NoticeRecyclerAdapter(Context context, ArrayList<NoticeDataModal> allNotice) {
        this.context = context;
        this.allNotice = allNotice;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_notice, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnouncementViewHolder holder, final int position) {

        NoticeDataModal thisNotice = allNotice.get(position);

        Glide.with(context).load(thisNotice.getImageLink()).into(holder.noticeImage);

        String normalText = thisNotice.getText();
        if (!normalText.isEmpty()) {
            ArrayList<Integer> asterisks = new ArrayList<>();
            ArrayList<Integer> underscores = new ArrayList<>();
            for (int i = 0; i < normalText.length(); i++) {
                if (normalText.charAt(i) == '*')
                    asterisks.add(i);
            }
            SpannableStringBuilder convertText = new SpannableStringBuilder(normalText);
            for (int i = 0; i < asterisks.size(); i += 2) {
                convertText.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), asterisks.get(i) - i, asterisks.get(i + 1) - i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                convertText.delete(asterisks.get(i) - i, asterisks.get(i) - i + 1);
                convertText.delete(asterisks.get(i + 1) - i - 1, asterisks.get(i + 1) - i);
            }
            for (int i = 0; i < convertText.length(); i++) {
                if (convertText.charAt(i) == '_')
                    underscores.add(i);
            }
            for (int i = 0; i < underscores.size(); i += 2) {
                convertText.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), underscores.get(i) - i, underscores.get(i + 1) - i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                convertText.delete(underscores.get(i) - i, underscores.get(i) - i + 1);
                convertText.delete(underscores.get(i + 1) - i - 1, underscores.get(i + 1) - i);
            }
            holder.noticeText.setText(convertText);
        } else
            holder.noticeText.setVisibility(View.GONE);
        holder.noticeFrom.setText(thisNotice.getFrom());

    }

    @Override
    public int getItemCount() {
        return allNotice.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        ImageView noticeImage;
        TextView noticeFrom, noticeText;

        AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeImage = itemView.findViewById(R.id.notice_image);
            noticeText = itemView.findViewById(R.id.notice_text);
            noticeFrom = itemView.findViewById((R.id.notice_from));
        }
    }
}
