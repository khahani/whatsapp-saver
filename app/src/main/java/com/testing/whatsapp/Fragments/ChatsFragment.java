package com.testing.whatsapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.testing.firebase.admob.AdapterBanner;
import com.testing.firebase.admob.Interstitial;
import com.testing.firebase.analytic.LogEvent;
import com.testing.whatsapp.Adapters.ChatsAdapter;
import com.testing.whatsapp.Model.Chat;
import com.testing.whatsapp.Model.db.Db;
import com.testing.whatsapp.Model.db.ReceivedMessage;
import com.testing.whatsapp.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends BaseFragment {

    private ArrayList<Chat> chats;

    private RecyclerView rvChats;
    private ChatsAdapter adapter;
    private Observer<List<ReceivedMessage>> observer;

    private View layout;
    private Db db;
    private Interstitial interstitial;


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
        populateChats();
        setAdapter();

        //khahani: determine which ads show based on firebase config
        initBannerAds();
        initInterstitialAds();
    }

    private void initBannerAds() {
        String realBannerId = getString(R.string.banner_real_uid);
        AdapterBanner adaptiveBanner = new AdapterBanner(getActivity(), layout, R.id.bannerContainer, realBannerId);
        adaptiveBanner.initAds();
    }

    private void initInterstitialAds() {
        String realInterstitialId = getString(R.string.interstitial_real_uid);
        interstitial = new Interstitial(getActivity(), () -> interstitial.show(), realInterstitialId);
        interstitial.loadAd();
    }

    @Override
    public void onStart() {
        super.onStart();
        db.receivedMessageDao().getSenders().observe(getViewLifecycleOwner(), observer);
    }

    private void initialize(View view) {

        chats = new ArrayList<>();
        rvChats = view.findViewById(R.id.rvChats);

    }

    private void populateChats() {
        this.db = Db.getInstance(getContext());
        observer = receivedMessages -> {
            chats.clear();
            DateFormat format;
            for (ReceivedMessage rm : receivedMessages) {
                format = DateFormat.getTimeInstance(DateFormat.SHORT);
                chats.add(new Chat(rm.sender, rm.text, format.format(rm.date), rm.group));
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
        db.receivedMessageDao().getSenders().removeObserver(observer);
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
