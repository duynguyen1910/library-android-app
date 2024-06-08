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
import com.duynguyen.sample_project.DAOs.ReceiptDetailDAO;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ReceiptFragment extends Fragment {

    View view;
    RecyclerView recyclerViewReceiptDetails;
    FloatingActionButton fabCreateReceipt;
    ArrayList<ReceiptDetail>  detailsList;


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
        recyclerViewReceiptDetails.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ReceiptDetailDAO receiptDetailDAO = new ReceiptDetailDAO(requireActivity());


        for (ReceiptDetail detail : detailsList){
//            private String fullname;
//            private String note;
//            private String startDay;
//            private String endDay;
//            private ArrayList<ReceiptDetail> list;
//            ArrayList<ReceiptDetail> receiptCom
            int receiptID = detail.getReceiptID();
            String fullname = detail.getFullname();
            String note = detail.getNote();
            String startDay = detail.getStartDay();
            String endDay = detail.getEndDay();
            ArrayList<ReceiptDetail> listItems = new ArrayList<>();
            History history = new History(receiptID, fullname, note, startDay, endDay, listItems);
        }




    }

    private void Mapping() {
        fabCreateReceipt = view.findViewById(R.id.fabCreateReceipt);
        recyclerViewReceiptDetails = view.findViewById(R.id.recyclerViewReceiptDetails);
    }


}