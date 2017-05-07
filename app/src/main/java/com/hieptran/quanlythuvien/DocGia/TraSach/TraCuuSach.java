package com.hieptran.quanlythuvien.DocGia.TraSach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hieptran.quanlythuvien.R;

/**
 * Created by hieptran on 5/7/2017.
 */

public class TraCuuSach extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trasach_docgia, container, false);
        return view;
    }
}
