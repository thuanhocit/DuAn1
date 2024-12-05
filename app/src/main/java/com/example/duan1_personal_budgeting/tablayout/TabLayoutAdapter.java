package com.example.duan1_personal_budgeting.tablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_personal_budgeting.fragment.ChiTieuFragment;
import com.example.duan1_personal_budgeting.fragment.ThuNhapFragment;

public class TabLayoutAdapter extends FragmentStateAdapter {
    public static final String[] title = new String[]{"Chi tiêu","Thu nhập"};
    public TabLayoutAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new ChiTieuFragment();
        } else if (position == 1) {
            return new ThuNhapFragment();
        }
        return new ChiTieuFragment();
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
