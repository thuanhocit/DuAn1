package com.example.duan1_personal_budgeting.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.dao.ChiTieuDAO;
import com.example.duan1_personal_budgeting.dao.DanhMucDAO;
import com.example.duan1_personal_budgeting.dao.GiaoDichDAO;
import com.example.duan1_personal_budgeting.dao.TaiKhoanDAO;
import com.example.duan1_personal_budgeting.dao.ThuNhapDAO;
import com.example.duan1_personal_budgeting.model.GiaoDich;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThuNhapAdapter extends RecyclerView.Adapter<ThuNhapAdapter.ViewHolder>{

    private Context context;
    private GiaoDichDAO giaoDichDAO;
    private ThuNhapDAO thuNhapDAO;
    private TaiKhoanDAO taiKhoanDAO;

    DanhMucDAO danhMucDAO;
    private List<Map<String, String>> danhSachGiaoDichTN;

    public ThuNhapAdapter(Context context, GiaoDichDAO giaoDichDAO, List<Map<String, String>> danhSachGiaoDichTN, ThuNhapDAO thuNhapDAO) {
        this.context = context;
        this.giaoDichDAO = giaoDichDAO;
        this.danhSachGiaoDichTN = danhSachGiaoDichTN;
        this.thuNhapDAO = thuNhapDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thu_nhap, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Map<String, String> giaoDich = danhSachGiaoDichTN.get(position);

        holder.txtLoaiGD.setText("Loại giao dịch: "+giaoDich.get("loai"));
        holder.txtNgayTN.setText("Ngày tháng: "+giaoDich.get("ngayThang"));
        holder.txtDanhMucTN.setText("Danh mục: "+giaoDich.get("tenDanhMuc"));
        holder.txtTkTN.setText("Tài khoản: "+giaoDich.get("tenTaiKhoan"));
        holder.txtTienTN.setText("Số tiền: "+giaoDich.get("soTien"));
        holder.txtMoTaTN.setText("Mô tả: "+giaoDich.get("moTa"));

        holder.ivEditTN.setOnClickListener(v -> {
            // Tạo đối tượng GiaoDich từ dữ liệu
            GiaoDich giaoDichObj = new GiaoDich();
            giaoDichObj.setGiaoDichID(Integer.parseInt(giaoDich.get("giaoDichID")));
            giaoDichObj.setSoTien(Integer.parseInt(giaoDich.get("soTien")));
            giaoDichObj.setNgayThang(giaoDich.get("ngayThang"));
            giaoDichObj.setMoTa(giaoDich.get("moTa"));
            if (giaoDich.get("danhMuc_id") != null){
                giaoDichObj.setDanhMuc_id(Integer.parseInt(giaoDich.get("danhMuc_id")));
                Log.i("/===== ", "Danh muc id"+ giaoDich.get("danhMuc_id"));
            }


            if (giaoDich.get("taiKhoan_id") != null){
                giaoDichObj.setTaiKhoan_id(Integer.parseInt(giaoDich.get("taiKhoan_id")));
            }

            giaoDichObj.setLoai(giaoDich.get("loai"));

            // Hiển thị hộp thoại sửa
            showDialogEditTN(giaoDichObj);
        });

        holder.ivDeleteTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo đối tượng GiaoDich từ dữ liệu
                GiaoDich giaoDichObj = new GiaoDich();
                giaoDichObj.setGiaoDichID(Integer.parseInt(giaoDich.get("giaoDichID")));
                giaoDichObj.setLoai(giaoDich.get("loai"));
                giaoDichObj.setNgayThang(giaoDich.get("ngayThang"));
                giaoDichObj.setTenDanhMuc(giaoDich.get("tenDanhMuc"));
                giaoDichObj.setTenTaiKhoan(giaoDich.get("tenTaiKhoan"));
                giaoDichObj.setSoTien(Integer.parseInt(giaoDich.get("soTien")));
                giaoDichObj.setMoTa(giaoDich.get("moTa"));
                if (giaoDich.get("taiKhoan_id") != null){
                    giaoDichObj.setTaiKhoan_id(Integer.parseInt(giaoDich.get("taiKhoan_id")));
                    Log.i("/===== ", "Tai khoan id"+ giaoDich.get("taiKhoan_id"));
                }

                // Hiển thị dialog để xác nhận xoá
                showDialogDeleteTN(giaoDichObj);
            }
        });
    }



    @Override
    public int getItemCount() {
        return danhSachGiaoDichTN.size();
    }

    public void showDialogDeleteTN(GiaoDich giaoDich) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setMessage(R.string.deleteDM);
        builder.setIcon(R.drawable.baseline_assignment_late_24);
        builder.setTitle("Xoá giao dịch thu nhập");

        int tienCu = giaoDich.getSoTien();

        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                thuNhapDAO = new ThuNhapDAO(context);
                int tienHienTai = thuNhapDAO.getSoDuTaiKhoan(giaoDich.getTaiKhoan_id());
                int reTien = tienHienTai - tienCu;
                thuNhapDAO.updateSoDu(giaoDich.getTaiKhoan_id(), reTien);

                if (reTien == 0){
                    Log.i("//=====Tien cu", "không có giữ liệu");
                } else {
                    Log.i("//=====Tien cu", "Tien cu = " + tienCu + " Tiền hiện tại " +tienHienTai+ "Tiền trả lại" + reTien);
                }

                boolean check = giaoDichDAO.deleteGDCT(giaoDich.getGiaoDichID(), giaoDich.getDanhMuc_id(), giaoDich.getTaiKhoan_id());
                if (check) {
                    loadData();
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


        builder.setNegativeButton("Thoát", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void showDialogEditTN(GiaoDich giaoDich) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_thu_nhap, null, false);

        // Ánh xạ view
        Spinner spnDM = view.findViewById(R.id.spnDMTN);
        Spinner spnTK = view.findViewById(R.id.spnTKTN);
        EditText edtSoTien = view.findViewById(R.id.edtSoTienTN);
        EditText edNgay = view.findViewById(R.id.edNgayTN);
        EditText edMoTa = view.findViewById(R.id.edMoTaTN);

        // Lấy DAO
        danhMucDAO = new DanhMucDAO(context);
        taiKhoanDAO = new TaiKhoanDAO(context);

        // Lấy danh sách danh mục và tài khoản
        List<HashMap<String, Object>> danhMucList = danhMucDAO.getDanhMucList();
        List<HashMap<String, Object>> taiKhoanList = taiKhoanDAO.getTaiKhoanList();

        // Hiển thị danh sách trong Spinner
        setupDanhMucSpinner(spnDM, danhMucList);
        setupTaiKhoanSpinner(spnTK, taiKhoanList);

        // Hiển thị dữ liệu hiện tại trong giao dịch
        edtSoTien.setText(String.valueOf(giaoDich.getSoTien()));
        edNgay.setText(giaoDich.getNgayThang());
        edMoTa.setText(giaoDich.getMoTa());

        // Lấy số tiền cũ từ giao dịch
        int tienCu = Integer.parseInt(edtSoTien.getText().toString().trim());


        // Đặt danh mục và tài khoản hiện tại
        spnDM.setSelection(getDanhMucPosition(danhMucList, giaoDich.getDanhMuc_id()));
        spnTK.setSelection(getTaiKhoanPosition(taiKhoanList, giaoDich.getTaiKhoan_id()));

        // DatePicker cho ngày tháng
        edNgay.setOnClickListener(v -> hienThiDatePickerDialog(edNgay));

        builder.setView(view);
        builder.setIcon(R.drawable.clipboard);
        builder.setTitle("Chỉnh sửa giao dịch");

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            try {
                // Lấy dữ liệu từ EditText
                int tienMoi = Integer.parseInt(edtSoTien.getText().toString().trim());
                String ngayThang = edNgay.getText().toString().trim();
                String moTa = edMoTa.getText().toString().trim();

                // Lấy thông tin danh mục
                int danhMucID = (int) danhMucList.get(spnDM.getSelectedItemPosition()).get("danhMucID");

                // Lấy thông tin tài khoản mới
                int taiKhoanMoiID = (int) taiKhoanList.get(spnTK.getSelectedItemPosition()).get("taiKhoanID");

                // Lấy thông tin tài khoản cũ từ giao dịch
                int taiKhoanCuID = giaoDich.getTaiKhoan_id();

                // Xác định loại giao dịch từ danh mục
                String loai = (String) danhMucList.get(spnDM.getSelectedItemPosition()).get("loaiDanhMuc");

                // Lấy số dư tài khoản cũ
                thuNhapDAO = new ThuNhapDAO(context);
                int soDuCuTaiKhoan = thuNhapDAO.getSoDuTaiKhoan(taiKhoanCuID);

                // Hoàn trả số tiền cũ vào tài khoản cũ
                int soDuMoiTaiKhoanCu = soDuCuTaiKhoan - giaoDich.getSoTien();
                boolean updateCuSuccess = thuNhapDAO.updateSoDu(taiKhoanCuID, soDuMoiTaiKhoanCu);
                if (!updateCuSuccess) {
                    Toast.makeText(context, "Cập nhật tài khoản cũ thất bại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Nếu tài khoản mới khác tài khoản cũ, xử lý tiếp
                if (taiKhoanMoiID != taiKhoanCuID) {
                    // Lấy số dư tài khoản mới
                    int soDuMoiTaiKhoan = thuNhapDAO.getSoDuTaiKhoan(taiKhoanMoiID);

                    // Tính toán số dư mới cho tài khoản mới
                    int soDuMoiTaiKhoanMoi = soDuMoiTaiKhoan - tienMoi;
                    boolean updateMoiSuccess = thuNhapDAO.updateSoDu(taiKhoanMoiID, soDuMoiTaiKhoanMoi);
                    if (!updateMoiSuccess) {
                        Toast.makeText(context, "Cập nhật tài khoản mới thất bại!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Cập nhật giao dịch với tài khoản mới và số tiền mới
                boolean check = giaoDichDAO.updateGiaoDich(
                        giaoDich.getGiaoDichID(), tienMoi, ngayThang, moTa, danhMucID, taiKhoanMoiID, loai);

                if (check) {
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    loadData(); // Làm mới danh sách giao dịch
                } else {
                    Toast.makeText(context, "Cập nhật giao dịch thất bại!", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Log.e("NumberFormatException", "Lỗi định dạng số: " + e.getMessage());
                Toast.makeText(context, "Số tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });




        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    private int getDanhMucPosition(List<HashMap<String, Object>> danhMucList, int danhMucID) {
        for (int i = 0; i < danhMucList.size(); i++) {
            if ((int) danhMucList.get(i).get("danhMucID") == danhMucID) {
                return i;
            }
        }
        return 0;
    }

    private int getTaiKhoanPosition(List<HashMap<String, Object>> taiKhoanList, int taiKhoanID) {
        for (int i = 0; i < taiKhoanList.size(); i++) {
            if ((int) taiKhoanList.get(i).get("taiKhoanID") == taiKhoanID) {
                return i;
            }
        }
        return 0;
    }

    private void hienThiDatePickerDialog(EditText edNgay) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String ngayDaChon = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDay);
                    edNgay.setText(ngayDaChon);
                },
                year, month, day);

        datePickerDialog.show();
    }

    // Hàm hiển thị danh mục trong Spinner
    private void setupDanhMucSpinner(Spinner spinnerDanhMuc, List<HashMap<String, Object>> danhMucList) {
        // Tạo danh sách chỉ chứa tên danh mục
        List<String> tenDanhMucList = new ArrayList<>();
        for (HashMap<String, Object> danhMuc : danhMucList) {
            tenDanhMucList.add((String) danhMuc.get("tenDanhMuc"));
        }

        // Gắn danh sách tên danh mục vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, tenDanhMucList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDanhMuc.setAdapter(adapter);

    }


    // Hàm hiển thị tài khoản trong Spinner
    private void setupTaiKhoanSpinner(Spinner spinnerTaiKhoan, List<HashMap<String, Object>> taiKhoanList) {
        // Tạo danh sách chỉ chứa tên tài khoản
        List<String> tenTaiKhoanList = new ArrayList<>();
        for (HashMap<String, Object> taiKhoan : taiKhoanList) {
            tenTaiKhoanList.add((String) taiKhoan.get("tenTaiKhoan"));
        }

        // Gắn danh sách tên tài khoản vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, tenTaiKhoanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaiKhoan.setAdapter(adapter);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtLoaiGD, txtNgayTN, txtDanhMucTN, txtTkTN, txtTienTN, txtMoTaTN;
        ImageView ivEditTN, ivDeleteTN;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLoaiGD = itemView.findViewById(R.id.txtLoaiGD);
            txtNgayTN = itemView.findViewById(R.id.txtNgayTN);
            txtDanhMucTN = itemView.findViewById(R.id.txtDanhMucTN);
            txtTkTN = itemView.findViewById(R.id.txtTkTN);
            txtTienTN = itemView.findViewById(R.id.txtTienTN);
            txtMoTaTN = itemView.findViewById(R.id.txtMoTaTN);

            ivEditTN = itemView.findViewById(R.id.ivEditTN);
            ivDeleteTN = itemView.findViewById(R.id.ivDeleteTN);
        }
    }

    public void loadData(){
        danhSachGiaoDichTN.clear();
        danhSachGiaoDichTN = giaoDichDAO.getThuNhap();
        notifyDataSetChanged();
    }
}
