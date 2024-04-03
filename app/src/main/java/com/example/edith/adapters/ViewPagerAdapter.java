package com.example.edith.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.edith.fragments.calendarView;
import com.example.edith.fragments.todoList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){super(fragmentActivity);}

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new todoList();
            default:
                return new calendarView();
        }
    }

    @Override
    public int getItemCount(){
        return 2;
    }
}
