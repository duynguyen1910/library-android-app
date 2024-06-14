package com.duynguyen.sample_project.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.Locale;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CreateReceiptActivity extends AppCompatActivity {
    RecyclerView recyclerViewBook, recyclerViewReceipt;
    EditText edtStartDate, edtNote;
    TextView txtFullname;
    ArrayList<Book> listBook;
    SearchView searchView;
    AutoCompleteTextView autotxtPhoneNumber;
    MemberNameArrayAdapter memberNameAdapter;
    BookForSearchAdapter bookForSearchAdapter;
    ReceiptDetailsAdapter receiptDetailsAdapter;
    BookDAO bookDAO;
    MemberDAO memberDAO;
    ArrayList<Member> listCustomer;
    ArrayList<Book> mListSuggest;
    SharedPreferences sharedPreferences;
    Button btnSubmit, btnReset;
    ImageButton btnBack;
    ArrayList<ReceiptDetail> tempoReceiptDetailsList;
    ReceiptDAO receiptDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        Objects.requireNonNull(getSupportActionBar()).hide();
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

        btnReset.setOnClickListener(v -> resetForm());

        getDateToday(edtStartDate);

        btnSubmit.setOnClickListener(v -> handleSubmitReceipt());

        btnBack.setOnClickListener(v -> finish());

        autotxtPhoneNumber.setOnItemClickListener((parent, view, position, id) -> {
            Member selectedMember = (Member) parent.getItemAtPosition(position);
            txtFullname.setText(selectedMember.getFullname());

        });

        autotxtPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validatePhoneNumber();


            }

        });

        autotxtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 9 || s.length() > 11) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> txtFullname.setText(""), 300);
                }


            }
        });


    }

    private boolean validatePhoneNumber() {
        String phoneNumber = autotxtPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            Toast.makeText(CreateReceiptActivity.this, "Hãy điền số điện thoại", Toast.LENGTH_SHORT).show();
            autotxtPhoneNumber.setError("Vui lòng nhập trường này");
            return false;
        }
        if (phoneNumber.length() < 9) {
            autotxtPhoneNumber.setError("Số điện thoại phải nhiều hơn 8 ký tự");
            return false;
        }
        Member member = memberDAO.getMemberByPhoneNumber(phoneNumber);
        if (member == null) {
            txtFullname.setText("");
            showConfirmationRegisterDialog();
            return false;
        } else {
            txtFullname.setText(member.getFullname());

            if (!receiptDAO.checkBorrowAbility(member)) {
                Toast.makeText(CreateReceiptActivity.this, "Thành viên này có phiếu mượn chưa trả. Không thể tạo phiếu!", Toast.LENGTH_SHORT).show();
                autotxtPhoneNumber.setError("Thành viên này có phiếu mượn chưa trả. Không thể tạo phiếu!");
                return false;
            }
        }


        return true;
    }

    private void showConfirmationRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateReceiptActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Số điện thoại không tồn tại trong database, ấn đăng ký để tiếp tục?");
        builder.setTitle("Warning!");
        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            Intent intent = new Intent(CreateReceiptActivity.this, CreateCustomerActivity.class);
            intent.putExtra("newPhoneNumber", autotxtPhoneNumber.getText().toString().trim());
            startActivity(intent);
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void handleSubmitReceipt() {
        String phoneNumber = autotxtPhoneNumber.getText().toString().trim();
        String startDay = edtStartDate.getText().toString();
        String endDay = "";
        String note = edtNote.getText().toString().trim();
        if (!validatePhoneNumber()) {
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

            sharedPreferences = getSharedPreferences("dataRememberLogin", MODE_PRIVATE);
            String currentMemberFullname = sharedPreferences.getString("currentMemberFullname", null);
//            public Receipt(int receiptID, String creator, String startDay, String endDay, String note, int memberID, int status)
            Receipt receipt = new Receipt(currentMemberFullname, startDay, endDay, note, memeberID, 0);

            int check = receiptDAO.addReceipt(receipt);


            if (check != -1) {
                receiptID = check;
                ReceiptDetailDAO receiptDetailDAO = new ReceiptDetailDAO(CreateReceiptActivity.this);


                for (ReceiptDetail detail : tempoReceiptDetailsList) {
                    int bookID = detail.getBookID();
                    int quantity = detail.getQuantity();
                    int inStock = bookDAO.getInStock(bookID);

                    if (inStock >= quantity) {
                        long checkDetail = receiptDetailDAO.addReceiptDetail(receiptID, bookID, quantity);

                        if (checkDetail == -1) {
                            isSuccess = false;
                            break;
                        } else {
                            bookDAO.updateInStock(bookID, inStock - quantity);
                        }
                    }
                }

            } else {
                isSuccess = false;
            }
        } else {
            isSuccess = false;
            Toast.makeText(CreateReceiptActivity.this, "Phai dang ky thanh vien truoc", Toast.LENGTH_SHORT).show();
        }


        if (isSuccess) {
            Toast.makeText(CreateReceiptActivity.this, "Tạo phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(CreateReceiptActivity.this, "Tạo phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }


    }

    private void resetForm() {
        autotxtPhoneNumber.setText("");
        txtFullname.setText("");
        edtNote.setText("");
        searchView.setQuery("", true);
        tempoReceiptDetailsList.clear();
        receiptDetailsAdapter.notifyItemRangeRemoved(0, tempoReceiptDetailsList.size() - 1);
    }

    private void handleQueryInSearchView(String newText) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            recyclerViewBook.setVisibility(View.VISIBLE);
            mListSuggest.clear();

            String filter = newText.toLowerCase().trim();
            for (Book book : listBook) {
                if (book.getBookName().toLowerCase().startsWith(filter)) {
                    mListSuggest.add(book);
                }
            }
            bookForSearchAdapter.notifyDataSetChanged();

        }, 300);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();

        String phone = autotxtPhoneNumber.getText().toString().trim();
        Member member = memberDAO.getMemberByPhoneNumber(phone);
        if (member != null) {
            txtFullname.setText(member.getFullname());
        }
    }

    private void initUI() {
        memberNameAdapter = new MemberNameArrayAdapter(this, R.layout.item_member_name, listCustomer);
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
        txtFullname = findViewById(R.id.txtFullname);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtNote = findViewById(R.id.edtNote);
        mListSuggest = new ArrayList<>();
        tempoReceiptDetailsList = new ArrayList<>();
        memberDAO = new MemberDAO(CreateReceiptActivity.this);
        receiptDAO = new ReceiptDAO(CreateReceiptActivity.this);
        listCustomer = getListCustomers();
    }

    private ArrayList<Member> getListCustomers() {
        return memberDAO.getListCustomers();
    }
}