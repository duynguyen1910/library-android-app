package com.duynguyen.sample_project.Adapters;

import android.annotation.SuppressLint;
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
import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class BookForSearchAdapter extends RecyclerView.Adapter<BookForSearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> bookList;
    private ArrayList<ReceiptDetail> tempoList;
    private ReceiptDetailsAdapter receiptDetailsAdapter;

    RecyclerView recyclerViewBook;

    public BookForSearchAdapter(Context context, ArrayList<ReceiptDetail> tempoList, ArrayList<Book> bookList, ReceiptDetailsAdapter receiptDetailsAdapter, RecyclerView recyclerViewBook) {
        this.context = context;
        this.bookList = bookList;
        this.tempoList = tempoList;
        this.receiptDetailsAdapter = receiptDetailsAdapter;
        this.recyclerViewBook = recyclerViewBook;
    }

    @NonNull
    @Override
    public BookForSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_book_for_search, parent, false);

        return new BookForSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookForSearchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + bookList.get(position).getBookImageURI();
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        holder.bookImage.setImageBitmap(bitmap);
        holder.bookImage.setImageBitmap(bitmap);
        holder.bookName.setText(bookList.get(position).getBookName());
        holder.bookAuthor.setText(bookList.get(holder.getBindingAdapterPosition()).getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewBook.setVisibility(View.GONE);

                ReceiptDetail receiptDetail = new ReceiptDetail(
                        bookList.get(holder.getBindingAdapterPosition()).getBookID(),
                        bookList.get(holder.getBindingAdapterPosition()).getBookImageURI(),
                        bookList.get(holder.getBindingAdapterPosition()).getBookName(),
                        bookList.get(holder.getBindingAdapterPosition()).getAuthor(), 1
                );
                boolean existed = false;
                for (ReceiptDetail detail : tempoList) {
                    if (detail.getBookID() == receiptDetail.getBookID()) {
                        existed = true;
                        detail.setQuantity(detail.getQuantity() + 1);

                        break;
                    }
                }
                if (existed == false) {
                    tempoList.add(receiptDetail);
                }

                receiptDetailsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookName, bookAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookName = itemView.findViewById(R.id.bookName);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
        }
    }

}
