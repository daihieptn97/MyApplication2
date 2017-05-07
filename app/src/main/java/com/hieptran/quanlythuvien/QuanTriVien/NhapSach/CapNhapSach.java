package com.hieptran.quanlythuvien.QuanTriVien.NhapSach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.Scan;
import com.hieptran.quanlythuvien.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 5/2/2017.
 */

public class CapNhapSach extends Fragment {

    private EditText edMaSach, edSoLuong, edTheLoai;
    private Button btnDone;
    private ImageButton imgMaSach;

    private DatabaseReference mDatabase;
    private String key_KhoSach = "KhoSach", key_MaSach = "maSach", soLuong = "soLuong", key_TheLoai = "theLoai";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.capnhap_sach, container, false);
        AnhXa(view);
        imgMaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanMaSach();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempMaSach = edMaSach.getText().toString().trim();
                if (kiemTraMaSach()) {
                    tempSoLuong = edSoLuong.getText().toString().trim();
                    tempTheLoai = edTheLoai.getText().toString().trim();
                    capNhapThongTin();
                }
            }
        });

        return view;
    }

    private String tempMaSach, tempSoLuong, tempTheLoai; // để tránh việc suống hàm con không get được giá trị từ editText

    private void capNhapThongTin(){
        mDatabase.child(key_KhoSach).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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

        Query query = mDatabase.child(key_KhoSach).orderByChild(key_MaSach).equalTo(tempMaSach);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SachNhap sachNhap = snapshot.getValue(SachNhap.class);
                    if (kiemTraSoLuong()) {
                       // Log.d("AAAA", sachNhap.getSoLuong() + " : " + Integer.parseInt(tempSoLuong));
                        snapshot.getRef().child(soLuong).setValue(sachNhap.getSoLuong() + Integer.parseInt(tempSoLuong));
                        edSoLuong.setText("");
                        edMaSach.setText("");
                    }
                    if (kiemTraTheLoai()) {
                        edTheLoai.setText("");
                        edMaSach.setText("");
                        snapshot.getRef().child(key_TheLoai).setValue(tempTheLoai);

                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), (CharSequence) databaseError.toException(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean kiemTraSoLuong() {
        if (edSoLuong.length() > 0 && Integer.parseInt(edSoLuong.getText().toString().trim()) > 0)
            return true;
        else return false;
    }

    private boolean kiemTraTheLoai() {
        if (edTheLoai.length() > 0) return true;
        else return false;
    }

    private boolean kiemTraMaSach() {
        if (edMaSach.length() <= 0) {
            Toasty.warning(getContext(), "Bạn hãy quét mã , hoặc nhập mã để cập nhập thông tin sách", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            String maSach = data.getStringExtra(Scan.MA_THE);
            edMaSach.setText(maSach);
        }
    }

    private void scanMaSach() {
        Intent intent = new Intent(getActivity(), Scan.class);
        startActivityForResult(intent, 0);
    }

    private void AnhXa(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edMaSach = (EditText) view.findViewById(R.id.ed_CapNhapMasach);
        edSoLuong = (EditText) view.findViewById(R.id.ed_CapNhapSoLuong);
        edTheLoai = (EditText) view.findViewById(R.id.ed_capnhapTheLoai);
        btnDone = (Button) view.findViewById(R.id.btn_capnhapDone);
        imgMaSach = (ImageButton) view.findViewById(R.id.imageButton_capnhapMaSach);
    }


}
