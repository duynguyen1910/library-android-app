package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.Objects;

public class UpdateLibrarianActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnReset;
    Button btnSubmit;
    EditText edtFullname;
    EditText edtPhoneNumber;
    EditText edtAddress;
    EditText edtPassword;
    Spinner spinner;
    MemberDAO memberDAO;
    private int role = -1;
    Member memberData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_librarian);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        Objects.requireNonNull(getSupportActionBar()).hide();
        Mapping();
        initUI();


        String[] rolesList = new String[]{"Thủ thư", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rolesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    role = position + 1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });
        }


        btnSubmit.setOnClickListener(v -> {
            assert edtFullname != null;
            String fullname = edtFullname.getText().toString().trim();
            assert edtAddress != null;
            String address = edtAddress.getText().toString().trim();
            assert edtPassword != null;
            String password = edtPassword.getText().toString().trim();
            if (fullname.isEmpty() || address.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();

            } else if (password.length() < 6) {
                edtPassword.setError("Mật khẩu ít nhất 6 ký tự");
            } else {
                memberData.setFullname(fullname);
                memberData.setAddress(address);
                memberData.setPassword(password);

                if (!memberDAO.updateMember(memberData)) {
                    Toast.makeText(this, "Lỗi update, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnReset.setOnClickListener(v -> {
            edtFullname.setText("");
            edtAddress.setText("");
            edtPassword.setText("");
        });


        btnBack.setOnClickListener(v -> finish());
    }

    private void initUI() {
        Intent intent = getIntent();
        if (intent != null) {
            memberData = (Member) intent.getSerializableExtra("memberData");
            if (memberData != null) {
                edtFullname.setText(memberData.getFullname());
                edtPhoneNumber.setText(memberData.getPhoneNumber());
                edtAddress.setText(memberData.getAddress());
                edtPassword.setText(memberData.getPassword());
                spinner.setSelection(memberData.getRole() - 1);
            }
        }
    }

    private void Mapping() {
        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtFullname = findViewById(R.id.edtFullname);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtAddress = findViewById(R.id.edtAddress);
        edtPassword = findViewById(R.id.edtPassword);
        spinner = findViewById(R.id.spinner);
        btnReset = findViewById(R.id.btnReset);
        memberDAO = new MemberDAO(this);
    }
}