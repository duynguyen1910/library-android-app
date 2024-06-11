package com.duynguyen.sample_project.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.duynguyen.sample_project.Adapters.CategoryAdapter;
import com.duynguyen.sample_project.DAOs.BookDAO;
import com.duynguyen.sample_project.DAOs.CategoryDAO;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Category;
import com.duynguyen.sample_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CategoryFragment extends Fragment implements CategoryAdapter.OnItemClickListener {
    private RecyclerView categoryRecyclerView;
    private FloatingActionButton addProductFab;
    private ImageView imageAddImv;
    private ArrayList<Category> categoryList;
    private BookDAO bookDAO;
    private CategoryDAO categoryDAO;
    private String imageFileName;
    View view;

    SharedPreferences sharedPreferences;
    private int categoryType = -1;

    public interface OnCategorySelectedListener {
        void onCategorySelected(Category category);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        Mapping();
        initUI();

        addProductFab.setOnClickListener(v -> onShowAdditionDialog());
        return view;
    }

    private void initUI() {
        setUpUIByRole();

        categoryList = categoryDAO.getListCategory();
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList, this);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoryRecyclerView.setAdapter(categoryAdapter);
    }


    private void onShowAdditionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_addition_book, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imageAddImv = view.findViewById(R.id.imageAddImv);
        EditText nameAddEdt = view.findViewById(R.id.nameAddEdt);
        EditText quantityAddEdt = view.findViewById(R.id.quantityAddEdt);
        EditText authorAddEdt = view.findViewById(R.id.authorAddEdt);
        Spinner categoryTypeSpn = view.findViewById(R.id.categoryTypeSpn);
        EditText descAddEdt = view.findViewById(R.id.descAddEdt);
        Button closeDialogBtn = view.findViewById(R.id.closeDialogBtn);
        Button addDialogBtn = view.findViewById(R.id.addDialogBtn);

        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        List<Category> clonedList = new ArrayList<>(categoryList);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, clonedList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryTypeSpn.setAdapter(adapter);

        categoryTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageAddImv.setOnClickListener(v -> {

            Intent photoPicker = new Intent();
            photoPicker.setAction(Intent.ACTION_GET_CONTENT);
            photoPicker.setType("image/*");
            activityAddResultLauncher.launch(photoPicker);
        });

        addDialogBtn.setOnClickListener(v -> {
            String name = nameAddEdt.getText().toString();
            String quantity = quantityAddEdt.getText().toString();
            String author = authorAddEdt.getText().toString();
            String desc = descAddEdt.getText().toString();

            if (name.length() < 1 || quantity.length() < 1 || categoryType == -1) {
                Toast.makeText(getContext(), "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Book newBook = new Book(name, imageFileName, desc, author, Integer.parseInt(quantity), categoryType + 1);
                boolean addProductStatus = bookDAO.addBook(newBook);

                if (addProductStatus) {
                    Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        closeDialogBtn.setOnClickListener(v -> alertDialog.dismiss());
    }


    @Override
    public void onItemClick(Category category) {
        if (getActivity() instanceof OnCategorySelectedListener) {
            ((OnCategorySelectedListener) getActivity()).onCategorySelected(category);
        }
    }

    ActivityResultLauncher<Intent> activityAddResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data != null){
                            imageFileName = getFileName(Objects.requireNonNull(data.getData()));
                            imageAddImv.setImageURI(data.getData());
                        }

                    } else {
                        Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (Objects.requireNonNull(uri.getScheme()).equals("content")) {
            try (Cursor cursor = Objects.requireNonNull(requireActivity()).getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = 0;
            if (result != null) {
                cut = result.lastIndexOf('/');
            }
            if (cut != -1) {
                if (result != null) {
                    result = result.substring(cut + 1);
                }
            }
        }
        return result;
    }

    private void Mapping() {
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        addProductFab = view.findViewById(R.id.addProductFab);
        bookDAO = new BookDAO(getContext());
        categoryDAO = new CategoryDAO(getContext());
    }

    private void setUpUIByRole() {
        sharedPreferences = requireActivity().getSharedPreferences("dataRememberLogin", MODE_PRIVATE);
        int currentMemberRole = sharedPreferences.getInt("currentMemberRole", -1);
        if (currentMemberRole == 1) {
            addProductFab.setVisibility(View.GONE);
        }
    }
}