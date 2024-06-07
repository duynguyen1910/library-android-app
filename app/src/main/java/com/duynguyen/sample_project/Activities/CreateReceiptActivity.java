package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.util.Locale;
import java.util.Objects;

public class CreateReceiptActivity extends AppCompatActivity {
    RecyclerView recyclerViewBook, recyclerViewReceipt;
    EditText edtFullname, edtStartDate, edtNote;
    ArrayList<Book> listBook;
    SearchView searchView;
    AutoCompleteTextView autotxtPhoneNumber;
    MemberNameArrayAdapter memberNameAdapter;
    BookForSearchAdapter bookForSearchAdapter;
    ReceiptAdapter receiptAdapter;
    BookDAO bookDAO;
    ArrayList<Book> mListSuggest;
    Button btnSubmit, btnReset;
    ImageButton btnBack;
    ArrayList<ReceiptDetail> tempoReceiptDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        Objects.requireNonNull(getSupportActionBar()).hide();
        Mapping();
        initUI();
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

        btnReset.setOnClickListener(v -> resetForm());

        getDateToday(edtStartDate);

        btnSubmit.setOnClickListener(v -> handleSubmitReceipt());

        btnBack.setOnClickListener(v -> finish());
    }

    private void handleSubmitReceipt() {
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
            Toast.makeText(CreateReceiptActivity.this, "Hãy chọn sách để mượn đi nào", Toast.LENGTH_SHORT).show();
            return;
        }
        int receiptID; // Khởi tạo ID của receipt
        MemberDAO memberDAO = new MemberDAO(CreateReceiptActivity.this);
        Member member = memberDAO.getMemberByPhoneNumber(phoneNumber);
        boolean isSuccess = true;

        //  tìm thấy member đã đăng ký trong database thì tiếp tục tạo phiếu mượn
        if (member != null) {

            // Bước 1:  insert phiếu mượn mới vào database
            Toast.makeText(CreateReceiptActivity.this, member.getFullname() + ", ID: " + member.getMemberID(), Toast.LENGTH_SHORT).show();
            int memeberID = member.getMemberID();

            Receipt receipt = new Receipt(startDay, endDay, note, memeberID);
            ReceiptDAO receiptDAO = new ReceiptDAO(CreateReceiptActivity.this);
            int check = receiptDAO.addReceipt(receipt);

            // Bước 2:  tạo các receipt detail liên kết với phiếu mượn vừa tạo
            // Nếu tạo phiếu mượn thành công thì kết quả của quá trình insert chính là receiptID
            if (check != -1) {
                receiptID = check;
                Toast.makeText(CreateReceiptActivity.this, "New receiptID = " + receiptID, Toast.LENGTH_SHORT).show();

                // Dùng vòng lặp for trong tempoReceiptDetailsList để khởi tạo receipt detail để insert vào database,
                ReceiptDetailDAO receiptDetailDAO = new ReceiptDetailDAO(CreateReceiptActivity.this);

                StringBuilder listTacGia = new StringBuilder();
                for (ReceiptDetail detail : tempoReceiptDetailsList) {
                    int bookID = detail.getBookID();
                    int quantity = detail.getQuantity();
                    int status = 1; // Chuyển trạng thái từ 0 (có sẵn) thành 1 (đang mượn)

                    // Insert vào database
                    long checkDetail = receiptDetailDAO.addReceiptDetail(receiptID, bookID, quantity, status);
                    // Insert detail thất bại
                    if (checkDetail == -1) {
                        isSuccess = false;
                        Toast.makeText(CreateReceiptActivity.this, "Tạo detail thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    // insert detail thành công
                    else {

                        listTacGia.append(bookDAO.getBookById(bookID).getAuthor()).append(", ");
                    }
                }
                Toast.makeText(CreateReceiptActivity.this, "listTacGia:  " + listTacGia, Toast.LENGTH_SHORT).show();


            }
            // Nếu có lỗi trong quá trình insert thì toast message
            else {
                Toast.makeText(CreateReceiptActivity.this, "Gặp lỗi trong quá trình insert", Toast.LENGTH_SHORT).show();
            }


        }
        // Trường hợp không tìm thấy thì tiến hành đăng ký thành viên
        else {
            isSuccess = false;
            Toast.makeText(CreateReceiptActivity.this, "Phai dang ky thanh vien truoc", Toast.LENGTH_SHORT).show();
        }
        if (isSuccess) {
            Toast.makeText(CreateReceiptActivity.this, "Tạo phiếu mượn thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CreateReceiptActivity.this, "Tạo phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }

    }

    private void resetForm() {
        autotxtPhoneNumber.setText("");
        edtFullname.setText("");
        edtNote.setText("");
        searchView.setQuery("", true);
        tempoReceiptDetailsList.clear();
        receiptAdapter.notifyDataSetChanged();
    }

    private void handleQueryInSearchView(String newText) {
        recyclerViewBook.setVisibility(View.VISIBLE);
        mListSuggest.clear();
        if (newText == null || newText.length() == 0) {
            mListSuggest.addAll(listBook);
        } else {
            String filter = newText.toLowerCase().trim();
            for (Book book : listBook) {
                if (book.getBookName().toLowerCase().contains(filter)) {
                    mListSuggest.add(book);
                }
            }
            if (mListSuggest.isEmpty()) {
                Toast.makeText(CreateReceiptActivity.this, "No book found", Toast.LENGTH_SHORT).show();
            } else {
                bookForSearchAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initUI() {
        memberNameAdapter = new MemberNameArrayAdapter(this, edtFullname, R.layout.item_member_name, getListMembers());
        autotxtPhoneNumber.setAdapter(memberNameAdapter);

        bookDAO = new BookDAO(this);
        listBook = bookDAO.getListProduct();
        receiptAdapter = new ReceiptAdapter(this, tempoReceiptDetailsList);
        bookForSearchAdapter = new BookForSearchAdapter(this, tempoReceiptDetailsList, mListSuggest, receiptAdapter, recyclerViewBook);


        recyclerViewBook.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBook.setAdapter(bookForSearchAdapter);

        recyclerViewReceipt.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReceipt.setAdapter(receiptAdapter);
    }

    private void getDateToday(EditText edt) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy", Locale.CHINESE);
        edt.setText(dinhDangNgay.format(calendar.getTime()));
    }


    private void Mapping() {
        recyclerViewBook = findViewById(R.id.recyclerViewBook);
        recyclerViewReceipt = findViewById(R.id.recyclerViewReceipt);
        searchView = findViewById(R.id.searchView);
        autotxtPhoneNumber = findViewById(R.id.autotxtPhoneNumber);
        edtFullname = findViewById(R.id.edtFullname);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtNote = findViewById(R.id.edtNote);
        mListSuggest = new ArrayList<>();
        tempoReceiptDetailsList = new ArrayList<>();
    }

    private List<Member> getListMembers() {
        MemberDAO memberDAO = new MemberDAO(CreateReceiptActivity.this);
        return memberDAO.getListMembers();
    }
}