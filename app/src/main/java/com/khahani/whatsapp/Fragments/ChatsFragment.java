package com.khahani.whatsapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khahani.whatsapp.Adapters.ChatsAdapter;
import com.khahani.whatsapp.Model.Chat;
import com.khahani.whatsapp.Model.db.Db;
import com.khahani.whatsapp.Model.db.ReceivedMessage;
import com.khahani.whatsapp.R;
import com.khahani.whatsapp.admob.AdapterBanner;
import com.khahani.whatsapp.admob.Interstitial;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

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
        AdapterBanner adaptiveBanner = new AdapterBanner(getActivity(), layout);
        adaptiveBanner.initAds();
    }

    private void initInterstitialAds() {
        interstitial = new Interstitial(getActivity(), () -> interstitial.show());
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
}
