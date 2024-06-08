package com.duynguyen.sample_project.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Activities.CreateReceiptActivity;
import com.duynguyen.sample_project.Adapters.HistoryAdapter;
import com.duynguyen.sample_project.DAOs.HistoryDAO;
import com.duynguyen.sample_project.DAOs.ReceiptDetailDAO;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ReceiptFragment extends Fragment {

    View view;
    RecyclerView recyclerViewHistory;
    FloatingActionButton fabCreateReceipt;
    HistoryDAO historyDAO;
    HistoryAdapter historyAdapter;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        Mapping();

        initUI();

        fabCreateReceipt.setOnClickListener(v -> startActivity(new Intent(requireActivity(), CreateReceiptActivity.class)));
        return view;
    }
    private void initUI(){
        historyDAO = new HistoryDAO(requireActivity());
        ArrayList<History> historiesList = historyDAO.getALLHistories();
        historyAdapter = new HistoryAdapter(requireActivity(), historiesList);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerViewHistory.setAdapter(historyAdapter);
    };



    private void Mapping() {
        fabCreateReceipt = view.findViewById(R.id.fabCreateReceipt);
        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
    }


}