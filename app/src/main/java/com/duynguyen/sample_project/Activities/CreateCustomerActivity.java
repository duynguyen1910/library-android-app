package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.Objects;

public class CreateCustomerActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnReset;
    Button btnSubmit;
    EditText edtFullname;
    EditText edtPhoneNumber;
    EditText edtAddress;

    MemberDAO memberDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        Objects.requireNonNull(getSupportActionBar()).hide();

        Mapping();
        Intent intent = getIntent();
        if (intent != null) {
            String newPhoneNumber = intent.getStringExtra("newPhoneNumber");
            if (newPhoneNumber != null) {
                edtPhoneNumber.setText(newPhoneNumber);
            }
        }

        btnBack.setOnClickListener(v -> finish());


        btnSubmit.setOnClickListener(v -> {
            assert edtFullname != null;
            String fullname = edtFullname.getText().toString().trim();
            assert edtPhoneNumber != null;
            String phoneNumber = edtPhoneNumber.getText().toString().trim();
            assert edtAddress != null;
            String address = edtAddress.getText().toString().trim();

            if (fullname.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(CreateCustomerActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();


            } else {
                MemberDAO memberDAO = new MemberDAO(CreateCustomerActivity.this);
                Member newMember = new Member(fullname, R.mipmap.avatar_customer, phoneNumber, address, "", 0);
                int check = memberDAO.register(newMember);
                if (check == 0) {
                    Toast.makeText(CreateCustomerActivity.this, "Số điện thoại tồn tại, không thể đăng ký", Toast.LENGTH_SHORT).show();
                } else if (check == -1) {

                    Toast.makeText(CreateCustomerActivity.this, "Lỗi đăng ký, vui lòng thử lại", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(CreateCustomerActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnReset.setOnClickListener(v -> {
            edtFullname.setText("");
            edtAddress.setText("");
            edtPhoneNumber.setText("");
        });

    }

    private void Mapping() {
        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtFullname = findViewById(R.id.edtFullname);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtAddress = findViewById(R.id.edtAddress);
        btnReset = findViewById(R.id.btnReset);
        memberDAO = new MemberDAO(CreateCustomerActivity.this);
    }
}