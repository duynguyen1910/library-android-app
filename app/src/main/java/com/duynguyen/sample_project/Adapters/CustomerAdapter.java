package com.duynguyen.sample_project.Adapters;

import android.annotation.SuppressLint;
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
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Member> list;


    public CustomerAdapter(Context context, ArrayList<Member> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_customer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Member member = list.get(holder.getBindingAdapterPosition());
        holder.txtFullname.setText(member.getFullname());
        holder.txtPhoneNumber.setText(member.getPhoneNumber());
        holder.txtAddress.setText(member.getAddress());
        holder.btnEdit.setOnClickListener(v -> {

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFullname, txtAddress, txtPhoneNumber;
        ImageView btnEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFullname = itemView.findViewById(R.id.txtFullname);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            txtAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
