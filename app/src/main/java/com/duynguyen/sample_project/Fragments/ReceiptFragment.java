package com.duynguyen.sample_project.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Activities.CreateReceiptActivity;
import com.duynguyen.sample_project.Adapters.HistoryAdapter;
import com.duynguyen.sample_project.Adapters.MemberNameArrayAdapter;
import com.duynguyen.sample_project.DAOs.HistoryDAO;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


public class ReceiptFragment extends Fragment {
    View view;
    RecyclerView recyclerViewHistory;
    FloatingActionButton fabCreateReceipt;
    HistoryDAO historyDAO;
    HistoryAdapter historyAdapter;
    MemberNameArrayAdapter memberNameAdapter;
    AutoCompleteTextView autotxtPhoneNumber;
    EditText edtFullname;
    TextView btnSearch;
    ArrayList<History> mListSuggest;
    ArrayList<History> historiesList;
    ArrayList<History> borrowingList;
    CheckBox chkOption;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        Mapping();
        fabCreateReceipt.setOnClickListener(v -> startActivity(new Intent(requireActivity(), CreateReceiptActivity.class)));

        btnSearch.setOnClickListener(v -> handleQuerySearch());

        chkOption.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                borrowingList = historyDAO.getAllBorrowingHistories();
                historyAdapter = new HistoryAdapter(requireActivity(), borrowingList);
                recyclerViewHistory.setAdapter(historyAdapter);
            }else {
                historiesList = historyDAO.getAllHistories();
                historyAdapter = new HistoryAdapter(requireActivity(), historiesList);
                recyclerViewHistory.setAdapter(historyAdapter);
            }
        });


        return view;
    }


    private void initUI() {
        chkOption.setChecked(false);
        memberNameAdapter = new MemberNameArrayAdapter(requireActivity(), R.layout.item_member_name, getListCustomer());
        autotxtPhoneNumber.setAdapter(memberNameAdapter);
        historyDAO = new HistoryDAO(requireActivity());
        historiesList = historyDAO.getAllHistories();
        historyAdapter = new HistoryAdapter(requireActivity(), historiesList);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerViewHistory.setAdapter(historyAdapter);
    }


    private void handleQuerySearch() {
        String phoneNumber = autotxtPhoneNumber.getText().toString().trim();
        mListSuggest.clear();



        if (phoneNumber.length() == 0) {
            Toast.makeText(requireActivity(), "Hãy nhập số điện thoại để tìm phiếu mượn nhé", Toast.LENGTH_SHORT).show();
        } else {
            if (chkOption.isChecked()){
                for (History history : borrowingList) {
                    if (history.getPhoneNumber().equals(phoneNumber)) {
                        mListSuggest.add(history);

                    }
                }
            }else {
                for (History history : historiesList) {
                    if (history.getPhoneNumber().equals(phoneNumber)) {
                        mListSuggest.add(history);
                    }
                }
            }

            if (mListSuggest.isEmpty()) {
                Toast.makeText(requireActivity(), "No receipt found", Toast.LENGTH_SHORT).show();
            } else {
                historyAdapter = new HistoryAdapter(requireActivity(), mListSuggest);
                recyclerViewHistory.setAdapter(historyAdapter);
            }
        }

    }

    @Override
    public void onResume() {
        initUI();
        super.onResume();
    }

    private void Mapping() {
        fabCreateReceipt = view.findViewById(R.id.fabCreateReceipt);
        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
        edtFullname = view.findViewById(R.id.edtFullname);
        autotxtPhoneNumber = view.findViewById(R.id.autotxtPhoneNumber);
        btnSearch = view.findViewById(R.id.btnSearch);
        chkOption = view.findViewById(R.id.chkOption);
        mListSuggest = new ArrayList<>();
        borrowingList = new ArrayList<>();

    }

    private List<Member> getListCustomer() {
        MemberDAO memberDAO = new MemberDAO(requireActivity());
        return memberDAO.getListCustomers();
    }


}