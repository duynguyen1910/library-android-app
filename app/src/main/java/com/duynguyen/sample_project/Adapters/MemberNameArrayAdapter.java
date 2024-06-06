package com.duynguyen.sample_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;
import java.util.List;

public class MemberNameArrayAdapter extends ArrayAdapter {
    private List<Member> mListMembers;
    private EditText edtFullname;


    public MemberNameArrayAdapter(@NonNull Context context, EditText edtFullname, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mListMembers = new ArrayList<>(objects);
        this.edtFullname = edtFullname;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_name, parent, false);
        }
        TextView txtBookName = convertView.findViewById(R.id.txtFullname);
        TextView txtPhonenumber = convertView.findViewById(R.id.txtPhonenumber);

        Member member = (Member) getItem(position);
        txtPhonenumber.setText(member.getPhoneNumber());
        txtBookName.setText(member.getFullname());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Member> mListSuggest = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    mListSuggest.addAll(mListMembers);
                } else {
                    for (Member member : mListMembers) {
                        if (member.getPhoneNumber().contains(constraint)) {
                            mListSuggest.add(member);
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
                addAll((List<Member>) results.values);
                notifyDataSetChanged();

            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {

                edtFullname.setText(((Member) resultValue).getFullname());
                return ((Member) resultValue).getPhoneNumber();
            }


        };

    }
}
