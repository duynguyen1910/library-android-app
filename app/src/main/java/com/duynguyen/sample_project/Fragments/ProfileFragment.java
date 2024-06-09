package com.duynguyen.sample_project.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

public class ProfileFragment extends Fragment {
    View view;
    LinearLayout layoutLogout;
    TextView txtRole, txtFullname;
    ImageView imvAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        Mapping();

        initUI();
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        return view;
    }

    private void initUI() {

        Intent intent = requireActivity().getIntent();
        if (intent != null){
            Member member = (Member) intent.getSerializableExtra("memberData");
            if (member != null){
                txtFullname.setText(member.getFullname());
                int role = member.getRole();
                if (role == 2){
                    txtRole.setText("Admin");
                    txtRole.setBackgroundColor(Color.parseColor("#DC405C"));
                }else if (role == 1){
                    txtRole.setText("Librarian");
                    txtRole.setBackgroundColor(Color.parseColor("#6F57F6"));
                }
            }
        }



    }

    private void Mapping() {
        layoutLogout = view.findViewById(R.id.layoutLogout);
        txtRole = view.findViewById(R.id.txtRole);
        txtFullname = view.findViewById(R.id.txtFullname);
        imvAvatar= view.findViewById(R.id.imvAvatar);
    }
}