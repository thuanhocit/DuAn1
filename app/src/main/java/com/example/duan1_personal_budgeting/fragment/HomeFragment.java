package com.example.duan1_personal_budgeting.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.database.DBHelper;

import java.text.NumberFormat;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView tvTongSoDu, tvTongTN, tvTongCT;
    private Button btnQLDM, btnQLTK, btnQLGD;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ TextView
        tvTongSoDu = view.findViewById(R.id.tvTongSoDu);
        tvTongTN = view.findViewById(R.id.tvTongTN);
        tvTongCT = view.findViewById(R.id.tvTongCT);

        // Ánh xạ Button
        btnQLDM = view.findViewById(R.id.btnQLDM);
        btnQLTK = view.findViewById(R.id.btnQLTK);
        btnQLGD = view.findViewById(R.id.btnQLGD);

        // Cài đặt sự kiện cho các nút
        setButtonListeners();

        // Khởi tạo DBHelper và cập nhật dữ liệu
        dbHelper = new DBHelper(getContext());
        updateData();

        return view;
    }

    private void setButtonListeners() {
        btnQLDM.setOnClickListener(v -> navigateToFragment(new DanhMucFragment()));
        btnQLTK.setOnClickListener(v -> navigateToFragment(new TaiKhoanFragment()));
        btnQLGD.setOnClickListener(v -> navigateToFragment(new GiaoDichFragment()));
    }

    private void navigateToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment) // `fragment_container` là ID của FrameLayout chứa các Fragment
                .addToBackStack(null) // Đưa Fragment hiện tại vào BackStack để quay lại
                .commit();
    }

    public void updateData() {
        int totalBalance = dbHelper.getTotalBalance();
        int totalIncome = dbHelper.getTotalIncome();
        int totalExpense = dbHelper.getTotalExpense();

        // Định dạng số tiền
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        tvTongSoDu.setText(numberFormat.format(totalBalance));
        tvTongTN.setText(numberFormat.format(totalIncome));
        tvTongCT.setText(numberFormat.format(totalExpense));
    }

    // Phương thức mới để cập nhật số dư tài khoản cụ thể
    public void updateAccountBalance(int accountId) {
        int balance = dbHelper.getAccountBalance(accountId);
        // Giả sử bạn muốn cập nhật TextView hiển thị số dư tài khoản cụ thể
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTongSoDu.setText(numberFormat.format(balance));
    }
}
