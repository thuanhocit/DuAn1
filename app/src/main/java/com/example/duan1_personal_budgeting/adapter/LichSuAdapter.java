package com.example.duan1_personal_budgeting.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.dao.GiaoDichDAO;
import com.example.duan1_personal_budgeting.model.GiaoDich;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.ViewHolder>{

    private Context context;
    private List<Map<String, String>> danhSachGiaoDichGD;
    private List<Map<String, String>> danhSachGiaoDichFull; // Danh sách đầy đủ
    private GiaoDichDAO giaoDichDAO;

    public LichSuAdapter(Context context, List<Map<String, String>> danhSachGiaoDich, GiaoDichDAO giaoDichDAO) {
        this.context = context;
        this.danhSachGiaoDichGD = danhSachGiaoDich;
        this.giaoDichDAO = giaoDichDAO;
        this.danhSachGiaoDichFull = new ArrayList<>(danhSachGiaoDich); // Sao chép danh sách đầy đủ
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_lich_su_giao_dich, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, String> giaoDich = danhSachGiaoDichGD.get(position);

        holder.txtLoaiGD.setText("Loại giao dịch: "+giaoDich.get("loai"));
        holder.txtNgayGD.setText("Ngày tháng: "+giaoDich.get("ngayThang"));
        holder.txtDanhMucGD.setText("Danh mục: "+giaoDich.get("tenDanhMuc"));
        holder.txtTkGD.setText("Tài khoản: "+giaoDich.get("tenTaiKhoan"));
        holder.txtTienGD.setText("Số tiền: "+giaoDich.get("soTien"));
        holder.txtMoTaGD.setText("Mô tả: "+giaoDich.get("moTa"));

    }

    @Override
    public int getItemCount() {
        return danhSachGiaoDichGD.size();
    }

    public void filter(String keyword) {
        danhSachGiaoDichGD.clear(); // Xóa danh sách hiện tại

        if (keyword.isEmpty()) {
            danhSachGiaoDichGD.addAll(danhSachGiaoDichFull); // Nếu không có từ khóa, hiển thị tất cả
        } else {
            for (Map<String, String> giaoDich : danhSachGiaoDichFull) {
                // Tìm kiếm theo các trường: loại, danh mục, tài khoản, mô tả
                if (giaoDich.get("loai").toLowerCase().contains(keyword.toLowerCase()) ||
                        giaoDich.get("tenDanhMuc").toLowerCase().contains(keyword.toLowerCase()) ||
                        giaoDich.get("tenTaiKhoan").toLowerCase().contains(keyword.toLowerCase()) ||
                        giaoDich.get("moTa").toLowerCase().contains(keyword.toLowerCase())) {
                    danhSachGiaoDichGD.add(giaoDich); // Thêm giao dịch khớp từ khóa
                }
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtLoaiGD, txtNgayGD, txtDanhMucGD, txtTkGD, txtTienGD, txtMoTaGD;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLoaiGD = itemView.findViewById(R.id.txtLoaiGD);
            txtNgayGD = itemView.findViewById(R.id.txtNgayGD);
            txtDanhMucGD = itemView.findViewById(R.id.txtDanhMucGD);
            txtTkGD = itemView.findViewById(R.id.txtTkGD);
            txtTienGD = itemView.findViewById(R.id.txtTienGD);
            txtMoTaGD = itemView.findViewById(R.id.txtMoTaGD);
        }
    }

    public void loadData(){
        danhSachGiaoDichGD.clear();
        danhSachGiaoDichGD = giaoDichDAO.getAllGiaoDich();
        notifyDataSetChanged();
    }
}
