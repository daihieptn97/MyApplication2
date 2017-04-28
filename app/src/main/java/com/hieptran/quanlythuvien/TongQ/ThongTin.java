package com.hieptran.quanlythuvien.TongQ;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hieptran.quanlythuvien.R;

/**
 * Created by Hiep Tran on 4/23/2017.
 */

public class ThongTin extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongtin, container, false);
        return  view;
    }
}
