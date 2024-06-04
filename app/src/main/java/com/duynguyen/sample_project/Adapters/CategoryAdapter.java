package com.duynguyen.sample_project.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Fragments.BookFragment;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Category;
import com.duynguyen.sample_project.R;

import java.io.File;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> categoryList;
    private FragmentActivity fragmentActivity;
    private OnItemClickListener listener;

    public CategoryAdapter(Context context, ArrayList<Category> categoryList, OnItemClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_category, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryBackgroundCv.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(context, categoryList.get(position).getBackgroundColor())));
        holder.categoryNameTv.setText(categoryList.get(position).getCategoryName());
        holder.categoryImageImv.setImageResource(categoryList.get(position).getCategoryImage());

        holder.categoryImageCv.setRotation(holder.categoryImageCv.getRotation() + 25F);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(categoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView categoryBackgroundCv, categoryImageCv;
        TextView categoryNameTv;
        ImageView categoryImageImv;
        View itemViewCurent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryBackgroundCv = itemView.findViewById(R.id.categoryBackgroundCv);
            categoryNameTv = itemView.findViewById(R.id.categoryNameTv);
            categoryImageCv = itemView.findViewById(R.id.categoryImageCv);
            categoryImageImv = itemView.findViewById(R.id.categoryImageImv);
            itemViewCurent = itemView;

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }
}
