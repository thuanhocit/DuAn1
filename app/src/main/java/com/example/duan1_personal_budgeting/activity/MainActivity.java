package com.example.duan1_personal_budgeting.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.dao.NguoiDungDAO;
import com.example.duan1_personal_budgeting.database.DemoDB;
import com.example.duan1_personal_budgeting.fragment.DanhMucFragment;
import com.example.duan1_personal_budgeting.fragment.GiaoDichFragment;
import com.example.duan1_personal_budgeting.fragment.HomeFragment;
import com.example.duan1_personal_budgeting.fragment.LichSuFragment;
import com.example.duan1_personal_budgeting.fragment.MucTieuFragment;
import com.example.duan1_personal_budgeting.fragment.TaiKhoanFragment;
import com.example.duan1_personal_budgeting.fragment.ThongKeThuChiFragment;
import com.example.duan1_personal_budgeting.fragment.ThongTinCaNhanFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView txtEmailContact;
    NguoiDungDAO nguoiDungDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // setting icon

        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        NavigationView navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        View viewHeaderLayout = navigationView.getHeaderView(0);
        nguoiDungDAO = new NguoiDungDAO(this);
        txtEmailContact = viewHeaderLayout.findViewById(R.id.txtEmail);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Trang chủ");
        actionBar.setHomeAsUpIndicator(R.drawable.menu_regular);

        // Lấy tên đăng nhập từ Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (username != null) {
            txtEmailContact.setText(username);
        }

        if (savedInstanceState == null) {
            // Thay thế fragment mặc định vào layout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DefaultFragment())
                    .commit();
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;


                if (menuItem.getItemId() == R.id.nav_home){
                    fragment = new HomeFragment();
                    actionBar.setTitle("Trang chủ");
                } else if (menuItem.getItemId() == R.id.nav_danhMuc) {
                    fragment = new DanhMucFragment();
                    actionBar.setTitle("Quản lý danh mục");
                } else if (menuItem.getItemId() == R.id.nav_taiKhoan){
                    fragment = new TaiKhoanFragment();
                    actionBar.setTitle("Quản lý Tài khoản");
                } else if (menuItem.getItemId() == R.id.nav_giaoDich){
                    fragment = new GiaoDichFragment();
                    actionBar.setTitle("Quản lý giao dịch thu chi");
                } else if (menuItem.getItemId() == R.id.nav_TKTC){
                    fragment = new ThongKeThuChiFragment();
                    actionBar.setTitle("Thống kê thu chi");
                } else if (menuItem.getItemId() == R.id.nav_LichSu){
                    fragment = new LichSuFragment();
                    actionBar.setTitle("Lịch sử giao dịch");
                } else if (menuItem.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_thongTinCaNhan) {
                    fragment = new ThongTinCaNhanFragment();
                    actionBar.setTitle("Thông tin cá nhân");
                }


                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,fragment)
                            .commit();
                    setTitle(menuItem.getTitle());
                }
                //txtEmailContact.setText();


                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DefaultFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_home, container, false);
        }
    }
}