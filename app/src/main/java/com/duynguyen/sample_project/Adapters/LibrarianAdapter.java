package com.duynguyen.sample_project.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Activities.UpdateLibrarianActivity;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class LibrarianAdapter extends RecyclerView.Adapter<LibrarianAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Member> list;
    int role;


    public LibrarianAdapter(Context context, ArrayList<Member> list, int role) {
        this.context = context;
        this.list = list;
        this.role = role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_librarian, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Member member = list.get(holder.getBindingAdapterPosition());
        holder.txtFullname.setText(member.getFullname());
        holder.txtPhoneNumber.setText(member.getPhoneNumber());
        holder.txtAddress.setText(member.getAddress());
        if (role == 1){
            holder.btnEdit.setVisibility(View.GONE);
        }

        if (member.getRole() == 2) {
            holder.txtRole.setText("Admin");
            holder.txtRole.setBackgroundResource(R.drawable.role_bg_2);

        } else if (member.getRole() == 1) {

            holder.txtRole.setText("Librarian");
            holder.txtRole.setBackgroundResource(R.drawable.role_bg_1);
        }

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateLibrarianActivity.class);
            intent.putExtra("memberData", member);
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFullname, txtPhoneNumber, txtAddress, txtRole;
        ImageView btnEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFullname = itemView.findViewById(R.id.txtFullname);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtRole = itemView.findViewById(R.id.txtRole);
        }
    }
}
