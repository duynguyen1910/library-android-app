package com.duynguyen.sample_project.Adapters;

import android.app.Activity;
import android.content.Context;
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

import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class BookGridAdapter extends RecyclerView.Adapter<BookGridAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> bookList;

    public BookGridAdapter(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_grid, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + bookList.get(position).getBookImageURI();
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);

        holder.bookImageImv.setImageBitmap(bitmap);
        holder.bookNameTv.setText(bookList.get(position).getBookName());
        holder.authorBookTv.setText(bookList.get(position).getAuthor());
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
