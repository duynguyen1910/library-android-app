package com.duynguyen.sample_project.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Activities.HistoryDetailActivity;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.ReceiptDetail;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<History> historiesList;



    public HistoryAdapter(Context context, ArrayList<History> historiesList) {
        this.context = context;
        this.historiesList = historiesList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFullname, txtNote, txtStartDay, txtEndDay;
        TextView txtStatus, txtSumOfQuantity, txtPhoneNumber;
        TextView txtSeeMore, txtReturnReceipt, txtCreator;
        RecyclerView recyclerViewDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFullname = itemView.findViewById(R.id.txtFullname);
            txtNote = itemView.findViewById(R.id.txtNote);
            txtStartDay = itemView.findViewById(R.id.txtStartDay);
            txtEndDay = itemView.findViewById(R.id.txtEndDay);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtSeeMore = itemView.findViewById(R.id.txtSeeMore);
            txtReturnReceipt = itemView.findViewById(R.id.txtReturnReceipt);
            recyclerViewDetails = itemView.findViewById(R.id.recyclerViewDetails);
            txtSumOfQuantity = itemView.findViewById(R.id.txtSumOfQuantity);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            txtCreator = itemView.findViewById(R.id.txtCreator);

        }
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.recycler_item_history, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        History history = historiesList.get(holder.getBindingAdapterPosition());
        holder.txtFullname.setText(history.getFullname());
        holder.txtPhoneNumber.setText(history.getPhoneNumber());
        holder.txtStartDay.setText(history.getStartDay());
        holder.txtEndDay.setText(history.getEndDay());
        holder.txtNote.setText(history.getNote());
        holder.txtCreator.setText(history.getCreator());
        if (history.getStatus() == 0) {
            holder.txtStatus.setText("Chưa trả");
            holder.txtEndDay.setText("null");
            holder.txtEndDay.setTextColor(ContextCompat.getColor(context, R.color.text_error));
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.text_error));
        } else {
            holder.txtEndDay.setText(history.getEndDay());
            holder.txtStatus.setText("Đã trả");
            holder.txtEndDay.setTextColor(ContextCompat.getColor(context, R.color.text_success));
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.text_success));
        }

        int sum = 0;
        for (ReceiptDetail detail : history.getDetailsList()){
            sum += detail.getQuantity();
        }

        holder.txtSumOfQuantity.setText(sum + " quyển");
        HistoryDetailsAdapter historyDetailsAdapter = new HistoryDetailsAdapter(holder.recyclerViewDetails.getContext(), history.getDetailsList(), true);
        holder.recyclerViewDetails.setLayoutManager(new LinearLayoutManager(holder.recyclerViewDetails.getContext()));
        holder.recyclerViewDetails.setAdapter(historyDetailsAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HistoryDetailActivity.class);
                intent.putExtra("history", history);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return historiesList.size();
    }


}
