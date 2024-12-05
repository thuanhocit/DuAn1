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
import com.example.duan1_personal_budgeting.dao.TaiKhoanDAO;
import com.example.duan1_personal_budgeting.model.DanhMuc;
import com.example.duan1_personal_budgeting.model.TaiKhoan;

import java.util.ArrayList;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder>{

    private Context context;
    private ArrayList<TaiKhoan> list;

    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanAdapter(Context context, ArrayList<TaiKhoan> list, TaiKhoanDAO taiKhoanDAO) {
        this.context = context;
        this.list = list;
        this.taiKhoanDAO = taiKhoanDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_tai_khoan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenTK.setText(list.get(position).getTenTaiKhoan());
        holder.txtSoDu.setText(String.valueOf(list.get(position).getSoDu()));

        holder.ivEditTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditTK(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDeleteTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteTK(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenTK, txtSoDu;
        ImageView ivEditTK, ivDeleteTK;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenTK = itemView.findViewById(R.id.txtTenTK);
            txtSoDu = itemView.findViewById(R.id.txtSoDu);
            ivEditTK = itemView.findViewById(R.id.ivEditTK);
            ivDeleteTK = itemView.findViewById(R.id.ivDeleteTK);

        }
    }

    public void showDialogEditTK(TaiKhoan obj){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_tai_khoan, null, false);
        builder.setView(view);
        builder.setIcon(R.drawable.baseline_edit_square_24);
        builder.setTitle("Sửa tài khoản");

        // ánh xạ
        EditText edtTenTK = view.findViewById(R.id.edtTenTK);
        EditText edtSoDuTK = view.findViewById(R.id.edtSoDU);

        edtTenTK.setText(obj.getTenTaiKhoan());
        edtSoDuTK.setText(String.valueOf(obj.getSoDu()));

        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenDM = edtTenTK.getText().toString();
                int soDuTK = Integer.parseInt(edtSoDuTK.getText().toString());
                int id = obj.getTaiKhoanID();

                // check
                if (tenDM.isEmpty() || soDuTK < 0){
                    Toast.makeText(context, "Yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = taiKhoanDAO.updateTK(id,tenDM, soDuTK);
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

    public void showDialogDeleteTK(TaiKhoan obj){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setMessage(R.string.deleteDM);
        builder.setIcon(R.drawable.baseline_assignment_late_24);
        builder.setTitle("Xoá tài khoản");


        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                long check = taiKhoanDAO.xoaTaiKhoan(obj.getTaiKhoanID());
                if (check == 1) {
                    loadData();
                    Toast.makeText(context, "Xoá danh mục thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Không thể xoá tài khoản còn tồn tại trong giao dịch", Toast.LENGTH_SHORT).show();
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
        list = taiKhoanDAO.getTaiKhoan();
        notifyDataSetChanged();
    }
}
