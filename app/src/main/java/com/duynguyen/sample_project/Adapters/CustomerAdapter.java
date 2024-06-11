package com.duynguyen.sample_project.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Member> list;


    public CustomerAdapter(Context context, ArrayList<Member> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_customer, parent, false);
        return new ViewHolder(view);
    }

    private void handleCreateCustomer(Member member) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_update_customer, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnClose = alertDialog.findViewById(R.id.btnClose);
        Button btnSubmit = alertDialog.findViewById(R.id.btnSubmit);
        EditText edtAddress = alertDialog.findViewById(R.id.edtAddress);
        EditText edtFullname = alertDialog.findViewById(R.id.edtFullname);
        EditText edtPhoneNumber = alertDialog.findViewById(R.id.edtPhoneNumber);


        edtFullname.setText(member.getFullname());
        edtPhoneNumber.setText(member.getPhoneNumber());
        edtAddress.setText(member.getAddress());


        assert btnSubmit != null;
        btnSubmit.setOnClickListener(v -> {
            assert edtFullname != null;
            String fullname = edtFullname.getText().toString().trim();
            assert edtAddress != null;
            String address = edtAddress.getText().toString().trim();

            if (fullname.isEmpty()) {
                Toast.makeText(context, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                MemberDAO memberDAO = new MemberDAO(context);
                member.setFullname(fullname);
                member.setAddress(address);
                if (!memberDAO.updateMember(member)) {
                    Toast.makeText(context, "Lỗi update, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> alertDialog.dismiss());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Member member = list.get(holder.getBindingAdapterPosition());

        holder.imvAvatar.setImageResource(member.getMemberImageURI());
        holder.txtFullname.setText(member.getFullname());
        holder.txtPhoneNumber.setText(member.getPhoneNumber());
        holder.btnEdit.setOnClickListener(v -> handleCreateCustomer(member));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFullname, txtPhoneNumber;
        ImageView imvAvatar, btnEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvAvatar = itemView.findViewById(R.id.imvAvatar);
            txtFullname = itemView.findViewById(R.id.txtFullname);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
