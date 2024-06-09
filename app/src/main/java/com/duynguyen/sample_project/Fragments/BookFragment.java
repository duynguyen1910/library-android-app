package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynguyen.sample_project.Adapters.BookListAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.DAOs.CategoryDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Category;
import com.duynguyen.sample_project.R;

import java.util.ArrayList;

public class BookFragment extends Fragment {
    private static final String CATEGORY_ID = "categoryID";
    private static final String CATEGORY_NAME = "categoryName";
    private static final String CATEGORY_IMAGE = "categoryImage";


    private int categoryID;
    private String categoryName;
    private int categoryImage;
    private RecyclerView bookListRecyclerView;
    private ArrayList<Book> listBook = new ArrayList<>();

//    @Override
//    public void onResume() {
//        super.onResume();
//        Activity activity = getActivity();
//        if (activity != null) {
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            activity.getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            );
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.background_blur));

        ImageView categoryImgImv = view.findViewById(R.id.categoryImgImv);
        TextView textCategoryTv = view.findViewById(R.id.textCategoryTv);
        bookListRecyclerView = view.findViewById(R.id.bookListRecyclerView);

        categoryImgImv.setImageResource(categoryImage);
        textCategoryTv.setText(categoryName);

        BookDAO bookDAO = new BookDAO(getContext());
        listBook = bookDAO.getBookByCategoryId(categoryID);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        bookListRecyclerView.setLayoutManager(linearLayoutManager);

        BookListAdapter bookListAdapter = new BookListAdapter(getContext(), listBook);
        bookListRecyclerView.setAdapter(bookListAdapter);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView backImv = view.findViewById(R.id.backImv);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getInt(CATEGORY_ID);
            categoryName = getArguments().getString(CATEGORY_NAME);
            categoryImage = getArguments().getInt(CATEGORY_IMAGE);

        }
    }



    @Override
    public void onPause() {
        super.onPause();
        resetSystemUiVisibility();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetSystemUiVisibility();
    }

    public static BookFragment newInstance(Category category) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, category.getCategoryID());
        args.putString(CATEGORY_NAME, category.getCategoryName());
        args.putInt(CATEGORY_IMAGE, category.getCategoryImage());
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnBackButtonClickListener {
        void onBackButtonClicked();
    }

    private void resetSystemUiVisibility() {
        Activity activity = getActivity();
        if (activity != null) {
//            activity.getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            );

            activity.getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
            activity.getWindow().setNavigationBarColor(Color.WHITE);
        }
    }
}
