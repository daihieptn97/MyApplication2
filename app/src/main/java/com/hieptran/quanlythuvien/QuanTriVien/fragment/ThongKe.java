package com.hieptran.quanlythuvien.QuanTriVien.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;

/**
 * Created by hieptran on 5/11/2017.
 */

public class ThongKe extends Fragment {
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_frangmet, container, false);
        anhXa(view);

        return view;
    }

    private void ve() {

    }

    private ArrayList<String> mCotX() {
        ArrayList<String> mCot = new ArrayList<>();
        for (int i = 1;  i  <=12; i++){
            mCot.add("tháng" + i);
        }
        return mCot;
    }

    private void anhXa(View view) {
        barChart = (BarChart) view.findViewById(R.id.barchar);
    }
}
