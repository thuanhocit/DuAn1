package com.example.duan1_personal_budgeting.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.adapter.ChiTieuAdapter;
import com.example.duan1_personal_budgeting.adapter.DanhMucAdapter;
import com.example.duan1_personal_budgeting.dao.ChiTieuDAO;
import com.example.duan1_personal_budgeting.dao.DanhMucDAO;
import com.example.duan1_personal_budgeting.dao.GiaoDichDAO;
import com.example.duan1_personal_budgeting.dao.TaiKhoanDAO;
import com.example.duan1_personal_budgeting.model.ChiTieu;
import com.example.duan1_personal_budgeting.model.DanhMuc;
import com.example.duan1_personal_budgeting.model.GiaoDich;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiTieuFragment extends Fragment {

    private GiaoDichDAO giaoDichDAO;

    RecyclerView recyclerViewCT;
    FloatingActionButton floatAddCT;
    EditText edtSoTien, edNgay, edMoTa;
    DanhMucDAO danhMucDAO;
    TaiKhoanDAO taiKhoanDAO;
    ChiTieuDAO chiTieuDAO;
    private ChiTieuAdapter chiTieuAdapter;


    private List<Map<String, String>> danhSachGiaoDich;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tieu, container, false);
        recyclerViewCT = view.findViewById(R.id.reChiTieu);

        loadData();

        floatAddCT = view.findViewById(R.id.floatAddCT);

        floatAddCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddCT();
            }
        });
        return view;
    }

    public void showDialogAddCT() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_chi_tieu, null, false);

        // Ánh xạ
        Spinner spnDM = view.findViewById(R.id.spnDM);
        Spinner spnTK = view.findViewById(R.id.spnTK);
        edtSoTien = view.findViewById(R.id.edtSoTien);
        edNgay = view.findViewById(R.id.edNgay);
        edMoTa = view.findViewById(R.id.edMoTa);

        // Lấy danh sách danh mục
        danhMucDAO = new DanhMucDAO(getContext());
        chiTieuDAO = new ChiTieuDAO(getContext());
        List<HashMap<String, Object>> danhMucList = danhMucDAO.getDanhMucList();

        // Hiển thị danh mục trong Spinner
        setupDanhMucSpinner(spnDM, danhMucList);

        // Lấy danh sách tài khoản
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        List<HashMap<String, Object>> taiKhoanList = taiKhoanDAO.getTaiKhoanList();

        // Hiển thị danh sách tài khoản trong Spinner
        setupTaiKhoanSpinner(spnTK, taiKhoanList);

        builder.setView(view);
        builder.setIcon(R.drawable.clipboard);
        builder.setTitle("Thêm chi tiêu");

        // DatePicker cho ngày tháng
        edNgay.setOnClickListener(view1 -> hienThiDatePickerDialog());

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            try {
                // Lấy dữ liệu từ EditText
                int tien = Integer.parseInt(edtSoTien.getText().toString().trim());
                String ngayThang = edNgay.getText().toString().trim();
                String moTa = edMoTa.getText().toString().trim();

                // Lấy thông tin danh mục
                int danhMucID = (int) danhMucList.get(spnDM.getSelectedItemPosition()).get("danhMucID");

                // Lấy thông tin tài khoản
                int taiKhoanID = (int) taiKhoanList.get(spnTK.getSelectedItemPosition()).get("taiKhoanID");

                // Xác định loại giao dịch từ danh mục
                String loai = (String) danhMucList.get(spnDM.getSelectedItemPosition()).get("loaiDanhMuc");

                // Thêm giao dịch vào cơ sở dữ liệu
                // Lấy số dư hiện tại của tài khoản
                int soDuHienTai = chiTieuDAO.getSoDuTaiKhoan(taiKhoanID);

                if (tien > soDuHienTai) {
                    Toast.makeText(getContext(), "Số tiền nhập lớn hơn số dư tài khoản!", Toast.LENGTH_SHORT).show();
                } else {
                    // Tính toán số dư mới
                    int soDuMoi = soDuHienTai - tien;

                    // Cập nhật số dư trong tài khoản
                    chiTieuDAO.updateSoDu(taiKhoanID, soDuMoi);
                }


                chiTieuDAO.addGDCT(tien, ngayThang, moTa, danhMucID, taiKhoanID, loai);

                // Hiển thị thông báo
                Toast.makeText(getContext(), "Thêm chi tiêu thành công!", Toast.LENGTH_SHORT).show();

                // Cập nhật dữ liệu RecyclerView
                loadData();
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Vui lòng nhập số tiền hợp lệ!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Lỗi khi thêm chi tiêu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ChiTieuFragment", "Error: ", e);
            }
        });

        builder.setNegativeButton("Thoát", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void hienThiDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Chỉnh sửa định dạng ngày tháng
                    String ngayDaChon = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDay);
                    edNgay.setText(ngayDaChon);
                },
                year, month, day);

        datePickerDialog.show();
    }


    // Hàm hiển thị danh mục trong Spinner
    private void setupDanhMucSpinner(Spinner spinnerDanhMuc, List<HashMap<String, Object>> danhMucList) {
        // Tạo danh sách chỉ chứa tên danh mục
        List<String> tenDanhMucList = new ArrayList<>();
        for (HashMap<String, Object> danhMuc : danhMucList) {
            tenDanhMucList.add((String) danhMuc.get("tenDanhMuc"));
        }

        // Gắn danh sách tên danh mục vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tenDanhMucList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDanhMuc.setAdapter(adapter);

    }


    // Hàm hiển thị tài khoản trong Spinner
    private void setupTaiKhoanSpinner(Spinner spinnerTaiKhoan, List<HashMap<String, Object>> taiKhoanList) {
        // Tạo danh sách chỉ chứa tên tài khoản
        List<String> tenTaiKhoanList = new ArrayList<>();
        for (HashMap<String, Object> taiKhoan : taiKhoanList) {
            tenTaiKhoanList.add((String) taiKhoan.get("tenTaiKhoan"));
        }

        // Gắn danh sách tên tài khoản vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tenTaiKhoanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaiKhoan.setAdapter(adapter);

    }



    public void loadData(){
        //data
        giaoDichDAO = new GiaoDichDAO(getContext());
        danhSachGiaoDich = giaoDichDAO.getChiTieu();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCT.setLayoutManager(layoutManager);
        chiTieuAdapter = new ChiTieuAdapter(getContext(), giaoDichDAO, danhSachGiaoDich, chiTieuDAO);
        recyclerViewCT.setAdapter(chiTieuAdapter);
    }
}