package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Adapters.BookForSearchAdapter;
import com.duynguyen.sample_project.Adapters.MemberNameArrayAdapter;
import com.duynguyen.sample_project.Adapters.ReceiptAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.DAOs.ReceiptDAO;
import com.duynguyen.sample_project.DAOs.ReceiptDetailDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.Models.Receipt;
import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReceiptFragment extends Fragment {

    RecyclerView recyclerViewBook, recyclerViewReceipt;
    EditText edtFullname, edtStartDate, edtNote;
    ArrayList<Book> listBook = new ArrayList<>();
    SearchView searchView;
    AutoCompleteTextView autotxtPhoneNumber;
    MemberNameArrayAdapter memberNameAdapter;
    View view;
    BookForSearchAdapter bookForSearchAdapter;
    ReceiptAdapter receiptAdapter;
    BookDAO bookDAO;
    ArrayList<Book> mListSuggest;
    Button btnSubmit, btnReset;
    ArrayList<ReceiptDetail> tempoReceiptDetailsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        Mapping();

        memberNameAdapter = new MemberNameArrayAdapter(requireActivity(), edtFullname, R.layout.item_member_name, getListMembers());
        autotxtPhoneNumber.setAdapter(memberNameAdapter);

        bookDAO = new BookDAO(requireActivity());
        listBook = bookDAO.getListProduct();
        receiptAdapter = new ReceiptAdapter(requireActivity(), tempoReceiptDetailsList);
        bookForSearchAdapter = new BookForSearchAdapter(requireActivity(), tempoReceiptDetailsList, mListSuggest, receiptAdapter, recyclerViewBook);


        recyclerViewBook.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewBook.setAdapter(bookForSearchAdapter);

        recyclerViewReceipt.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewReceipt.setAdapter(receiptAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                recyclerViewBook.setVisibility(View.GONE);
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {

                recyclerViewBook.setVisibility(View.VISIBLE);
                mListSuggest.clear();
                if (newText == null || newText.length() == 0) {
                    mListSuggest.addAll(mListSuggest);
                } else {
                    String filter = newText.toString().toLowerCase().trim();
                    for (Book book : listBook) {
                        if (book.getBookName().toLowerCase().contains(filter)) {
                            mListSuggest.add(book);
                        }
                    }
                    if (mListSuggest.isEmpty()) {
                        Toast.makeText(getActivity(), "No book found", Toast.LENGTH_SHORT).show();
                    } else {
                        bookForSearchAdapter.notifyDataSetChanged();
                    }
                }
                return true;
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                autotxtPhoneNumber.setText("");
                edtFullname.setText("");
                edtNote.setText("");
                searchView.setQuery("", true);
                tempoReceiptDetailsList.clear();
                receiptAdapter.notifyDataSetChanged();
            }
        });
        getDateToday(edtStartDate);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = autotxtPhoneNumber.getText().toString().trim();
                String startDay = edtStartDate.getText().toString();
                String endDay = "08/06/2024";
                String fullname = edtFullname.getText().toString().trim();
                String note = edtNote.getText().toString().trim();
                if (phoneNumber.isEmpty()) {
                    autotxtPhoneNumber.setError("Vui lòng nhập trường này");
                    return;
                }
                if (fullname.isEmpty()) {
                    edtFullname.setError("Vui lòng nhập trường này");
                    return;
                }


                if (tempoReceiptDetailsList.isEmpty()) {
                    Toast.makeText(getActivity(), "Hãy chọn sách để mượn đi nào", Toast.LENGTH_SHORT).show();
                    return;

                }
                int receiptID = -1; // Khởi tạo ID của receipt
                MemberDAO memberDAO = new MemberDAO(requireActivity());
                Member member = memberDAO.getMemberByPhoneNumber(phoneNumber);
                boolean isSuccess = true;

                //  tìm thấy member đã đăng ký trong database thì tiếp tục tạo phiếu mượn
                if (member != null) {

                    // Bước 1:  insert phiếu mượn mới vào database
                    Toast.makeText(getActivity(), member.getFullname() + ", ID: " + member.getMemberID(), Toast.LENGTH_SHORT).show();
                    int memeberID = member.getMemberID();

                    Receipt receipt = new Receipt(startDay, endDay, note, memeberID);
                    ReceiptDAO receiptDAO = new ReceiptDAO(requireActivity());
                    int check = receiptDAO.addReceipt(receipt);

                    // Bước 2:  tạo các receipt detail liên kết với phiếu mượn vừa tạo
                    // Nếu tạo phiếu mượn thành công thì kết quả của quá trình insert chính là receiptID
                    if (check != -1) {
                        receiptID = check;
                        Toast.makeText(requireActivity(), "New receiptID = " + receiptID, Toast.LENGTH_SHORT).show();

                        // Dùng vòng lặp for trong tempoReceiptDetailsList để khởi tạo receipt detail để insert vào database,
                        ReceiptDetailDAO receiptDetailDAO = new ReceiptDetailDAO(requireActivity());

                        String listTacGia = "";
                        for (ReceiptDetail detail : tempoReceiptDetailsList) {
                            int bookID = detail.getBookID();
                            int quantity = detail.getQuantity();
                            int status = 1; // Chuyển trạng thái từ 0 (có sẵn) thành 1 (đang mượn)
//                            ReceiptDetail newInsertDetail = new ReceiptDetail(receiptID, bookID, quantity, status);
                            // Insert vào database
                            long checkDetail = receiptDetailDAO.addReceiptDetail(receiptID, bookID, quantity, status);
                            // Insert detail thất bại
                            if (checkDetail == -1) {
                                isSuccess = false;
                                Toast.makeText(getActivity(), "Tạo detail thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            // insert detail thành công
                            else {

                                listTacGia += bookDAO.getBookById(bookID).getAuthor() + ", ";
                            }
                        }
                        Toast.makeText(getActivity(), "listTacGia:  " + listTacGia, Toast.LENGTH_SHORT).show();


                    }
                    // Nếu có lỗi trong quá trình insert thì toast message
                    else {
                        Toast.makeText(requireActivity(), "Gặp lỗi trong quá trình insert", Toast.LENGTH_SHORT).show();
                    }


                }
                // Trường hợp không tìm thấy thì tiến hành đăng ký thành viên
                else {
                    isSuccess = false;
                    Toast.makeText(getActivity(), "Phai dang ky thanh vien truoc", Toast.LENGTH_SHORT).show();
                }
                if (isSuccess) {
                    Toast.makeText(getActivity(), "Tạo phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Tạo phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    private void validateFormReceipt() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    private void getDateToday(EditText edt) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
        edt.setText(dinhDangNgay.format(calendar.getTime()));
    }

    private void showDatePickerDialog(EditText edt) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                edt.setText(selectedDate);
            }
        });
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    private void Mapping() {
        recyclerViewBook = view.findViewById(R.id.recyclerViewBook);
        recyclerViewReceipt = view.findViewById(R.id.recyclerViewReceipt);
        searchView = view.findViewById(R.id.searchView);
        autotxtPhoneNumber = view.findViewById(R.id.autotxtPhoneNumber);
        edtFullname = view.findViewById(R.id.edtFullname);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnReset = view.findViewById(R.id.btnReset);
        edtStartDate = view.findViewById(R.id.edtStartDate);
        edtNote = view.findViewById(R.id.edtNote);
        mListSuggest = new ArrayList<>();
        tempoReceiptDetailsList = new ArrayList<>();

    }

    private List<Member> getListMembers() {
        MemberDAO memberDAO = new MemberDAO(getActivity());
        ArrayList<Member> list = memberDAO.getListMembers();
        return list;
    }


}