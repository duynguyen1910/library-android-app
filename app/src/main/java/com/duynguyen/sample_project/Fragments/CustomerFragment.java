package com.duynguyen.sample_project.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Adapters.CustomerAdapter;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {


    SearchView searchView;
    RecyclerView recyclerViewCustomer;
    CustomerAdapter customerAdapter;
    ArrayList<Member> customersList;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer, container, false);

        Mapping();
        initUI();


        return view;
    }

    private void initUI(){
        MemberDAO memberDAO = new MemberDAO(requireActivity());
        customersList = memberDAO.getListMembersByRole(0);
        customerAdapter = new CustomerAdapter(requireActivity(), customersList);
        recyclerViewCustomer.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerViewCustomer.setAdapter(customerAdapter);


    }
    @Override
    public void onResume() {
        super.onResume();
    }

    private void Mapping(){

        searchView = view.findViewById(R.id.searchView);
        recyclerViewCustomer = view.findViewById(R.id.recyclerViewCustomer);

    }
}
