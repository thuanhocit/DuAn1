package com.example.duan1_personal_budgeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.database.DBHelper;
import com.example.duan1_personal_budgeting.dao.NguoiDungDAO;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText edtUser, edtNewPass, edtReNewPass;
    private Button btnConfirm, btnBack;
    private DBHelper dbHelper;

    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        dbHelper = new DBHelper(this);
        nguoiDungDAO = new NguoiDungDAO(this);

        edtUser = findViewById(R.id.edtUser);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtReNewPass = findViewById(R.id.edtReNewPass);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUser.getText().toString();
                String newPassword = edtNewPass.getText().toString();
                String reNewPassword = edtReNewPass.getText().toString();

                if (username.isEmpty() || newPassword.isEmpty() || reNewPassword.isEmpty()) {
                    Toast.makeText(ForgotPassActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(reNewPassword)) {
                    Toast.makeText(ForgotPassActivity.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                } else {
                    if (nguoiDungDAO.updatePassword(username, newPassword)) {
                        Toast.makeText(ForgotPassActivity.this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ForgotPassActivity.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
            }
        });
    }
}