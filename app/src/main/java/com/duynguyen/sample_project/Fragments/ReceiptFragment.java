package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Adapters.BookForSearchAdapter;
import com.duynguyen.sample_project.Adapters.BookNameArrayAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ReceiptFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Book> listBook = new ArrayList<>();
    SearchView searchView;
    View view;
    BookForSearchAdapter adapter;
    BookDAO bookDAO;
    ArrayList<Book> mListSuggest = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        Mapping();

        bookDAO = new BookDAO(requireActivity());
        listBook = bookDAO.getAllBooks();
        adapter = new BookForSearchAdapter((Context) getActivity(), mListSuggest);



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {

                mListSuggest.clear();
                if (newText == null || newText.length() == 0){
                    mListSuggest.addAll(mListSuggest);
                }else {
                    String filter = newText.toString().toLowerCase().trim();
                    for (Book book : listBook){
                        if (book.getBookName().toLowerCase().contains(filter)){
                            mListSuggest.add(book);
                        }
                    }
                    if (mListSuggest.isEmpty()){
                        Toast.makeText(getActivity(), "No book found", Toast.LENGTH_SHORT).show();
                    }else {
                        adapter.notifyDataSetChanged();
                    }
                }
                return true;
            }
        });


        return view;
    }

    private void Mapping() {
        recyclerView =view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);
        mListSuggest = new ArrayList<>();

    }
}