package com.example.duan1_personal_budgeting.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.database.DBHelper;
import com.example.duan1_personal_budgeting.dao.NguoiDungDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUser, edtPass;
    private CheckBox chkRemember;
    private TextView tvForgotPass;
    private Button btnLogin, btnRegister;
    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;

    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);
        nguoiDungDAO = new NguoiDungDAO(this);
        sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        chkRemember = findViewById(R.id.chkRemember);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // kiểm tra thông tin đăng nhập đã được lưu chưa
        if (sharedPreferences.getBoolean("rememberMe", false)) {
            edtUser.setText(sharedPreferences.getString("username", ""));
            edtPass.setText(sharedPreferences.getString("password", ""));
            chkRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUser.getText().toString();
                String password = edtPass.getText().toString();

                if (nguoiDungDAO.checkUser(username, password)) {
                    if (chkRemember.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.putBoolean("rememberMe", true);
                        editor.apply();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                    }
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}