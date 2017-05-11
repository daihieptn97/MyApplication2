package com.hieptran.quanlythuvien.DocGia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.Adapter_nhapsach.Adapter_DanhSach;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach.MuonSach;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach.SachMuon;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia.DocGia;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieptran on 5/7/2017.
 */

public class ThongTinMuonSachDG extends Fragment {
    private ListView lv_danhsachMuon;
    private SwipeRefreshLayout swipeRefreshLayoutMuon;
    private DatabaseReference mDatabase;
    private final String key_KhoSach = "KhoSach", key_MaSach = "maSach", keyKhoSachMuon = "KhoMuonSach", keyMaDG = "maDocGia";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongtinmuonsach_docgia, container, false);
        anhXa(view);
        loadData();
        return view;
    }

//    private String mTenDangNhap() {
//        Intent intent = getActivity().getIntent();
//        String a =  intent.getStringExtra("email");
//        return a;
//    }

    private final String tenDangNhap = "";

    private void loadData() {
      //  loadDsMuon();
    }

    private List<String> listMaSachMuon = new ArrayList<>();

    private void loadDsMuon() {

        Query query = mDatabase.child(keyKhoSachMuon).orderByChild(keyMaDG).equalTo(tenDangNhap);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SachMuon muonSach = snapshot.getValue(SachMuon.class);
                    if (tenDangNhap.equals(muonSach.getMaDocGia()) && muonSach.isHienTrangMuon()) {
                        listMaSachMuon.add(muonSach.getMaSach());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void anhXa(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lv_danhsachMuon = (ListView) view.findViewById(R.id.lv_DanhsachMuonDG);
        swipeRefreshLayoutMuon = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_DGtracusach);
    }
}
