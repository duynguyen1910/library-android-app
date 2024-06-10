package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.duynguyen.sample_project.Adapters.CustomerAdapter;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Objects;

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

        fabCreateCustomer.setOnClickListener(v -> handleCreateCustomer());


        return view;
    }

    private void handleQueryInSearchView(String newText) {
        mListSuggest.clear(); // Xóa danh sách gợi ý cũ trước khi thêm mới

        if (newText == null || newText.isEmpty()) {
            // Nếu không có văn bản hoặc văn bản trống, hiển thị tất cả các thư viện viên
            mListSuggest.addAll(customersList);
        } else {
            // Nếu có văn bản tìm kiếm, thực hiện tìm kiếm và thêm vào danh sách gợi ý
            for (Member member : customersList) {
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
            customerAdapter = new CustomerAdapter(requireActivity(), mListSuggest);
            recyclerViewCustomer.setAdapter(customerAdapter);
        }
    }

    private void handleCreateCustomer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_customer, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnClose = alertDialog.findViewById(R.id.btnClose);
        Button btnSubmit = alertDialog.findViewById(R.id.btnSubmit);
        EditText edtPhoneNumber = alertDialog.findViewById(R.id.edtPhoneNumber);
        EditText edtAddress = alertDialog.findViewById(R.id.edtAddress);
        EditText edtFullname = alertDialog.findViewById(R.id.edtFullname);


        assert btnSubmit != null;
        btnSubmit.setOnClickListener(v -> {
            assert edtFullname != null;
            String fullname = edtFullname.getText().toString().trim();
            assert edtPhoneNumber != null;
            String phoneNumber = edtPhoneNumber.getText().toString().trim();
            assert edtAddress != null;
            String address = edtAddress.getText().toString().trim();

            if (fullname.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(requireActivity(), "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();


            } else {
                Member newMember = new Member(fullname, phoneNumber, address, "", 0);
                int check = memberDAO.register(newMember);
                if (check == 0) {
                    Toast.makeText(requireActivity(), "Số điện thoại tồn tại, không thể đăng ký", Toast.LENGTH_SHORT).show();
                } else if (check == -1) {
                    Toast.makeText(requireActivity(), "Lỗi đăng ký, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    initUI();

                }
            }
        });

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> alertDialog.dismiss());
        }
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
