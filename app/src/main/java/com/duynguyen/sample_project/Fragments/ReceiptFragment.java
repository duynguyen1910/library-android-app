package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.duynguyen.sample_project.Adapters.BookForSearchAdapter;
import com.duynguyen.sample_project.Adapters.MemberNameArrayAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;
import java.util.ArrayList;
import java.util.List;


public class ReceiptFragment extends Fragment {

    RecyclerView recyclerView;
    EditText edtFullname;
    ArrayList<Book> listBook = new ArrayList<>();
    SearchView searchView;
    AutoCompleteTextView autotxt;
    MemberNameArrayAdapter memberNameAdapter;
    View view;
    BookForSearchAdapter adapter;
    BookDAO bookDAO;
    ArrayList<Book> mListSuggest = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        Mapping();

        memberNameAdapter = new MemberNameArrayAdapter(getActivity(), edtFullname, R.layout.item_member_name, getListMembers());
        autotxt.setAdapter(memberNameAdapter);

        bookDAO = new BookDAO(requireActivity());
        listBook = bookDAO.getAllBooks();
        adapter = new BookForSearchAdapter(requireActivity(), mListSuggest);



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
        autotxt = view.findViewById(R.id.autotxt);
        edtFullname = view.findViewById(R.id.edtFullname);
        mListSuggest = new ArrayList<>();

    }
    private List<Member> getListMembers() {
        List<Member> list = new ArrayList<>();
        list.add(new Member("Ngọc Đại", "0868441273", "quận Bình Thạnh, HCM", "12345678", 2));
        list.add(new Member("Tấn Duy", "123456789", "quận 7, HCM", "12345678", 1));
        list.add(new Member("Tấn Bảo", "987654321", "Gò Vấp, HCM", "12345678", 0));
        list.add(new Member("Chí Thành", "555666777", "quận Bình Thạnh, HCM", "12345678", 0));
        return list;
    }

//    @Override
//    public void fillFullnameToEditText(String fullname) {
//        edtFullname.setText(fullname);
//    }
}