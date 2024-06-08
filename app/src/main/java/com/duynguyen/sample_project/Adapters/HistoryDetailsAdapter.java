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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class HistoryDetailsAdapter extends RecyclerView.Adapter<HistoryDetailsAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ReceiptDetail> tempoList;

    public HistoryDetailsAdapter(Context context, ArrayList<ReceiptDetail> tempoList) {
        this.context = context;
        this.tempoList = tempoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.recycler_item_history_detail, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + tempoList.get(position).getBookImageURI();
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        holder.bookImage.setImageBitmap(bitmap);
        holder.bookImage.setImageBitmap(bitmap);
        holder.txtBookName.setText(tempoList.get(holder.getBindingAdapterPosition()).getBookName());
        holder.txtAuthor.setText(tempoList.get(holder.getBindingAdapterPosition()).getAuthor());
        holder.txtQuantity.setText(String.valueOf(tempoList.get(holder.getBindingAdapterPosition()).getQuantity()));
        holder.itemView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return tempoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView txtBookName, txtQuantity, txtAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.bookImage);
            txtBookName = itemView.findViewById(R.id.txtBookName);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }
}
