package com.duynguyen.sample_project.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.duynguyen.sample_project.Adapters.BookGridAdapter;
import com.duynguyen.sample_project.Adapters.MostBorrowedMemberAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.DAOs.MemberDAO;
import com.duynguyen.sample_project.DAOs.StatisticsDAO;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.Models.Statistic;
import com.duynguyen.sample_project.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    TextView txtFullname, txtRole;
    PieChart pieChart;
    private RecyclerView mostBorrowedRecyclerView, mostReadsRecyclerview;
    ArrayList<Statistic> booksList;
    StatisticsDAO statisticsDAO;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Mapping();
        initUI();
        setUpPieChart();


        return view;
    }

    private void initUI() {
        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            Member member = (Member) intent.getSerializableExtra("memberData");
            if (member != null) {
                txtFullname.setText("Hello, " + member.getFullname());

                int role = member.getRole();
                if (role == 2){
                    txtRole.setText("Admin");
                    txtRole.setBackgroundResource(R.drawable.role_bg_2);
                }else if (role == 1){
                    txtRole.setText("Librarian");
                    txtRole.setBackgroundResource(R.drawable.role_bg_1);
                }
            }
        }

        LinearLayoutManager memberLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager bookGridLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        MemberDAO memberDAO = new MemberDAO(getContext());
        MostBorrowedMemberAdapter mostBorrowedMemberAdapter = new MostBorrowedMemberAdapter(getContext(), memberDAO.getListMembers());
        mostBorrowedRecyclerView.setLayoutManager(memberLayoutManager);
        mostBorrowedRecyclerView.setAdapter(mostBorrowedMemberAdapter);

        BookDAO bookDAO = new BookDAO(getContext());
        BookGridAdapter bookGridAdapter = new BookGridAdapter(getContext(), bookDAO.getListProduct());
        mostReadsRecyclerview.setLayoutManager(bookGridLayoutManager);
        mostReadsRecyclerview.setAdapter(bookGridAdapter);

    }

    private void setUpPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        booksList = statisticsDAO.getListBooksWithHighestQuantities(4);
        int bookListLength = booksList.size();
        for (int i = 0; i < bookListLength; i++) {
            entries.add(new PieEntry(booksList.get(i).getSumQuantity(), booksList.get(i).getBookName()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Top sách được mượn nhiều nhất");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        dataSet.setValueTextSize(14f); // Adjust the size of the value labels
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        dataSet.setValueTextColor(Color.parseColor("#ffffff"));

        pieChart.getDescription().setEnabled(false);
        pieChart.setData(data);
        pieChart.getLegend().setTextSize(14f);
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setEntryLabelTextSize(15f);
        pieChart.setEntryLabelColor(Color.BLACK);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setXEntrySpace(0f);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setYEntrySpace(6f);
        legend.setXEntrySpace(8f);


        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelColor(Color.parseColor("#333333"));
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();
    }
    private void Mapping() {
        txtFullname = view.findViewById(R.id.txtFullname);
        txtRole = view.findViewById(R.id.txtRole);
        mostBorrowedRecyclerView  = view.findViewById(R.id.mostBorrowedRecyclerView);
        mostReadsRecyclerview = view.findViewById(R.id.mostReadsRecyclerview);
        pieChart = view.findViewById(R.id.pieChart);
        statisticsDAO = new StatisticsDAO(requireActivity());

    }
}