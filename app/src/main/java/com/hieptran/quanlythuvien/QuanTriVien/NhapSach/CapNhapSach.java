package com.hieptran.quanlythuvien.QuanTriVien.NhapSach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hieptran.quanlythuvien.R;

/**
 * Created by Hiep Tran on 5/2/2017.
 */

public class CapNhapSach extends Fragment {

    private EditText edMaSach, edSoLuong, edTHeLoai;
    private Button btnDone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.capnhap_sach, container, false);
        AnhXa(view);
        return view;
    }

    private void AnhXa(View view) {

    }
}
