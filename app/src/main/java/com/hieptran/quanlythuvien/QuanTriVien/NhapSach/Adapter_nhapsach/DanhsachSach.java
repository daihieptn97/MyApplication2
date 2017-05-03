package com.hieptran.quanlythuvien.QuanTriVien.NhapSach.Adapter_nhapsach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;

/**
 * Created by Hiep Tran on 5/3/2017.
 */

public class DanhsachSach extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private DatabaseReference mDatabase;
    private ArrayList<SachNhap> sachNhapList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danhsach_sach, container, false);
        anhXa(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadData();
        return view;
    }

    private final String key_KhoSach = "KhoSach";

    private void loadData() {
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
                Adapter_DanhSach adapter_danhSach = new Adapter_DanhSach(sachNhapList, getContext().getApplicationContext());
                recyclerView.setAdapter(adapter_danhSach);
                adapter_danhSach.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void anhXa(View view) {
        sachNhapList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_danhsach_sach);
        recyclerView.setHasFixedSize(true); // can chinh kich thuoc phu hop voi app
        searchView = (SearchView) view.findViewById(R.id.search_sach);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
