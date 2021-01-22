package com.khahani.whatsappshowdeletedmessages.Adapters;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.khahani.whatsappshowdeletedmessages.Model.Chat;
import com.khahani.whatsappshowdeletedmessages.Model.db.utils.scrapping.Scrapping;
import com.khahani.whatsappshowdeletedmessages.Model.db.utils.scrapping.ScrappingExecutor;
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

        if (chat.getGroup() != null) {
            if (!chat.getGroup().equals("c")) {
                chatsViewHolder.group.setText(chat.getGroup());
                chatsViewHolder.group.setVisibility(View.VISIBLE);
            }
        }

//<meta property="og:url" content="http://www.nytimes.com/2013/03/16/world/europe/pope-francis-praises-benedict-urges-cardinals-to-spread-gospel.html"/>
//<meta property="og:type" content="article"/>
//<meta property="og:title" content="Vatican Rejects Argentine Accusations Against Pope Francis"/>
//<meta property="og:description" content="The Vatican on Friday formally defended Pope Francis’ role in Argentina’s so-called “Dirty War,” amid accusations that he failed to halt abuses of which he had knowledge.">
//<meta property="og:image" content="http://graphics8.nytimes.com/images/2013/03/16/world/16vatican/16vatican-superJumbo.jpg"/>

        if (chat.getLastMessage().startsWith("http://") || chat.getLastMessage().startsWith("https://")) {
            Scrapping s = new Scrapping(chat.getLastMessage(), new Scrapping.CompletedListener() {
                @Override
                public void onCompleted(String title, String description, String imageUrl) {
                    boolean isFull = !title.isEmpty() && !description.isEmpty() && !imageUrl.isEmpty();
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (isFull) {
                                    chatsViewHolder.group.setText(title);
                                    chatsViewHolder.message.setText(description + " $$$" + imageUrl);
                                } else {
                                    chatsViewHolder.message.setMovementMethod(LinkMovementMethod.getInstance());
                                    chatsViewHolder.message.setText(String.format("a link <a href=\"%s\">%s</a>", chat.getLastMessage(), chat.getLastMessage()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            ScrappingExecutor se = new ScrappingExecutor();
            se.execute(s);
        }

        resizeCardView(chat, chatsViewHolder);
        chatsViewHolder.message.setText(chat.getLastMessage());
        chatsViewHolder.date.setText(chat.getLastMessageTime());

    }

    private void resizeCardView(Chat chat, ChatsViewHolder chatsViewHolder) {
        if (chat.getLastMessage().length() < 50) {

            ConstraintLayout.LayoutParams params =
                    (ConstraintLayout.LayoutParams) chatsViewHolder.cardView.getLayoutParams();

            ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);

            newParams.topToTop = params.topToTop;
            newParams.startToStart = params.startToStart;
            newParams.leftToLeft = params.leftToLeft;
            newParams.topMargin = params.topMargin;
            newParams.setMarginStart(params.getMarginStart());
            newParams.setMarginEnd(params.getMarginEnd());

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
        public TextView group;

        ChatsViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.tv_message);
            date = itemView.findViewById(R.id.tv_date);
            cardView = itemView.findViewById(R.id.cardView);
            group = itemView.findViewById(R.id.tv_group);

        }
    }
}
