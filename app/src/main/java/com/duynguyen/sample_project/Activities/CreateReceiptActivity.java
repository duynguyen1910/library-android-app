package com.duynguyen.sample_project.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.duynguyen.sample_project.Adapters.BookForSearchAdapter;
import com.duynguyen.sample_project.Adapters.MemberNameArrayAdapter;
import com.duynguyen.sample_project.Adapters.ReceiptDetailsAdapter;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CreateReceiptActivity extends AppCompatActivity {
    RecyclerView recyclerViewBook, recyclerViewReceipt;
    EditText edtFullname, edtStartDate, edtNote;
    ArrayList<Book> listBook;
    SearchView searchView;
    AutoCompleteTextView autotxtPhoneNumber;
    MemberNameArrayAdapter memberNameAdapter;
    BookForSearchAdapter bookForSearchAdapter;
    ReceiptDetailsAdapter receiptDetailsAdapter;
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
        String endDay = "";
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
            int memeberID = member.getMemberID();

            Receipt receipt = new Receipt(startDay, endDay, note, memeberID, 0);
            ReceiptDAO receiptDAO = new ReceiptDAO(CreateReceiptActivity.this);
            int check = receiptDAO.addReceipt(receipt);

            // Bước 2:  tạo các receipt detail liên kết với phiếu mượn vừa tạo
            // Nếu tạo phiếu mượn thành công thì kết quả của quá trình insert chính là receiptID
            if (check != -1) {
                receiptID = check;
                // Dùng vòng lặp for trong tempoReceiptDetailsList để khởi tạo receipt detail để insert vào database,
                ReceiptDetailDAO receiptDetailDAO = new ReceiptDetailDAO(CreateReceiptActivity.this);

                StringBuilder listTacGia = new StringBuilder();
                for (ReceiptDetail detail : tempoReceiptDetailsList) {
                    int bookID = detail.getBookID();
                    int quantity = detail.getQuantity();
                    // Insert vào database
                    long checkDetail = receiptDetailDAO.addReceiptDetail(receiptID, bookID, quantity);
                    // Insert detail thất bại
                    if (checkDetail == -1) {
                        isSuccess = false;
                        break;
                    }
                    // insert detail thành công
                }
                if (isSuccess) {
                    Toast.makeText(CreateReceiptActivity.this, "Tạo phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateReceiptActivity.this, "Tạo phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Trường hợp không tìm thấy thì tiến hành đăng ký thành viên
        else {
            isSuccess = false;
            Toast.makeText(CreateReceiptActivity.this, "Phai dang ky thanh vien truoc", Toast.LENGTH_SHORT).show();
        }


    }

    private void resetForm() {
        autotxtPhoneNumber.setText("");
        edtFullname.setText("");
        edtNote.setText("");
        searchView.setQuery("", true);
        tempoReceiptDetailsList.clear();
        receiptDetailsAdapter.notifyItemRangeRemoved(0, tempoReceiptDetailsList.size() - 1);
    }

    private void handleQueryInSearchView(String newText) {
        recyclerViewBook.setVisibility(View.VISIBLE);
        mListSuggest.clear();
        if (newText == null || newText.length() == 0) {
            bookForSearchAdapter.notifyDataSetChanged();
        } else {
            String filter = newText.toLowerCase().trim();
            for (Book book : listBook) {
                if (book.getBookName().toLowerCase().contains(filter)) {
                    mListSuggest.add(book);
                    bookForSearchAdapter.notifyDataSetChanged();
                }
            }
            if (mListSuggest.isEmpty()) {
                Toast.makeText(CreateReceiptActivity.this, "No book found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initUI() {
        memberNameAdapter = new MemberNameArrayAdapter(this, edtFullname, R.layout.item_member_name, getListMembers());
        autotxtPhoneNumber.setAdapter(memberNameAdapter);

        bookDAO = new BookDAO(this);
        listBook = bookDAO.getListProduct();
        receiptDetailsAdapter = new ReceiptDetailsAdapter(this, tempoReceiptDetailsList);
        bookForSearchAdapter = new BookForSearchAdapter(this, tempoReceiptDetailsList, mListSuggest, receiptDetailsAdapter, recyclerViewBook);


        recyclerViewBook.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBook.setAdapter(bookForSearchAdapter);

        recyclerViewReceipt.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReceipt.setAdapter(receiptDetailsAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                tempoReceiptDetailsList.remove(position);
                receiptDetailsAdapter.notifyItemRemoved(position);

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerViewReceipt, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(CreateReceiptActivity.this, R.color.primary_color))
                        .addActionIcon(R.drawable.ic_delete)
                        .setSwipeLeftLabelColor(ContextCompat.getColor(CreateReceiptActivity.this, R.color.white))
                        .addSwipeLeftLabel("Delete")
                        .setIconHorizontalMargin(TypedValue.COMPLEX_UNIT_DIP, 16)
                        .addCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 12)
                        .setSwipeLeftLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 14)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerViewReceipt);

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