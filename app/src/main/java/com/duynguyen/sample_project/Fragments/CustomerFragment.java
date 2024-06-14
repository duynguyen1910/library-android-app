package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Activities.CreateCustomerActivity;
import com.duynguyen.sample_project.Adapters.CustomerAdapter;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {


    SearchView searchView;
    RecyclerView recyclerViewCustomer;
    CustomerAdapter customerAdapter;
    ArrayList<Member> customersList;
    ArrayList<Member> mListSuggest;
    FloatingActionButton fabCreateCustomer;
    View view;
    MemberDAO memberDAO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer, container, false);

        Mapping();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {
                handleQueryInSearchView(newText);
                return true;
            }
        });

        fabCreateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), CreateCustomerActivity.class));
            }
        });


        return view;
    }

    private void handleQueryInSearchView(String newText) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (newText.isEmpty()){
                mListSuggest.clear();
                mListSuggest.addAll(customersList);
                return;
            }
            if (newText.length()  >  7 && newText.length() < 12) {
                mListSuggest.clear();
                for (Member member : customersList) {
                    if (member.getPhoneNumber().startsWith(newText)) {
                        mListSuggest.add(member);
                    }
                }
            }
            if (!mListSuggest.isEmpty()) {
                customerAdapter = new CustomerAdapter(requireActivity(), mListSuggest);
                recyclerViewCustomer.setAdapter(customerAdapter);
            }
        }, 300);

    }

    private void initUI() {
        customersList = memberDAO.getListMembersByRole(0);
        customerAdapter = new CustomerAdapter(requireActivity(), customersList);
        recyclerViewCustomer.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerViewCustomer.setAdapter(customerAdapter);
    }

    @Override
    public void onResume() {
        initUI();
        super.onResume();
    }

    private void Mapping() {
        searchView = view.findViewById(R.id.searchView);
        recyclerViewCustomer = view.findViewById(R.id.recyclerViewCustomer);
        fabCreateCustomer = view.findViewById(R.id.fabCreateCustomer);
        memberDAO = new MemberDAO(requireActivity());
        mListSuggest = new ArrayList<>();


    }
}
