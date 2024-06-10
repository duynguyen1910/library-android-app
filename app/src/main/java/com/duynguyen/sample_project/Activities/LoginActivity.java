package com.duynguyen.sample_project.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button btnSignin;
    EditText edtPhoneNumber, edtPassword;
    CheckBox chkRemember;
    SharedPreferences sharedPreferences;
    MemberDAO memberDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        Objects.requireNonNull(getSupportActionBar()).hide();
        Mapping();
        btnSignin.setOnClickListener(v -> handleLoginData());
    }

    private boolean handleValidateLoginForm(String phoneNumber, String password){
        if (phoneNumber.isEmpty()){
            edtPhoneNumber.setError("Vui lòng nhập trường này");
            return false;
        }
        if (password.isEmpty()){
            edtPassword.setError("Vui lòng nhập trường này");
            return false;
        }
        if (password.length() < 6){
            Toast.makeText(this, "Mật khẩu ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void handleLoginData(){
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (handleValidateLoginForm(phoneNumber, password)){
            Member existedMember = memberDAO.getMemberByPhoneNumber(phoneNumber);
            // Case 1: Số điện thoại đã được đăng ký thủ thư
            if (existedMember != null){

                // Nếu là khách hàng thuê sách thì không cho login, role = 0
                if (existedMember.getRole() == 0){
                    Toast.makeText(this, "Hãy trở thành thủ thư của thư viện để đăng nhập nhé", Toast.LENGTH_SHORT).show();
                }
                // Nếu là thủ thư thì xét SDT và password
                else {

                    // Nếu đúng mật khẩu
                    if (existedMember.getPassword().equals(password)){

                        SharedPreferences.Editor editorRemember = sharedPreferences.edit();
                        if(chkRemember.isChecked()){
                            editorRemember.putBoolean("checked", true);
                            editorRemember.putString("phoneNumber", phoneNumber);
                            editorRemember.putString("password", password);
                        }else {
                            editorRemember.putBoolean("checked", false);
                            editorRemember.remove("phoneNumber");
                            editorRemember.remove("password");
                        }
                        editorRemember.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("memberData", existedMember);
                        startActivity(intent);
                    }
                    // Nếu sai mật khẩu
                    else {
                        Toast.makeText(this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            // Case 2: Số điện thoại chưa được đăng ký thủ thư
            else {
                Toast.makeText(this, "Số điện thoại chưa được đăng ký", Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void getDataRememberLogin() {
        sharedPreferences = getSharedPreferences("dataRememberLogin", MODE_PRIVATE);
        boolean checkedStatus = sharedPreferences.getBoolean("checked", false);
        chkRemember.setChecked(checkedStatus);  // Set the checkbox status
        if (checkedStatus){
            String key_phoneNumber = sharedPreferences.getString("phoneNumber", "");
            String key_password = sharedPreferences.getString("password", "");
            edtPhoneNumber.setText(key_phoneNumber);
            edtPassword.setText(key_password);
        }else {
            edtPhoneNumber.setText("");
            edtPassword.setText("");
        }
    }

    @Override
    protected void onResume() {
        getDataRememberLogin();
        super.onResume();
    }

    private void Mapping(){
        btnSignin = findViewById(R.id.btnSignin);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        chkRemember = findViewById(R.id.chkRemember);
        memberDAO = new MemberDAO(LoginActivity.this);
    }
}