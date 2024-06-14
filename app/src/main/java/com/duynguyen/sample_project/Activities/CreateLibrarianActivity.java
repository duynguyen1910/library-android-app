package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

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

public class CreateLibrarianActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_librarian);
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
            assert edtPhoneNumber != null;
            String phoneNumber = edtPhoneNumber.getText().toString().trim();
            assert edtAddress != null;
            String address = edtAddress.getText().toString().trim();
            assert edtPassword != null;
            String password = edtPassword.getText().toString().trim();
            if (fullname.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || password.isEmpty()) {
                Toast.makeText(CreateLibrarianActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();

            } else if (password.length() < 6) {
                edtPassword.setError("Mật khẩu ít nhất 6 ký tự");
            } else {
                Member newMember = new Member(fullname, phoneNumber, address, password, role);
                int check = memberDAO.register(newMember);
                if (check == 0) {
                    Toast.makeText(CreateLibrarianActivity.this, "Số điện thoại tồn tại, không thể đăng ký", Toast.LENGTH_SHORT).show();
                } else if (check == -1) {
                    Toast.makeText(CreateLibrarianActivity.this, "Lỗi đăng ký, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateLibrarianActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        btnReset.setOnClickListener(v -> {
            edtFullname.setText("");
            edtAddress.setText("");
            edtPassword.setText("");
            edtPhoneNumber.setText("");
        });


        btnBack.setOnClickListener(v -> finish());
    }

    private void initUI() {

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
        memberDAO = new MemberDAO(CreateLibrarianActivity.this);
    }



}