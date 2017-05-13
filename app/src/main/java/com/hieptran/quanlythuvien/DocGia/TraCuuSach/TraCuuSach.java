package com.hieptran.quanlythuvien.DocGia.TraCuuSach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.Adapter_nhapsach.Adapter_DanhSach;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * Created by hieptran on 5/7/2017.
 */

public class TraCuuSach extends Fragment {
    private TextView tv_CanhBaoHetHan;
    private ListView lv_danhsach;
    private SwipeRefreshLayout refreshLayout;
    private DatabaseReference mDatabase;
    private Adapter_DanhSach adapterDanhSach;
    private final String key_KhoSach = "KhoSach", key_MaSach = "maSach";
    private ArrayList<SachNhap> sachNhapList;
    private AVLoadingIndicatorView progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tracuusach_docgia, container, false);
        anhXa(view);
        progressBar.show();
        upLoadData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setColorSchemeResources(R.color.blue, R.color.purple, R.color.green, R.color.orange);
                sachNhapList.removeAll(sachNhapList);
                upLoadData();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void upLoadData() {
        mDatabase.child(key_KhoSach).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SachNhap sachNhap = dataSnapshot.getValue(SachNhap.class);
                sachNhapList.add(sachNhap);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child(key_KhoSach).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapterDanhSach = new Adapter_DanhSach(sachNhapList, getContext(), R.layout.adapter_recyclerview);
                lv_danhsach.setAdapter(adapterDanhSach);
                adapterDanhSach.notifyDataSetChanged();
                progressBar.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }




    private void anhXa(View view) {
        sachNhapList = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_DGtracusach);
        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progressBarDanhSach_DGTracuu);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tv_CanhBaoHetHan = (TextView) view.findViewById(R.id.tv_tracuusach_hethanmuon);
        lv_danhsach = (ListView) view.findViewById(R.id.lv_DGdanhsach_sach);
    }
}
