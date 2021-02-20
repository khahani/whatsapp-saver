package com.whatsappear.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khahani.usecase_firebase.admob.Banner;
import com.khahani.usecase_firebase.admob.Interstitial;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.whatsappear.Activities.MainActivity;
import com.whatsappear.Adapters.ChatsAdapter;
import com.whatsappear.Model.Chat;
import com.whatsappear.R;
import com.whatsappear.creator.firebase.BannerCreator;
import com.whatsappear.creator.firebase.InterstitialCreator;
import com.whatsappear.db.Db;
import com.whatsappear.db.adapter.ChatAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends BaseFragment {

    private ArrayList<Chat> chats;

    private RecyclerView rvChats;
    private ChatsAdapter adapter;
    private Observer<List<com.whatsappear.db.Chat>> observer;

    private View layout;
    private Db db;
    private Interstitial interstitial;
    private Banner banner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.chats_layout, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
        setAdapter();
        populateChats();
        runAds();

    }

    private void runAds() {
        //khahani: determine which ads show based on firebase config
        if (isNotificationEnabled()) {
            initBannerAds();
            initInterstitialAds();
        }
    }

    private boolean isNotificationEnabled() {
        MainActivity activity = (MainActivity) getActivity();
        return activity != null && activity.isNotificationEnabled();
    }

    private void initBannerAds() {
        //khahani: put real bannerId
        String realBannerUid = getString(R.string.banner_real_uid);
        banner = new BannerCreator(getActivity(),
                layout, R.id.bannerContainer, realBannerUid).factoryMethod();
        banner.run();
    }

    private void initInterstitialAds() {
        //khahani: put real bannerId
        String realInterstitialId = getString(R.string.interstitial_real_uid);
        interstitial = new InterstitialCreator(getActivity(), realInterstitialId, interstitial).factoryMethod();
        interstitial.run();
    }

    @Override
    public void onStart() {
        super.onStart();
        db.chatDao().getAll().observe(getViewLifecycleOwner(), observer);
    }

    private void initialize(View view) {
        chats = new ArrayList<>();
        rvChats = view.findViewById(R.id.rvChats);
    }

    private void populateChats() {
        this.db = Db.getInstance(getContext());

        observer = chatsDb -> {
            new ChatAdapter();
            chats.clear();
            DateFormat format;
            for (com.whatsappear.db.Chat chat : chatsDb) {
                format = DateFormat.getTimeInstance(DateFormat.SHORT);
                chats.add(new Chat(chat.sender, chat.text, format.format(chat.date), chat.group));
            }
            adapter.setChats(chats);
            adapter.notifyDataSetChanged();
        };
    }

    private void setAdapter() {

        adapter = new ChatsAdapter(getContext(), chats);
        rvChats.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChats.setAdapter(adapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        db.chatDao().getAll().removeObserver(observer);
    }

    @Override
    public String getScreenName() {
        return "contacts and groups screen";
    }

    @Override
    public String getClassName() {
        return ChatsFragment.class.getName();
    }

    @Override
    protected TrackScreen initTrackScreen(LogEvent logger) {
        return new TrackScreen(logger, this);
    }
}