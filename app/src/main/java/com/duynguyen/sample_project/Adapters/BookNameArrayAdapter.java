package com.duynguyen.sample_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;
import java.util.List;

public class BookNameArrayAdapter extends ArrayAdapter {
    private List<Book> mListBook;

    public BookNameArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mListBook = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookname, parent, false);
        }
        TextView txtBookName = convertView.findViewById(R.id.txtBookName);
        Book book = (Book) getItem(position);
        txtBookName.setText(book.getBookName());
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Book> mListSuggest = new ArrayList<>();
                if (constraint == null || constraint.length() == 0){
                    mListSuggest.addAll(mListBook);
                }else {
                    String filter = constraint.toString().toLowerCase().trim();
                    for (Book book : mListBook){
                        if (book.getBookName().toLowerCase().contains(filter)){
                            mListSuggest.add(book);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListSuggest;
                filterResults.count = mListSuggest.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List<Book>)results.values);
                notifyDataSetChanged();

            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Book) resultValue).getBookName();
            }
        };

    }
}
