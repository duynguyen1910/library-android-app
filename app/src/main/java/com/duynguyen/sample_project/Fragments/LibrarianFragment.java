package com.duynguyen.sample_project.Fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.duynguyen.sample_project.Activities.CreateLibrarianActivity;
import com.duynguyen.sample_project.Adapters.LibrarianAdapter;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class LibrarianFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerViewLibrarian;
    LibrarianAdapter librarianAdapter;
    ArrayList<Member> librariansList;
    ArrayList<Member> mListSuggest;
    FloatingActionButton fabCreateLibrarian;
    MemberDAO memberDAO;
    Member memberData;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_librarian, container, false);

        Mapping();
        fabCreateLibrarian.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CreateLibrarianActivity.class);
            startActivity(intent);
        });

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
        mListSuggest.clear();

        if (newText == null || newText.isEmpty()) {

            mListSuggest.addAll(librariansList);
        } else {

            for (Member member : librariansList) {
                if (newText.length() > 5 && member.getPhoneNumber().startsWith(newText)) {
                    mListSuggest.add(member);
                }
            }
        }


        if (mListSuggest.isEmpty()) {
            Toast.makeText(requireActivity(), "No member found", Toast.LENGTH_SHORT).show();
        } else {

            librarianAdapter = new LibrarianAdapter(requireActivity(), mListSuggest, memberData.getRole());
            recyclerViewLibrarian.setAdapter(librarianAdapter);
        }
    }

    private void setUpUIByRole(){
        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            memberData = (Member) intent.getSerializableExtra("memberData");
            if (memberData != null) {
                int role = memberData.getRole();
                if (role == 1){
                    fabCreateLibrarian.setVisibility(View.GONE);
                }
            }
        }
    }


    private void initUI() {
        setUpUIByRole();
        librariansList = memberDAO.getListMembersByRolesArray(1, 2);
        librarianAdapter = new LibrarianAdapter(requireActivity(), librariansList, memberData.getRole());
        recyclerViewLibrarian.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerViewLibrarian.setAdapter(librarianAdapter);
    }


    @Override
    public void onResume() {
        initUI();
        super.onResume();
    }

    private void Mapping() {
        fabCreateLibrarian = view.findViewById(R.id.fabCreateLibrarian);
        searchView = view.findViewById(R.id.searchView);
        recyclerViewLibrarian = view.findViewById(R.id.recyclerViewLibrarian);
        mListSuggest = new ArrayList<>();
        memberDAO = new MemberDAO(requireActivity());

    }
}
