package com.duynguyen.sample_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;


import com.duynguyen.sample_project.DAOs.StatisticsDAO;
import com.duynguyen.sample_project.Models.Statistic;
import com.duynguyen.sample_project.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Objects;

public class StatisticsActivity extends AppCompatActivity {

    PieChart pieChart;
    StatisticsDAO statisticsDAO;
    ArrayList<Statistic> booksList;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        Objects.requireNonNull(getSupportActionBar()).hide();

        Mapping();

        initUI();

        btnBack.setOnClickListener(v -> finish());


    }

    private void initUI() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        booksList = statisticsDAO.getListBooksWithHighestQuantities(5);
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
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
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
        pieChart = findViewById(R.id.pieChart);
        btnBack = findViewById(R.id.btnBack);
        statisticsDAO = new StatisticsDAO(this);
    }
}