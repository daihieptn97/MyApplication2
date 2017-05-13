package com.hieptran.quanlythuvien.QuanTriVien.fragment.TongQuan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach.SachMuon;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia.DocGia;
import com.hieptran.quanlythuvien.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hiep Tran on 4/23/2017.
 */

public class TongQuan_NV extends Fragment {
    private TextView tv_TongSachDuocMuon;
    private ListView lv_SachSapHetHan;
    private DatabaseReference mDtabase;
    private String key_KhoMuonSach = "KhoMuonSach", key_KhoSach = "KhoSach", key_MaMuonSach = "maMuonSach", hienTrangMuon = "hienTrangMuon";
    private String keyKhoDG = "KhoDocGia";
    private int tongSachMuon = 0;
    private ArrayList<SachNhap> dsSach;
    private ArrayList<SachMuon> dsMuonSachTemp;
    private ArrayList<SachMuon> dsMuonSach;
    private ArrayList<DocGia> dsDocGia;
    private Adapter_SachHetHan adapterSachHetHan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nv_tongquan, container, false);
        getActivity().setTitle("Tổng Quan");
        anhXa(view);
        tongSachMuon();
        mDocGiaHetHanMuonSach();
        return view;
    }

    private String keyNgayTra = "ngayTra";

    private void tongSachMuon() {
        Query query = mDtabase.child(key_KhoMuonSach).orderByChild(hienTrangMuon).equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tongSachMuon++;
                }
                tv_TongSachDuocMuon.setText(tongSachMuon + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    int kemSoat = 0;

    private boolean getDateTra2Ngay(String datetemp) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_WEEK, 2);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String a = dateFormat.format(date);

        if (a.equals(datetemp)) {
            return true;
        } else return false;
    }

    private boolean getDateTra1Ngay(String datetemp) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_WEEK, 1);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String a = dateFormat.format(date);

        if (a.equals(datetemp)) {
            return true;
        } else return false;
    }

    private void mDocGiaHetHanMuonSach() {
        mDtabase.child(key_KhoMuonSach).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SachMuon sachMuon = dataSnapshot.getValue(SachMuon.class);
                dsMuonSachTemp.add(sachMuon);
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
        mDtabase.child(key_KhoMuonSach).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < dsMuonSachTemp.size(); i++) {
                    if ((getDateTra2Ngay(dsMuonSachTemp.get(i).getNgayTra()) || getDateTra1Ngay(dsMuonSachTemp.get(i).getNgayTra())) && dsMuonSachTemp.get(i).isHienTrangMuon()) {
                        dsMuonSach.add(dsMuonSachTemp.get(i));
                    }
                    adapterSachHetHan = new Adapter_SachHetHan(dsMuonSach, getContext(), R.layout.item_row_sachhethan);
                    lv_SachSapHetHan.setAdapter(adapterSachHetHan);
                    adapterSachHetHan.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void anhXa(View view) {
        dsDocGia = new ArrayList<>();
        dsMuonSachTemp = new ArrayList<>();
        dsSach = new ArrayList<>();
        dsMuonSach = new ArrayList<>();
        mDtabase = FirebaseDatabase.getInstance().getReference();
        tv_TongSachDuocMuon = (TextView) view.findViewById(R.id.tvsosachmuon);
        lv_SachSapHetHan = (ListView) view.findViewById(R.id.lv_sachsaphethan);

    }
}
