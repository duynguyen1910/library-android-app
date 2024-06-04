package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.R;

public class BookDetailActivity extends AppCompatActivity {
    private BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );


        ImageView backgroundImageView = findViewById(R.id.backgroundImageView);
        TextView bookTitleTv = findViewById(R.id.bookTitleTv);
        TextView bookAuthorTv = findViewById(R.id.bookAuthorTv);
        ImageView backBookListImv = findViewById(R.id.backBookListImv);

        bookDAO = new BookDAO(this);

        Intent intent = getIntent();
        int bookID = intent.getIntExtra("bookID", -1);
        Book productDetail = bookDAO.getBookById(bookID);


        String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + productDetail.getBookImageURI();
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);

        backgroundImageView.setImageBitmap(bitmap);
        bookTitleTv.setText(productDetail.getBookName());
        bookAuthorTv.setText(productDetail.getAuthor());


        backBookListImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}