package com.khahani.whatsappshowdeletedmessages.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.khahani.whatsappshowdeletedmessages.Model.Chat;
import com.khahani.whatsappshowdeletedmessages.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatsViewHolder> {

    private final Context context;
    private ArrayList<Chat> chats;

    public MessageAdapter(Context context, ArrayList<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChatsViewHolder(LayoutInflater.from(context).inflate(R.layout.message_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder chatsViewHolder, int i) {

        Chat chat = chats.get(i);

        if (chat.getName() == null) {
            //It is just a footer
            chatsViewHolder.cardView.setVisibility(View.INVISIBLE);
        }

//		Glide.with(context)
//			.load(chat.getImage())
//			.apply(new RequestOptions().placeholder(R.drawable.profile))
//			.into(chatsViewHolder.profilePic);
        resizeCardView(chat, chatsViewHolder);
        chatsViewHolder.message.setText(chat.getLastMessage());
        chatsViewHolder.date.setText(chat.getLastMessageTime());

    }

    private void resizeCardView(Chat chat, ChatsViewHolder chatsViewHolder) {
        if (chat.getLastMessage().length() < 60) {
            ConstraintLayout.LayoutParams params =
                    (ConstraintLayout.LayoutParams) chatsViewHolder.cardView.getLayoutParams();

            ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);

            newParams.topToTop = params.topToTop;
            newParams.startToStart = params.startToStart;
            newParams.leftToLeft = params.leftToLeft;
            newParams.setMarginStart(params.getMarginStart());
            newParams.topMargin = params.topMargin;

            chatsViewHolder.cardView.setLayoutParams(newParams);
        }
    }

    @Override
    public int getItemCount() {
        if (chats != null) {
            return chats.size();
        }
        return 0;
    }

    class ChatsViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView message;
        public CardView cardView;

        ChatsViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.tv_message);
            date = itemView.findViewById(R.id.tv_date);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
