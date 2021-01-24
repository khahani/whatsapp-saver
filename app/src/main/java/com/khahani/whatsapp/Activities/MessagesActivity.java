package com.khahani.whatsapp.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.khahani.whatsapp.Adapters.MessageAdapter;
import com.khahani.whatsapp.Model.Chat;
import com.khahani.whatsapp.Model.db.Db;
import com.khahani.whatsapp.Model.db.ReceivedMessage;
import com.khahani.whatsapp.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MessagesActivity extends AppCompatActivity {

    private String sender;
    private ArrayList<Chat> chats;
    private MessageAdapter adapter;
    private RecyclerView rvMessages;
    private Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        sender = getIntent().getStringExtra("sender");

        if (sender == null || sender.isEmpty()) {
            finish();
            return;
        }

        setTitle(sender);

        setupViews();

        setAdapter();

        populateChats();

        initAds();
    }

    private void initAds() {
        final AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.native1))
                .forUnifiedNativeAd(ad -> {


//                        if (adLoader.isLoading()) {
//                            // The AdLoader is still loading ads.
//                            // Expect more adLoaded or onAdFailedToLoad callbacks.
//                        } else {
//                            // The AdLoader has finished loading ads.
//                        }

                    if (isDestroyed()) {
                        ad.destroy();
                    }

                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void setupViews() {
        rvMessages = findViewById(R.id.rvMessages);
    }

    private void populateChats() {
        //Population logic goes here
        this.db = Db.getInstance(this);
        db.receivedMessageDao().getChats(sender).observe(this, receivedMessages -> {
            chats.clear();
            DateFormat format;
            for (ReceivedMessage rm : receivedMessages) {
                format = DateFormat.getTimeInstance(DateFormat.SHORT);
                chats.add(new Chat(rm.sender, rm.text, format.format(rm.date), rm.group));
            }
            adapter.setChats(chats);
            adapter.notifyDataSetChanged();
            rvMessages.smoothScrollToPosition(adapter.getItemCount() - 1);
        });
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
//        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(linearLayoutManager);
        rvMessages.setAdapter(adapter);

    }

}