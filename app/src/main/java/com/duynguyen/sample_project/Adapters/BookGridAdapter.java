package com.duynguyen.sample_project.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Activities.BookDetailActivity;
import com.duynguyen.sample_project.DAOs.ReceiptDetailDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BookGridAdapter extends RecyclerView.Adapter<BookGridAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> bookList;

    private ReceiptDetailDAO receiptDetailDAO;

    public BookGridAdapter(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_grid, parent, false);

        receiptDetailDAO = new ReceiptDetailDAO(context);

        // set total books borrowed
        for (Book book : bookList) {
            int totalBorrowed = receiptDetailDAO.getTotalBooksBorrowed(book.getBookID());
            book.setTotalBooksBorrowed(totalBorrowed);
        }

        // sort by total books borrowed
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return Integer.compare(o2.getTotalBooksBorrowed(), o1.getTotalBooksBorrowed());
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + bookList.get(position).getBookImageURI();
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);

        holder.bookImageImv.setImageBitmap(bitmap);
        holder.bookNameTv.setText(bookList.get(holder.getAdapterPosition()).getBookName());
        holder.authorBookTv.setText(bookList.get(holder.getAdapterPosition()).getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("bookID", bookList.get(holder.getAdapterPosition()).getBookID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImageImv;
        TextView bookNameTv, authorBookTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImageImv = itemView.findViewById(R.id.bookImageImv);
            bookNameTv = itemView.findViewById(R.id.bookNameTv);
            authorBookTv = itemView.findViewById(R.id.authorBookTv);


        }
    }


//    private String getCategoryNameById(int categoryId) {
//
//        for (Category item : categoryDAO.getListCategory()) {
//            if (item.getCategoryId() == categoryId) {
//                return item.getCategoryName();
//            }
//        }
//
//        return "";
//    }
}
