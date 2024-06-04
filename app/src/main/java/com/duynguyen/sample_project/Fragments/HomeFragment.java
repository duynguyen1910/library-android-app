package com.duynguyen.sample_project.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duynguyen.sample_project.Adapters.BookGridAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.R;

public class HomeFragment extends Fragment {
    private RecyclerView productRecyclerview, tagFilterRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        productRecyclerview = view.findViewById(R.id.productRecyclerview);
        tagFilterRecyclerView = view.findViewById(R.id.tagFilterRecyclerView);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tagFilterRecyclerView.setLayoutManager(layoutManager);


        BookDAO bookDAO = new BookDAO(getContext());
        BookGridAdapter bookGridAdapter = new BookGridAdapter(getContext(), bookDAO.getListProduct());
        productRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));

        productRecyclerview.setAdapter(bookGridAdapter);
        return view;
    }
}