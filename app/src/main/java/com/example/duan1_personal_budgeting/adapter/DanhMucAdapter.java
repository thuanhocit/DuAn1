package com.example.duan1_personal_budgeting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.dao.DanhMucDAO;
import com.example.duan1_personal_budgeting.model.DanhMuc;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder>{

    private Context context;
    private ArrayList<DanhMuc> list;

    private DanhMucDAO danhMucDAO;



    public DanhMucAdapter(Context context, ArrayList<DanhMuc> list, DanhMucDAO danhMucDAO) {
        this.context = context;
        this.list = list;
        this.danhMucDAO = danhMucDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danh_muc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenDM.setText("Tên danh mục: "+list.get(position).getTenDanhMuc());
        holder.txtLoaiDM.setText("Tên loại: " + list.get(position).getLoaiDanhMuc());


        holder.ivEditDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditDM(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDeleteDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteDM(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenDM, txtLoaiDM;
        ImageView ivEditDM, ivDeleteDM;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenDM = itemView.findViewById(R.id.txtTenDM);
            txtLoaiDM = itemView.findViewById(R.id.txtLoaiDM);
            ivEditDM = itemView.findViewById(R.id.ivEditDM);
            ivDeleteDM = itemView.findViewById(R.id.ivDeleteDM);
        }
    }

    public void showDialogEditDM(DanhMuc obj){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_danh_muc, null, false);
        builder.setView(view);
        builder.setIcon(R.drawable.baseline_edit_square_24);
        builder.setTitle("Sửa danh mục");

        // ánh xạ
        EditText edtTenDM = view.findViewById(R.id.edtTenDM);
        EditText edtLoaiDM = view.findViewById(R.id.edtLoaiDM);

        edtTenDM.setText(obj.getTenDanhMuc());
        edtLoaiDM.setText(obj.getLoaiDanhMuc());

        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenDM = edtTenDM.getText().toString();
                String loaiDM = edtLoaiDM.getText().toString();
                int id = obj.getDanhMucID();

                // check
                if (tenDM.isEmpty() || loaiDM.isEmpty()){
                    Toast.makeText(context, "Yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = danhMucDAO.updateDM(id,tenDM, loaiDM);
                    if (check) {
                        loadData();
                        Toast.makeText(context, "Sửa danh mục thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa danh mục thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void showDialogDeleteDM(DanhMuc obj){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setMessage(R.string.deleteDM);
        builder.setIcon(R.drawable.baseline_assignment_late_24);
        builder.setTitle("Xoá danh mục");


        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                long check = danhMucDAO.deleteDM(obj.getDanhMucID());
                if (check == 1) {
                    loadData();
                    Toast.makeText(context, "Xoá danh mục thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Không thể xoá danh mục còn tồn tại trong giao dịch", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void loadData(){
        list.clear();
        list = danhMucDAO.getDanhMuc();
        notifyDataSetChanged();
    }
}
