package com.duynguyen.sample_project.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Activities.BookDetailActivity;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.R;

import java.io.File;
import java.util.ArrayList;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> bookList;

    public BookListAdapter(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ratingBookTv.setText(String.valueOf(position + 1));
        String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + bookList.get(position).getBookImageURI();
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        holder.bookImageListImv.setImageBitmap(bitmap);
        holder.bookImageListImv.setImageBitmap(bitmap);
        holder.bookNameListTv.setText(bookList.get(position).getBookName());

        holder.itemViewCurent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("bookID", bookList.get(position).getBookID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImageListImv;
        TextView ratingBookTv, bookNameListTv, quantityBookListTv;
        View itemViewCurent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImageListImv = itemView.findViewById(R.id.bookImageListImv);
            ratingBookTv = itemView.findViewById(R.id.ratingBookTv);
            bookNameListTv = itemView.findViewById(R.id.bookNameListTv);
            quantityBookListTv = itemView.findViewById(R.id.quantityBookListTv);
            itemViewCurent = itemView;
        }
    }
}
