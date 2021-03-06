package com.whatsappear.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khahani.usecase_firebase.admob.Interstitial;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.whatsappear.Adapters.MessageAdapter;
import com.whatsappear.AdvertiseTimeCommand;
import com.whatsappear.Model.Chat;
import com.whatsappear.R;
import com.whatsappear.creator.firebase.InterstitialCreator;
import com.whatsappear.db.Db;
import com.whatsappear.db.ReceivedMessage;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessagesActivity extends BaseActivity {

    private String sender;
    private final Observer<List<ReceivedMessage>> observerPrepareChatsForAdapter = new Observer<List<ReceivedMessage>>() {
        @Override
        public void onChanged(List<ReceivedMessage> receivedMessages) {
            chats.clear();
            DateFormat format;
            for (ReceivedMessage rm : receivedMessages) {
                format = DateFormat.getTimeInstance(DateFormat.SHORT);
                chats.add(new Chat(rm.sender, rm.text, format.format(rm.date), rm.group));
            }
            adapter.setChats(chats);
            //khahani: must change with a better performance
            adapter.notifyDataSetChanged();
            rvMessages.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    };
    private ArrayList<Chat> chats;
    private MessageAdapter adapter;
    private RecyclerView rvMessages;
    private Db db;
    private String group;
    private Interstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        sender = getIntent().getStringExtra("sender");
        group = getIntent().getStringExtra("group");

        if (sender == null || sender.isEmpty()) {
            finish();
            return;
        }

        if (group.equals("c")) {
            setTitle(sender);
        } else {
            setTitle(group);
        }


        setupViews();

        setAdapter();

        populateChats();

        initInterstitialAds();
    }

    private void initInterstitialAds() {
        //khahani: put real bannerId
        AdvertiseTimeCommand a = new AdvertiseTimeCommand(this);
        a.run();
        if (!a.shouldShow())
            return;

        String realInterstitialId = getString(R.string.interstitial_real_uid);
        interstitial = new InterstitialCreator(this, realInterstitialId).factoryMethod();
        interstitial.run();
    }

    @Override
    protected TrackScreen initTrackScreen(LogEvent logger) {
        return new TrackScreen(logger, this);
    }

    private void setupViews() {
        rvMessages = findViewById(R.id.rvMessages);
    }

    private void populateChats() {
        this.db = Db.getInstance(this);
        if (group.equals("c")) {
            db.receivedMessageDao().getMessages(sender).observe(this, observerPrepareChatsForAdapter);
        } else {
            db.receivedMessageDao().getMessagesByGroup(group).observe(this, observerPrepareChatsForAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter() {
        chats = new ArrayList<>();
        adapter = new MessageAdapter(this, chats);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(linearLayoutManager);
        rvMessages.setAdapter(adapter);

    }

    @Override
    public String getScreenName() {
        return "messages screen";
    }

    @Override
    public String getClassName() {
        return MessagesActivity.class.getName();
    }
}