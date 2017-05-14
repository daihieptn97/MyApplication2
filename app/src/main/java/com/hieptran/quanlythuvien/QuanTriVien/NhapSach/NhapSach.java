package com.hieptran.quanlythuvien.QuanTriVien.NhapSach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.Scan.Scan;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 4/23/2017.
 */

public class NhapSach extends Fragment {
    private EditText ed_maSach, ed_TheLoai, ed_soLuong, ed_TenSach;
    private Button btn_Done;
    private DatabaseReference mDatabase;
    private ImageButton img_maSach;
    public static final String KeyKhoSach = "KhoSach";
    private String tempMaSach;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nhapsach, container, false);
        AnhXa(view);
        //getActivity().setTitle("Nhập Sách");
        lisMaSach = new ArrayList<>();
        Done();


        img_maSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaSach_Scan();
            }
        });
        // getActivity().setTitle(R.string.nhap_sach_title);
        return view;
    }

    private void Done() {
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempMaSach = ed_maSach.getText().toString().trim();
                checkMasach();
            }
        });
    }


    private void PutDataUpDatabase() {
        if (ed_maSach.length() > 0 && ed_TheLoai.length() > 0 && ed_soLuong.length() > 0 && ed_TenSach.length() > 0) {
            SachNhap sachNhap = new SachNhap();
            sachNhap.setMaSach(ed_maSach.getText().toString().trim());
            sachNhap.setTheLoaiSach(ed_TheLoai.getText().toString().trim());
            sachNhap.setTenSach(ed_TenSach.getText().toString().trim());
            sachNhap.setSoLuong(Integer.parseInt(ed_soLuong.getText().toString().trim()));
            mDatabase.child(KeyKhoSach).push().setValue(sachNhap);

            ed_TenSach.setText("");
            ed_soLuong.setText("");
            ed_TheLoai.setText("");
            ed_maSach.setText("");
            Toasty.success(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT, true).show();
            lisMaSach.removeAll(lisMaSach);
        } else
            Toasty.warning(getContext(), "Bạn chưa nhập đủ Thông tin", Toast.LENGTH_SHORT).show();


    }

    private List<String> lisMaSach;

    private void checkMasach() {
        mDatabase.child(KeyKhoSach).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SachNhap sachNhap = dataSnapshot.getValue(SachNhap.class);
                lisMaSach.add(sachNhap.getMaSach());
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
        mDatabase.child(KeyKhoSach).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = lisMaSach.size();
                for (int i = 0; i < lisMaSach.size(); i++) {
                    if (tempMaSach.equals(lisMaSach.get(i))) {
                        count--;
                    }
                }

                if (count == lisMaSach.size()) {
                    PutDataUpDatabase();
                } else {
                    Toasty.warning(getContext(), "Mã sách đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void MaSach_Scan() {
        Intent intent = new Intent(getActivity(), Scan.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String maThe = data.getStringExtra(Scan.MA_THE);
            ed_maSach.setText(maThe);
        }
    }

    private void AnhXa(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ed_maSach = (EditText) view.findViewById(R.id.ed_nhap_masach);
        ed_TheLoai = (EditText) view.findViewById(R.id.ed_nhap_theloaisach);
        ed_soLuong = (EditText) view.findViewById(R.id.ed_nhap_soluong);
        btn_Done = (Button) view.findViewById(R.id.btn_nhap_Done);
        ed_TenSach = (EditText) view.findViewById(R.id.ed_nhap_TenSach);
        img_maSach = (ImageButton) view.findViewById(R.id.img_nhap_MaSach);

    }


}


