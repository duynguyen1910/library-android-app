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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.DAOs.ReceiptDAO;
import com.duynguyen.sample_project.DAOs.ReceiptDetailDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MostBorrowedMemberAdapter extends  RecyclerView.Adapter<MostBorrowedMemberAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Member> memberList;
    private ReceiptDAO receiptDAO;


    public MostBorrowedMemberAdapter(Context context, ArrayList<Member> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_member_circle, parent, false);

        receiptDAO = new ReceiptDAO(context);

        // set total books borrowed
        for (Member member : memberList) {
            int totalBorrowed = receiptDAO.getReceiptTotalByMemberID(member.getMemberID());
            member.setTotalMemberBorrowed(totalBorrowed);
        }

        // sort by total books borrowed
        Collections.sort(memberList, new Comparator<Member>() {
            @Override
            public int compare(Member o1, Member o2) {
                return Integer.compare(o2.getTotalMemberBorrowed(), o1.getTotalMemberBorrowed());
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(memberList.get(position).getRole() == 0) {
            holder.memberAvtImv.setImageResource(memberList.get(position).getMemberImageURI());
            holder.memberNameTv.setText(memberList.get(position).getFullname());
        }
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView memberAvtImv;
        TextView memberNameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            memberAvtImv = itemView.findViewById(R.id.memberAvtImv);
            memberNameTv = itemView.findViewById(R.id.memberNameTv);

        }
    }
}
