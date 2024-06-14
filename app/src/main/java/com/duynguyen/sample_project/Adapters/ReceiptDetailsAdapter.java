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

public class ReceiptDetailsAdapter extends RecyclerView.Adapter<ReceiptDetailsAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ReceiptDetail> tempoList;

    public ReceiptDetailsAdapter(Context context, ArrayList<ReceiptDetail> tempoList) {
        this.context = context;
        this.tempoList = tempoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.recycler_item_receipt_detail, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ReceiptDetail detail = tempoList.get(holder.getBindingAdapterPosition());
        String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + detail.getBookImageURI();
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        holder.bookImage.setImageBitmap(bitmap);
        holder.bookImage.setImageBitmap(bitmap);
        holder.txtBookName.setText(detail.getBookName());
        holder.txtAuthor.setText(detail.getAuthor());
        holder.txtQuantity.setText(String.valueOf(detail.getQuantity()));
        holder.txtInStock.setText(String.valueOf(detail.getInStock()));
        holder.itemView.setOnClickListener(v -> {


        });

        holder.btnPlus.setOnClickListener(v -> {
            int inStock = detail.getInStock();
            int quantity = detail.getQuantity();
            if (inStock > quantity){
                detail.setQuantity(quantity + 1);
                holder.txtQuantity.setText(String.valueOf(detail.getQuantity()));
            }

        });

        holder.btnMinus.setOnClickListener(v -> {
            int inStock = detail.getInStock();
            int quantity = detail.getQuantity();
            if (inStock >= quantity){
                if (quantity == 1){
                    tempoList.remove(detail);
                    notifyDataSetChanged();
                }else {
                    detail.setQuantity(quantity - 1);
                    holder.txtQuantity.setText(String.valueOf(detail.getQuantity()));
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return tempoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView txtBookName, txtQuantity, txtAuthor, txtInStock;
        ImageButton btnPlus, btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.bookImage);
            txtBookName = itemView.findViewById(R.id.txtBookName);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            txtInStock = itemView.findViewById(R.id.txtInStock);
        }
    }
}
