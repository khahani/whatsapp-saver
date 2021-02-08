package com.testing.whatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.testing.whatsapp.Activities.MessagesActivity;
import com.testing.whatsapp.Model.Chat;
import com.testing.whatsapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

	private final Context context;
	private ArrayList<Chat> chats;

	public ChatsAdapter(Context context, ArrayList<Chat> chats) {
		this.context = context;
		this.chats = chats;
	}

	public void setChats(ArrayList<Chat> chats) {
		this.chats = chats;
	}

	@NonNull
	@Override
	public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		return new ChatsViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_row, viewGroup, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ChatsViewHolder chatsViewHolder, int i) {

		Chat chat = chats.get(i);

		if (chat.getName() == null) {
			//It is just a footer
			chatsViewHolder.chatLayoutContainer.setVisibility(View.INVISIBLE);
		}

		Glide.with(context)
				.load(chat.getImage())
				.apply(new RequestOptions().placeholder(R.drawable.profile))
				.into(chatsViewHolder.profilePic);

		if (chat.getGroup().equals("c")) {
			chatsViewHolder.tvName.setText(chat.getName());
			chatsViewHolder.tvMsg.setText(chat.getLastMessage());
		} else {
			chatsViewHolder.tvName.setText(chat.getGroup());
			chatsViewHolder.tvMsg.setText(String.format("%s: %s", chat.getName(), chat.getLastMessage()));
		}

		chatsViewHolder.tvTime.setText(chat.getLastMessageTime());

	}

	@Override
	public int getItemCount() {
		if (chats != null) {
			return chats.size();
		}
		return 0;
	}

	class ChatsViewHolder extends RecyclerView.ViewHolder {
		CircleImageView profilePic;
		TextView tvName;
		TextView tvMsg;
		TextView tvTime;
		RelativeLayout chatLayoutContainer;

		ChatsViewHolder(@NonNull View itemView) {
			super(itemView);

			profilePic = itemView.findViewById(R.id.profile_image);
			tvName = itemView.findViewById(R.id.tvName);
			tvMsg = itemView.findViewById(R.id.tvLastMsg);
			tvTime = itemView.findViewById(R.id.tvTime);
			chatLayoutContainer = itemView.findViewById(R.id.chat_row_container);

			chatLayoutContainer.setOnClickListener(view -> {

			});

			chatLayoutContainer.setOnClickListener(v -> {
				Intent intent = new Intent(context, MessagesActivity.class);
				//khahani: decide based on group or contact
				intent.putExtra("sender", chats.get(getAdapterPosition()).getName());
				context.startActivity(intent);
			});
		}
	}
}
