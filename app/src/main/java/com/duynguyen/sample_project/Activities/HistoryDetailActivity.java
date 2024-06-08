package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.duynguyen.sample_project.Adapters.HistoryDetailsAdapter;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryDetailActivity extends AppCompatActivity {
    ImageButton btnBack;
    HistoryDetailsAdapter historyDetailsAdapter;
    RecyclerView recyclerViewDetails;
    TextView txtFullname, txtNote, txtStartDay, txtEndDay;
    TextView txtStatus, txtPhoneNumber, txtAddress;
    ArrayList<ReceiptDetail> detailsList;
    History history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        Objects.requireNonNull(getSupportActionBar()).hide();

        Mapping();
        initUI();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initUI(){
        Intent intent = getIntent();
        if (intent != null){
            history = (History) intent.getSerializableExtra("history");
            if (history != null){
                txtFullname.setText(history.getFullname());
                txtPhoneNumber.setText(history.getPhoneNumber());
                txtAddress.setText(history.getAddress());
                txtNote.setText(history.getNote());
                if (history.getStatus() == 0){
                    txtStatus.setText("Chưa trả");
                    txtStatus.setTextColor(Color.parseColor("#E91E63"));
                }else {
                    txtStatus.setText("Đã trả");
                    txtStatus.setTextColor(Color.parseColor("#009688"));
                }

                txtStartDay.setText(history.getStartDay());
                txtEndDay.setText(history.getEndDay());
                detailsList = history.getDetailsList();
            }
        }

        recyclerViewDetails.setLayoutManager(new LinearLayoutManager(HistoryDetailActivity.this));
        historyDetailsAdapter = new HistoryDetailsAdapter(HistoryDetailActivity.this, detailsList, false);
        recyclerViewDetails.setAdapter(historyDetailsAdapter);

    }

    private void Mapping(){
        btnBack = findViewById(R.id.btnBack);
        recyclerViewDetails = findViewById(R.id.recyclerViewDetails);
        txtFullname = findViewById(R.id.txtFullname);
        txtNote =findViewById(R.id.txtNote);
        txtStartDay = findViewById(R.id.txtStartDay);
        txtEndDay = findViewById(R.id.txtEndDay);
        txtStatus = findViewById(R.id.txtStatus);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        detailsList = new ArrayList<>();
        history = null;
    }
}