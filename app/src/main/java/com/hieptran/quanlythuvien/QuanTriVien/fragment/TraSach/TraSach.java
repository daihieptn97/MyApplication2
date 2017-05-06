package com.hieptran.quanlythuvien.QuanTriVien.fragment.TraSach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach.SachMuon;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 4/25/2017.
 */

public class TraSach extends Fragment {
    private String key_KhoMuonSach = "KhoMuonSach", key_KhoSach = "KhoSach", key_MaSach = "maSach", soLuong = "soLuong";
    private AutoCompleteTextView autoMaDG, autoMaSach;
    private ImageButton imgMaDG, imgMaSach;
    private Button btn_Done;
    private DatabaseReference mDatabse;
    private List<String> listMaSach, listMaDG;
    private String maSach, maDG;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trasach, container, false);
        AnhXa(view);

        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraDoDaiMa()) {
                    maSach = autoMaSach.getText().toString().trim();
                    maDG = autoMaDG.getText().toString().trim();
                    KiemTraTrung();
                }
            }
        });
        return view;
    }



    private void KiemTraTrung() {
        mDatabse.child(key_KhoMuonSach).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SachMuon sachMuon = dataSnapshot.getValue(SachMuon.class);
                listMaSach.add(sachMuon.getMaSach());
                listMaDG.add(sachMuon.getMaDocGia());

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

        mDatabse.child(key_KhoMuonSach).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toasty.success(getContext(), "Tải xong dữ liệu", Toast.LENGTH_SHORT).show();
                int count = listMaSach.size();
                for (int positon = 0; positon < listMaSach.size(); positon++) {
                    if (listMaSach.get(positon).equals(maSach) && listMaDG.get(positon).equals(maDG)){
                        mDone(maSach, maDG);
                       // Toasty.warning(getContext(), "Mã này chưa tồn tại trong kho", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void mDone(String maSach, String maDG) {

    }


    private boolean kiemTraDoDaiMa() {
        if (autoMaDG.length() > 0 && autoMaSach.length() > 0) return true;
        else {
            Toasty.warning(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void AnhXa(View view) {
        listMaDG = new ArrayList();
        listMaSach = new ArrayList();
        mDatabse = FirebaseDatabase.getInstance().getReference();
        autoMaDG = (AutoCompleteTextView) view.findViewById(R.id.auto_Tra_MaDG);
        autoMaSach = (AutoCompleteTextView) view.findViewById(R.id.auto_Tra_Masach);
        imgMaDG = (ImageButton) view.findViewById(R.id.img_Tra_MaDG);
        imgMaSach = (ImageButton) view.findViewById(R.id.img_Tra_MaSach);
        btn_Done = (Button) view.findViewById(R.id.btn_Tra_Done);
    }
}
