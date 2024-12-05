package com.example.duan1_personal_budgeting.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.database.DBHelper;
import com.example.duan1_personal_budgeting.dao.NguoiDungDAO;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUser, edtEmail, edtPass, edtRePass;
    private Button btnRegister, btnCancel;
    private DBHelper dbHelper;
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);
        nguoiDungDAO = new NguoiDungDAO(this);

        edtUser = findViewById(R.id.edtUser);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtRePass = findViewById(R.id.edtRePass);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUser.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString();
                String rePassword = edtRePass.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                } else if (nguoiDungDAO.checkAccount(username)) {
                    Toast.makeText(RegisterActivity.this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                } else if (!nguoiDungDAO.regexEmail(email)){
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                } else if (nguoiDungDAO.checkEmailExists(email)) {
                    Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    if (nguoiDungDAO.addUser(username, email, password)) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}