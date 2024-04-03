package com.example.edith.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.edith.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the view with the layout;
        View view = inflater.inflate(R.layout.activity_main_bottom_sheet_layout, container, false);
        // Configure UI elements
        return view;
    }
}
