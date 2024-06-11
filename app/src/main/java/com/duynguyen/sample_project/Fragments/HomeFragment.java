package com.duynguyen.sample_project.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.duynguyen.sample_project.Adapters.BookGridAdapter;
import com.duynguyen.sample_project.Adapters.MostBorrowedMemberAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

public class HomeFragment extends Fragment {
    TextView txtFullname, txtRole;
    private RecyclerView mostBorrowedRecyclerView, mostReadsRecyclerview;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Mapping();
        initUI();


        return view;
    }

    private void initUI() {
        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            Member member = (Member) intent.getSerializableExtra("memberData");
            if (member != null) {
                txtFullname.setText("Hello, " + member.getFullname());

                int role = member.getRole();
                if (role == 2){
                    txtRole.setText("Admin");
                    txtRole.setBackgroundResource(R.drawable.role_bg_2);
                }else if (role == 1){
                    txtRole.setText("Librarian");
                    txtRole.setBackgroundResource(R.drawable.role_bg_1);
                }
            }
        }

        LinearLayoutManager memberLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager bookGridLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        MemberDAO memberDAO = new MemberDAO(getContext());
        MostBorrowedMemberAdapter mostBorrowedMemberAdapter = new MostBorrowedMemberAdapter(getContext(), memberDAO.getListMembers());
        mostBorrowedRecyclerView.setLayoutManager(memberLayoutManager);
        mostBorrowedRecyclerView.setAdapter(mostBorrowedMemberAdapter);

        BookDAO bookDAO = new BookDAO(getContext());
        BookGridAdapter bookGridAdapter = new BookGridAdapter(getContext(), bookDAO.getListProduct());
        mostReadsRecyclerview.setLayoutManager(bookGridLayoutManager);
        mostReadsRecyclerview.setAdapter(bookGridAdapter);

    }

    private void Mapping() {
        txtFullname = view.findViewById(R.id.txtFullname);
        txtRole = view.findViewById(R.id.txtRole);
        mostBorrowedRecyclerView  = view.findViewById(R.id.mostBorrowedRecyclerView);
        mostReadsRecyclerview = view.findViewById(R.id.mostReadsRecyclerview);

    }
}