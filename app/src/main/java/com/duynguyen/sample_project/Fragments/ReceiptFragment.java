package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duynguyen.sample_project.Activities.CreateReceiptActivity;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class ReceiptFragment extends Fragment {

   View view;
    FloatingActionButton fabCreateReceipt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        Mapping();

        fabCreateReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), CreateReceiptActivity.class));
            }
        });
        return view;
    }

    private void Mapping() {
        fabCreateReceipt = view.findViewById(R.id.fabCreateReceipt);
    }




}