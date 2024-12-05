package com.example.duan1_personal_budgeting.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.adapter.DanhMucAdapter;
import com.example.duan1_personal_budgeting.adapter.TaiKhoanAdapter;
import com.example.duan1_personal_budgeting.dao.DanhMucDAO;
import com.example.duan1_personal_budgeting.dao.TaiKhoanDAO;
import com.example.duan1_personal_budgeting.model.TaiKhoan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaiKhoanFragment extends Fragment {

    private TaiKhoanDAO taiKhoanDAO;
    RecyclerView recyclerViewTK;

    ArrayList<TaiKhoan> list;

    EditText edtTenTK, edtSoDU;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // giao diện
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        recyclerViewTK = view.findViewById(R.id.reTaiKhoan);
        FloatingActionButton floatAddTaiKhoan = view.findViewById(R.id.floatAddTaiKhoan);

        loadData();

        floatAddTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddTK();
                loadData();
            }
        });
        return view;
    }

    public void showDialogAddTK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        LayoutInflater inflater =getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_tai_khoan, null, false);

        // ánh xạ
        edtTenTK = view.findViewById(R.id.edtTenTK);
        edtSoDU = view.findViewById(R.id.edtSoDU);

        builder.setView(view);
        builder.setIcon(R.drawable.wallet);
        builder.setTitle("Thêm tài khoản");



        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenDM = edtTenTK.getText().toString();
                String soDUTKStr = edtSoDU.getText().toString();


                // check
                if (tenDM.isEmpty() || soDUTKStr.isEmpty()){
                    Toast.makeText(getContext(), "Yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int soDUTK = Integer.parseInt(edtSoDU.getText().toString());
                    boolean check = taiKhoanDAO.addTK(tenDM, soDUTK);
                    if (check) {
                        loadData();
                        Toast.makeText(getContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
//                        updateHomeFragmentData();
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
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        list = taiKhoanDAO.getTaiKhoan();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewTK.setLayoutManager(layoutManager);
        TaiKhoanAdapter taiKhoanAdapter = new TaiKhoanAdapter(getContext(), list, taiKhoanDAO);
        recyclerViewTK.setAdapter(taiKhoanAdapter);
    }

    private void addTaiKhoan() {
        String tenDM = edtTenTK.getText().toString();
        String soDUTKStr = edtSoDU.getText().toString();

        if (tenDM.isEmpty() || soDUTKStr.isEmpty()) {
            Toast.makeText(getContext(), "Yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            int soDUTK = Integer.parseInt(soDUTKStr);
            boolean check = taiKhoanDAO.addTK(tenDM, soDUTK);
            if (check) {
                loadData();
                Toast.makeText(getContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                updateHomeFragmentData(); // Gọi phương thức này sau khi thay đổi dữ liệu
            } else {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateHomeFragmentData() {
        HomeFragment homeFragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (homeFragment != null) {
            homeFragment.updateData();
        }
    }

}