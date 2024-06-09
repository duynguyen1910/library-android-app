package com.duynguyen.sample_project.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Adapters.CustomerAdapter;
import com.duynguyen.sample_project.Adapters.LibrarianAdapter;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class LibrarianFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerViewLibrarian;
    LibrarianAdapter librarianAdapter;
    ArrayList<Member> librariansList;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_librarian, container, false);

        Mapping();

        initUI();
        return view;
    }
    private void initUI(){
        MemberDAO memberDAO = new MemberDAO(requireActivity());
        librariansList = memberDAO.getListMembersByRolesArray(1, 2);
        librarianAdapter = new LibrarianAdapter(requireActivity(), librariansList);
        recyclerViewLibrarian.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerViewLibrarian.setAdapter(librarianAdapter);


    }
    @Override
    public void onResume() {
        super.onResume();
    }

    private void Mapping(){

        searchView = view.findViewById(R.id.searchView);
        recyclerViewLibrarian = view.findViewById(R.id.recyclerViewLibrarian);

    }
}
