package com.example.duan1_personal_budgeting.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.adapter.ChiTieuAdapter;
import com.example.duan1_personal_budgeting.adapter.LichSuAdapter;
import com.example.duan1_personal_budgeting.dao.GiaoDichDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LichSuFragment extends Fragment {

    RecyclerView recyclerViewGD;
    private GiaoDichDAO giaoDichDAO;
    private List<Map<String, String>> danhSachGiaoDichGD;
    private SearchView searchView;
    private LichSuAdapter lichSuAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_su, container, false);
        recyclerViewGD = view.findViewById(R.id.rvLichSuGiaoDich);
        searchView = view.findViewById(R.id.searchView);

        loadData();

        // Tích hợp tìm kiếm với SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (lichSuAdapter != null) {
                    lichSuAdapter.filter(query); // Lọc danh sách
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (lichSuAdapter != null) {
                    lichSuAdapter.filter(newText); // Lọc danh sách khi nhập từ khóa
                }
                return false;
            }
        });

        return view;
    }

    public void loadData(){
        //data
        giaoDichDAO = new GiaoDichDAO(getContext());
        danhSachGiaoDichGD= giaoDichDAO.getAllGiaoDich();

        // Kiểm tra nếu danh sách null hoặc rỗng
        if (danhSachGiaoDichGD == null || danhSachGiaoDichGD.isEmpty()) {
            danhSachGiaoDichGD = new ArrayList<>(); // Tránh lỗi NullPointerException
            Log.i("//======", "loi");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewGD.setLayoutManager(layoutManager);
        lichSuAdapter = new LichSuAdapter(getContext(), danhSachGiaoDichGD, giaoDichDAO);
        recyclerViewGD.setAdapter(lichSuAdapter);
    }
}