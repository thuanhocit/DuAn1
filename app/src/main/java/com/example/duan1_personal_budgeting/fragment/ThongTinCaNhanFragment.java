package com.example.duan1_personal_budgeting.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.duan1_personal_budgeting.R;

public class ThongTinCaNhanFragment extends Fragment {
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin_ca_nhan, container, false);

        btnSave = view.findViewById(R.id.btnSave);
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng này hiện tại đang update nên không sử dụng được", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}