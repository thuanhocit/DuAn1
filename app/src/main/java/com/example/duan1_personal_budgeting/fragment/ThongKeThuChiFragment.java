package com.example.duan1_personal_budgeting.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.dao.GiaoDichDAO;
import com.example.duan1_personal_budgeting.dao.ThongKeDAO;

import java.util.Calendar;

public class ThongKeThuChiFragment extends Fragment {



    private EditText edtNgayBatDau, edtNgayKetThuc;
    private Button btnThongKe;
    private TextView tvKetQuaThuNhap, tvKetQuaChiTieu;
    private ThongKeDAO thongKeDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke_thu_chi, container, false);

        // Ánh xạ
        edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        tvKetQuaThuNhap = view.findViewById(R.id.tvKetQuaThuNhap);
        tvKetQuaChiTieu = view.findViewById(R.id.tvKetQuaChiTieu);
        thongKeDAO = new ThongKeDAO(getContext());

        // Hiển thị DatePicker khi chọn ngày bắt đầu
        edtNgayBatDau.setOnClickListener(v -> showDatePickerDialog(edtNgayBatDau));

        // Hiển thị DatePicker khi chọn ngày kết thúc
        edtNgayKetThuc.setOnClickListener(v -> showDatePickerDialog(edtNgayKetThuc));

        // Nút thống kê
        btnThongKe.setOnClickListener(v -> thongKe());

        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Định dạng lại ngày tháng
                    String ngayDaChon = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDay);
                    editText.setText(ngayDaChon);
                },
                year, month, day);

        datePickerDialog.show();
    }



    private void thongKe() {

        thongKeDAO = new ThongKeDAO(getContext());
        String ngayBatDau = edtNgayBatDau.getText().toString().trim();
        String ngayKetThuc = edtNgayKetThuc.getText().toString().trim();
        if (ngayBatDau == null){
            Log.i("//===========", "loi");
        } else {
            Log.i("//===========", "ngay bắt đầu: "+ngayBatDau);
        }

        if (ngayKetThuc == null){
            Log.i("//===========", "loi");
        } else {
            Log.i("//===========", "ngay bắt đầu: "+ngayKetThuc);
        }

        if (ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Thống kê thu nhập và chi tiêu trong khoảng thời gian
            int tongThuNhap = thongKeDAO.thongKeThuNhapTheoKhoangThoiGianTN(ngayBatDau, ngayKetThuc);
            if (tongThuNhap == 0){
                Log.i("//===========", "loi");
            } else {
                Log.i("//===========", "Tổng thu nhập: "+tongThuNhap);
            }
            int tongChiTieu = thongKeDAO.thongKeChiTieuTheoKhoangThoiGian(ngayBatDau, ngayKetThuc);

            if (tongChiTieu == 0){
                Log.i("//===========", "loi");
            } else {
                Log.i("//===========", "Tổng thu nhập: "+tongChiTieu);
            }
            // Hiển thị kết quả
            tvKetQuaThuNhap.setText("Tổng thu nhập từ " + ngayBatDau + "\n đến " + ngayKetThuc + ": " + tongThuNhap + " VND");
            tvKetQuaChiTieu.setText("Tổng chi tiêu từ " + ngayBatDau + "\n đến " + ngayKetThuc + ": " + tongChiTieu + " VND");
        } catch (Exception e) {
            Toast.makeText(getContext(), "Lỗi khi thống kê: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

