package com.duynguyen.sample_project.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    View view;
    LinearLayout layoutLogout, layoutChangePassword;
    TextView txtRole, txtFullname;
    ImageView imvAvatar;
    Member memberData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        Mapping();

        initUI();
        layoutLogout.setOnClickListener(v -> requireActivity().finish());

        layoutChangePassword.setOnClickListener(v -> handleChangePasswordRequest());
        return view;
    }

    private void initUI() {

        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            memberData = (Member) intent.getSerializableExtra("memberData");
            if (memberData != null) {
                txtFullname.setText(memberData.getFullname());
                int role = memberData.getRole();
                if (role == 2) {
                    txtRole.setText("Admin");
                    txtRole.setBackgroundColor(Color.parseColor("#DC405C"));
                } else if (role == 1) {
                    txtRole.setText("Librarian");
                    txtRole.setBackgroundColor(Color.parseColor("#6F57F6"));
                }
            }
        }


    }


    private void handleChangePasswordRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnClose = alertDialog.findViewById(R.id.btnClose);
        Button btnSubmit = alertDialog.findViewById(R.id.btnSubmit);
        EditText edtCurrentPassword = alertDialog.findViewById(R.id.edtCurrentPassword);
        EditText edtNewPassword = alertDialog.findViewById(R.id.edtNewPassword);
        EditText edtValidateNewPassword = alertDialog.findViewById(R.id.edtValidateNewPassword);


        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> {
                String currentPassword = edtCurrentPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String validateNewPassword = edtValidateNewPassword.getText().toString().trim();

                // Validate Form
                if (currentPassword.isEmpty()) {
                    edtCurrentPassword.setError("Vui lòng nhập trường này");
                    return;

                }
                if (newPassword.isEmpty()) {
                    edtNewPassword.setError("Vui lòng nhập trường này");
                    return;

                }
                if (validateNewPassword.isEmpty()) {
                    edtValidateNewPassword.setError("Vui lòng nhập trường này");
                    return;

                }

                // Nếu mật khẩu hiện tại không chính xác
                if (!currentPassword.equals(memberData.getPassword())) {
                    Toast.makeText(requireActivity(), "Mật khẩu hiện tại không chính xác", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPassword.length() < 6) {
                    edtNewPassword.setError("Mật khẩu mới ít nhất 6 ký tự");
                    return;
                }

                // Nhập đúng mật khẩu hiện tại
                // Nếu confirm mật khẩu mới sai
                if (!newPassword.equals(validateNewPassword)) {
                    edtValidateNewPassword.setError("Giá trị nhập vào không chính xác ");

                }
                // Nếu confirm mật khẩu mới đúng
                else {
                    //
                    MemberDAO memberDAO = new MemberDAO(requireActivity());
                    if (memberDAO.updateMemberPassword(newPassword, memberData.getMemberID())) {
                        Toast.makeText(requireActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(requireActivity(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> alertDialog.dismiss());
        }
    }


    private void Mapping() {
        layoutLogout = view.findViewById(R.id.layoutLogout);
        layoutChangePassword = view.findViewById(R.id.layoutChangePassword);
        txtRole = view.findViewById(R.id.txtRole);
        txtFullname = view.findViewById(R.id.txtFullname);
        imvAvatar = view.findViewById(R.id.imvAvatar);
    }
}