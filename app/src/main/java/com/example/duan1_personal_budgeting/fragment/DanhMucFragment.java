package com.example.duan1_personal_budgeting.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.adapter.DanhMucAdapter;
import com.example.duan1_personal_budgeting.dao.DanhMucDAO;
import com.example.duan1_personal_budgeting.model.DanhMuc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class DanhMucFragment extends Fragment {




    private DanhMucDAO danhMucDAO;

    ArrayList<DanhMuc> list;

    
    EditText edtTenDM;

    RecyclerView recyclerViewDM;
    RadioGroup rdGroup;
    RadioButton rdChiTieu, rdThuNhap;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // lên giao diện
        View view = inflater.inflate(R.layout.fragment_danh_muc, container, false);
        recyclerViewDM = view.findViewById(R.id.reDanhMuc);
        FloatingActionButton floatAddDanhMuc = view.findViewById(R.id.floatAddDanhMuc);

        //data
        // lên adapter
        loadData();



        floatAddDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddDP();
            }
        });

        return view;
    }

    @SuppressLint("MissingInflatedId")
    public void showDialogAddDP(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_danh_muc, null, false);

        // ánh xạ
        edtTenDM = view.findViewById(R.id.edtTenDM);
        rdGroup = view.findViewById(R.id.rdGroup);
        rdChiTieu = view.findViewById(R.id.rdChiTieu);
        rdThuNhap = view.findViewById(R.id.rdThuNhap);


        builder.setView(view);
        builder.setIcon(R.drawable.clipboard);
        builder.setTitle("Thêm danh mục");



        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenDM = edtTenDM.getText().toString();
                int checkLoaiDM = rdGroup.getCheckedRadioButtonId();
                String loaiDM = "";

                // check
                if (tenDM.isEmpty()){
                    Toast.makeText(getContext(), "Yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkLoaiDM == R.id.rdChiTieu){
                        loaiDM = "Chi tiêu";
                    } else if (checkLoaiDM == R.id.rdThuNhap) {
                        loaiDM = "Thu nhập";
                    }
                    boolean check = danhMucDAO.addDM(tenDM, loaiDM);
                    if (check) {
                        loadData();
                        Toast.makeText(getContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    public void loadData(){
        //data
        danhMucDAO = new DanhMucDAO(getContext());
        list = danhMucDAO.getDanhMuc();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewDM.setLayoutManager(layoutManager);
        DanhMucAdapter danhMucAdapter = new DanhMucAdapter(getContext(), list, danhMucDAO);
        recyclerViewDM.setAdapter(danhMucAdapter);
    }
}