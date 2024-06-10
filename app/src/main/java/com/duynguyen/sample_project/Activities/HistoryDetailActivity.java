package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.duynguyen.sample_project.Adapters.HistoryDetailsAdapter;
import com.duynguyen.sample_project.DAOs.ReceiptDAO;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class HistoryDetailActivity extends AppCompatActivity {
    ImageButton btnBack;
    HistoryDetailsAdapter historyDetailsAdapter;
    RecyclerView recyclerViewDetails;
    TextView txtFullname, txtNote, txtStartDay, txtEndDay;
    TextView txtStatus, txtPhoneNumber, txtAddress, txtReturnReceipt;
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

    private void initUI() {
        Intent intent = getIntent();
        if (intent != null) {
            history = (History) intent.getSerializableExtra("history");
            if (history != null) {
                txtFullname.setText(history.getFullname());
                txtPhoneNumber.setText(history.getPhoneNumber());
                txtAddress.setText(history.getAddress());
                txtNote.setText(history.getNote());
                if (history.getStatus() == 0) {
                    updateStatus0();
                } else {
                    updateStatus1();

                }
                txtStartDay.setText(history.getStartDay());

                detailsList = history.getDetailsList();
            }
        }

        recyclerViewDetails.setLayoutManager(new LinearLayoutManager(HistoryDetailActivity.this));
        historyDetailsAdapter = new HistoryDetailsAdapter(HistoryDetailActivity.this, detailsList, false);
        recyclerViewDetails.setAdapter(historyDetailsAdapter);

        txtReturnReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReturnReceipt();
            }
        });

    }

    private void handleReturnReceipt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryDetailActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Xác nhận trả phiếu mượn?");
        builder.setTitle("Warning!");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReceiptDAO receiptDAO = new ReceiptDAO(HistoryDetailActivity.this);
                receiptDAO.updateReceipt(history.getReceiptID(), getDateToday(), 1);
                updateStatus1();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void updateStatus0() {
        txtStatus.setText("Chưa trả");
        txtEndDay.setTextColor(Color.parseColor("#E91E63"));
        txtStatus.setTextColor(Color.parseColor("#E91E63"));
        txtReturnReceipt.setVisibility(View.VISIBLE);
        txtEndDay.setText("null");
    }

    private void updateStatus1() {
        txtStatus.setText("Đã trả");
        txtEndDay.setText(getDateToday());
        txtEndDay.setTextColor(Color.parseColor("#009688"));
        txtStatus.setTextColor(Color.parseColor("#009688"));
        txtReturnReceipt.setVisibility(View.GONE);

    }

    private String getDateToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy", Locale.CHINESE);
        return dinhDangNgay.format(calendar.getTime());
    }

    private void Mapping() {
        btnBack = findViewById(R.id.btnBack);
        recyclerViewDetails = findViewById(R.id.recyclerViewDetails);
        txtFullname = findViewById(R.id.txtFullname);
        txtNote = findViewById(R.id.txtNote);
        txtStartDay = findViewById(R.id.txtStartDay);
        txtEndDay = findViewById(R.id.txtEndDay);
        txtStatus = findViewById(R.id.txtStatus);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtReturnReceipt = findViewById(R.id.txtReturnReceipt);
        detailsList = new ArrayList<>();
        history = null;
    }
}