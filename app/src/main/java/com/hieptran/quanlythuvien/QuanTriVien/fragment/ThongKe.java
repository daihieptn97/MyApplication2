package com.hieptran.quanlythuvien.QuanTriVien.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach.SachMuon;
import com.hieptran.quanlythuvien.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hieptran on 5/11/2017.
 */

public class ThongKe extends Fragment {
    private BarChart barChart;
    private ArrayList<Date> dsDate;
    private DatabaseReference mDatabase;
    private String key_KhoMuonSach = "KhoMuonSach", key_KhoSach = "KhoSach", key_MaSach = "maSach", soLuong = "soLuong";
    private ArrayList<Float> dsTongSoLuotMuon;
    private int mTong = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_frangmet, container, false);
        anhXa(view);

        return view;
    }

    private Date mDauThangHienTai() {
        Date date = new Date();
        date.setDate(1);
        return date;
    }

    private Date mCuoiThangHienTai() {
        Date date = new Date();
        date.setDate(30);
        return date;
    }

    private void loadDataDeVe() {
        mDatabase.child(key_KhoMuonSach).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SachMuon sachMuon = snapshot.getValue(SachMuon.class);
                    Date ngaymuon = new Date(sachMuon.getNgayMuon());
                    if (ngaymuon.after(mDauThangHienTai()) && ngaymuon.before(mCuoiThangHienTai())) {
                        mTong++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void ve() {
            
    }

    private ArrayList<String> mCotThang_X() {
        ArrayList<String> mCot = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            mCot.add("tháng " + i);
        }
        return mCot;
    }

    private void anhXa(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        barChart = (BarChart) view.findViewById(R.id.barchar);
    }
}
