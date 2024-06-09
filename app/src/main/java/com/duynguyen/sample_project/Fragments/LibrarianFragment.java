package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    ArrayList<Member> mListSuggest;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_librarian, container, false);

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
        return view;
    }

    private void handleQueryInSearchView(String newText) {
        mListSuggest.clear(); // Xóa danh sách gợi ý cũ trước khi thêm mới

        if (newText == null || newText.isEmpty()) {
            // Nếu không có văn bản hoặc văn bản trống, hiển thị tất cả các thư viện viên
            mListSuggest.addAll(librariansList);
        } else {
            // Nếu có văn bản tìm kiếm, thực hiện tìm kiếm và thêm vào danh sách gợi ý
            for (Member member : librariansList) {
                if (member.getPhoneNumber().startsWith(newText)) {
                    mListSuggest.add(member);
                }
            }
        }

        // Kiểm tra xem danh sách gợi ý có rỗng hay không
        if (mListSuggest.isEmpty()) {
            Toast.makeText(requireActivity(), "No member found", Toast.LENGTH_SHORT).show();
        } else {
            // Nếu có thành viên trong danh sách gợi ý, cập nhật adapter với danh sách này
            librarianAdapter = new LibrarianAdapter(requireActivity(), mListSuggest);
            recyclerViewLibrarian.setAdapter(librarianAdapter);
        }
    }


    private void initUI() {
        MemberDAO memberDAO = new MemberDAO(requireActivity());
        librariansList = memberDAO.getListMembersByRolesArray(1, 2);
        librarianAdapter = new LibrarianAdapter(requireActivity(), librariansList);
        recyclerViewLibrarian.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerViewLibrarian.setAdapter(librarianAdapter);


    }

    @Override
    public void onResume() {
        initUI();
        super.onResume();
    }

    private void Mapping() {

        searchView = view.findViewById(R.id.searchView);
        recyclerViewLibrarian = view.findViewById(R.id.recyclerViewLibrarian);
        mListSuggest = new ArrayList<>();

    }
}
